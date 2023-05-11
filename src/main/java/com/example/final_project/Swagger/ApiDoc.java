package com.example.final_project.Swagger;


import com.example.final_project.models.Food;
import com.example.final_project.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class ApiDoc {

    @Autowired
    FoodRepository foodRepository;

    @GetMapping("/menu")
    public List<Food> menu(){
        return foodRepository.findAll();
    }
    @PostMapping("/save")
    public Food postFood(@ModelAttribute Food food){
        return foodRepository.save(food);
    }

}
