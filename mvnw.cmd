@echo off
setlocal

set MVNW_REPOURL=https://repo.maven.apache.org/maven2

set WRAPPER_DIR=%~dp0.mvn\wrapper
set JAR=%WRAPPER_DIR%\maven-wrapper.jar

if not exist "%JAR%" (
    echo Downloading Maven Wrapper JAR...
    mkdir "%WRAPPER_DIR%"
    powershell -Command "& {Invoke-WebRequest -Uri %MVNW_REPOURL%/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar -OutFile %JAR%}"
)

"%JAVA_HOME%\bin\java" -jar "%JAR%" %*
