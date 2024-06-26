package com.pedro.gerenciamentodepessoas.endereco;

public record GetEnderecoDto(String logradouro, String cep, int numero, String cidade, String estado, Boolean principal, Long id) {
    public GetEnderecoDto(Endereco endereco){
        this(endereco.getLogradouro(), endereco.getCep(), endereco.getNumero(), endereco.getCidade(), endereco.getEstado(), endereco.isPrincipal(), endereco.getId());
    }
}
