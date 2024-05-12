package com.pedro.gerenciamentodepessoas.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pedro.gerenciamentodepessoas.endereco.Endereco;
import com.pedro.gerenciamentodepessoas.endereco.EnderecoDto;
import com.pedro.gerenciamentodepessoas.endereco.EnderecoRepository;
import com.pedro.gerenciamentodepessoas.endereco.GetEnderecoDto;
import com.pedro.gerenciamentodepessoas.endereco.UpdateEnderecoDto;
import com.pedro.gerenciamentodepessoas.pessoa.CadastroPessoaDto;
import com.pedro.gerenciamentodepessoas.pessoa.GetPessoaDto;
import com.pedro.gerenciamentodepessoas.pessoa.Pessoa;
import com.pedro.gerenciamentodepessoas.pessoa.PessoaRepository;
import com.pedro.gerenciamentodepessoas.pessoa.UpdatePessoaDto;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity addPessoa(@RequestBody @Valid CadastroPessoaDto dados, UriComponentsBuilder builder){
        var pessoa = new Pessoa(dados);
        pessoaRepository.save(pessoa);

        var uri = builder.path("/pessoas/{id}").buildAndExpand(pessoa.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetPessoaDto(pessoa));
    }

    @GetMapping
    public ResponseEntity<List<GetPessoaDto>> getPessoas(@PageableDefault(size = 10) Pageable paginacao){
        var page = pessoaRepository.findAll().stream().map(GetPessoaDto::new).toList();

        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity getPessoa(@PathVariable Long id){
        var pessoa = pessoaRepository.getReferenceById(id);

        return ResponseEntity.ok(new GetPessoaDto(pessoa));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updatePessoa(@RequestBody @Valid UpdatePessoaDto dados){
        var pessoa = pessoaRepository.getReferenceById(dados.id());
        pessoa.updateInfo(dados);

        return ResponseEntity.ok(new GetPessoaDto(pessoa));
    }

    @GetMapping("/{id}/enderecos")
    public ResponseEntity<List<GetEnderecoDto>> getEnderecosPessoa(@PathVariable Long id){
        var pessoa = pessoaRepository.getReferenceById(id);

        var enderecos = pessoa.getEnderecos().stream().map(GetEnderecoDto::new).toList();

        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{pessoaId}/enderecos/{enderecoId}")
    public ResponseEntity getEnderecoPessoa(@PathVariable Long pessoaId, @PathVariable Long enderecoId){
        try {
            Endereco endereco = enderecoRepository.findByIdAndPessoaId(enderecoId, pessoaId)
            .orElseThrow(() -> new RuntimeException("Endereço não encontrado para esta pessoa"));

            return ResponseEntity.ok(new GetEnderecoDto(endereco));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{pessoaId}/enderecos")
    @Transactional
    public ResponseEntity addEnderecoPessoa(@PathVariable Long id, @RequestBody @Valid EnderecoDto dados, UriComponentsBuilder builder){
        var pessoa = pessoaRepository.getReferenceById(id);

        var endereco = new Endereco(dados);

        pessoa.addEndereco(endereco);

        enderecoRepository.save(endereco);

        var uri = builder.path("/pessoas/{pessoaId}/enderecos/{enderecoId}").buildAndExpand(id, endereco.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetEnderecoDto(endereco));
    }

    @PutMapping("/{pessoaID}/enderecos/{enderecoId}")
    @Transactional
    public ResponseEntity updateEndereco(@PathVariable Long pessoaId, @PathVariable Long enderecoId, @RequestBody @Valid UpdateEnderecoDto dados){
        try {
            Endereco endereco = enderecoRepository.findByIdAndPessoaId(enderecoId, pessoaId)
            .orElseThrow(() -> new RuntimeException("Endereço não encontrado para esta pessoa"));

            endereco.updateInfo(dados);

            return ResponseEntity.ok(new GetEnderecoDto(endereco));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
