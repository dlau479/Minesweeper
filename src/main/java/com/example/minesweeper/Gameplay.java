package com.example.minesweeper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.util.StopWatch;

import java.util.Random;

import javax.print.PrintException;

//Custom Model class
import com.example.minesweeper.models.BoardSize;
import com.example.minesweeper.models.BoardGame;
import com.example.minesweeper.models.Mine;

//@RestController
@Controller
public class Gameplay {

    BoardGame gb ;
    //Console Board
    Mine [][] board; 
    //mines is to ensure that the correct amount of mines are set per game mode
    int mines;   
    //To be displayed at the start of the game and to indicate how many mines are one the game board
    int minecount;
    BoardSize boardsize;

    int movecounter = 0;
    
    int safecell = 0;

    private void PrintBoard(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.print(board[i][j].getValue());
            }
            System.out.println();
        }
        System.out.println("Move Done");
    }

    //create a board on this side and bring it out later in code
    private void setGameBoard(int row ,int column){
        Random random = new Random();
        if(mines > 0){
            for (int i = 0; i < column; i++){
                for (int j = 0; j < row; j++){
                    if(board[i][j] == null){
                        board[i][j] = new Mine('s');
                    }
                    else if(random.nextInt(50) <= 2  && board[i][j].getValue() != 'b'){
                        board[i][j] = new Mine('b');
                        mines -= 1; 
                    }
                    else if(board[i][j].getValue() != 'b'){
                        board[i][j].setValue('s'); 
                    }
                }
            }
            setGameBoard(row, column);
        }
        //Print Board
        for (int i = 0; i < column; i++){
            for (int j = 0; j < row; j++){
                System.out.print(board[i][j].getValue());
            }
            System.out.println();
        }
        System.out.println(mines);
        System.out.println("Board Set");
        //gb = new BoardGame(board);
        return;
    }


    //deteremines board size and the mine amounts
    private BoardSize whichMode(String difficulty){
        if(difficulty.equals("Classic") == true){ mines = 5; minecount = 5; return new BoardSize(8,8);}
        else if(difficulty.equals("Easy")){ mines = 10; minecount = 10;  return new BoardSize(9,9); }
        else if(difficulty.equals("Medium")){ mines = 40; minecount = 40; return new BoardSize(16,16); }
        else if(difficulty.equals("Expert")){ mines = 99; minecount = 99; return new BoardSize(30,16);}
        return new BoardSize(0,0);
    }

    //Collect Cells finds all the possible cells in the grid and adds them to the list for later usage
    private void CollectCells(int x, int y){
        int left = 0;
        int right = 0;
        int up = 0;
        int down = 0;
        
        int height = board.length;
        int length = board[0].length;
        

        for (int i = x; i >= 0; i--){
            if(board[i][y].getValue() == 'b')
                break;
            board[i][y].setValue('c');
        }
        for(int  i = x ; i < height ; i++){
            if(board[i][y].getValue() == 'b')
                break;
            board[i][y].setValue('c');
        }
        for(int i = y; i >= 0; i--){
            if(board[x][i].getValue() == 'b')
                break;
            board[x][i].setValue('c');
        }
        for(int  i = y; i < length; i++){
            if(board[x][i].getValue() == 'b')
                break;
            board[x][i].setValue('c');
        }
        return ;
    }

    //Check board
    private boolean BoardChecker(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                if(board[i][j].getValue() == 's'){
                    return false;
                }
            }
        }
        return true;
    }
    //Count safe Cells
    private void BoardChecker(int count){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                if(board[i][j].getValue() == 's'){
                    count += 1;
                }
            }
        }
        safecell = count;
        return;
    }

    //clear the cells when player clicks on it.
    private void ClearCells(int x, int y){
        if(board[x][y].getValue() == 'b'){
          // GameOver();
          
        }
        CollectCells(x, y);
    }

    //Function is called when a player makes a move 
    private void GameMoves(String x, String y, String val, String button, String button1){
        
        int i = Integer.parseInt(x);
        int j = Integer.parseInt(y);
        System.out.println(button1);
        if(button == null){
            //return;
        }
        else if(button.equals("submit") && val.charAt(0) != 't'){
            ClearCells(i,j);
        }
        if(button1 == null){
           // return;
        }
        else if(button1.equals("flag")){
            if(board[i][j].getFlag() == 'f')
                board[i][j].setFlag('t'); 
            else
                board[i][j].setFlag('f');
            return;
        }
    }


    //This is all the contents the web page is going to use and return 
    @GetMapping("/gameplay")
    public String makeTable(@RequestParam (name = "difficulty") String difficulty ,
    @RequestParam (name = "x", required =  false) String x ,
    @RequestParam (name = "y", required = false) String y ,
    @RequestParam (name = "value" , required = false) String val ,
    @RequestParam (name = "flag", required = false) String flag,
    @RequestParam (name = "submit", required = false) String submit,
    Model model ){
        //makes sure to run this method only once.
        if(boardsize == null && board == null){
            boardsize = whichMode( difficulty);
            board = new Mine[boardsize.getColumn()][boardsize.getRow()];
            setGameBoard(boardsize.getRow(), boardsize.getColumn());
            
        }
        else {
            System.out.println("x:"+x+" y:"+y+" value:"+val );
            
            GameMoves(x, y, val, submit, flag);
            movecounter+=1;
            if(val.charAt(0) == 'b' && submit != null){
                BoardChecker(safecell);
                boardsize = null;
                board = null;
               return "redirect:/gameover?difficulty="+difficulty+"&minecount="+minecount+"&safecell="+safecell+"&clicks="+movecounter;
            }
            else if(BoardChecker()){
                return "redirect:/win";
            }

            PrintBoard();
        }
        model.addAttribute("MoveCounter", movecounter);
        model.addAttribute("minecount" , minecount);
        model.addAttribute("board" , board);
        model.addAttribute("difficulty", difficulty);
        model.addAttribute("column",boardsize.getColumn());
        model.addAttribute("row",boardsize.getRow());
        return "/pages/gameplay";
    }

    @PostMapping("/gameplay")
    public String gameover(@RequestParam (name = "difficulty", required = false) String difficulty,
    @RequestParam (name = "minecount", required = false) String mines,
    @RequestParam (name = "clicks", required = false) String clicks,
    @RequestParam (name = "safe_cells" , required = false) String safecells,
    Model model){
        System.out.println("should be brought over "+ difficulty);
        System.out.println("Over here");
        return "/pages/gameover";
    }

}