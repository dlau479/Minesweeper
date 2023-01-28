package com.example.minesweeper;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import javax.management.modelmbean.ModelMBean;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.minesweeper.models.highscores;
import com.example.minesweeper.models.BoardSize;
import com.example.minesweeper.models.BoardGame;
import com.example.minesweeper.Database;

@Controller
public class GameOver {
    @Autowired
    Database repository;
    
    String difficulty; 
    int mines;
    int clicks;
    int safecells;

    @GetMapping("/gameover")
    public String gameover(
    @RequestParam (name = "difficulty", required = false) String difficulty,
    @RequestParam (name = "minecount", required = false) String mines,
    @RequestParam (name = "clicks", required = false) String clicks,
    @RequestParam (name = "safecell" , required = false) String safecells,
    Model model){
        this.difficulty = difficulty;
        this.mines = Integer.parseInt(mines);
        this.clicks = Integer.parseInt(clicks);
        this.safecells  = Integer.parseInt(safecells);
        model.addAttribute("difficulty",difficulty);
        model.addAttribute("mines",mines);
        model.addAttribute("clicks",clicks);
        model.addAttribute("safecells",safecells);
        return "pages/gameover";
    }

    @PostMapping("/gameover")
    public String gameplay(
    @RequestParam (name = "name", required = false) String name,     
    Model model){

        model.addAttribute("difficulty",difficulty);
        model.addAttribute("mines",mines);
        model.addAttribute("clicks",clicks);
        model.addAttribute("safecells",safecells);

        if(name != null){
            
            repository.save(new highscores(
                name,
                false,
                difficulty,
                mines,
                safecells,
                clicks
            ));

        }
        



        return "redirect:/";
    }


    
    public void demo(Database repository) {
		for(highscores score: repository.findAll()){
			System.out.println( (score.toString()) ); 
		}
	}
}
