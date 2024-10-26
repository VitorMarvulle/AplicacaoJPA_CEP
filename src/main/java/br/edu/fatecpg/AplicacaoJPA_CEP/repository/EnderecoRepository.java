package br.edu.fatecpg.AplicacaoJPA_CEP.repository;

import br.edu.fatecpg.AplicacaoJPA_CEP.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    Optional<Endereco> findByCep(String cep);
}