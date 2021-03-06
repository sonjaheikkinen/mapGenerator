# Project implementation document

## Project Structure

Picture shows the package structure and classes of the program as well as the most important relationships between classes. Program launches from Main class, which then creates a ProgramHandler. ProgramHandler calls for MapConstructor to create a map using the Calculator. The map is created into an array of MapCell objects. MapConstructor calls for NoiseMapGenerator to generate height and moisture maps, WaterGenerator to generate ocean, lakes and rivers and BiomeSelector to select biomes based on height and moisture. 

WaterGenerator uses Node and BinaryHeap in its execution. BiomeSelector needs the Biomes class which provides it with a BiomeSelection table, from where biomes can be picked using height and moisture. It also generates a biomeColor array used in UI.

After everything is created, MapConstructor returns the created map and other thing to ProgramHandler, which stores the map in Map, gives Map to GraphicUI and calls for GraphicUI to start. GraphicUI calls for Painter to draw the map on screen using given Map and BiomeColor array. 

![Project structure](https://github.com/sonjaheikkinen/mapGenerator/blob/master/documentation/projectStructure.png)

## Complexity and performance

The maps produced by this generator are always the size of n*n where n = 2^(given exponent) + 1. 
Algorithm has five main phases:

1. Generate a height map with diamond-square algorithm
2. Loop trough map and add water
3. Generate a moisture map with diamond-square algorithm
4. Generate rivers
5. Loop trough map and choose biomes

Since the map is of size n^2, we can conclude that the time complexity of phases 2 and 5 is O(n^2). 

### Time complexity of the diamond-square algorithm (phases 1 and 3)

The basic idea of the diamond square is:

1. Set map corners to random values
2. Perform diamond and square steps repeatedly until all array values have been set. At each iteration, divide the square size by two. 

* **The diamond step:** For each square in the array (where the values put to the array in the square step work as square corners, except at first iteration where we use the actual map corners), count the average values of the corners and put this average + random value in the middle of the square. 

* **The square step:** For each diamond the array (where the values put to the array in the diamond step work as diamond corners), take the average value of the corners and put this average + random value in the middle of the diamond.

As we can see, we have one value calculation for every slot of the array. Since the are n^2 slots, the time complexity of the algorithm is O(n^2) (The first row of works in constant time).  

### Time complexity of river generation (phase 4)

River generation in this generator is quite simple:
* Loop trough every cell of the map
* When in a cell, there is certain probability that a river may start from the cell. This probability increases if there is a lot of moisture in the area or if the area is high. If a river is started, loop trough these steps until hitting the edge of the map or water area:
1. Find every neighbor of the cell that is eligible for next river direction (every cell has up to eight neighbors, and the requirements the neighbor must meet to be considered might change over time, but should not affect time complexity). 
2. Put the neighbors to a binary heap where smallest cost is the root of the heap (currently cost is the height of the neighboring cell, so that lower height has smaller cost and rivers flow downwards).
3. The next river cell will be the root of the binary heap or randomly some other cell with lower height than current cell.
4. If there are no neighbors that meet the requirements, the river just flows in random direction. 

From the pseudocode we can conclude that looping trough every cell has n^2 steps. Generating a river has a random amount of steps, but the amount of river steps we go trough has a theoretical maximum of n^2 (if every cell of the map is covered by river, which is highly unlikely and almost impossible in reality), so the whole algorithm has a maximum step number of about n^2 + n^2. The time complexity of river generation is therefore O(n^2).

### Time complexity of map generation

Since every step has a time complexity of O(n^2), that is also the time complexity of the whole algorithm.

## Possible improvements

### More customization to map generation

There should be options in the UI to choose the size, randomizer range and seed of the generated map. Other optional choises could be to choose which algorithm to use to generate rivers, how much rivers one wants to generate, what is the water level and what is the biome zone of the map (there should be options for more cold or warm maps, more deserted maps etc.)

### River generation

River generation is quite random at the time. I tested dijkstra as the algorithm and it looked nicer, but was too slow. Maybe there could be an option to choose if one wants to use the slower and better algorithm or the faster one.

### Cities and roads

The algorithm does not currenty generate cities or roads, but this could easily be implemented by adding cities to random locations (or more cleverly chosen locations) and generatin roads in between with some kind of pathfinding algorithm.

### Time complexity

The algorithm is too slow with big map sizes. There might be ways to optimize the algorithm more and make it faster. 

### Detail generation

Algorithm currently only generates the layout of the overworld map. This could be combined with another generator that generates details within map cells. If we were for example to create a game, one map cell could be like 100x100 m^2 or 100x100 m^2, and this detail generator would generate random details and graphics based on the overrall information (height, biome type, city or road information) of the map cell. 

### Structure

Project structure is not perfect. The dividion between datastructure and domain is a bit unclear. BiomeColors should maybe be part of the gui package. Some of the methods in Painter should probably be in logic.

### Saving maps and reading from file

It would be nice to have an option for saving maps. This would be easy to implement for example by looping trough map and writing a new line for every map into the file. For every cell the line should contain x coordinate, y coordinate, water, height, moisture and biome of the cell. A new file for every map would probably be the best solution. Reading a map file into to the program could be implemented the same way but backwars, as in reading every line and generating an array of MapCells based on the file. 

## Sources

1. https://en.wikipedia.org/wiki/Diamond-square_algorithm

2. Tietorakenteet ja algoritmit -kirja (Antti Laaksonen 11.07.2020) https://www.cs.helsinki.fi/u/ahslaaks/tirakirja/

