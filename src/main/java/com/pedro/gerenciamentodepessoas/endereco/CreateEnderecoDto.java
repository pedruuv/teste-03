package com.pedro.gerenciamentodepessoas.endereco;

public record CreateEnderecoDto(String logradouro, String cep, int numero, String cidade, String estado, Boolean principal) {

}
