package com.pedro.gerenciamentodepessoas.endereco;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
    Optional<Endereco> findByIdAndPessoaId(Long enderecoId, Long pessoaId);
}
