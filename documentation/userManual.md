# User manual

## Installation and starting the program

Currently the program can only be installed and started using the command line. You may need to install some other things before the project can work (atleast java and gradle). 

1. Download or clone the project from [Github](https://github.com/sonjaheikkinen/mapGenerator). **Download**: Press "Code" -> "Download ZIP" and then extract the zip file in the folder you want it to go. **Clone**: Press "Code" and copy the ssh address from the field. Then go to the folder you want to put the project in and run command "git clone *address*".
2. Go into folder "mapGenerator" (should be under another mapGenerator or mapGenerator-master, unless you renamed the folder you cloned this project). You should now be in a folder that contains folders src, build, config etc.
3. Run command "gradle run". If it does not work, check if you are missing some dependencies, install them and try again. 


## Using the program

* New maps can be created by pressing the button "New map". As default, the graphic UI will show you the simple biome map. 
* If you want to see the heightmap, press the button "Show heightmap". 
* If you want to see the moisture map, press the button "Show moisture". 
* If you want to draw the biomes with improved colors, press "Shaded", and if you want the biomes simple again, press "Simple". Note that the "Shaded"-option is still in development.

* You can view the placement of biomes by choosing a biome from the left. The chosen biome is shown as red on the map. If you do not see any red, it is likely that the chosen biome does not exist in current map. Try creating a new map or choosing a different biome. Some biomes are on the option bar twice. This is because some biomes can currently occur in multiple heights and moistures, and are therefore twice in the programs biome selection table. This will be fixed when more biomes are added. 




