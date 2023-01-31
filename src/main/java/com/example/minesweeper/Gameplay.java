package com.example.minesweeper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.util.StopWatch;

import java.util.Queue;
import java.util.Random;
import java.util.LinkedList;

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
    private void safeCellChecker(int count){
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


    //Collect Cells finds all the possible cells in the grid and adds them to the list for later usage
    private void CollectCells(Queue<Integer> xList, Queue<Integer> yList, Queue<Mine> mineList){
        int x = xList.peek();
        int y = yList.peek();
        int width = board.length-1;
        int length = board[0].length-1;
        int left = x-1;
        int right = x+1;
        int up = y-1; 
        int down = y+1;

        try{
            if(left >= 0 && right <= width && up >= 0 && down <= length){
                if(board[left][y].getValue() == 's'){
                    board[left][y].setValue('c');
                    mineList.add(board[left][y]);
                    xList.add(left);
                    yList.add(y);
                    System.out.println("Left complete x:"+left+" y:"+y);
                }
                if(board[right][y].getValue() == 's'){
                    board[right][y].setValue('c');
                    mineList.add(board[right][y]);
                    xList.add(right);
                    yList.add(y);
                    System.out.println("Right complete x:"+right+" y:"+y);
                }
                if(board[x][up].getValue() == 's'){
                    board[x][up].setValue('c');
                    mineList.add(board[x][up]);
                    xList.add(x);
                    yList.add(up);
                    System.out.println("Up complete x:"+x+" y:"+up);
                }
                if(board[x][down].getValue() == 's'){
                    board[x][down].setValue('c');
                    mineList.add(board[x][down]);
                    xList.add(x);
                    yList.add(down);
                    System.out.println("Down complete x:"+x+" y:"+down);
                }
            }
            board[x][y].setValue('c');
            mineList.remove();
            xList.remove();
            yList.remove();
        }
        catch (IndexOutOfBoundsException e){
            System.out.println(e+" x: "+x + " y: "+y);
            board[x][y].setValue('c');
            mineList.remove();
            xList.remove();
            yList.remove();
        }
        /*
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
        */


        return ;
    }

    //clear the cells when player clicks on it.
    private void ClearCells(int x, int y){
        if(board[x][y].getValue() == 'b'){
          // GameOver();
          
        }
        Queue<Integer> xList = new LinkedList<Integer>();
        Queue<Integer> yList = new LinkedList<Integer>();
        Queue<Mine> mineList = new LinkedList<Mine>();
        xList.add(x);
        yList.add(y);
        mineList.add(board[x][y]);
        while(!mineList.isEmpty()){
            CollectCells(xList,yList,mineList);
        };
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
                safeCellChecker(safecell);
                boardsize = null;
                board = null;
               return "redirect:/gameover?difficulty="+difficulty+"&minecount="+minecount+"&safecell="+safecell+"&clicks="+movecounter;
            }
            else if(BoardChecker()){
                safeCellChecker(safecell);
                boardsize = null;
                board = null;
                return "redirect:/win?difficulty="+difficulty+"&minecount="+minecount+"&safecell="+safecell+"&clicks="+movecounter;
            }

            PrintBoard();
        }
        model.addAttribute("MoveCounter", movecounter);
        model.addAttribute("minecount" , minecount);
        model.addAttribute("board" , board);
        model.addAttribute("difficulty", difficulty);
        model.addAttribute("column",boardsize.getColumn());
        model.addAttribute("row",boardsize.getRow());
        return "pages/gameplay";
    }

    @PostMapping("/gameplay")
    public String gameover(@RequestParam (name = "difficulty", required = false) String difficulty,
    @RequestParam (name = "minecount", required = false) String mines,
    @RequestParam (name = "clicks", required = false) String clicks,
    @RequestParam (name = "safe_cells" , required = false) String safecells,
    Model model){
        System.out.println("should be brought over "+ difficulty);
        System.out.println("Over here");
        return "pages/gameover";
    }

}
