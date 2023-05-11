package com.example.final_project.controllers;


import com.example.final_project.models.Food;
import com.example.final_project.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    FoodRepository foodRepository;

    @GetMapping("/")
    public String mainPage(Model model){
        return "mainPage";
    }
    @GetMapping("/menu")
    public String Menu(Model model){

        model.addAttribute("foods", foodRepository.findAll());
        return "menu";
    }

    @GetMapping("/post")
    public String saveFoof(){
        return "save";
    }
    @PostMapping("/post")
    public String postFood(@ModelAttribute Food food){
        foodRepository.save(food);
        return "redirect:/menu";
    }
}
