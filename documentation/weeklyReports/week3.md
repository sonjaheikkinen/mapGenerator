# Week 2

Time spent on the project: 17 hours

## Week 2 progress

This week I meant to do rivers and lakes, but I changed my plans since I wanted to focus on moisture 
first and use that ase a guide for adding more water next week. I spent several hours in trying to create my own moisture algorithm which would simulate wind effect and distance from water, but I could not get that to look good enough, so I then decided to use the diamond-square for moisture generation also. I will probably attempt to do an additional wind simulating and/or distance from water algorithm and take my moisture map trough that if I have time later.

With moisture ready I was able to create a simple biome selector which decides the biome based on elevation and moisture level. I also added a roughen method for terrain and moisture maps because I thought the biome borders looked too clean. That method still needs improvement though, since it only looks good if I don't check the max value after running it, which is not how thing are supposed to be. 

### Course progress

* Making progress with project core mechanics: diamond-square actually ready, 
started on moisture and biome generation
* There is still no need for other data structures than basic arrays (except one hashmap, but I am still unsure if that is going to be in the finished project so no reason to start implementing it yet). 

### Project progress

* Removed edge wrapping
* Moisture generation with diamond square
* Biomes based on height and moisture
* Biome colors
* Jitter/noise on height and moisture maps for more natural look

## New things learned

I learned that creating my own wind-simulating distance-calculating algorithm is much harder than I thought, but luckily I had a simpler solution ready. 

## Questions and problems

* When using map exponents greater than 9, the program takes too long (about a minute or more) to 
get started. After initial start, new maps are still generated rather quickly so this is not too much of a problem, but I would still like to find a way to make it a bit faster. Problem is, I don't really know whats taking so long on the first time (propably something to do with the computer allocating memory space for big arrays?), but maybe that will clear up. 

## Next week

* Create rivers and lakes based on humidity (less water to dry areas)
* Reduce space complexity by making water another biome?
* Make a better roughen method for more natural looking noise maps (maybe separate methods for heights and humidity?)
* Make research concerning cities and roads
* Slider or selection for water level if time
* If time, adjust biome colors and borders for more natural looking maps + draw height lines
or basic lighting to show height differences
