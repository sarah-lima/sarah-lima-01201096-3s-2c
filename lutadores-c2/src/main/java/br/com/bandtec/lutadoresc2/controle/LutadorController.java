package br.com.bandtec.lutadoresc2.controle;

import br.com.bandtec.lutadoresc2.dominio.Lutador;
import br.com.bandtec.lutadoresc2.repositorio.LutadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity postConcentrar(@PathVariable int id){
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
    @PostMapping("/golpe")
    public ResponseEntity postGolpe(@RequestBody int idLutadorBate, @RequestBody int idLutadorApanha){
        if (idLutadorApanha > 0 && idLutadorBate > 0) {
            if(repository.existsById(idLutadorApanha) && repository.existsById(idLutadorBate)){
            Optional<Lutador> lut1 = repository.findById(idLutadorBate);
            Optional<Lutador> lut2 = repository.findById(idLutadorApanha);
            if(lut1.get().getVivo() && lut2.get().getVivo()){
            Double golpe = lut1.get().getForcaGolpe();
            lut2.get().apanha(golpe);
            List vetor = new ArrayList();
            vetor.add(lut1);
            vetor.add(lut2);
                return ResponseEntity.status(201).body(vetor);
            }
                return ResponseEntity.status(400).body("Ambos os lutadores devem estar vivos!");
            }
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.status(400).build();
        }

    }
    @GetMapping("/contagem-vivos")
    public ResponseEntity getMortos(){
        return ResponseEntity.ok(repository.findByVivoFalse().size());
    }


}
