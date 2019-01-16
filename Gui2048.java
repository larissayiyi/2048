/* 
 * 
 * Date: May 21, 2015
 * File: Gui2048.java
 * Description: This program creates a graphic interface for the game
 * 2048.  We constructed the game board with different types of panes such
 * as GridPane and StackPane. It is able to take user's keystrokes
 * input as command and updates the board each time user makes a move. It
 * also saves the game with "S" command.
 */

/** Gui2048.java */

//import javafx libraries 
import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;

/*
 * Name: Gui2048
 * Purpose: This program creates a graphic interface for the game 2048.  We
 * constructed the game board with different types of panes such as GridPane
 * and StackPane. It is able to take user's keystrokes input as command and
 * updates the board each time user makes a move. It also saves the game with
 * "S" command.
 */
public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(238, 228, 218, 0.35);
    private static final Color COLOR_2 = Color.rgb(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(237, 194, 46);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); // For tiles >= 8
    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); // For tiles < 8

    /** Add your own Instance Variables here */
    // the overall pane that fits the title and grid
    private GridPane pane = new GridPane();
    // a solid color with rgb of 205,192,180 used for empty grid
    private static final Color EMPTY_GRID = Color.rgb(205, 192, 180);
    // the score for the game
    private Text score = new Text();
    // the overall scene 
    private Scene scene = new Scene(pane);
    
    /*
    * Name: start
    * Purpose: To initialize the stage of the interface 
    * Parameter: Stage primaryStage
    * Return: None
    */
    @Override
    public void start(Stage primaryStage)
    {
        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        /** Add your Code for the GUI Here */
        // set pane to align to the center 
        pane.setAlignment(Pos.CENTER);
        // set padding for the pane
        pane.setPadding (new Insets(11.5,12.5,13.5,14.5));
        // set horizontal and verticle gaps between the elements
        pane.setHgap(5.5);
        pane.setVgap(5.5);
        // set background color for the pane
        pane.setStyle("-fx-background-color:rgb(187,173,160)");

        // create a new pane head to fit the title and the score
        BorderPane head = new BorderPane();
        // create a text object name "title"
        Text title = new Text();
        // set the text to 2048
        title.setText("2048");
        // set font for the text
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD,
        FontPosture.ITALIC, 50));
        // set the title to the left of the board
        head.setLeft(title);

        // set score 
        score.setText("Score: " + board.getScore());
        // set score font
        score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        // set score color
        score.setFill(Color.BLACK);
        // set score to the right of the pane
        head.setRight(score);

        // add head pane to the overall pane
        pane.add(head, 0, 0);
        // add grid below the head pane
        pane.add(getGrid(), 0, 1);
        // align GridPane to the center horizontally
        GridPane.setHalignment(score, HPos.CENTER);

        // assign keyhandler to key action
        scene.setOnKeyPressed( new KeyHandler());
        // set the title of the stage to "Gui2048"
        primaryStage.setTitle("Gui2048");
        // set scene to scene
        primaryStage.setScene(scene);;
        // show stage
        primaryStage.show();
    }


     /*
      * Name: KeyHandler
      * Purpose: Handles keystrokes input then either move or save the board
      * Parameter: None
      * Return: None
      */
    /** Add your own Instance Methods Here */ 
      private class KeyHandler implements EventHandler<KeyEvent> {
        // it has to override the handle method inherited from eventhandler
       /*
        * Name: handle
        * Purpose: take in keystrokes input then either move or save the board
        * Parameter: KeyEvent e
        * Return: None
        */
        @Override
        public void handle(KeyEvent e) {
          // if the keystroke is up and the board can move up
          if (e.getCode().getName().equals("Up") && board.canMoveUp()) {
            // print in terminal
            System.out.println("Moving Up");
            // move the board up
            board.move(Direction.UP);
            // add a random tile to the board
            board.addRandomTile();
            // update the score
            score.setText("Score: " + board.getScore());
            // add grid to the pane
            pane.add(getGrid(),0,1);
          }

          // if the keystroke is down and the board can move down 
          else if (e.getCode().getName().equals("Down") && board.canMoveDown())
          {
            // print in terminal
            System.out.println("Moving Down");
            // move the board down
            board.move(Direction.DOWN);
            // add a random tile to the board 
            board.addRandomTile();
            // update the score
            score.setText("Score: " + board.getScore());
            // add grid to the pane
            pane.add(getGrid(),0,1);
          }

          // if the keystroke is up and the board can move up 
          else if (e.getCode().getName().equals("Left") && board.canMoveLeft())
          {
            // print in terminal
            System.out.println("Moving Left");
            // move the board left
            board.move(Direction.LEFT);
            // add a random tile to the board
            board.addRandomTile();
            // update the score 
            score.setText("Score: " + board.getScore());
            // add grid to the pane
            pane.add(getGrid(),0,1);
          }
          // if the keystroke is right and the board can move right
          else if (e.getCode().getName().equals("Right") &&
          board.canMoveRight()) {
            // print in terminal
            System.out.println("Moving Right");
            // move the board left
            board.move(Direction.RIGHT);
            // add a random tile to the board
            board.addRandomTile();
            // update the score
            score.setText("Score: " + board.getScore());
            //add grid to the pane
            pane.add(getGrid(),0,1);
          }

          // if the keystroke is "S" 
          else if (e.getCode().getName().equals("S")) {
            // save board with trial and catch
            try {
              board.saveBoard(outputBoard);
              System.out.println("Saving board to " + outputBoard);
            } 
            catch (IOException o) {
              System.out.println("saveBoard threw an Exception");
            }
          }
        }
      }

      /*
      * Name: getGrid
      * Purpose: get the board grid
      * Parameter: None
      * Return: StackPane
      */
      private StackPane getGrid() {
        // creates a new stack pane
        StackPane stack = new StackPane();
        // creates a new grid pane
        GridPane grid = new GridPane();
        // set horizontal and verticle gap 
        grid.setHgap(10);
        grid.setVgap(10);
        // get the grid values
        int [][] tileArray = board.getGrid();
        // loop through the grid
        for (int row = 0; row < board.GRID_SIZE; row++){
          for (int col = 0; col < board.GRID_SIZE; col++){
            // get the value of the grid 
            int tileVal = tileArray[row][col];
            // creates a new stack pane for tile 
            StackPane tile = new StackPane();
            // make a new square
            Rectangle square = new Rectangle();
            // set the width of the square
            square.setWidth(100);
            square.setHeight(100);
            // make a new text object
            Text num = new Text();
            // set font for num
            num.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
            // set color for the square and num for zero tile
            if (tileVal == 0) {
              square.setFill(EMPTY_GRID);
              num.setText("");
            }
            // set color for the square and num for two tile
            else if (tileVal == 2) {
              square.setFill(COLOR_2);
              // set tile value
              num.setText(""+tileVal);
              num.setFill(COLOR_VALUE_DARK);
            }
            // set color for the square and num for four tile
            else if (tileVal == 4) {
              square.setFill(COLOR_4);
              num.setText(""+tileVal);
              num.setFill(COLOR_VALUE_DARK);
            }
            // set color for the square and num for eight  tile
            else if (tileVal == 8) {
              square.setFill(COLOR_8);
              num.setText(""+tileVal);
              num.setFill(COLOR_VALUE_LIGHT);
             }
            // set color for the square and num for 16 tile
            else if (tileVal == 16) {
              square.setFill(COLOR_16);
              num.setText(""+tileVal);
              num.setFill(COLOR_VALUE_LIGHT);
            }
             // set color for the square and num for 32 tile
            else if (tileVal == 32) {
              square.setFill(COLOR_32);
              num.setText(tileVal+"");
              num.setFill(COLOR_VALUE_LIGHT);
            }            
             // set color for the square and num for 64 tile
            else if (tileVal == 64) {
              square.setFill(COLOR_64);
              num.setText(tileVal+"");
              num.setFill(COLOR_VALUE_LIGHT);
            }
             // set color for the square and num for 128 tile
            else if (tileVal == 128) {
              square.setFill(COLOR_128);
              num.setText(tileVal+"");
              num.setFill(COLOR_VALUE_LIGHT);
            } 
             // set color for the square and num for 256 tile
            else if (tileVal == 256) {                                        
              square.setFill(COLOR_256);         
              num.setText(tileVal+"");                      
              num.setFill(COLOR_VALUE_LIGHT);         
            }
             // set color for the square and num for 512 tile
            else if (tileVal == 512) {                                        
              square.setFill(COLOR_512);                                      
              num.setText(tileVal+"");                                        
              num.setFill(COLOR_VALUE_LIGHT);                                 
            }
             // set color for the square and num for 1024 tile
            else if (tileVal == 1024) {                                        
              square.setFill(COLOR_1024);                                      
              num.setText(tileVal+"");                                        
              num.setFill(COLOR_VALUE_LIGHT);                                 
            }
             // set color for the square and num for 2048 tile
            else if (tileVal == 2048) {                                        
              square.setFill(COLOR_2048);                                      
              num.setText(tileVal+"");                                        
              num.setFill(COLOR_VALUE_LIGHT);                                 
            }
             // set color for the square and num for any other tile
            else {
              square.setFill(COLOR_OTHER);
              num.setText(tileVal+"");
              num.setFill(COLOR_VALUE_LIGHT);
            } 
            // add the num and square to the stack pane
            tile.getChildren().add(square);
            tile.getChildren().add(num);
            // add the stackpane to the gridpane
            grid.add(tile, col, row);
          }
        }
        // add the grid pane to the filtering stack pane
        stack.getChildren().add(grid);

        // if it is game over
        if (board.isGameOver()) {
          // create a new rectangle
          Rectangle filter = new Rectangle();
          // bind width and height property to the property of grid
          filter.widthProperty().bind(grid.widthProperty());
          filter.heightProperty().bind(grid.heightProperty());
          // set the color of the square to the color assigned
          filter.setFill(COLOR_GAME_OVER);
          // set text to game over!
          Text gameOver = new Text("Game Over!");
          // set fill of the text to be a transparent black
          gameOver.setFill(Color.rgb(0, 0, 0, 0.5));
          // set font of the game over text
          gameOver.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
          // add filter and gameover to the stack pane
          stack.getChildren().addAll(filter,gameOver);
        }
        // return the filtered pane
        return stack;
      }
        





    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be used to save the 2048 board");
        System.out.println("                If none specified then the default \"2048.board\" file will be used");
        System.out.println("  -s [size]  -> Specifies the size of the 2048 board if an input file hasn't been");
        System.out.println("                specified.  If both -s and -i are used, then the size of the board");
        System.out.println("                will be determined by the input file. The default size is 4.");
    }
}
