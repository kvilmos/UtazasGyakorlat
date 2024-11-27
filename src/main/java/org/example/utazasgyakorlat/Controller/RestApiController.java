package org.example.utazasgyakorlat.Controller;

import org.apache.logging.log4j.Logger;
import org.example.utazasgyakorlat.Model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


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
    @PutMapping("/szalloda/{id}")
    public ResponseEntity<Szalloda> updateSzalloda(
            @PathVariable String id,
            @RequestBody Szalloda szalloda) {
        try {
            Optional<Szalloda> existingSzallodaOpt = repo.findById(id);
            if (existingSzallodaOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Szalloda existingSzalloda = existingSzallodaOpt.get();

            existingSzalloda.setNev(szalloda.getNev());
            existingSzalloda.setBesorolas(szalloda.getBesorolas());
            existingSzalloda.setHelyseg_az(szalloda.getHelyseg_az());
            existingSzalloda.setTengerpart_tav(szalloda.getTengerpart_tav());
            existingSzalloda.setRepter_tav(szalloda.getRepter_tav());
            existingSzalloda.setFelpanzio(szalloda.isFelpanzio());

            if (szalloda.getHelyseg() != null) {
                existingSzalloda.setHelyseg(szalloda.getHelyseg());
            }

            Szalloda updatedSzalloda = repo.save(existingSzalloda);
            return ResponseEntity.ok(updatedSzalloda);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/szalloda/{id}")
    public void deleteSzalloda(@PathVariable String id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Szalloda not found with id: " + id);
        }
        repo.deleteById(id);
    }


}
