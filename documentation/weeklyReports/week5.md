# Week 5

Time spent on the project: 16 hours

## Week 5 progress 

I wanted to get my algorithms and data structures shomewhat ready this week, and I think I succeeded. I decided to not use dijkstra in river generation, because I thought it was too slow. Instead I chose an algorithm that basically just always goes to the smallest height, and sometimes in random direction. The rivers don't look as good, but the good thing is this also creates lakes and is much faster and easier to test and implement. I also left in the Binary Heap used for dijkstra even though it is not necessary anymore, because it allows to change the riveralgorithm back to dijkstra or some other better algorithm if I want to. 

With river generation and the binary heap ready, I only have some finishing adjustments and refactoring to do next week. I will probably try and make the rivers look better. I also need to replace the random and math imports with my own, but luckily they are almost ready too. 

I also created a new datastructure called mapCell, and replaced all my different height-, water-, etc. arrays with one array containing these MapCells. Each MapCell contains a height, moisture, water and biome value. I thought this was way simpler than my earlier solution.

### Course progress

* Algorithms and data structures ready (but may still be adjusted). 

### Project progress

* Remove dijkstra from river generation and insted use a simpler and faster (but not as natural-looking) algorithm to generate both rivers and lakes
* Implement binary heap, node, random number generation and math functions
* Replace most imports with own implementations
* Replase all map arrays with one array containing MapCell objects
* More tests

## New things learned

* How to make a binary heap

## Questions and problems


## Next week

* Improve roughen method and biome selection for more natural looking maps
* Add some randomization to river generation so that it does not always pick neighbors in the same order if there are several with same weight
* Adjust the probability of starting a river so that there is a natural looking amount of rivers and more in wet areas than in dry areas.
* Improve code quality
* Add tests if needed
* If time: improve UI (make it nicer, add buttons for showing height and moisture maps and possibly other functions). Try to make the maps look good on all sizes (now they look best when map size exponent is 9 and multiplier 1). 


