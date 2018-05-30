**ROCK-PAPER-SCISSORS** is very simple and very popular fingers game.
Using this application you can play with a server as with normal opponent.

**HOW TO START APPLICATION**
If you are using non-Window environment and have docker and docker-compose installed
on your machine then:
1. Clone the repository to your local machine.
2. cd dir_where_you_cloned_repository.
3. cp settings.xml file to the root of project to let docker download all the dependencies.
This file is added to .gitignore, so it won't appear on github even if you make a commit.
4. docker-compose build
5. docker-compose up
It will start 2 docker containers: one for mysql database with port 3306 exposed and
java backend with port 8080 opened to FE/browser.

If you are using Windows or don't have docker on your machine:
1. Install mysql on your local machine and create game database.
2. Use Launcher to start this application as normal Spring Boot Application.

**HOW TO PLAY**
1. To start the game itself send GET request to /game/new
2. To make a turn send GET request to /game/turn/{move}, where move can be one of the following:
paper, rock, scissors. upper/lower case will be ignored and work fine for any combination.
3. To stop the game and see the final score send GET request to /game/stop

**SWAGGER integration**
After application is launched you may use the following link to see available endpoint as well
as expected params and possible response codes:
http://localhost:8080/swagger-ui.html

