package br.com.CadastroPessoasSpring.Controller.dto;

import br.com.CadastroPessoasSpring.Model.Pessoas;
import org.springframework.data.domain.Page;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PessoaDto {

    private Long id;
    private String nome;
    private int idade;
    private LocalDateTime dataCadastro;
    private String endereco;
    @Column(unique = true)
    private String cpf;

    public PessoaDto(Pessoas pessoas) {
        this.id = pessoas.getId();
        this.nome = pessoas.getNome();
        this.idade = pessoas.getIdade();
        this.endereco = pessoas.getEndereco();
        this.dataCadastro = pessoas.getDataCadastro();
        this.cpf = pessoas.getCpf();
    }

    public static Page<PessoaDto> converter(Page<Pessoas> pessoas) {
        return pessoas.map(PessoaDto::new);
    }


    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getCpf() {
        return cpf;
    }


}
