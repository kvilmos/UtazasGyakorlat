package org.example.utazasgyakorlat.Controller;

import org.example.utazasgyakorlat.Model.User;
import org.example.utazasgyakorlat.Model.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "kezdőlap");
        model.addAttribute("contentTemplate", "home");
        return "layout";
    }

    @GetMapping("/offers")
    public String about(Model model) {
        model.addAttribute("title", "Ajánlatok");
        model.addAttribute("contentTemplate", "offers");
        return "layout";
    }

    @GetMapping("/admin/adminpanel")
    public String admin(Model model) {
        model.addAttribute("title", "AdminPanel");
        model.addAttribute("contentTemplate", "adminpanel");
        return "layout";
    }

    @GetMapping("/regisztral")
    public String greetingForm(Model model) {
        model.addAttribute("title", "Regisztráció");
        model.addAttribute("contentTemplate", "regisztral");
        model.addAttribute("reg", new User());
        return "layout";
    }

    @Autowired
    private UserRepository userRepo;
    @PostMapping("/regisztral_feldolgoz")
    public String Regisztráció(@ModelAttribute User user, Model model) {
        for(User felhasznalo2: userRepo.findAll())
            if(felhasznalo2.getEmail().equals(user.getEmail())){
                model.addAttribute("title", "Regisztráció");
                model.addAttribute("contentTemplate", "regisztral");
                model.addAttribute("uzenet", "A regisztrációs email már foglalt!");
                return "layout";
            }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole("ROLE_USER");
        userRepo.save(user);
        model.addAttribute("title", "Regisztráció");
        model.addAttribute("contentTemplate", "regjo");
        model.addAttribute("uzenet","újid"+ user.getId());
        return "layout";
    }
}
