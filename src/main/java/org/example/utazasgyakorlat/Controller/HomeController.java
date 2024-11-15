package org.example.utazasgyakorlat.Controller;

import jakarta.validation.Valid;
import org.example.utazasgyakorlat.Model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.stream.StreamSupport;

import java.net.Authenticator;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class HomeController {
    @Autowired private HelysegRepository helysegRepository;
    @Autowired private SzallodaRepository szallodaRepository;
    @Autowired private TavaszRepository tavaszRepository;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "CAMELUM - Home Page");
        model.addAttribute("contentTemplate", "home");
        return "layout";
    }


    @GetMapping("/offers")
    public String offers(Model model) {
        model.addAttribute("title", "CAMELUM - Offers");
        model.addAttribute("contentTemplate", "offers");
        String str = A();
        model.addAttribute("str", str);
        return "layout";
    }
    String A() {
        String str = "";

        for(Tavasz tavasz : tavaszRepository.findAll()) {
            str+=tavasz.getSorszam() +";"+tavasz.getSzalloda_az()+";"+tavasz.getIndulas()+";"+tavasz.getIdotartam()+";"+tavasz.getAr()+";"+tavasz.getSzalloda().getNev()+";"+tavasz.getSzalloda().getHelyseg().getNev()+";"+tavasz.getIndulas();
            str+="<br>";
        }

        return str;
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "CAMELUM - Contact");
        model.addAttribute("contentTemplate", "contact");
        ContactMessage contactMessage = new ContactMessage();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"))) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String userEmail = userDetails.getUsername();
            Optional<User> userOptional = userRepo.findByEmail(userEmail);
            userOptional.ifPresent(user -> contactMessage.setEmail(user.getEmail()));
        }
        model.addAttribute("email", contactMessage.getEmail());
        model.addAttribute("contactMessage", contactMessage);
        return "layout";
    }

    @Autowired private ContactMessageRepository contactMessageRepository;
    @PostMapping(value = "/contact_sendmessage")
    public String sendContactMessage(@Valid @ModelAttribute("contactMessage") ContactMessage contactMessage, BindingResult bindingResult,Model model, RedirectAttributes redirAttr) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error");
            model.addAttribute("title", "CAMELUM - Contact");
            model.addAttribute("contentTemplate", "contact");
            return "layout";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"))) {

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String userEmail = userDetails.getUsername();
            Optional<User> userOptional = userRepo.findByEmail(userEmail);

            if (userOptional.isPresent()) {
                contactMessage.setUserid(userOptional.get().getId());
            } else {
                contactMessage.setUserid(0);
            }
        } else {
            contactMessage.setUserid(0);
        }
        contactMessageRepository.save(contactMessage);

        model.addAttribute("title", "CAMELUM - Contact");
        model.addAttribute("contentTemplate", "contact_success");
        model.addAttribute("id",contactMessage.getId());
        return "layout";
    }

    @GetMapping("/admin/messages")
    @ResponseBody
    public Iterable<ContactMessage> getMessages() {
        return contactMessageRepository.findAll();
    }

    @GetMapping("/admin/adminpanel")
    public String admin(Model model) {
        model.addAttribute("title", "CAMELUM - AdminPanel");
        model.addAttribute("contentTemplate", "adminpanel");
        String str = "";

        for(ContactMessage message : contactMessageRepository.findAll()) {
            str+=message.getEmail()+"; "+message.getMessage()+";"+message.getDate();
            str+="<br>";
        }
        model.addAttribute("str", str);

        return "layout";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "CAMELUM - Login");
        model.addAttribute("contentTemplate", "login");
        return "layout";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("title", "CAMELUM - Registration");
        model.addAttribute("contentTemplate", "registration");
        model.addAttribute("reg", new User());
        return "layout";
    }

    @Autowired
    private UserRepository userRepo;
    @PostMapping("/registration_process")
    public String registrationProcess(@Valid @ModelAttribute("reg") User user, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error");
            model.addAttribute("title", "CAMELUM - Registration");
            model.addAttribute("contentTemplate", "registration");
            return "layout";
        }
        if (StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .anyMatch(anotherUser -> anotherUser.getEmail().equals(user.getEmail()))) {
            model.addAttribute("error", "The Email Already Exists!");
            model.addAttribute("title", "CAMELUM - Registration");
            model.addAttribute("contentTemplate", "registration");
            return "layout";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String firstname = user.getFirstname().substring(0,1).toUpperCase() + user.getFirstname().substring(1).toLowerCase();
        String lastname = user.getLastname().substring(0,1).toUpperCase() + user.getLastname().substring(1).toLowerCase();
        user.setFirstname(firstname);
        user.setFirstname(lastname);

        user.setRole("ROLE_USER");
        userRepo.save(user);

        model.addAttribute("title", "CAMELUM - Registration");
        model.addAttribute("contentTemplate", "registration_success");
        model.addAttribute("id","Your UserID: "+ user.getId());
        return "layout";
    }
}
