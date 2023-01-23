package com.example.minesweeper.models;

public class BoardSize{
    private int column;
    private int row;

    public BoardSize(int column,int row){
        this.column = column;
        this.row = row; 
    }

    public int getColumn(){
        return column;
    }

    public int getRow(){
        return row;
    }
}
