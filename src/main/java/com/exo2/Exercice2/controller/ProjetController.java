package com.exo2.Exercice2.controller;

import com.exo2.Exercice2.dto.EtudiantDto;
import com.exo2.Exercice2.dto.ProjetDto;
import com.exo2.Exercice2.service.ProjetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projets")
public class ProjetController {
    @Autowired
    private ProjetService projetService;

    @GetMapping
    @Cacheable(value = "ProjetList")
    public ResponseEntity<List<ProjetDto>> findAll() {
        return ResponseEntity.ok(projetService.findAll());
    }

    @GetMapping("/{id}")
    @Cacheable(value = "ProjetListId",key = "#projet_id")
    public ResponseEntity<ProjetDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(projetService.findById(id));
    }

    @PostMapping
    @Caching(put = {@CachePut(value = "ProjetList"),@CachePut(value = "ProjetListId",key = "#projet_id")})
    public ResponseEntity<ProjetDto> save(@RequestBody ProjetDto projetDto) {
        return ResponseEntity.ok(projetService.save(projetDto));
    }

    @GetMapping("/{id}/etudiants")
    public ResponseEntity<List<EtudiantDto>> findEtudiantsByProjetId(@PathVariable Long id) {
        return ResponseEntity.ok(projetService.findEtudiantsByProjetId(id));
    }
}
