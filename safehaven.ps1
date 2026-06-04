<#
.SYNOPSIS
    SafeHaven backend dev script.

.DESCRIPTION
    Manages Docker DB + Gradle tasks in one command.

.PARAMETER Mode
    run    - levanta la BD y arranca la app (default)
    test   - levanta la BD y corre los tests
    reset  - borra el volumen de Postgres, levanta la BD limpia y arranca la app
    db     - solo levanta/verifica la BD (sin gradle)
    stop   - detiene los contenedores Docker

.EXAMPLE
    .\safehaven.ps1          # modo run (default)
    .\safehaven.ps1 run
    .\safehaven.ps1 test
    .\safehaven.ps1 reset
    .\safehaven.ps1 stop
#>

param(
    [ValidateSet("run", "test", "reset", "db", "stop")]
    [string]$Mode = "run"
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$PROJECT_ROOT = $PSScriptRoot
$GRADLEW      = Join-Path $PROJECT_ROOT "gradlew.bat"
$DB_PORT      = 5433
$DB_HOST      = "localhost"
$APP_PORT     = 8080

# helpers

function Write-Step([string]$msg) {
    Write-Host "`n==> $msg" -ForegroundColor Cyan
}

function Write-OK([string]$msg) {
    Write-Host "    [OK] $msg" -ForegroundColor Green
}

function Write-Warn([string]$msg) {
    Write-Host "    [!]  $msg" -ForegroundColor Yellow
}

function Write-Err([string]$msg) {
    Write-Host "    [ERROR] $msg" -ForegroundColor Red
}

function Assert-Docker {
    Write-Step "Verificando Docker..."
    docker ps --quiet | Out-Null
    if ($LASTEXITCODE -ne 0) {
        Write-Err "Docker no esta corriendo. Abrelo y vuelve a ejecutar el script."
        exit 1
    }
    Write-OK "Docker esta corriendo."
}

function Stop-AppOnPort([int]$port) {
    $conn = Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue
    if ($conn) {
        $pid = $conn.OwningProcess | Select-Object -First 1
        Write-Warn "Puerto $port ocupado (PID $pid). Liberandolo..."
        Stop-Process -Id $pid -Force -ErrorAction SilentlyContinue
        Start-Sleep -Seconds 1
        Write-OK "Puerto $port liberado."
    }
}

function Start-DB {
    Write-Step "Levantando la base de datos..."
    Set-Location $PROJECT_ROOT
    docker compose up -d
    if ($LASTEXITCODE -ne 0) {
        Write-Err "Fallo docker compose up."
        exit 1
    }

    Write-Host "    Esperando a que Postgres este listo en ${DB_HOST}:${DB_PORT}..." -NoNewline
    $timeout = 30
    $elapsed = 0
    while ($elapsed -lt $timeout) {
        try {
            $tcp = New-Object System.Net.Sockets.TcpClient
            $tcp.Connect($DB_HOST, $DB_PORT)
            $tcp.Close()
            Write-Host " listo." -ForegroundColor Green
            return
        } catch {
            Write-Host "." -NoNewline
            Start-Sleep -Seconds 1
            $elapsed++
        }
    }
    Write-Host ""
    Write-Err "Postgres no respondio en ${timeout} segundos."
    exit 1
}

function Reset-DB {
    Write-Step "Reseteando la base de datos (borra volumen)..."
    Set-Location $PROJECT_ROOT
    docker compose down -v
    Write-OK "Volumen eliminado."
}

function Stop-DB {
    Write-Step "Deteniendo contenedores..."
    Set-Location $PROJECT_ROOT
    docker compose down
    Write-OK "Contenedores detenidos."
}

function Invoke-GradleTask([string]$task) {
    Write-Step "Ejecutando: gradlew $task"
    Set-Location $PROJECT_ROOT
    & $GRADLEW $task
    if ($LASTEXITCODE -ne 0) {
        Write-Err "gradlew $task fallo (exit $LASTEXITCODE)."
        exit $LASTEXITCODE
    }
}

# modos

switch ($Mode) {

    "run" {
        Assert-Docker
        Start-DB
        Stop-AppOnPort $APP_PORT
        Write-Step "Arrancando la aplicacion..."
        Write-Host "    Swagger UI -> http://localhost:${APP_PORT}/swagger-ui.html" -ForegroundColor Magenta
        Write-Host "    OpenAPI    -> http://localhost:${APP_PORT}/v3/api-docs" -ForegroundColor Magenta
        Write-Host "    Ctrl+C para detener.`n"
        Invoke-GradleTask "bootRun"
    }

    "test" {
        Assert-Docker
        Start-DB
        Invoke-GradleTask "test"
        Write-OK "Tests completados. Reporte: build\reports\tests\test\index.html"
    }

    "reset" {
        Assert-Docker
        Reset-DB
        Start-DB
        Stop-AppOnPort $APP_PORT
        Write-Step "Arrancando la aplicacion con BD limpia (Flyway aplicara V1-V4)..."
        Write-Host "    Swagger UI -> http://localhost:${APP_PORT}/swagger-ui.html" -ForegroundColor Magenta
        Write-Host "    Ctrl+C para detener.`n"
        Invoke-GradleTask "bootRun"
    }

    "db" {
        Assert-Docker
        Start-DB
        Write-OK "BD lista en ${DB_HOST}:${DB_PORT} (DB: safeHaven, user: user, pass: 123456)"
    }

    "stop" {
        Assert-Docker
        Stop-DB
    }
}