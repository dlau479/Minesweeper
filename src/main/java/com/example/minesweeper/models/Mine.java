package com.example.minesweeper.models;

public class Mine {
    char value;
    //flag is set to f as in false
    char flag;


    public Mine(char value ){
        this.value = value;
        this.flag = 'f';
    }

    public char getValue(){
        if(flag == 't')
            return flag;
        return value;
    }

    public void setValue(char value){
        this.value = value;
    }

    public char getFlag(){
        return flag;
    }

    public void setFlag(char flag){
        this.flag = flag;
    }
}
