@REM
@REM Copyright 2017 Wealins
@REM
@REM run the flyway scripts on every configured database
@REM

@Echo off

setlocal

IF [%1]==[] (set CMD=info) ELSE (set CMD=%1)

IF NOT [%2]==[] (
	flyway -configFile=.\conf\%2.dev.conf %CMD%
	goto :end
)

For /R .\conf %%G IN (*.dev.conf) DO (
	flyway -configFile=%%G %CMD%
)

:end