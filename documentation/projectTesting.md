# Project testing document

## JUnit testing

Testing by package:

### mapgenerator

Only Main class is here, which does nothing else than create and launch a program handler. No reason to test it. 

### mapgenerator.domain

This package is mainly for storing information, so most of the methods are getters and setters. Biomes class has one method to create a biome selection table, but the table is currently so small, that automated testing is not needed. If it was really big, it may be reasonable to check that all slots are filled with some biome. 

### mapgenerator.gui

Graphic UI does not need to be tested in this course. 

### mapgenerator.logic

**BiomeSelector**: Checked that all water slots are filled with biome zero and not with a land biome.

**NoiseMapGenerator**: Test coverage is fine. The tests check that all slots of the noise map are filled with values after the algorithm has run. There should also be a test that checks that no slots are given a value more than once. 

**WaterGenerator**: Class and methods still changing rapidly, more tests needed. 

**MapConstructor**: Tests mainly check that all needed arrays are created when running a method that is supposed to call all kinds of other methods to create these things. No other testing needed, as there is not much logic in this class. 

**ProgramHandler**: This is mainly an intermediate communication class between mapConstructor, domain classes and graphic UI. Quite hard to test automatically, and no reason to, since if this does not work, it will show itself clearly in manual testing when starting up the program. 

## Performance

The map creation and drawing times are tested with four mostly used map sizes. A map size of k means that there are (2^k + 1)^2 cells in the map array, so the n is therefore a lot bigger than the map size. There is a weird problem with the performance where creating and drawing the map (but especially drawing) take a lot more time when the map is first created in comparison to the average time in subsequent creations. The cause of the problem is still unknown. All creating and drawing times are shown in milliseconds in the table below. 

Map Size | n | Creating time (first) | Drawing time (first) | Creating time (subsequent) | Drawing time (subsequent) |
---------|---|-----------------------|----------------------|----------------------------|---------------------------|
7 | 16 641 | 20 | 40 | 20 | 15
8 | 66 049 | 40 | 150 | 25 | 25
9 | 263 169 | 95 | 4500 | 50 | 50
10 | 1 050 625 | 200 | 106000 | 130 | 250

## Manual tests

So far this has just been looking at the maps and checking if they look like what they should. 


