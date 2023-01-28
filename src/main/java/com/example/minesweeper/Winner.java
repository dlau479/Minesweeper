package com.example.minesweeper;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.minesweeper.models.highscores;
import com.example.minesweeper.models.BoardSize;
import com.example.minesweeper.models.BoardGame;
import com.example.minesweeper.Database;

@Controller
public class Winner {
    String difficulty;
    String name;
    int mines;
    int clicks;
    int safecells;

    @Autowired
    Database repository;

    @GetMapping("/win")
    public String getInfo(
        @RequestParam (name = "difficulty", required = true) String difficulty,
        @RequestParam (name = "minecount", required = true) String mines,
        @RequestParam (name = "clicks", required = true) String clicks,
        @RequestParam (name = "safecell" , required = true) String safecells,
        Model model){

            this.difficulty=difficulty;
            this.mines = Integer.parseInt(mines);
            this.clicks = Integer.parseInt(clicks);
            this.safecells = Integer.parseInt(safecells);
            model.addAttribute("difficulty",difficulty);
            model.addAttribute("mines",mines);
            model.addAttribute("clicks",clicks);
            model.addAttribute("safecells",safecells);
            
            return "/pages/win";
    }

    @PostMapping("/win")
    public String postInfo(
        @RequestParam (name = "name", required = false) String name,
        Model model){
        
        model.addAttribute("difficulty",difficulty);
        model.addAttribute("mines",mines);
        model.addAttribute("clicks",clicks);
        model.addAttribute("safecells",safecells);
        System.out.println("win post");
        if(name != null){
            repository.save(new highscores(
                name,
                true,
                difficulty,
                mines,
                safecells,
                clicks
            ));
        }

        return "redirect:/";
    }

}
