# connect-four

This is a Connect Four gui application written with `javax.swing` that provides a few AIs that the player can play against. Players can start multiple local games where players can play against each other, against a single AI, or have two AIs play against each other. The user can also progress, or reverse, each game they created to undo their moves and see what other moves the AI might do.

## Running the Application

### Requirements

- [Apache Maven](https://maven.apache.org) is used to build and compile the jar file to run the application.
- At the least, the application was programmed with Java SE 11.

### Directions

1. Change directory into the root directory of the project
2. To build the project, run Maven with `mvn clean package`.
   - This will provide a clean slate for the target build files.
   - Then this will start building the project.
3. To start the application, run java on the newly created jar file with the command `java -cp ./target/connect-four.jar connectfour.ConnectFourDriver`.