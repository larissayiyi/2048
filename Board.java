/*
 *
 * Date: April 30, 2015
 * File: Board.java  
 * Sources of Help: Textbook 
 * Description: The Board class creates new board from input size or 
 * loads board from input file. It saves board to a board file. It contains
 * the addRandomTile methods which adds a random tile (either 2 or 4) to a 
 * random empty tile. 
 *
 */
/**     Sample Board
 *
 *      0   1   2   3
 *  0   -   -   -   -
 *  1   -   -   -   -
 *  2   -   -   -   -
 *  3   -   -   -   -
 *
 *  The sample board shows the index values for the columns and rows
 *  Remember that you access a 2D array by first specifying the row
 *  and then the column: grid[row][column]
 */
//import the java.util package
import java.util.*;
//import the java.io package
import java.io.*;

/*
 * Name: Board
 * Purpose: The class creates new board from inpt size or load board from input
 * file, saves board to a board file, and adds new random tiles to the board.
 * Parameter: N/A
 * Return: N/A
 */
public class Board
{

  // set the constants which will stay permanent. 
  public final int NUM_START_TILES = 2;
  public final int TWO_PROBABILITY = 90;
  // Declares grid size without initializing its value.
  public final int GRID_SIZE;

  // set random as constant Random
  private final Random random;
  // declare 2-D integer array grid
  private int[][] grid;
  // declare integer score
  private int score;

  /*
   * Name: Board(int boardSize, Random random)
   * Purpose: Construct a new board with desired size and random tiles.
   * Parameters: boardSize is an integer which is taken in for the dimension 
   * of the board. random is a random number generator with specific seed we
   * would use to generate our random tiles.
   * Return: None
   *
   */
  // CONSTRUCTOR  
  public Board(int boardSize, Random random)
  {
    // Setting calling obeject's random to be random permanently
    this.random = random;
    // setting the grid size of the calling object to be boardSize permanently
    this.GRID_SIZE = boardSize;
    // initializing score to be 0
    this.score = 0;
    // assign grid to a new 2D array with dimension of the grid size
    this.grid = new int [GRID_SIZE][GRID_SIZE];
    
    //loop through number of times we want to add a new random tile
    for (int i = 0; i < NUM_START_TILES; i++){
          this.addRandomTile();
    }
  }
  
  /*
   * Name: Board(String inputBoard, Random random)
   * Purpose: Construct a board based off of an input file.
   * Parameters: inputBoard is a string which represents the name of the file 
   * we would like to load to the board. random is a random number generator
   * with specific seed we would use to generate our random tiles.
   * Return: None
   *
   */
  public Board(String inputBoard, Random random) throws IOException
  {
    // make inputBoard into a new file object
    File loadBoard = new File(inputBoard);
    // create a new scanner
    Scanner input = new Scanner(loadBoard);
    // Setting calling obeject's random to be random permanently
    this.random = random;
    // scan the grid size
    this.GRID_SIZE = input.nextInt();
    // set the grid to a new 2D array with grid size dimension
    this.grid = new int [GRID_SIZE][GRID_SIZE];
    // scan the score
    this.score = input.nextInt();
    
    // while there is still new input
    while (input.hasNext()){
      //loop through the grid
      for (int row = 0; row < GRID_SIZE; row++){
        for (int col = 0; col < GRID_SIZE; col++){
          // scan in the value
          int gridVal = input.nextInt();
          // set the grid to the value
          grid[row][col] = gridVal;
        }
      }
    }
      
  }
  
  /*
   * Name: saveBoard(String outputBoard)
   * Purpose: Saves the current board to a file.
   * Parameters: outBoard is a string which represents the name of the file 
   * we would like to write our board on.
   * Return: None
   *
   */
  //METHOD
  public void saveBoard(String outputBoard) throws IOException
  {
    // set outputBoard to be the new file object
    File savedBoard = new File(outputBoard);
    //closes the file automatically after done
    try ( 
      // new printWriter object
      PrintWriter output = new PrintWriter(savedBoard);
    ){

      // write grid size to the file
      output.println(GRID_SIZE);
      // write score to the file
      output.println(score);

      // loop through the grid
      for (int row = 0; row < GRID_SIZE; row++){
        for (int col = 0; col < GRID_SIZE; col++){
          // get the value of the grid element
          int gridVal = grid[row][col];
          // write the value to file with a space in between each output
          output.print(gridVal + " ");
        }
        // start the next row of grid so its in a matrix form
        output.println();
      }
    }
  }

