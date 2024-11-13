package org.example.utazasgyakorlat.Controller;

import org.example.utazasgyakorlat.Model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.Authenticator;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


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
        model.addAttribute("contactMessage", new ContactMessage());
        return "layout";
    }
    @Autowired private ContactMessageRepository contactMessageRepository;
    @PostMapping(value = "/contact_sendmessage")
    public String sendContactMessage(@ModelAttribute ContactMessage contactMessage, RedirectAttributes redirAttr) {
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
        return "redirect:/";
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
