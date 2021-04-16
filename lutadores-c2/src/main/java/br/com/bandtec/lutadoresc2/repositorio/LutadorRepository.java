package br.com.bandtec.lutadoresc2.repositorio;

import br.com.bandtec.lutadoresc2.dominio.Lutador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LutadorRepository extends JpaRepository<Lutador,Integer> {
    public List<Lutador> findByOrderByForcaGolpeDesc();
    public List<Lutador> findByVivoTrue();
}
