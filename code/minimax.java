/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connect4project;

import static connect4project.Connect4Project.board;
import static connect4project.Connect4Project.createBoard;
import static connect4project.Connect4Project.dropPiece;
import static connect4project.Connect4Project.getNextValidRow;
import static connect4project.Connect4Project.maxDepth;
import static connect4project.Connect4Project.printBoard;
import java.util.Arrays;

/**
 *
 * @author ASUS
 */
public class minimax {
     public static int[][] tempBoard=createBoard();
    
    //method to put the contents of the game board into the tempboard after each turn
    public static void updateTempBoard(int[][] OGboard, int[][] tempBoard){
        for(int r=0;r<OGboard.length;r++){
            for(int c=0; c<OGboard[r].length;c++){
                tempBoard[r][c]= OGboard[r][c];
            }
        }
    }
    
    public static boolean isFilledCol(int[][] board, int c){
        for(int r=0;r<6;r++){
            if(board[r][c]==0)
                return false;
        }
        return true;
    }
    public static int isMiddleCol(int col){
        if(col==3)
           return 3;
        else 
            return 0;
    }
    
    //heuristic function
    public static final int ROWS=6;
    public static final int COLS=7;
    public static int heuristic(int[][] board) {
    int score = 0;
    int opponent = 2;
    int player = 1;
    
    // check horizontal
    for (int row = 0; row < ROWS; row++) {
        for (int col = 0; col < COLS - 3; col++) {
            int count = 0;
            for (int i = 0; i < 4; i++) {
                if (board[row][col+i] == player) {
                    count++;
                } else if (board[row][col+i] == opponent) {
                    count = 0;
                    break;
                }
            }
            if (count > 0) {
                score += Math.pow(10, count);
            }
        }
    }
    
    // check vertical
    for (int col = 0; col < COLS; col++) {
        for (int row = 0; row < ROWS - 3; row++) {
            int count = 0;
            for (int i = 0; i < 4; i++) {
                if (board[row+i][col] == player) {
                    count++;
                } else if (board[row+i][col] == opponent) {
                    count = 0;
                    break;
                }
            }
            if (count > 0) {
                score += Math.pow(10, count);
            }
        }
    }
    
    // check diagonal (up-left to down-right)
    for (int row = 0; row < ROWS - 3; row++) {
        for (int col = 0; col < COLS - 3; col++) {
            int count = 0;
            for (int i = 0; i < 4; i++) {
                if (board[row+i][col+i] == player) {
                    count++;
                } else if (board[row+i][col+i] == opponent) {
                    count = 0;
                    break;
                }
            }
            if (count > 0) {
                score += Math.pow(10, count);
            }
        }
    }
    
    // check diagonal (down-left to up-right)
    for (int row = 3; row < ROWS; row++) {
        for (int col = 0; col < COLS - 3; col++) {
            int count = 0;
            for (int i = 0; i < 4; i++) {
                if (board[row-i][col+i] == player) {
                    count++;
                } else if (board[row-i][col+i] == opponent) {
                    count = 0;
                    break;
                }
            }
            if (count > 0) {
                score += Math.pow(10, count);
            }
        }
    }
    
    return score;
}

   

    
    //returns the column of the AI
public static int bestMoveMinimax(){
    int bestScore=-100000000;
    int bestMove=3;
    for(int c=0; c<7;c++){
        updateTempBoard(board,tempBoard);
        if(!isFilledCol(board,c)){
            int row=getNextValidRow(board,c);
            dropPiece(tempBoard, row, c, 1);
            int score=minimax(tempBoard,maxDepth,false);
            System.out.println(c+" : score= "+score);
            printBoard(tempBoard);
            if(score>bestScore){
                bestScore=score;
                bestMove=c;
            }
        }
    }
    System.out.println(bestMove);
    return bestMove;
    
}

