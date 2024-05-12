package com.pedro.gerenciamentodepessoas.pessoa;

import java.util.ArrayList;
import java.util.List;

import com.pedro.gerenciamentodepessoas.endereco.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Pessoa")
@Table(name = "pessoas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pessoa {

    public Pessoa(@NotNull @Valid CadastroPessoaDto dados) {
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();

    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "data_nascimento")
    private String dataNascimento;
    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    public void updateInfo(@Valid UpdatePessoaDto dados) {
        this.nome = dados.nome();
        this.dataNascimento = dados.dataNascimento();
    }

    public void addEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
        endereco.setPessoa(this);
    }

}
