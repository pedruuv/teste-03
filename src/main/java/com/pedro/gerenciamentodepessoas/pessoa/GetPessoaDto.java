package com.pedro.gerenciamentodepessoas.pessoa;

import java.util.List;

import com.pedro.gerenciamentodepessoas.endereco.Endereco;

public record GetPessoaDto(Long id, String nome, String dataNascimento, List<Endereco> enderecos) {

    public GetPessoaDto(Pessoa pessoa){
        this(pessoa.getId(), pessoa.getNome(), pessoa.getDataNascimento(), pessoa.getEnderecos());
    }

}
