@echo off
for %%f in (src\*.java) do (
    echo Processing %%f
    type "%%f" > temp.txt
    echo package jabberpoint; > "%%f"
    echo. >> "%%f"
    type temp.txt >> "%%f"
)
del temp.txt
echo Done processing all Java files
