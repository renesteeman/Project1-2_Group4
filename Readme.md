In order to run the code you have to create a project using the setup application from https://libgdx.badlogicgames.com/download.html 
while creating the project name Game class "Main" and as Sub Projects only check Desktop. Under extensions select none.
and then paste our code inside to overwrite the default files. Then you can run it using intellij by starting the 
DesktopLauncher located in rootFolder->desktop->src->com.mygdx.game.desktop->DesktopLauncher. 


To run the current software:

go to development/software 
open the project with your IDE or type the following commands in your terminal/cmd

MacOS/Linux

gradle wrapper
./gradlew build
./gradlew run

Windows

gradle wrapper
gradlew.bat build
gradlew.bat run
