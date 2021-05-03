@echo off

rem ----------------
set name="clearvehicle"
set version="1.0.2"
rem ----------------

call build.bat
copy "target\%name%-%version%.jar" "run\plugins"
cd "./run"
java -server -Xms1G -Xmx1G -jar "spigot-1.12.2.jar" nogui
pause