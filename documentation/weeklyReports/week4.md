# Week 4

Time spent on the project: 13 hours

## Week 4 progress

I had less time than usually for the project this week, so I did not progress as much as I hoped. I tried to implement river generation, but it turned out to be harder than expected. First I tried to use breadth-first search, but the rivers were unnatural-looking so I switched to dijkstra where the weighting is based on height difference between nodes. This works quite well, the rivers look nice, but it is too slow. I tried to make it faster by finding the general direction of nearest water before running dijkstra, but that did not make it fast enough. I also tried a simple random walk to downhill approach, but on some maps that created too much water so I gave up on that. Unfortunately I had no time for more, so I have to continue next week. Since the algorithm may change a lot and it is actually a mess when it comes to code quality, I did not write javadoc, tests, and data structures for it yet. 

Since the river generation was harder than I expected, I decided to not do towns and road network. I will probably try if my final river generation algorithm works with roads too, but if not, then I am not doing them. 

### Course progress

* Started writing testing and implementation documents.
* Data structures should have been started this week, but since I am still unsure of the needed data structures, I did not start them yet. 

### Project progress

* River generation with dijkstra

## New things learned

I (probably) learned how to make a simple time complexity analysis. I also learned a lot about different pathfiding algorithms, when making research on river generation. 

## Questions and problems

I had a hard time making a fast enough river algorithm, altough I could just generate less rivers, but that would be too easy solution. Next week I will try again, and hopefully find something new. Unfortunately most pathfinding algorithms seem to need a destination, when my algorithm needs to stop when it first finds water. I am not even sure if pathfinding algorithm is the right solution for this or if there is some other group of algorithms that would do this better. Most of the map generators I have been looking into just make the rivers flow downwards, but for some reason that did not work well on my map. 

## Next week

* Improve river generation speed and get started on data structures (preferably finish them)
* Get javadoc and tests up to date
* If time: Generate more rivers on places with more moisture and vice versa. Improve biome selection for more natural looking maps. Make sure that all lakes are connected to ocean via river. Create more lakes?

