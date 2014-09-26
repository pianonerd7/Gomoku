import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

/**
 * The Gomoku game. Two players alternate until win. Winning the game by getting five in a row.
 * Starts with black piece. 
 * @author Jiaxin He
 */
public class Gomoku implements ActionListener{
  
  /** Row in the grid. */
  private int rows;
  
  /** Column in the grid. */
  private int columns;
  
  /** The board of the game that contains the JButton */
  private JPanel board;
  
  /** Two dimensional array of JButtons. Responds when clicked. */
  private JButton[][] grid;
  
  /** Frame of the board. */
  private JFrame frame = new JFrame();
  
  /** Whether game is won or not. */
  private boolean isGameWon;
  
  /** Current player */
  private int player = 1;
  
  /**
   * Creates a new 19 by 19 grid with green background.
   */
  public Gomoku() {
    this(19,19);
  }
  
  /**
   * Creates a new rows by columns grid with green background.
   * @param rows The number of rows desired on the grid.
   * @param columns The number of columns desired on the grid.
   */
  public Gomoku(int rows, int columns){
    
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    }
    catch (Exception e) {
    }
    
    this.rows = rows;
    this.columns = columns;
    board = new JPanel(new GridLayout(rows, columns));
    grid = new JButton[rows][columns];
    isGameWon = false;
    
