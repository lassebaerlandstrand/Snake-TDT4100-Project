# TDT4100 - Project

This repository is related to a project for [TDT4100 - Object Oriented Programming](https://www.ntnu.no/studier/emner/TDT4100)

## Project description

This project creates the classic video game Snake. In addition I created a Snake AI, which uses an self-created algorithm to control the snake's movement based on looking certain moves ahead, and deciding the optimal move. <br/>
The UI and color choices are inspired by Google's Snake. <br/><br/>
Short description about Snake: <br/>
Snake is a classic game in which the player controls a growing snake as it moves around a confined space, such as a grid. The objective of the game is to eat as many randomly placed apples as possible without colliding with the walls or the snake's own body. As the snake consumes more objects, it grows in length, making it more challenging to avoid obstacles and navigate the playing field. The game continues until the snake collides with a wall or its own body, at which point the player loses and must start again from the beginning.


## Logic and structure

### Input handling

The [controller](src/main/java/Snake/Controller/FXMLController.java) handles receiving keyboard inputs, and calls the corresponding method in the model.<br/> 
To handle directional inputs, I have created a custom [datatype/class](src/main/java/Snake/Model/DirectionDefaultList.java), which is able to obtain multiple inputs on the same frame. The Queue interface served as a source of inspiration, but it has inherited the List interface because the final element is often retrieved. Every time an input is received, it is appended to the list. If the same input is received consecutively, only one instance is added to prevent accumulation of duplicates. Additionally, a default value is included to ensure that the datatype is never empty.

### Rendering
The rendering method in the [view](src/main/java/Snake/View/GameView.java) has been through many iterations to optimize and speed up the game.<br/>
The rendering method can be divided into two parts: Initialization (Initial frame of the game) and Ongoing Rendering.<br/><br/>

Initialization: <br/>
The first iteration of the initial frame was the naive double loop, which draw each small square individually. Later I optimized it to draw one large square followed by smaller squares on top, leading to a reduction of over fifty percent in the number of drawing calls required. <br/> 
To give some data to the improvements: <br/>
Before: 20 seconds to initialize a 1000x1000 grid <br/>
After: 0.05 seconds to initialize a 1000x1000 grid <br/>
The optimized method uses the same time to create a 7000x7000 grid as the naive used on a 1000x1000 grid (x49 as many squares). <br/><br/>

Ongoing rendering: <br/>
Initially the game could only handle a 100x100 grid with 20 fps (with flickering). This was the naive method of redrawing the whole canvas on each frame, which resulted in poor performance. The optimized method bases itself on the previous frame, and only redraws the difference. Since there are at max only 4 squares which have changed (1 for snake's head, 2 for snake's body, 1 for apple), we would only redraw at max 4 squares.
This gives an enormous performance boost, as where we previously drew 10000 squares (in a 100x100 grid), we now only draw max 4 squares. The numbers of draw calls can therefore be described as an improvement from O(n<sup>2</sup>) to O(1). <br/><br/>

Combined, these optimizations enable one to play on a 4000x4000 grid without any performance issues. In tests, the initialization process took 3 seconds, and the game ran at over 1000 frames per second.





## Images of the game
![Highscore Image](img/SnakeAI.gif)

![Highscore Image](img/Highscore.png)



