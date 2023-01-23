package com.example.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class MinesweeperApplication /* implements CommandLineRunner */{
 	
	

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperApplication.class, args);
	}
/*
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
	public void run(String...args)throws Exception{
		String sql = "SELECT  * FROM highscores ";
		jdbctemplate.execute(sql);
		if(rows > 0){
			System.out.println("Data has been proccesed");
		}
		System.out.pritnln(rows);
	}
*/
}

