package com.pedro.gerenciamentodepessoas.pessoa;

import com.pedro.gerenciamentodepessoas.endereco.EnderecoDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroPessoaDto(@NotBlank String nome, @NotBlank String dataNascimento, @NotNull @Valid EnderecoDto endereco) {

}
