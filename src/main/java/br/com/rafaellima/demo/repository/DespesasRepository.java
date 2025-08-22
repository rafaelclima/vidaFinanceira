package br.com.rafaellima.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rafaellima.demo.model.Despesa;

@Repository
public interface DespesasRepository extends JpaRepository<Despesa, Long> {

}
