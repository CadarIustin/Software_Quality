@echo off
setlocal enabledelayedexpansion

for %%f in (src\*.java) do (
    echo Processing %%f
    set "dupFound="
    set "lineNum=0"
    set "firstPackage="
    
    for /f "tokens=*" %%a in (%%f) do (
        set /a lineNum+=1
        set "line=%%a"
        
        if "!line:~0,7!"=="package" (
            if "!firstPackage!"=="" (
                set "firstPackage=!lineNum!"
            ) else (
                set "dupFound=yes"
                echo Duplicate package found in %%f at line !lineNum!
            )
        )
    )
    
    if defined dupFound (
        echo Fixing duplicate package in %%f
        powershell -Command "Get-Content '%%f' | Select-Object -Skip 2 | Set-Content 'temp.txt'; Get-Content 'temp.txt' | Set-Content '%%f'; Remove-Item 'temp.txt'"
    )
)

echo Done checking all Java files
