@echo off
set FBX_CONVERTER="D:\Projects\fbx-conv-win32.exe"

for /f "delims=" %%i in ('dir /b /a-d *.fbx') do %FBX_CONVERTER% -p  -f -o g3db "%%~i"

set G3DB_FILES="*.g3db"
set ASSETS_DIR="D:\Projects\likehunters\android\assets\models"

xcopy %G3DB_FILES% %ASSETS_DIR% /y
del %G3DB_FILES%