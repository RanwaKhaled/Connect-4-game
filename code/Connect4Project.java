/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package connect4project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import static connect4project.minimax.bestAlphaBeta;
import static connect4project.minimax.bestMoveMinimax;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class Connect4Project {

    public static int[][] createBoard(){
        int[][] board = new int[6][7];
        return board;
    }
    
    // if the last element in the column is not zero then the column is filled 
    public static boolean isValidLocation(int[][] board, int col){
        return board[5][col]==0;
    }
    
    public static int getNextValidRow(int[][] board, int col){
        int row=0;
        for(;row<6;row++){
            if(board[row][col]==0)
                return row;
        }
        return 0;
    }
    
    //piece= either the number or the color of the player
    public static void dropPiece(int[][] board, int row,int col,int piece){
        board[row][col]=piece;
    }
    
    //print board 
    public static void  printBoard(int[][] board){
        for(int i=board.length-1;i>-1;i--){
            for(int j=0;j<board[i].length;j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }
    
    public static boolean isFilled(int[][] board){
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[i].length;j++){
             if(board[i][j]==0)
                return false;
            }
        }
        return true;
    }
    
    
    public static int calculateWinner(int[][] board){
        int score1=0;
        int score2=0;
        //check horizontal
        for(int c =0;c<7-3;c++){
            for(int r=0; r<6;r++){
                if(board[r][c]==board[r][c+1]&&board[r][c+2]==board[r][c]&&board[r][c+3]==board[r][c])
                {    if(board[r][c]==1)
                     score1++;
                    else
                        score2++;
                }   
            }
        }
        //check vertical
        for(int c =0;c<7;c++){
            for(int r=0; r<6-3;r++){
                if(board[r][c]==board[r+1][c]&&board[r+1][c]==board[r][c]&&board[r+2][c]==board[r][c])
                {    if(board[r][c]==1)
                     score1++;
                    else
                      score2++;
                }
            }
        }
        
        //check positively sloped diagonals
        for(int c =0;c<7-3;c++){
            for(int r=0; r<6-3;r++){
                if(board[r][c]==board[r+1][c+1]&&board[r+2][c+2]==board[r][c]&&board[r+3][c+3]==board[r][c]){
                  if(board[r][c]==1)
                     score1++;
                    else
                      score2++;
                }
            }
        }
        
        //check negatively sloped diagonals
        for(int c =0;c<7-3;c++){
            for(int r=3; r<6;r++){
                if(board[r][c]==board[r-1][c+1]&&board[r][c]==board[r-2][c+2]&&board[r][c]==board[r-3][c+3]){
                     if(board[r][c]==1)
                     score1++;
                    else
                      score2++;
                }
            }
        }
    //return 1 if player 1 wins
    //return 2 if player 2 wins
    //According to the highest score
    if(score1>score2)
            return 1;
        else if(score1<score2)
            return 2;
        else 
            return 0;
        
    }
    
    

    
    public static int squareSize =100;
    public static int nbrRow=6;
    public static int nbrCol=7;
    
    //board drawing method
    public static class shape extends JPanel{
        int [][] board;
        int x=7;
        
        shape(int [][] board){
            this.board=board;
        }
        
        @Override
        public void paint(Graphics g){
            
         for(int col=0; col<nbrCol;col++){
            for(int row=0; row<nbrRow;row++){

            //fillRect(x, y, width, height)
            g.setColor(Color.blue);
            g.fillRect(col*squareSize,row*squareSize+squareSize,squareSize, squareSize);
            
           // if(board[row][col]==0){}
            g.setColor(Color.black);
            g.fillOval(col*squareSize+7,row*squareSize+squareSize+7,85 ,85); 
            
            for(int r=5;r>-1;r--){
              for(int c =0;c<7;c++){
              if(board[r][c]==1){
                g.setColor(Color.red);
                g.fillOval(c*squareSize+7,(5-r)*squareSize+squareSize+7,85 ,85); 
            }
            else if(board[r][c]==2){
                g.setColor(Color.yellow);
                g.fillOval(c*squareSize+7,(5-r)*squareSize+squareSize+7,85 ,85); 
            }
              }}
            }
         }
    }
    }
    
    //piece drawing mthod
       public static class circle{
        int x=7; 
        int y=5;
        Color color;
        
        public circle(){
            color=Color.yellow;
        }
        
        public void movePiece(int xPos){
            this.x= xPos;
        }
//        public void changeColor(int player){
//            if(player==1)
//                color= Color.red;
//            else 
//                color=Color.yellow;
//        }
    }
    
    public static class CircleDrawing extends JComponent{
        circle c= new circle();
        @Override
        public void paintComponent(Graphics g){
            g.setColor(c.color);
            g.fillOval(c.x,c.y,85,85);
        }
        public void movePiece(int x){
            c.movePiece(x);
            repaint();
        }
//        public void changeColor(int player){
//            c.changeColor(player);
//            repaint();
//        }
    }
       
    
    public static int column;
    public static int turn;
    public static int alphaBeta=0;
    public static int minimax=1; 
    public static JFrame mainframe=new JFrame();
     //the board as a global variable so that i can use it in other classes
    public static int [][] board = createBoard();
    //maximum depth of the tree which is taken from the user at the beginnig of the game
    //the bigger the depth the harder the game
    public static int maxDepth=0;
    
    public static void main(String[] args) throws InterruptedException{
        shape boardShape=new shape(board);
        
        //the piece and the panel where it will move
        CircleDrawing circleDrawing = new CircleDrawing();
        circleDrawing.setPreferredSize(new Dimension(700,100));
        JPanel mouvement = new JPanel();
        mouvement.setBounds(0, 0, 700, 103);       
        mouvement.add(circleDrawing);
        mouvement.setBackground(Color.black);
        
       // mainframe.setLocationRelativeTo(null);
        mainframe.add(mouvement);
        mainframe.add(boardShape);
        mainframe.setSize(nbrCol*squareSize+15, (nbrRow+1)*squareSize+38);
        mainframe.setVisible(true);
        mainframe.setResizable(false);
        mainframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainframe.setBackground(Color.black);
        
        mainframe.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) { }
                @Override
                public void mouseMoved(MouseEvent e) {
                    circleDrawing.movePiece(e.getX());
                }
            });


        //the actual game
        //upon launch
        String[] responses={"alpha-beta","minimax"};
        int algorithm=JOptionPane.showOptionDialog(null, "Choose your algorithm: ", "Algorithm", JOptionPane.YES_OPTION
                , JOptionPane.YES_NO_OPTION, null, responses, 0);
        String depth=JOptionPane.showInputDialog("Enter maximum depth:");
        maxDepth = Character.getNumericValue(depth.charAt(0));
        System.out.println(depth);
          ///////the game: AI's first turn//////////
           if(!isFilled(board)){
              if(turn%2==0){
       //Player 1's turn
      // System.out.println("Player1: drop your piece: choose a col 0:6");
      //circleDrawing.changeColor(2);
          int col1;
          if(algorithm==0){
              col1= bestAlphaBeta();
              if(isValidLocation(board, col1)){
            
            int row =getNextValidRow(board, col1);
            dropPiece(board, row, col1, 1);
         //   printBoard(board);
            boardShape.repaint();
            turn += 1;
          }
          }
          else if(algorithm==1){
              col1= bestMoveMinimax();
              if(isValidLocation(board, col1)){
            
            int row =getNextValidRow(board, col1);
            dropPiece(board, row, col1, 1);
         //   printBoard(board);
            boardShape.repaint();
            turn += 1;
            
          }
          }
          }}
         mainframe.addMouseListener(new MouseListener(){
         @Override
         public void mousePressed(MouseEvent me) { }
         @Override
         public void mouseReleased(MouseEvent me) {
           
           //////when the game is over///////
           String[] responses={"exit"};
           int result;
           if(isFilled(board)){
              if(calculateWinner(board)==1)
                  result = JOptionPane.showOptionDialog(null, "The computer wins",
                         "Game over", JOptionPane.YES_NO_CANCEL_OPTION, 
                         JOptionPane.INFORMATION_MESSAGE, null, 
                         responses, 0);
              else if(calculateWinner(board)==2)
                  result =JOptionPane.showOptionDialog(null, "Human wins",
                         "Game over", JOptionPane.YES_NO_CANCEL_OPTION, 
                         JOptionPane.INFORMATION_MESSAGE, null, 
                         responses, 0);
              else
                  result = JOptionPane.showOptionDialog(null, "Draw",
                         "Game over", JOptionPane.YES_OPTION, 
                         JOptionPane.INFORMATION_MESSAGE, null, 
                         responses, 0);
             System.exit(result);
           }
         }
         @Override
         public void mouseEntered(MouseEvent me) { 
             
         }
         @Override
         public void mouseExited(MouseEvent me) { }
         @Override
         public void mouseClicked(MouseEvent me) { 
         int col = me.getX()/100;
         column = col;
      if(turn!=0&&!isFilled(board)){
          //Player 2's turn
         // System.out.println("Player 2: drop your piece: choose a col 0:6");
         // circleDrawing.changeColor(1);
          int col2= column;
          if(isValidLocation(board, col2)){
            int row2 = getNextValidRow(board, col2);
            dropPiece(board, row2, col2, 2);
          // printBoard(board);
            boardShape.repaint();
            turn += 1;
            
            }
          }
             
         ///////the game: AI turn//////////
           if(!isFilled(board)){
              if(turn%2==0){
       //Player 1's turn
      // System.out.println("Player1: drop your piece: choose a col 0:6");
      //circleDrawing.changeColor(2);
          int col1=0;
          if(algorithm==0){
              col1= bestAlphaBeta();
              if(isValidLocation(board, col1)){
            
            int row =getNextValidRow(board, col1);
            dropPiece(board, row, col1, 1);
         //   printBoard(board);
         try {
                 Thread.sleep(1000);
             } catch (InterruptedException ex) {
                 Logger.getLogger(Connect4Project.class.getName()).log(Level.SEVERE, null, ex);
             }
            boardShape.repaint();
            turn += 1;
          }
          }
          else if(algorithm==1){
              col1= bestMoveMinimax();
              if(isValidLocation(board, col1)){
            
            int row =getNextValidRow(board, col1);
            dropPiece(board, row, col1, 1);
         //   printBoard(board);
            boardShape.repaint();
            turn += 1;
            
          }
          }
          }}
      
      }    
    });

 }
    
}
