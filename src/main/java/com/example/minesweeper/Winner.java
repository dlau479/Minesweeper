package com.example.minesweeper;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.minesweeper.models.BoardSize;
import com.example.minesweeper.models.BoardGame;

@Controller
public class Winner {

    @GetMapping("/win")
    public String gameplay(
        @RequestParam (name = "difficulty", required = false) String difficulty,
        @RequestParam (name = "minecount", required = false) String mines,
        @RequestParam (name = "clicks", required = false) String clicks,
        @RequestParam (name = "safecell" , required = false) String safecells,
        Model model){
            System.out.println("should be brought over "+ difficulty);
            System.out.println("should be brought over "+ mines);
            System.out.println("should be brought over "+ clicks);
            System.out.println("should be brought over "+ safecells);

            model.addAttribute("difficulty",difficulty);
            model.addAttribute("mines",mines);
            model.addAttribute("clicks",clicks);
            model.addAttribute("safecells",safecells);
            
            return "/pages/win";
    }

}
