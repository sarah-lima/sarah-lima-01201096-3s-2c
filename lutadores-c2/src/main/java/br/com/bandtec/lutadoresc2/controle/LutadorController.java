package br.com.bandtec.lutadoresc2.controle;

import br.com.bandtec.lutadoresc2.dominio.Lutador;
import br.com.bandtec.lutadoresc2.repositorio.LutadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/lutadores")
public class LutadorController {
    @Autowired
    private LutadorRepository repository;

    @PostMapping
    public ResponseEntity postLutador(@RequestBody @Valid Lutador lutador){

        return ResponseEntity.status(201).body(repository.save(lutador));
    }
    @GetMapping
    public ResponseEntity getLutador(){
        if(repository.count()>0){
        return ResponseEntity.ok(repository.findByOrderByForcaGolpeDesc());
        }return ResponseEntity.notFound().build();
    }

    @GetMapping("/contagem-vivos")
    public ResponseEntity getVivos(){
        return ResponseEntity.ok(repository.findByVivoTrue().size());
    }


    @PostMapping("/{id}/concentrar")
    public ResponseEntity postConcentrar(@RequestParam Integer id){
        if(repository.existsById(id)){
            Lutador lut = repository.getOne(id);
            if(lut.getConcentracoesRealizadas()==3){
                return ResponseEntity.status(400).body("Lutador já se concentrou 3 vezes!");
            }
            lut.setVida();
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
