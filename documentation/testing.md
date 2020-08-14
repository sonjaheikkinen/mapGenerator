# Project testing document

## JUnit testing

Testing by package:

## mapgenerator

Only Main class is here, which does nothing else than create and launch a program handler. No reason to test it. 

## mapgenerator.domain

This package is mainly for storing information, so most of the methods are getters and setters. Biomes class has one method to create a biome selection table, but the table is currently so small, that automated testing is not needed. If it was really big, it may be reasonable to check that all slots are filled with some biome. 

## mapgenerator.gui

Graphic UI does not need to be tested in this course. 

## mapgenerator.logic

**BiomeSelector**: Checked that all water slots are filled with biome zero and not with a land biome.

**NoiseMapGenerator**: Test coverage is fine. The tests check that all slots of the noise map are filled with values after the algorithm has run. There should also be a test that checks that no slots are given a value more than once. 

**WaterGenerator**: Class and methods still changing rapidly, more tests needed. 

**MapConstructor**: Tests mainly check that all needed arrays are created when running a method that is supposed to call all kinds of other methods to create these things. No other testing needed, as there is not much logic in this class. 

**ProgramHandler**: This is mainly an intermediate communication class between mapConstructor, domain classes and graphic UI. Quite hard to test automatically, and no reason to, since if this does not work, it will show itself clearly in manual testing when starting up the program. 

## Manual tests

So far this has basicly been looking at the maps and checking if they look like what they should. 


