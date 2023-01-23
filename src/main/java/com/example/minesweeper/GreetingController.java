package com.example.minesweeper;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//custom model
import com.example.minesweeper.models.BoardSize;


@Controller
public class GreetingController {
    @GetMapping("/")
	public String greeting(String name, Model model) {
		
		model.addAttribute("name", name);
		return "hello";
	}
/* 
	@RequestMapping(value = "/addition" , method = RequestMethod.POST)
	public String goToNextPage(Model model){
		return "/pages/addition";
	}
*/
}
