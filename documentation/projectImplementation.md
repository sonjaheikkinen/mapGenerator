# Project implementation document

## Project Structure

*Insert nice picture here when structure is final*

## Complexity and performance

The maps produced by this generator are always the size of n*n where n = 2^(given exponent) + 1. 
Algorithm has five main phases:

1. Generate a height map with diamond-square algorithm
2. Generate a moisture map with diamond-square algorithm
3. Loop trough map and add water
4. Generate rivers with dijkstra (? may still change) algorithm
5. Loop trough map and choose biomes

Since the map is of size n^2, we can conclude that the time complexity of phases 3 and 5 is O(n^2). 

### Time complexity of the diamond-square algorithm (phases 1 and 2)

The basic idea of the diamond square is:

1. Set map corners to random values
2. Perform diamond and square steps repeatedly until all array values have been set. At each iteration, divide the square size by two. 
---
	**The diamond step:** For each square in the array (where the values put to the array in the square step work as 		square corners, except at first iteration where we use the actual map corners), count the average values of the 	corners and put this average + random value in the middle of the square. 
	**The square step:** For each diamond the array (where the values put to the array in the diamond step work as 		diamond corners), take the average value of the corners and put this average + random value in the middle of the 	 diamond.
---

As we can see, we have one value calculation for every slot of the array. Since the are n^2 slots, the time complexity of the algorithm is O(n^2). 

### Time complexity of river generation

Algorithm still unclear.

## Possible improvements


## Sources

https://en.wikipedia.org/wiki/Diamond-square_algorithm
Tirakirja
