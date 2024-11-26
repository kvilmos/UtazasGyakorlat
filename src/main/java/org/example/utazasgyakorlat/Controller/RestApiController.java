package org.example.utazasgyakorlat.Controller;

import org.example.utazasgyakorlat.Model.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class RestApiController {
    private final SzallodaRepository repo;
    RestApiController(SzallodaRepository repo) {
        this.repo = repo;
    }


    @GetMapping("/szalloda")
    public Iterable<Szalloda> getAllOffers() {
        return repo.findAll();
    }
    @GetMapping("/szalloda/{id}")
    public Szalloda getSzallodaById(@PathVariable String id) {
        return repo.findById(id)
                .orElseThrow(() -> new SzallodaNotFoundException(id));
    }
    @PostMapping("/szalloda")
    public Szalloda addSzalloda(@RequestBody Szalloda newSzalloda) {
        return repo.save(newSzalloda);
    }


}
