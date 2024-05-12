package com.pedro.gerenciamentodepessoas.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
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
import com.pedro.gerenciamentodepessoas.endereco.CreateEnderecoDto;
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
    public ResponseEntity<GetPessoaDto> addPessoa(@RequestBody @Valid CadastroPessoaDto dados, UriComponentsBuilder builder){
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
    public ResponseEntity<GetPessoaDto> getPessoa(@PathVariable Long id){
        var pessoa = pessoaRepository.getReferenceById(id);

        return ResponseEntity.ok(new GetPessoaDto(pessoa));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<GetPessoaDto> updatePessoa(@RequestBody @Valid UpdatePessoaDto dados){
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
    public ResponseEntity<GetEnderecoDto> getEnderecoPessoa(@PathVariable Long pessoaId, @PathVariable Long enderecoId){
        var pessoa = pessoaRepository.getReferenceById(pessoaId);

        var enderecoOptional = pessoa.getEnderecos().stream().filter(e -> e.getId().equals(enderecoId)).findFirst();

        if (!enderecoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        var endereco = enderecoOptional.get();
        return ResponseEntity.ok().body(new GetEnderecoDto(endereco));
    }

    @PostMapping("/{pessoaId}/enderecos")
    @Transactional
    public ResponseEntity<GetEnderecoDto> addEnderecoPessoa(@PathVariable Long pessoaId, @RequestBody @Valid CreateEnderecoDto dados, UriComponentsBuilder builder) {
        Optional<Pessoa> optionalPessoa = pessoaRepository.findById(pessoaId);
        if (optionalPessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pessoa pessoa = optionalPessoa.get();

        Endereco endereco = new Endereco(dados);

        pessoa.addEndereco(endereco);

        enderecoRepository.save(endereco);

        var uri = builder.path("/pessoas/{pessoaId}/enderecos/{enderecoId}").buildAndExpand(pessoa.getId(), endereco.getId()).toUri();

        return ResponseEntity.created(uri).body(new GetEnderecoDto(endereco));
        
    }

    @PutMapping("/{pessoaId}/enderecos/{enderecoId}")
    @Transactional
    public ResponseEntity<GetEnderecoDto> updateEndereco(@PathVariable Long pessoaId, @PathVariable Long enderecoId, @RequestBody @Valid UpdateEnderecoDto dados){
        var pessoa = pessoaRepository.getReferenceById(pessoaId);

        var enderecoOptional = pessoa.getEnderecos().stream().filter(e -> e.getId().equals(enderecoId)).findFirst();

        if (!enderecoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        var endereco = enderecoOptional.get();
        endereco.updateInfo(dados);

        return ResponseEntity.ok(new GetEnderecoDto(endereco));
    }


}
