package com.example.minesweeper.models;


public class BoardGame {
    private char [][] board;

    public BoardGame (char [][] board){
        this.board = board;
    }

    public char[][] getBoard(){
        return board;
    }
}
