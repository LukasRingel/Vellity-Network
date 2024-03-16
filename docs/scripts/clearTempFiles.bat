@echo off

set "directoryPath=%APPDATA%\vellity"

del /f "%directoryPath%\network.db"

if exist "%directoryPath%" (
    echo Deleting directory: %directoryPath%
    rd /s /q "%directoryPath%"
    if errorlevel 1 (
        echo Failed to delete directory.
    ) else (
        echo Directory deleted successfully.
    )
) else (
    echo Directory does not exist.
)

pause