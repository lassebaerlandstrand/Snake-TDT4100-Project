# Documentation for TDT4100 - Project
*This is not the README-file. This is in correspondence to the documentation description, and created after the [README](README.md) file*

This repository is related to a project for [TDT4100 - Object Oriented Programming](https://www.ntnu.no/studier/emner/TDT4100)

## Project description

This project creates the classic video game Snake. In addition I created a Snake AI, which uses a self-created algorithm to control the snake's movement based on looking certain moves ahead, and deciding the optimal move. <br/>

Short description about Snake: <br/>
Snake is a classic game in which the player controls a growing snake as it moves around a confined space, such as a grid. The objective of the game is to eat as many randomly placed apples as possible without colliding with the walls or the snake's own body. As the snake consumes more objects, it grows in length, making it more challenging to avoid obstacles and navigate the playing field. The game continues until the snake collides with a wall or its own body, at which point the player loses and must restart.


### How to play

Run the main method in the [App Application](src/main/java/Snake/App.java). A 800x800 window opens, and to start playing, press WASD or the arrow buttons, the snake will move in the corresponding. To pause the game, press SPACE. The game will continue until you collide with the walls or yourself. Then a 'Game Over' screen will appear, which will display your score and the highscore. To continue playing, do the same as you initially did to start playing.

## Class diagram
The image below shows the class diagram for the `Game` class and its related classes. The attributes and methods of `FXMLController` have been omitted as we are only implementing an interface of it, and do not require knowledge of its internal implementation from the perspective of the `Game` class. 
![Class diagram](img/TDT4100%20-%20Project%20-%20Class%20Diagram.jpg)

## Questions

### 1. Which parts of the syllabus in the subject are covered in the project, and in what way? (For example use of inheritance, interface, delegation, etc.)
* Inheritance is extensively used in this project. An example is both [`SnakeCell`](src/main/java/Snake/Model/SnakeCell.java) and [`Food`](src/main/java/Snake/Model/Food.java) extends the [`Coordinate`](src/main/java/Snake/Utils/Coordinate.java) class. This is done to make it easier to handle the objects in the game, as they extends a class which is often used in the rest of the project. Many methods returns a `Coordinate` object, therefore one could return the SnakeCell or Food object directly (In other words, Polymorphism).
Inheritance was employed to implement the AI, leveraging the methods of the original game since it builds upon and extends its functionality. How the AI was implemented is documented in the [`README file`](README.md).
* Delegation is also used in this project. An example is the [`Game`](src/main/java/Snake/Game.java) class, which delegates the responsibility of handling the snake's movement to the [`Snake`](src/main/java/Snake/Model/Snake.java) class. This is done to make the code more readable and easier to maintain. The `Game` class does not need to know how the snake is moved, it only needs to know that it is moved. 
* I have implemented one custom interface [`ControllerListener`](src/main/java/Snake/ControllerListener.java) which is used to communicate between the [`Game`](src/main/java/Snake/Game.java) class and the [`FXMLController`](src/main/java/Snake/FXMLController.java) class. This is done to make the code more readable and easier to maintain. The `Game` class does not need to know how the `FXMLController` class is implemented, it only needs to know that certain methods are implemented. In addition I extend currently existing interfaces, such as `Comparable<T>`, which is implemented by `Coordinate` and `HighScoreObject`.
* Other parts of the syllabus that are covered in this project are: 
    * Use of Observer-Observable pattern
    * File handling
    * Overloading of methods
    * Overriding of methods
    * Polymorphism
    * Exception-handling
    * Lambda and Streams



### 2. If parts of the syllabus are not covered in your project, how could you use these parts of the syllabus in the project?
* This project does not explicitly implement the `Iterator<T>` or `Iterable<T>` interfaces. This is because the project does not require the use of these interfaces. However, if the project was to be extended, it could be useful to implement these interfaces. For example, if the game was to be extended to support multiple snakes, it could be useful to implement the `Iterable<T>` interface on the `Snake` class, to make it easier to iterate over the snakes in the game. This would make it easier to implement the collision detection between the snakes, as it would be possible to iterate over all the snakes in the game, and check if they collide with each other.



### 3. How does your code relate to the Model-View-Controller principle?
This project follows the [Model](src/main/java/Snake/Model/)-[View](src/main/java/Snake/View/)-[Controller](src/main/java/Snake/Controller/) (MVC) design pattern. This can clearly been seen in the folder structure, in which the Model, View and Controller are seperated into their own folders.
The game logic is seperated into the [Model package](src/main/java/Snake/Model/), and operate independently of the View and Controller, and therefore has no knowledge of the View's existence. It does however know about an [interface](src/main/java/Snake/Controller/ControllerListener.java) of the controller, which is used to implement the Observer-Observable pattern. 

### 4. How did you go about testing your app, and why did you choose the tests that you have chosen? Have you tested all parts of the code? If not, how have you prioritized which parts are tested and which are not?
To test my app, I focused on three primary parts: file handling, the Snake class, and the Game class. I prioritized testing the code within the model package because it contains the game's logic, which is crucial for the game to function properly. Additionally, I chose not to test the controller and view packages as they only handle user input and display the game, respectively, and are not a part of the game's core functionality. While I did not test all parts of the code, I carefully chose which parts to prioritize based on their importance to the game's functionality. When creating the tests, I based them on what I expected the code to do. If a test failed, I did not modify the test code itself, but rather fixed the underlying logic to ensure that it would perform as intended. Overall, I aimed to thoroughly test the critical components of the project to ensure that they work as intended. 




