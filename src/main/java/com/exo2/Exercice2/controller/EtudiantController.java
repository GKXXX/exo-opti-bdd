package com.exo2.Exercice2.controller;

import com.exo2.Exercice2.dto.EtudiantDto;
import com.exo2.Exercice2.entity.Etudiant;
import com.exo2.Exercice2.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @GetMapping
    @Cacheable(value = "EtudiantList")
    public ResponseEntity<List<EtudiantDto>> findAll()
    {
        return ResponseEntity.ok(etudiantService.findAll());
    }

    @GetMapping("/{id}")
    @Cacheable(value = "EtudiantListId", key = "#etudiant_id")
    public ResponseEntity<EtudiantDto> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(etudiantService.findById(id));
    }

    @GetMapping("/findBy")
    @Cacheable(value = "EtudiantListNomPrenom",key = "{#nom,#prenom}")
    public ResponseEntity<EtudiantDto> findBy(@RequestParam String nom, @RequestParam String prenom) {
        return ResponseEntity.ok(etudiantService.findOneByNomAndPrenom(nom, prenom));
    }

    @PostMapping
    @Caching(put = {@CachePut(value = "EtudiantListNomPrenom",key = "{#nom,#prenom}"),
            @CachePut(value = "EtudiantListId",key = "#etudiant_id"),
            @CachePut(value = "EtudiantList")})
    public ResponseEntity<EtudiantDto> save(@RequestBody EtudiantDto etudiantDto) {
        return ResponseEntity.ok(etudiantService.save(etudiantDto));
    }

    @PutMapping("/{id}")
    @Caching(put = {@CachePut(value = "EtudiantListNomPrenom",key = "{#nom,#prenom}"),
            @CachePut(value = "EtudiantListId",key = "#etudiant_id"),
            @CachePut(value = "EtudiantList")})
    public ResponseEntity<EtudiantDto> update(@PathVariable Long id, @RequestBody EtudiantDto etudiantDto) {
        return ResponseEntity.ok(etudiantService.update(id, etudiantDto));
    }

    @DeleteMapping("{id}")
    @Caching(evict = {@CacheEvict(value = "EtudiantList"),
            @CacheEvict(value = "EtudiantListId",key = "#etudiant_id"),
            @CacheEvict(value = "EtudiantListNomPrenom",key = "{#nom,#prenom}")})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
