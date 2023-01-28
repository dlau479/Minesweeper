package com.example.minesweeper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.minesweeper.models.highscores;
import com.example.minesweeper.models.BoardSize;
import com.example.minesweeper.models.BoardGame;
import com.example.minesweeper.Database;

@Controller
public class Scoreboard {
    //used to reach out to database    
    @Autowired
    Database repository;

    List<highscores> l = new ArrayList<highscores>();
    int len;

    public void getAllEntries(Database repository) { 
		for(highscores score: repository.findAll()){
			l.add(score);
		}
	}

    @GetMapping("/scoreboard")
    public String scores(Model model){
        if(l.isEmpty() || len != l.size()){
            l.clear();
            getAllEntries(repository);
            
            //sorts in decending order
            Collections.sort(l, new Comparator<highscores>(){
                @Override 
                public int compare(highscores h1, highscores h2){
                    return  h2.getClicks() - h1.getClicks();
                }
            });
            
        }
        len = l.size();
        model.addAttribute("list",l);
        return "pages/scoreboard";
    }

    @PostMapping("/scoreboard")
    public String Homepage(){
        return "redirect:/";
    }
}
