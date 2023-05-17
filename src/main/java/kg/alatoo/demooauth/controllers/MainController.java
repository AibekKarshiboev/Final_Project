package kg.alatoo.demooauth.controllers;

import kg.alatoo.demooauth.model.User;
import kg.alatoo.demooauth.repo.UserRepository;
import kg.alatoo.demooauth.service.UserService;
import org.apache.catalina.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sun.activation.registries.LogSupport.log;

@Controller
public class MainController {
    User user = new User();
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService service;

    
    Boolean fromLogin = false;


    Authentication auth = null;


    @GetMapping(value = "/",headers = {})
    public String index(Model model) {
        auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getAuthorities());
        if(auth.getAuthorities().toString().toLowerCase().contains("anonymous")){
            return "index";
        }
        System.out.println(auth.getAuthorities().stream().map(authority -> authority.getAuthority()));


        if (!auth.getAuthorities().toString().toLowerCase().contains("anonymous")) {

            if (auth != null && auth.isAuthenticated()) {
                System.out.println("\n\nfromLogin is " + fromLogin + "\n\n");

                if (!fromLogin) {
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
                    service.createUser(user);
                }
            }

            /*if(!userRepository.existsByEmail(user.getEmail())){
                model.addAttribute("user", user);
                return "registerUOauth";
            }*/
        }

        return "index";
    }
    @GetMapping("/authorization")
    public String AuthorizationPages(){
        if(auth.getAuthorities().toString().toLowerCase().contains("anonymous")){
            return "redirect:/login";
        }
        return "redirect:/logout";
    }

    @GetMapping("/logout")
    public String logout(){
        System.out.println("\n\nlogout is working\n\n");
        user.setLastname("");
        user.setFirstname("");
        user.setUsername("");
        user.setPassword("");


        auth.setAuthenticated(false);


        return "logout";
    }

    @GetMapping("/login")
    public String loginPage(){

        return "login";
    }



/*    @GetMapping("/oauthRegister")
    public String oauthReguster(Model model){
        if(!userRepository.existsByEmail(user.getEmail())){
            model.addAttribute("user", user);
            return "registerUOauth";
        }
        return "index";
    }*/
    boolean ok = false;



@PostMapping("/loginPage")
public String loginPost(@RequestParam("username") String username, @RequestParam("password") String password){

    if(userRepository.existsByUsername(username)){
            System.out.println("akjdjaa");
            User user1 = userRepository.findByUsername(username);
            if(user1.getPassword().equals(password)){
                System.out.println(user1.toString()+"\n\n\n");
                auth = SecurityContextHolder.getContext().getAuthentication();
                user.setLastname(user1.getLastname());
                user.setFirstname(user1.getFirstname());
                user.setUsername(user1.getUsername());
                user.setPassword(user1.getPassword());
                user = user1;
                fromLogin = true;
                auth.setAuthenticated(true);

                System.out.println("\n\nfromLoginerfseesf is " + fromLogin + "\n\n");
                log("\n\nfromLogin issefsefsf" + fromLogin + "\n\n");

                return "redirect:/my_status";
            }
            else {
                System.out.println(user1.getPassword());
                System.out.println(password);
                System.out.println("not correct password");
                return "redirect:/login";
            }

        }
    return "redirect:/login";

}

    @PostMapping("/register")
    public String postRegister(@ModelAttribute User user ,Model model){
        service.createUser(user);
        /*if(!userRepository.existsByEmail(user.getEmail())){

        }
        else {
            System.out.println("this email is already exists!");
            return "redirect:/oauthRegister";
        }*/

        return "redirect:/login";
    }
    @GetMapping("/my_status")
    public String myStatus(Model model){
        model.addAttribute("user", user);
        return "my_status";
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
