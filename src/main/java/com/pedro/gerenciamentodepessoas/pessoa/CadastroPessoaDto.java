package com.pedro.gerenciamentodepessoas.pessoa;

import jakarta.validation.constraints.NotBlank;

public record CadastroPessoaDto(@NotBlank String nome, @NotBlank String dataNascimento) {

}
