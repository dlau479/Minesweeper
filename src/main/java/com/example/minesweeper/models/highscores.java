package com.example.minesweeper.models;

import java.lang.annotation.Inherited;

import javax.annotation.processing.Generated;

import com.example.minesweeper.MinesweeperApplication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class highscores {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private boolean won;
    private String difficulty;
    private int mines;
    private int safespace;
    private int clicks;

    protected highscores(){}

    public highscores(String name,Boolean winner,String difficulty,int mines,int safespace,int clicks){
        this.name = name;
        this.won = winner;
        this.difficulty = difficulty;
        this.mines = mines;
        this.safespace = safespace;
        this.clicks = clicks;
    }

    @Override
    public String toString(){
        return String.format("highscores[id=%d,name='%s',difficulty='%s',mines='%d',safespace='%d',clicks=%d]", 
        id, name,difficulty,mines,safespace,clicks);
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setWinner(boolean winner){
        this.won = winner;
    }

    public boolean getWinner(){
        return won;
    }

    public String getDifficulty(){
        return difficulty;
    }

    public int getMines(){
        return mines;
    }

    public int getSafespace(){
        return safespace;
    }

    public int getClicks(){
        return clicks;
    }
    


    
}
