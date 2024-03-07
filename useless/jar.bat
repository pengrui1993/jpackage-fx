setLocal EnableDelayedExpansion
set SEARCH_CP=
for /R D:\software\openjfx\javafx-sdk-21.0.2\lib %%a in (*.jar) do (
set SEARCH_CP=!SEARCH_CP!%%a;
)
set SEARCH_CP=!SEARCH_CP!
echo !SEARCH_CP!
pause