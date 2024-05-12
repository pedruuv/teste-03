package com.pedro.gerenciamentodepessoas.endereco;

import com.pedro.gerenciamentodepessoas.pessoa.Pessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Endereco")
@Table(name = "endereco")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {
    public Endereco(@NotNull @Valid CreateEnderecoDto endereco) {
        this.logradouro = endereco.logradouro();
        this.cep = endereco.cep();
        this.numero = endereco.numero();
        this.cidade = endereco.cidade();
        this.estado = endereco.estado();
        this.principal = false;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String logradouro;
    private String cep;
    private int numero;
    private String cidade;
    private String estado;
    @ManyToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;
    private boolean principal;
    public void updateInfo(@Valid UpdateEnderecoDto dados) {
        if (dados.cep() != null) {
            this.cep = dados.cep();
        }
        if (dados.cidade()!=null) {
            this.cidade = dados.cidade();
        }
        if (dados.estado() != null) {
            this.estado = dados.estado();
            
        }
        if (dados.logradouro() != null) {
            this.logradouro = dados.logradouro();
        }

        if (dados.numero() != 0) {
            this.numero = dados.numero();
        }
        if (dados.principal() != null) {
            this.principal = dados.principal();
        }
    }

}
