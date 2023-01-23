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

    public String gameplay(){
        

        return "/pages/win";
    }

}
