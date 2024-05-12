package com.pedro.gerenciamentodepessoas.endereco;

public record EnderecoDto(String logradouro, String cep, int numero, String cidade, String estado, Boolean principal) {

}
