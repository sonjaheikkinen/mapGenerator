# Project implementation document

## Project Structure

*Insert nice picture here when structure is final.*

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
3. The next river cell will be the root of the binary heap.
4. If there are no neighbors that meet the requirements, the river just flows in random direction. 


## Possible improvements


## Sources

https://en.wikipedia.org/wiki/Diamond-square_algorithm

Tirakirja
