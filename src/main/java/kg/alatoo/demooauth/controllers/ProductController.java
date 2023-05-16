package kg.alatoo.demooauth.controllers;


import kg.alatoo.demooauth.config.EmailSenderService;
import kg.alatoo.demooauth.model.Products;
import kg.alatoo.demooauth.model.Send;
import kg.alatoo.demooauth.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    @Autowired
    EmailSenderService senderService;

    @Autowired
    ProductRepository productRepository;
    List<String> listSort = new ArrayList<>();


    @RequestMapping(path = "/products")
    public String products(Model model, String keyword){
        listSort.clear();
        listSort.add("By Id Asc");listSort.add("By Id Desc");listSort.add("By Name Asc");
        listSort.add("By Name Desc");listSort.add("By Prize Asc");listSort.add("By Prize Desc");
        model.addAttribute("list", listSort);

        if(keyword!=null) {
            switch (keyword){
                case "By Id Asc":
                    model.addAttribute("product", productRepository.findByOrderByIdAsc());
                    break;
                case "By Id Desc":
                    model.addAttribute("product", productRepository.findByOrderByIdDesc());
                    break;
                case "By Name Asc":
                    model.addAttribute("product", productRepository.findByOrderByNameAsc());
                    break;
                case "By Name Desc":
                    model.addAttribute("product", productRepository.findByOrderByNameDesc());
                    break;
                case "By Prize Asc":
                    model.addAttribute("product", productRepository.findByOrderByPrizeAsc());
                    break;
                case "By Prize Desc":
                    model.addAttribute("product", productRepository.findByOrderByPrizeDesc());
                    break;
            }


        }else {

            model.addAttribute("product", productRepository.findByOrderByIdAsc());}

        return "products";
    }
    @GetMapping("/product/{id}")
    public String buyProduct(@PathVariable("id") Long id, Model model){
        model.addAttribute("product", productRepository.findById(id));
        /*Optional<Products> product = productRepository.findById(id);
        senderService.sendEmail(product.get, adress.getAdress(), adress.getPhone_number(), adress.getProduct_id());
*/      return "buy";
    }
    @PostMapping("/product/{id}")
    public String postProduct(@ModelAttribute Send adress, Model model){
        Optional<Products> product = productRepository.findById(adress.getProduct_id());
        senderService.sendEmail(adress.getEmail().toString(), adress.getAddress(), adress.getPhone_number(), adress.getProduct_id());
        return "check";

    }
}
