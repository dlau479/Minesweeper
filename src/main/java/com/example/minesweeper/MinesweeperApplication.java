package com.example.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


import com.example.minesweeper.models.highscores;
@SpringBootApplication
public class MinesweeperApplication {
 	
	

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperApplication.class, args);
	}



}

