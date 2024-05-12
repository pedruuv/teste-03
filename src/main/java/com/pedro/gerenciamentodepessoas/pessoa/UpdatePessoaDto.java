package com.pedro.gerenciamentodepessoas.pessoa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePessoaDto(@NotNull Long id, @NotBlank String nome, @NotBlank String dataNascimento) {

}
