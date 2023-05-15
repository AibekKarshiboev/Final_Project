package kg.alatoo.demooauth.controllers;

import kg.alatoo.demooauth.model.User;
import kg.alatoo.demooauth.repo.UserRepository;
import kg.alatoo.demooauth.service.UserService;
import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    User user = new User();
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService service;

    @GetMapping(value = "/",headers = {})
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getAuthorities());
        if(auth.getAuthorities().toString().toLowerCase().contains("anonymous")){
            return "index";
        }

        if (!auth.getAuthorities().toString().toLowerCase().contains("anonymous")) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) principal;
            String UserText = oAuth2User.getAttributes().toString();
            System.out.println(UserText);
            int emailInt = UserText.lastIndexOf("email"),
                    family_nameInt = UserText.indexOf("family_name"),
                    given_nameInt = UserText.indexOf("given_name"),
                    nameInt = UserText.indexOf("name"),
                    iatInt = UserText.indexOf(", iat"),
                    localeInt = UserText.indexOf(", locale=");
            String userEmail = UserText.substring(emailInt + 6, UserText.length() - 1);
            String userLastname = UserText.substring(family_nameInt + 12, iatInt);
            String userFirstname = UserText.substring(given_nameInt + 11, localeInt);
            System.out.println(userEmail);
            System.out.println(userLastname);
            System.out.println(userFirstname);

            user.setEmail(userEmail);
            user.setFirstname(userFirstname);
            user.setLastname(userLastname);


            if(!userRepository.existsByEmail(user.getEmail())){
                model.addAttribute("user", user);
                return "registerUOauth";
            }
        }

        return "index";
    }
    @GetMapping("/authorization")
    public String AuthorizationPages(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getAuthorities().toString().toLowerCase().contains("anonymous")){
            return "redirect:/login";
        }
        return "redirect:/logout";
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }

    @GetMapping("/login")
    public String loginPage(){

        return "login";
    }



    @GetMapping("/oauthRegister")
    public String oauthReguster(Model model){
        if(!userRepository.existsByEmail(user.getEmail())){
            model.addAttribute("user", user);
            return "registerUOauth";
        }
        return "index";
    }
    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user ,Model model){
        if(!userRepository.existsByEmail(user.getEmail())){
            service.createUser(user);
        }
        else {
            System.out.println("this email is already exists!");
            return "redirect:/oauthRegister";
        }
        return "index";
    }
    @GetMapping("/my_status")
    public String myStatus(Model model){
        model.addAttribute("user", user);
        return "my_status";
    }

    @PostMapping("/login")
    public String loginPost(@PathVariable String username, @PathVariable String password){
        if(userRepository.existsByUsername(username)){
            User user1 = userRepository.findByUsername(username);
            if(user1.getPassword().equals(password)){
                return "redirect:/my_status";
            }
            return "redirect:/login";
        }
        return "redirect:/login";

    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }


    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @GetMapping("/other")
    public String otherPage(){
        return "other";
    }
}
