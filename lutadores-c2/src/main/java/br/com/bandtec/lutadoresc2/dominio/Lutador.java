package br.com.bandtec.lutadoresc2.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Lutador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 12)
    private String nome;

    @PositiveOrZero
    private Double forcaGolpe;

    private Double vida=100.0;
    private Integer concentracoesRealizadas=0;
    private Boolean vivo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getForcaGolpe() {
        return forcaGolpe;
    }

    public void setForcaGolpe(Double forcaGolpe) {
        this.forcaGolpe = forcaGolpe;
    }

    public Double getVida() {
        return vida;
    }
    public void setVida() {
        vida = vida*1.15;
    }

    public Integer getConcentracoesRealizadas() {
        return concentracoesRealizadas;
    }

    public Boolean getVivo() {
        return vivo;
    }

    public void setVivo() {
        if(vida>0){
        vivo=true;
        }
        else{
            vivo=false;
        }
    }
}