    private static int minimax(int[][] board, int depth, boolean isMaximizing) {
       if(depth==1){
           int score=heuristic(board);
           return score;
       }
       if(isMaximizing){
           int value=Integer.MIN_VALUE;
           for(int col=0;col<7;col++){
               int row=getNextValidRow(tempBoard,col);
               dropPiece(tempBoard,row,col,1);
               printBoard(tempBoard);
               value=Math.max(value, minimax(tempBoard,depth-1,false));
               dropPiece(tempBoard,row,col,0);
               System.out.println(value);
           }
           return value;
           
       }else{
           int value=Integer.MAX_VALUE;
           for(int col=0;col<7;col++){
               int row=getNextValidRow(tempBoard,col);
               dropPiece(tempBoard,row,col,2);
               printBoard(tempBoard);
               value=Math.min(value, minimax(tempBoard,depth-1,true));
               System.out.println(value);
               dropPiece(tempBoard,row,col,0);
             }
           return value;
           
       }
      
    }
    
    
//    public static int[] minimaxTutorial(int[][] board, int depth,boolean isMaximizing){
//      if(depth==1){
//        //  x[0]=score  x[1]=col
//          int [] x= {heuristic(tempBoard),};
//          return x ;
//      }
//      int bestScore;
//      
//      if(isMaximizing){
//          bestScore=-1000000000;
//          int column=3;
//          for(int col=0; col<7;col++){
//              if(!isFilledCol(board,col)){
//                  int row=getNextValidRow(board,col);
//                  dropPiece(tempBoard,row,col,1);
//                  int newScore=minimaxTutorial(tempBoard,depth-1,false)[0];
//                  if(newScore>bestScore){
//                      bestScore=newScore;
//                      column = col;
//                  }
//              }
//          }
//          int[] ai={bestScore,column};
//          return ai; 
//      }
//      else /*if(!isMaximizing)*/{
//        bestScore=100000000;
//        int column=3;
//        for(int col=0; col<7;col++){
//           if(!isFilledCol(board,col)){
//              int row=getNextValidRow(board,col);              
//              dropPiece(tempBoard,row,col,2);
//              int newScore=minimaxTutorial(tempBoard,depth-1,false)[0];
//                  if(newScore>bestScore){
//                      bestScore=newScore;
//                      column = col;
//                  }
//              }
//               
//          }
//          int[] human={bestScore,column};
//          return human; 
//    }
//     
//    }

    
    //alpha beta
    
    //returns the column the AI is dropping its piece in 
    public static int bestAlphaBeta(){
        int bestScore = -10000000;
        int bestMove=3;
        for(int col=0;col<7;col++){
            if(!isFilledCol(board,col)){
                int row= getNextValidRow(board, col);
                updateTempBoard(board,tempBoard);
                dropPiece(tempBoard, row, col, 1);
                int score=alphaBeta(tempBoard,0,false,maxDepth,Integer.MIN_VALUE,Integer.MAX_VALUE);
                System.out.println(col+" : score= "+score);
                printBoard(tempBoard);
                if(score>bestScore){
                    bestScore = score;
                    bestMove=col;
               }
            }
        }
        System.out.println(bestMove);
        return bestMove;
    }
    ///This method takes in a board, the current depth in the search
    //whether the current player is maximizing or minimizing, the maximum depth to search, and the alpha and beta values for pruning. 
    //it starts by checking if the maximum depth has been reached, and if so, returns the score of the current position using the scorePosition method.
public static int alphaBeta(int[][] board, int depth, boolean isMaximizing, int maxDepth, int alpha, int beta) {
    
    if (depth ==1) {
        int score=heuristic(board);
        return score;
    }
    if (isMaximizing) {//If the current player is maximizing, the method initializes the best score to the lowest possible value.
        int value = Integer.MIN_VALUE;
        for (int col = 0; col < 7; col++) {
            //if (!isFilledCol(board, col)) {
                int row = getNextValidRow(tempBoard, col);//loop to check if the row is already filled
                dropPiece(tempBoard, row, col, 1);
                printBoard(tempBoard);
                //score = alphaBeta(board, depth + 1, false, maxDepth, alpha, beta);
                value = Math.max(value,alphaBeta(tempBoard, depth + 1, false, maxDepth, alpha, beta) );
                alpha = Math.max(alpha, value);
                if (alpha >= beta) {
                    break;
                }
            //}
            dropPiece(tempBoard,row,col,0);
               System.out.println(value);
        }
        return value;
    } else {//If the current player is minimizing, the method initializes the best score to the highest possible value
        int value = Integer.MAX_VALUE;
        for (int col = 0; col < 7; col++) {
           // if (!isFilledCol(board, col)) {
                      int row = getNextValidRow(tempBoard, col);//checkig if col is filled
                dropPiece(tempBoard, row, col, 2);
                printBoard(tempBoard);
                //score = alphaBeta(tempBoard, depth + 1, true, maxDepth, alpha, beta);
                value = Math.min(value, alphaBeta(tempBoard, depth + 1, true, maxDepth, alpha, beta));
                beta = Math.min(beta, value);
                if (beta <= alpha) {
                    break;
                }
                dropPiece(tempBoard,row,col,0);
           // }
           System.out.println(value);
               dropPiece(tempBoard,row,col,0);
        }
        return value;
    }
}

}


