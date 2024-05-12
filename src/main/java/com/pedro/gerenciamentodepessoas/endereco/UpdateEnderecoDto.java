package com.pedro.gerenciamentodepessoas.endereco;

public record UpdateEnderecoDto(Boolean principal, String logradouro, String cep, int numero, String cidade, String estado) {

}
