@echo off
setlocal

set "JAVA_HOME=F:\Program Files\jdk 17"
set "PATH=%JAVA_HOME%\bin;%PATH%"

:: Установи имя пользователя GitHub
set GITHUB_USERNAME=Mirik9724

:: Прочитать токен из файла
set TOKEN_FILE=github_token.txt

if not exist %TOKEN_FILE% (
    echo [ERROR] Файл %TOKEN_FILE% не найден!
    pause
    exit /b 1
)

:: Прочитать первую строку токена
set /p GITHUB_TOKEN=<%TOKEN_FILE%

:: Запуск публикации
echo === Publishing to GitHub Packages ===
set GITHUB_USERNAME=%GITHUB_USERNAME%
set GITHUB_TOKEN=%GITHUB_TOKEN%


./gradlew build
./gradlew publish

endlocal
pause