    for (int i = 0; i < rows; i++) {
      for (int k = 0; k < columns; k++) {
        grid[i][k] = new JButton();
        grid[i][k].addActionListener(this);
        grid[i][k].setBackground(Color.CYAN);
        board.add(grid[i][k]);
        frame.setVisible(true);
      }
    }
    frame.add(board);
    frame.setSize(rows*30, columns*30);
  }
  
  /**
   * The response made to the button after it is pressed. Using the numberInLine and isOpen method,
   * win is checked in this method.
   * @param e The action occured.
   */
  public void actionPerformed(ActionEvent e) {
    
    if (isGameWon) {    
      return;
    }
    
    int row = 0;
    int column = 0;
    
    JButton button = (JButton) e.getSource();
    
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (grid[i][j].equals(button)){
          row = i;
          column = j;
        }
      }
    }        
    
    if (button.getBackground() != Color.CYAN){
      return;
    }
    
    if (player%2 != 0){
      button.setBackground(Color.BLACK);
    }
    
    else if (player%2 == 0){
      button.setBackground(Color.WHITE);
    }
    
    boolean exceptionThree = threeThree(row, column);
    boolean exceptionFour = fourFour(row, column);
    
    if (exceptionThree == true) {
      button.setBackground(Color.CYAN);
      JOptionPane.showMessageDialog(null, "You can't play this piece! It creates a three-three!");
      player--;
    }
    
    if (exceptionFour == true) {
      button.setBackground(Color.CYAN);
      JOptionPane.showMessageDialog(null, "You can't play this piece! It creates a four-four!");
      player--;
    }  
    
    if (isWon(row, column) == true) {
      if (player % 2 == 0) {
        JOptionPane.showMessageDialog(null, "The white player has won!");
      }
      if (player % 2 != 0) {
        JOptionPane.showMessageDialog(null, "The black player has won!");
      }   
    }
    
    player++;
  }
  
  /**
   * Checks to see if there is a three three. Care if there are open spaces, and only a three three if there
   * are open spaces.
   * @param row The row of the click.
   * @param column The column of the click.
   * @return The boolean representation of whether there was a three three.
   */
  public boolean threeThree(int row, int column) {
    boolean threeThree = false;
    
    int up = numberInLine(grid, row, column, "up");
    int right = numberInLine(grid, row, column, "right");
    int down = numberInLine(grid, row, column, "down");
    int left = numberInLine(grid, row, column, "left");
    int upRight = numberInLine(grid, row, column, "up-right");
    int rightDown = numberInLine(grid, row, column, "right-down");
    int downLeft = numberInLine(grid, row, column, "down-left");
    int leftUp = numberInLine(grid, row, column, "left-up");
    
    boolean upp = isOpen(grid, row, column, "up");
    boolean rightt = isOpen(grid, row, column, "right");
    boolean downn = isOpen(grid, row, column, "down");
    boolean leftt = isOpen(grid, row, column, "left");
    boolean upRightt = isOpen(grid, row, column, "up-right");
    boolean rightDownn = isOpen(grid, row, column, "right-down");
    boolean downLeftt = isOpen(grid, row, column, "down-left");
    boolean leftUpp = isOpen(grid, row, column, "left-up");
    
    
    //three three
    if ((up == 1 && right == 2 && down == 3 && left == 2) || (up == 2 && right == 1 && down == 2 && left == 3) ||
        (up == 3 && right == 2 && down == 1 && left == 2) || (up == 2 && right == 3 && down == 2 && left == 1) ||
        (up == 1 && right == 3 && down == 3 && left == 1) || (up == 1 && right == 1 && down == 3 && left == 3) ||
        (up == 3 && right == 1 && down == 1 && left == 3) || (up == 3 && right == 3 && down == 1 && left == 1) ||
        (up == 2 && right == 2 && down == 2 && left == 2)) {
      if (upp == true && rightt == true && downn == true && leftt == true){
        threeThree = true;
      }
    }
    
    if ((upRight == 2 && rightDown == 2 && downLeft == 2 && leftUp == 2) || (upRight == 1 && rightDown == 2 && downLeft == 3 && leftUp == 2) ||
        (upRight == 3 && rightDown == 2 && downLeft == 1 && leftUp == 2) || (upRight == 1 && rightDown == 3 && downLeft == 3 && leftUp == 1) ||
        (upRight == 1 && rightDown == 1 && downLeft == 3 && leftUp == 3) || (upRight == 3 && rightDown == 1 && downLeft == 1 && leftUp == 3) ||
        (upRight == 3 && rightDown == 3 && downLeft == 1 && leftUp == 1) || (upRight == 2 && rightDown == 3 && downLeft == 2 && leftUp == 1) ||
        (upRight == 2 && rightDown == 1 && downLeft == 2 && leftUp == 3)) {
      if (upRightt == true && rightDownn == true && downLeftt == true && leftUpp == true){
        threeThree = true;
      }
    }
    
    if (((up == 1 && right == 3 && down == 1 && left == 1) && (upRight == 3 && rightDown == 1 && downLeft == 1 && leftUp == 1)) || 
        ((up == 1 && right == 2 && down == 1 && left == 2) && (upRight == 3 && rightDown == 1 && downLeft == 1 && leftUp == 1)) ||
        ((up == 1 && right == 1 && down == 1 && left == 3) && (upRight == 3 && rightDown == 1 && downLeft == 1 && leftUp == 1)) || 
        ((up == 1 && right == 2 && down == 1 && left == 2) && (upRight == 2 && rightDown == 1 && downLeft == 2 && leftUp == 1)) ||
        ((up == 1 && right == 1 && down == 1 && left == 3) && (upRight == 1 && rightDown == 1 && downLeft == 3 && leftUp == 1)) || 
        ((up == 1 && right == 2 && down == 1 && left == 2) && (upRight == 1 && rightDown == 1 && downLeft == 3 && leftUp == 1)) ||
        ((up == 1 && right == 3 && down == 1 && left == 1) && (upRight == 1 && rightDown == 1 && downLeft == 3 && leftUp == 1)) ||
        ((up == 1 && right == 1 && down == 1 && left == 3) && (upRight == 2 && rightDown == 1 && downLeft == 2 && leftUp == 1)) ||
        ((up == 1 && right == 3 && down == 1 && left == 1) && (upRight == 2 && rightDown == 1 && downLeft == 2 && leftUp == 1))) {
      if ((upRightt == true && downLeftt == true) && (rightt == true && leftt == true)) {
        threeThree = true;
      }
    }
    
    if (((up == 1 && right == 3 && down == 1 && left == 1) && (upRight == 1 && rightDown == 3 && downLeft == 1 && leftUp == 1)) ||    
        ((up == 1 && right == 1 && down == 1 && left == 3) && (upRight == 1 && rightDown == 1 && downLeft == 1 && leftUp == 3)) ||   
        ((up == 1 && right == 2 && down == 1 && left == 2) && (upRight == 1 && rightDown == 1 && downLeft == 1 && leftUp == 3)) ||
        ((up == 1 && right == 3 && down == 1 && left == 1) && (upRight == 1 && rightDown == 1 && downLeft == 1 && leftUp == 3)) ||
        ((up == 1 && right == 2 && down == 1 && left == 2) && (upRight == 1 && rightDown == 2 && downLeft == 1 && leftUp == 2)) || 
        ((up == 1 && right == 2 && down == 1 && left == 2) && (upRight == 1 && rightDown == 3 && downLeft == 1 && leftUp == 1)) ||    
        ((up == 1 && right == 1 && down == 1 && left == 3) && (upRight == 1 && rightDown == 3 && downLeft == 1 && leftUp == 1)) ||   
        ((up == 1 && right == 1 && down == 1 && left == 3) && (upRight == 1 && rightDown == 2 && downLeft == 1 && leftUp == 2)) ||
        ((up == 1 && right == 3 && down == 1 && left == 1) && (upRight == 1 && rightDown == 2 && downLeft == 1 && leftUp == 2))) {
      if ((leftUpp == true && rightDownn == true) && (rightt == true && leftt == true)) {
        threeThree = true;
      }
    }
    return threeThree;
  }
  
  /**
   * Checks to see if there is a four four. Does not care if there are open spaces.
   * @param row The row of the click.
   * @param column The column of the click.
   * @return The boolean representation of whether there was a four four.
   */
  public boolean fourFour(int row, int column) {
    boolean fourFour = false;
    
    int up = numberInLine(grid, row, column, "up");
    int right = numberInLine(grid, row, column, "right");
    int down = numberInLine(grid, row, column, "down");
    int left = numberInLine(grid, row, column, "left");
    int upRight = numberInLine(grid, row, column, "up-right");
    int rightDown = numberInLine(grid, row, column, "right-down");
    int downLeft = numberInLine(grid, row, column, "down-left");
    int leftUp = numberInLine(grid, row, column, "left-up");
    
    boolean upp = isOpen(grid, row, column, "up");
    boolean rightt = isOpen(grid, row, column, "right");
    boolean downn = isOpen(grid, row, column, "down");
    boolean leftt = isOpen(grid, row, column, "left");
    boolean upRightt = isOpen(grid, row, column, "up-right");
    boolean rightDownn = isOpen(grid, row, column, "right-down");
    boolean downLeftt = isOpen(grid, row, column, "down-left");
    boolean leftUpp = isOpen(grid, row, column, "left-up");
    
    //four four
    if ((up == 4 && down == 1 && left == 1 && right == 4) || (up == 1 && down == 4 && left == 1 && right == 4) || 
        (up == 4 && down == 1 && left == 4 && right == 1) || (up == 1 && down == 4 && left == 4 && right == 1) ||
        (up == 3 && down == 2 && left == 1 && right == 4) || (up == 2 && down == 3 && left == 1 && right == 4) || 
        (up == 3 && down == 2 && left == 4 && right == 1) || (up == 2 && down == 3 && left == 4 && right == 1) ||
        (up == 3 && down == 2 && left == 3 && right == 2) || (up == 2 && down == 3 && left == 3 && right == 2) ||
        (up == 3 && down == 2 && left == 2 && right == 3) || (up == 2 && down == 3 && left == 2 && right == 3) ||
        (up == 4 && down == 1 && left == 2 && right == 3) || (up == 1 && down == 4 && left == 2 && right == 3) ||
        (up == 4 && down == 1 && left == 3 && right == 2) || (up == 1 && down == 4 && left == 3 && right == 2) ||
        (upRight == 1 && rightDown == 4 && downLeft == 4 && leftUp == 1) || (upRight == 1 && rightDown == 1 && downLeft == 4 && leftUp == 4) ||
        (upRight == 4 && rightDown == 1 && downLeft == 1 && leftUp == 4) || (upRight == 4 && rightDown == 4 && downLeft == 1 && leftUp == 1) ||
        (upRight == 1 && rightDown == 3 && downLeft == 4 && leftUp == 2) || (upRight == 2 && rightDown == 3 && downLeft == 3 && leftUp == 2) ||
        (upRight == 3 && rightDown == 3 && downLeft == 2 && leftUp == 2) || (upRight == 3 && rightDown == 2 && downLeft == 2 && leftUp == 3) || 
        (upRight == 2 && rightDown == 2 && downLeft == 3 && leftUp == 3) || (upRight == 1 && rightDown == 2 && downLeft == 4 && leftUp == 3) ||
        (upRight == 4 && rightDown == 3 && downLeft == 1 && leftUp == 2) || (upRight == 4 && rightDown == 2 && downLeft == 1 && leftUp == 3) ||
        (upRight == 2 && rightDown == 4 && downLeft == 3 && leftUp == 1) || (upRight == 3 && rightDown == 1 && downLeft == 2 && leftUp == 4) ||
        (upRight == 2 && rightDown == 1 && downLeft == 3 && leftUp == 4) || (upRight == 3 && rightDown == 4 && downLeft == 2 && leftUp == 1)) {
      fourFour = true;
    } 
    return fourFour;
  }
  
  /**
   * Checks to see if there is a game won, or 5 pieces of the same color in a row.
   * @param row The row of the click.
   * @param column The column of the click.
   * @return The boolean representation of game won.
   */
  public boolean isWon(int row, int column) {
    
    boolean isWon = false;
    
    int up = numberInLine(grid, row, column, "up");
    int right = numberInLine(grid, row, column, "right");
    int down = numberInLine(grid, row, column, "down");
    int left = numberInLine(grid, row, column, "left");
    int upRight = numberInLine(grid, row, column, "up-right");
    int rightDown = numberInLine(grid, row, column, "right-down");
    int downLeft = numberInLine(grid, row, column, "down-left");
    int leftUp = numberInLine(grid, row, column, "left-up");
    
    int vertical = up + down - 1;
    int horizontal = right + left - 1;
    int diagonalPositive = downLeft + upRight - 1;
    int diagonalNegative = leftUp + rightDown - 1;
    
    if (vertical == 5 || horizontal == 5 || diagonalPositive == 5 || diagonalNegative == 5) {
      isGameWon = true;
      isWon = true;
    }
    return isWon;
  }
  
  /**
   * Determines the number of colors in a certain direction. Directions consists of up, right, down, left, and the 
   * four diagonals.
   * @param board The two D array of JButtons representing the board of the game.
   * @param row The position for the row of the currently played piece.
   * @param column The position for the column of the currently played piece.
   * @param direction The direction to search (up, right, down, left, and the four diagonals).
   * @return The number of consecutive same color pieces counting the one starting with.
   */
  public int numberInLine (JButton[][] board, int row, int column, String direction){
    int r = row;
    int c = column;
    int numberInLine = 1;
    boolean exit = false;
    
    if (direction.equals("up")) {
      for (int i = row; ((i > 0) && exit == false); i--) {
        if ((board[row][column].getBackground().equals(board[i-1][column].getBackground())) && exit == false){
          
          if (board[i-1][column].getBackground().equals(board[i][column].getBackground()) == false){
            exit = true;
          }
          else {
            numberInLine++;
          }
        }
      }
    }
    
    else if (direction.equals("right")) {
      for (int i = column; ((i < this.columns-1) && exit == false); i++) {
        if ((board[row][column].getBackground().equals(board[row][i+1].getBackground())) && exit == false){
          
          if (board[row][i+1].getBackground().equals(board[row][i].getBackground()) == false){
            exit = true;
          }
          else {
            numberInLine++;
          }
        }
      }
    }
    
    else if (direction.equals("down")) {
      for (int i = row; ((i < this.rows-1) && exit == false); i++) {
        if ((board[row][column].getBackground().equals(board[i+1][column].getBackground())) && exit == false){
          
          if (board[i+1][column].getBackground().equals(board[i][column].getBackground()) == false){
            exit = true;
          }
          else {
            numberInLine++;
          }
        }
      }
    }
    
    else if (direction.equals("left")) {
      for (int i = column; ((i > 0) && exit == false); i--) {
        if ((board[row][column].getBackground().equals(board[row][i-1].getBackground())) && exit == false){
          
          if (board[row][i-1].getBackground().equals(board[row][i].getBackground()) == false){
            exit = true;
          }
          else {
            numberInLine++;
          }
        }
      }
    }
    
    else if (direction.equals("up-right")) {
      while ((r > 0) && (c < this.columns-1) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r-1][c+1].getBackground())) && exit == false) {
          
          if (board[row][column].getBackground().equals(board[r-1][c+1].getBackground()) == true) {
            numberInLine++;
          }
          else {
            exit = true;
          }
        }
        r--;
        c++;
      }
    }
    
    else if (direction.equals("right-down")) {
      while ((r < this.rows-1) && (c < this.columns-1) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r+1][c+1].getBackground())) && exit == false) {
          
          if (board[row][column].getBackground().equals(board[r+1][c+1].getBackground()) == true) {
            numberInLine++;
          }
          else {
            exit = true;
          }
        }
        r++;
        c++;
      }
    }
    
    else if (direction.equals("down-left")) {
      while ((r < this.rows-1) && (c > 0) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r+1][c-1].getBackground())) && exit == false) {
          
          if (board[row][column].getBackground().equals(board[r+1][c-1].getBackground()) == true) {
            numberInLine++;
          }
          else {
            exit = true;
          }
        }
        r++;
        c--;
      }
    }
    
    else if (direction.equals("left-up")) {
      while ((r > 0) && (c > 0) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r-1][c-1].getBackground())) && exit == false) {
          
          if (board[row][column].getBackground().equals(board[r-1][c-1].getBackground()) == true) {
            numberInLine++;
          }
          else {
            exit = true;
          }
        }
        r--;
        c--;
      }
    }
    
    return numberInLine;
  }
  
  /**
   * Determines if there is an open space following the input direction at position row, column on the board. 
   * Directions consists of up, right, down, left, and the four diagonals.
   * @param board The two D array of JButtons representing the board of the game.
   * @param row The position for the row of the currently played piece.
   * @param column The position for the column of the currently played piece.
   * @param direction The direction to search (up, right, down, left, and the four diagonals).
   * @return The boolean representation if there is an open space following the direction starting at the row, column on the board.
   */
  public boolean isOpen (JButton[][] board, int row, int column, String direction) {
    
    int r = row;
    int c = column;
    boolean open = false;
    boolean exit = false;
    
    if (direction.equals("up")) {
      while ((r > 0) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r-1][column].getBackground())) == false 
              && exit == false){
          
          if (board[r-1][column].getBackground().equals(Color.GREEN)) {
            open = true;
            exit = true;
          }
          else {
            open = false;
            exit = true;
          }   
        }
        r--;
      }
    }
    
    else if (direction.equals("right")) {
      while ((c < this.columns-1) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[row][c+1].getBackground())) == false
              && exit == false) {
          if (board[row][c+1].getBackground().equals(Color.GREEN)) {
            open = true;
            exit = true;
          }
          else {
            open = false;
            exit = true;
          }   
        }
        c++;
      }
    }
    
    else if (direction.equals("down")) {
      while ((r < this.rows-1) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r+1][column].getBackground())) == false
              && exit == false){
          if (board[r+1][column].getBackground().equals(Color.GREEN)){
            open = true;
            exit = true;
          }
          else {
            open = false;
            exit = true;
          }   
        }
        r++;
      }
    }
    
    else if (direction.equals("left")) {
      while ((c > 0) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[row][c-1].getBackground())) == false
              && exit == false){
          if (board[row][c-1].getBackground().equals(Color.GREEN)){
            open = true;
            exit = true;
          }
          else {
            open = false;
            exit = true;
          }   
        }
        c--;
      }
    }
    
    else if (direction.equals("up-right")) {
      while ((r > 0) && (c < this.columns-1) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r-1][c+1].getBackground())) == false
              && exit == false) {
          
          if (board[r-1][c+1].getBackground().equals(Color.GREEN)) {
            open = true;
            exit = true;
          }
          else {
            open = false;
            exit = true;
          }   
        }
        r--;
        c++;
      }
    }
    
    else if (direction.equals("right-down")) {
      while ((r < this.rows-1) && (c < this.columns-1) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r+1][c+1].getBackground())) == false
              && exit == false) {
          
          if (board[r+1][c+1].getBackground().equals(Color.GREEN)) {
            open = true;
            exit = true;
          }
          else {
            open = false;
            exit = true;
          }   
        }
        r++;
        c++;
      }
    }
    
    else if (direction.equals("down-left")) {
      while ((r < this.rows-1) && (c > 0) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r+1][c-1].getBackground())) == false
              && exit == false) {
          
          if (board[r+1][c-1].getBackground().equals(Color.GREEN)) {
            open = true;
            exit = true;
          }
          else {
            open = false;
            exit = true;
          }   
        }
        r++;
        c--;
      }
    }
    
    else if (direction.equals("left-up")) {
      while ((r > 0) && (c > 0) && (exit == false)) {
        if ((board[row][column].getBackground().equals(board[r-1][c-1].getBackground())) == false
              && exit == false) {
          
          if (board[r-1][c-1].getBackground().equals(Color.GREEN)) {
            open = true;
            exit = true;
          }
          else {
            open = false;
            exit = true;
          }   
        }
        r--;
        c--;
      }
    }
    return open;
  }
  
  /**
   * Helper method sets the grid at row, column to the input color.
   * @param row The position for the color change.
   * @param column The position for the color change.
   * @param color The color to change to.
   */
  public void setColor (int row, int column, Color color) {
    grid[row][column].setBackground(color);     
  }
  
  /**
   * Helper method that returns the grid; used in testing.
   * @return The grid of JButtons.
   */
  public JButton[][] getGrid() {
    return grid;
  }
  
  /**
   * Main method runs the program and creates a default 19 by 19 grid if no argument,
   * or creates a rows by columns grid with the input.
   * @param args The inputs for Gomoku.
   * @throws exception When invalid argument is given.
   */
  public static void main(String args[]) throws Exception {
    try{
      if (args.length == 0) {
        new Gomoku();
      }
      else if (args.length == 2) {
        new Gomoku(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
      }
    }
    catch (Exception e) {
    }
    finally {
      if (args.length == 1){
        JOptionPane.showMessageDialog(null, "Sorry! Invalid! Only one arguement!");
      }
      else if (args.length != 0 && (Integer.parseInt(args[0]) <= 0 ||Integer.parseInt(args[1]) <= 0)) {
        JOptionPane.showMessageDialog(null, "Sorry! Invalid! 0 or less cannot be inputs!");
      }
    }
  }
}