  /*
   * Name: addRandomTile()
   * Purpose: Adds a Random Tile (of value 2 or 4) to a random Empty space on
   * the boarod
   * Parameters: None 
   * Return: None 
   *
   */  
  public void addRandomTile()
  {
    //initialize count to be 0
    int count = 0;
    // initializing locCount to be 0
    int locCount = 0;
    // declare newGridVal
    int newGridVal;
    
    //loop through the grid
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 0; col < GRID_SIZE; col++){
        // get the value of grid
        int gridVal = grid[row][col];
        
        //execute if value is 0
        if (gridVal == 0){
          //increment count
          count++;
        }

      }
    }
    // if count ends up being 0
    if (count == 0){
      //exit mehod
      return;
    }
    
    // get random generators
    Random location = this.random;
    Random value = this.random;

    //generates random values for location
    int gridLoc = location.nextInt(count);
    //generates new probability of 2 for the new grid
    int newGridProb = value.nextInt(100);
    
    //set the probablity of 2 or 4
    if (newGridProb < TWO_PROBABILITY){
      newGridVal = 2;
    }
    else{ 
      newGridVal = 4;
    }

    //loop through the grid
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 0; col < GRID_SIZE; col++){
        // set gridVal to the value of current element
        int gridVal = grid[row][col];
        
        // if the tile is empty
        if (gridVal == 0){
          // find the location the random generated   
          if(locCount == gridLoc){
            // set the value of tile to either 2 or 4
            grid[row][col] = newGridVal;
            // exit method
            return;
          }
          // if the location doesn't match, increment the loccount and keep
          // finding the location
          else {locCount++;}

        }

      }
    }

    return;
  }
   
  /*
   * Name: isGameOver
   * Purpose: Check to see if the game is over
   * Parameter: None
   * Return: boolean, true if there are no valid moves left, false is game is
   * not over
   */
  public boolean isGameOver()
  {
    // if the board can no longer move up, down, left and right
    if (!canMove(Direction.UP) && !canMove(Direction.DOWN) && 
        !canMove(Direction.LEFT) && !canMove(Direction.RIGHT)){
      // print the game over message
      System.out.println("Game Over!");
      // return that the game is over
      return true;
    }
    // game is not over
    return false;

  }

  /*
   * Name: canMoveUp
   * Purpose: helper method for the canMove method, check to see if an up move
   * can be executed
   * Parameter: None
   * Return: boolean, true if the board can move up and false if up is not a
   * valid move
   */
  public boolean canMoveUp(){
    // loop through the tiles
    for (int row = 0; row < (GRID_SIZE - 1); row++){
      for (int col = 0; col < GRID_SIZE; col++){
        // get current tile value
        int tileVal = grid[row][col];
        // get the value of the grid below
        int tileBelow = grid[row + 1][col];
        // if there is a tile that is empty and the tile below is not empty
        if (tileVal == 0 && tileBelow != 0){ 
          // the statement will be true
          return true;
        }
        // if the current tile is not zero and the tile below it has the same
        // value 
        else if (tileVal != 0 && tileVal == tileBelow){
          // the statement will be true
          return true;
        }
      }
    }
    // if can find either case then the statement is false
    return false;    
  }
  
  /*                                                                               
   * Name: canMoveDown           
   * Purpose: helper method for the canMove method, check to see if a down
   * move 
   * can be executed       
   * Parameter: None                     
   * Return: boolean, true if the board can move down and false if down is not
   * an valid move 
   */
  public boolean canMoveDown(){
    // loop through the tiles
    for (int row = 1; row < GRID_SIZE; row++){
      for (int col = 0; col < GRID_SIZE; col++){
        // get current tile value
        int tileVal = grid[row][col];
        // get the value of the tile above
        int tileAbove = grid[row - 1][col];
        // if there is a tile that is empty and the tile above is not empty
        if (tileVal == 0 && tileAbove != 0){
          // the statement will be true
          return true;
        }   
        // if there is a tile that has the same value as the tile above and it
        // is not empty
        else if (tileVal !=0 && tileVal == tileAbove){
          // the statement will be true
          return true;
        }    
      }   
    }   
    // if cant find either case then the statement is false
    return false;    
  }

  /*                           
   * Name: canMoveLeft                                              
   * Purpose: helper method for the canMove method, check to see if a left move 
   * can be executed                                                 
   * Parameter: None                                         
   * Return: boolean, true if the board can move left and false if left is not
   * a valid move        
   */
  public boolean canMoveLeft(){
  // loop through the tiles
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 0; col < (GRID_SIZE - 1); col++){
        // get the current tile value
        int tileVal = grid[row][col];
        // get the value of the tile on the right
        int tileOnRight = grid[row][col + 1];
        // if there is a tile that is empty and the tile on the right is not
        // empty
        if (tileVal == 0 && tileOnRight != 0){
        // the statement will be true
          return true;
        }
        // if there is a tile that has the same value as the tile on the right
        // and it's not empty
        else if (tileVal != 0 && tileVal == tileOnRight){
        // the statement will be true
          return true;
        }
      }
    }
    // if can't find either case then the statement is false
    return false;
  }
 
  /*                                             
   * Name: canMoveRight                                      
   * Purpose: helper method for the canMove method, check to see if a right
   * move can be executed                                  
   * Parameter: None                                  
   * Return: boolean, true if the board can move right and false if right is not
   * a valid move         
   */
  public boolean canMoveRight(){
    // loop through the tiles 
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 1; col < GRID_SIZE; col++){
        // get the current tile value
        int tileVal = grid[row][col];
        //get hte value of the tile on the left
        int tileOnLeft = grid[row][col - 1];
        // if there is a tile that is empty and the tile on the left is not
        // empty
        if (tileVal == 0 && tileOnLeft != 0){
          // the statement will be true
          return true;
        }
        // if there is a tile that has the same value as the tile on the left
        else if (tileVal != 0 && tileVal == tileOnLeft){
          // the statement will be true
          return true;
        }
      }
    }
    // if can't find either case then the statement is false
    return false;
  }

  /*                                             
   * Name: canMove                                 
   * Purpose: Determine if we can move in a given direction
   * Parameter: direction we desire to move at                        
   * Return: boolean, true if the board can move in that direction and false 
   * if it is not valid        
   */
  public boolean canMove(Direction direction){
    // if input direction is up
    if (direction.equals(Direction.UP)){
      // check if up move is possible
      if(this.canMoveUp()){
        return true;
      }
    }
    // if input direction is down
    else if (direction.equals(Direction.DOWN)){
      // check if down move is possible
      if (this.canMoveDown()){
        return true;
      }
    }
    // if input direction is left
    else if (direction.equals(Direction.LEFT)){
    // check if left move is possible 
      if (this.canMoveLeft()){
        return true;
      }
    }
    // if input direction is right 
    else if (direction.equals(Direction.RIGHT)){
       // check if right move is possible 
      if (this.canMoveRight()){
        return true;
      }
    }
    // return false otherwise
    return false;
  }
  
  /* 
   * Name: moveUp
   * Purpose: helper method for move to move the board in upward direction
   * Parameter: None
   * Reture: None
   */
  public void moveUp(){
    // loops through rows then columns so it will check and change values 
    // column by column
    for (int col = 0; col < GRID_SIZE; col++){
      for (int row = 1; row < GRID_SIZE; row++){
        // get current tile value
        int tileVal = grid[row][col];
        // get value of the tile on top
        int upperTile = grid[row - 1][col];
        // if the tile on top is zero and the current tile is not zero
        if (upperTile == 0 && tileVal != 0){
          // set upper tile to the value of current tile
          grid[row - 1][col] = tileVal;
          // set current tile to zero
          grid[row][col] = 0;
          // set row back to zero (not 1 because row increments at the end of
          // the loop) so it can iterate over again 
          row = 0;
        }
      }
    }

    // loop through the tiles
    for (int col = 0; col <GRID_SIZE; col++){
      for (int row = 0; row < GRID_SIZE - 1; row++){
        // get current tile
        int tileVal = grid[row][col];
        // get value of the lower tile
        int lowerTile = grid[row + 1][col];
        // if the two tiles have the same value and they are not zero
        if (tileVal != 0 && tileVal == lowerTile){
          // add the value of the tiles and set it to the current tile
          grid[row][col] = tileVal + tileVal;
          // set the lower tile to zero
          grid[row + 1][col] = 0;
          // update the score
          score = score + tileVal + tileVal;
        }
      }
    }

    // loop through the tiles again to check for any zeros after merging
    for (int col = 0; col< GRID_SIZE; col++){
      for (int row = 1; row <  GRID_SIZE; row++){
        // get current tile
        int tileVal = grid[row][col];
        // get the value of the tile above
        int upperTile = grid[row - 1][col];
        // if the upper tile is zero and the current tile is not zero
        if (upperTile == 0 && tileVal != 0){
          // set the upper tile to the current value
          grid[row - 1][col] = tileVal;
          // set the current tile to zero
          grid[row][col] = 0;
          // set row back to zero to check again
          row = 0;
        }
      }
    }

  }

  /*                                     
   * Name: moveDown                                           
   * Purpose: helper method for move to move the board in downward direction 
   * Parameter: None                                
   * Reture: None                                 
   */
  public void moveDown(){
    // loops through rows then columns so it will check and change values
    // column by column    
    for (int col = 0; col < GRID_SIZE; col++){
      for (int row = 0; row < (GRID_SIZE - 1); row++){
        // get current tile value
        int tileVal = grid[row][col];
        // get value of the tile on the bottom
        int lowerTile = grid[row + 1][col];
        // if the tile on bottom is zero and the current tile is not zero
        if (lowerTile == 0 && tileVal != 0){ 
          // set lower tile to the value of current tile
          grid[row + 1][col] = grid[row][col];
          // set current tile to zero
          grid[row][col] = 0;
          // set row to -1 (not 0 because row increments at the end of
          // the loop) so it can iterate over again  
          row = -1;
        }   
      }   
    }   
    
    // loop through the tiles
    for (int col = 0; col < GRID_SIZE; col++){
      for (int row = GRID_SIZE - 1; row > 0; row--){
        // get current tile
        int tileVal = grid[row][col];
        // get value of the upper tile
        int upperTile = grid[row - 1][col];
        // if the two tiles have the same value and they are not zero
        if (tileVal != 0 && tileVal == upperTile){
          // add the value of the tiles and set it to the current tile
          grid[row][col] = tileVal + tileVal;
          // set the upper tile to zero
          grid[row - 1][col] = 0;
          // update the score
          score = score + tileVal + tileVal;
        }   
      }   
    }   
        
    // loop through the tiles again to check for any zeros after merging    
    for (int col = 0; col < GRID_SIZE; col++){
      for (int row = 0; row < (GRID_SIZE - 1); row++){
        // get the value of the tile below
        int lowerTile = grid[row + 1][col];
        // get current tile
        int tileVal = grid[row][col];
        // if the lower tile is zero and the current tile is not zero
        if (lowerTile == 0 && tileVal != 0){ 
          // set the lower tile to the current value
          grid[row + 1][col] = grid[row][col];
          //set the current tile to zero
          grid[row][col] = 0;
          // set row back to -1 to check again
          row = -1;
        }   
      }   
    }   
  }

  /*                                                   
   * Name: moveLeft                                             
   * Purpose: helper method for move to move the board in leftward direction
   * Parameter: None                                       
   * Reture: None                                      
   */
  public void moveLeft(){
    // loops through columns then rows so it will check and change values 
    // row by row
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 1; col < GRID_SIZE; col++){
        // get current tile value
        int tileVal = grid[row][col];
        // get value of the tile on the left
        int leftTile = grid[row][col - 1];
        // if the tile on the left is zero and the current tile is not zero 
        if (leftTile == 0 && tileVal != 0){
          //  set left tile to the value of current tile
          grid[row][col - 1] = grid[row][col];
          // set current tile to zero 
          grid[row][col] = 0;
          // set col back to zero (not 1 because row increments at the end of
          // the loop) so it can iterate over again
          col = 0;
        }
      }
    }
    
    // loop through the tiles 
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 0; col < (GRID_SIZE - 1); col++){
        // get value of the right tile
        int rightTile = grid[row][col + 1];
        // get current tile   
        int tileVal = grid[row][col];
        // if the two tiles have the same value and they are not zero
        if (tileVal != 0 && tileVal == rightTile){
          // add the value of the tiles and set it to the current tile
          grid[row][col] = tileVal + tileVal;
          // set right tile to zero
          grid[row][col + 1] = 0;
          // update the score
          score = score + tileVal + tileVal;
        }
      }
    }
    
    // loop through the tiles again to check for any zeros after merging
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 1; col < GRID_SIZE; col++){
        // get current tile value
        int tileVal = grid[row][col];
        // get value of the tile on the left 
        int leftTile = grid[row][col - 1];
        // if the tile on the left is zero and the current tile is not zero
        if (leftTile == 0 && tileVal != 0){
        //  set left tile to the value of current tile 
          grid[row][col - 1] = grid[row][col];
          // set current tile to zero 
          grid[row][col] = 0;
          // set col back to zero (not 1 because row increments at the end of   
          // the loop) so it can iterate over again
          col = 0;
        }
      }
    }       
  }

  /*                                                                   
   * Name: moveRight                                                       
   * Purpose: helper method for move to move the board in rightward direction
   * Parameter: None                                             
   * Reture: None                                     
   */
  public void moveRight(){
    // loops through columns then rows so it will check and change values
    // row by row  
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 0; col < (GRID_SIZE - 1); col++){
        // get value of grid on the right 
        int rightTile = grid[row][col + 1];
        // get current grid
        int tileVal = grid[row][col];
        // if hte tile on the right is zero and the current tile is not zero
        if (rightTile == 0 && tileVal != 0){
          // set right tile to the value of current tile 
          grid[row][col + 1] = grid[row][col];
          // set the current tile to zero
          grid[row][col] = 0;
          // set col back to -1 (not 0 because row increments at the end of
          // the loop) so it can iterate over again
          col = -1;
        }
      }
    }
    // loop through the tiles  
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = GRID_SIZE - 1; col > 0; col--){
        // get the value of the tile on the left
        int leftTile = grid[row][col - 1];
        // get current tile 
        int tileVal = grid[row][col];
        // if the two tiles have the same value and they are not zero
        if (tileVal != 0 && tileVal == leftTile){
          // add the value of the tiles and set it to the current tile 
          grid[row][col] = tileVal + tileVal;
          // set the left tile to zero
          grid[row][col -1] = 0;
          // update the score
          score = score + tileVal + tileVal;
        }
      }
    }

    // loop through the tiles again to check for any zeros after merging
    for (int row = 0; row < GRID_SIZE; row++){
      for (int col = 0; col < (GRID_SIZE -1); col++){
      // get value of grid on the right
        int rightTile = grid[row][col + 1];
        // get current grid
        int tileVal = grid[row][col];
        // if hte tile on the right is zero and the current tile is not zero
        if (rightTile == 0 && tileVal != 0){
          // set right tile to the value of current tile
          grid[row][col + 1] = grid[row][col];
          // set the current tile to zero
          grid[row][col] = 0;
          // set col back to -1 (not 0 because row increments at the end of
          // the loop) so it can iterate over again 
          col = -1;
        }
      }
    }
    
  }

  /*                                                              
   * Name: move                                                    
   * Purpose: perform a move operation
   * Parameter: direction we desire to move in 
   * Reture: boolean, true if move is successful, false if unsucessful
   */
  public boolean move(Direction direction)
  {
    // check to see if the board can move in the direction that was inputed
    if (!this.canMove(direction)){
      // return false if move cannot be executed
      return false;
    }
    // if move can be executed
    else{
      //if input direction was up
      if (direction.equals(Direction.UP)){
        this.moveUp();
      }
      //if input direction was down
      if (direction.equals(Direction.DOWN)){
        this.moveDown();
      }
      //if input direction was left
      if (direction.equals(Direction.LEFT)){
        this.moveLeft();
      }
      //if input direction was right 
      if (direction.equals(Direction.RIGHT)){
        this.moveRight();
      }
      // return true for successful move 
      return true;
    }
  } 

  /* 
   * Name: getGrid()
   * Purpose: Return the reference to the 2048 Grid 
   * Parameters: None
   * Return: 2-D integer array, elements are values of the tiles
   *
   */    
  public int[][] getGrid()
  {
    return grid;
  }

  /* 
   * Name: getScore()
   * Purpose: Return the score
   * Parameters: None
   * Return: Integer representing the score
   *
   */
  public int getScore()
  {
    return score;
  }

  /* 
   * Name: toString()
   * Purpose: Return the score and the board
   * Parameters: None
   * Return: String representing the score and the board.
   *
   */
  @Override
  public String toString()
  {
    StringBuilder outputString = new StringBuilder();
    outputString.append(String.format("Score: %d\n", score));
      for (int row = 0; row < GRID_SIZE; row++)
      {
        for (int column = 0; column < GRID_SIZE; column++)
            outputString.append(grid[row][column] == 0 ? "    -" :
                                String.format("%5d", grid[row][column]));

        outputString.append("\n");
      }
      return outputString.toString();
  }
}
