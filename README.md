# TDT4100 - Project

This repository is related to a project for [TDT4100 - Object Oriented Programming](https://www.ntnu.no/studier/emner/TDT4100)

## Project description

Lorem ipsum dolor sit amet.


## Logic and structure

### Input handling

I have created a custom [datatype/class](src/main/java/Snake/Model/DirectionDefaultList.java) to obtain multiple inputs on the same frame. This datatype is inspired by Queue interface (although it inherits the List interface since we frequently get the last element). On each input, the information is added to the list, and if the same input repeats twice in a row, only one will be added, to ensure to not stack multiples of the same input. This datatype also has a default value, to ensure it is never empty.

## Images of the game
![Highscore Image](img/SnakeAI.gif)

![Highscore Image](img/Highscore.png)



