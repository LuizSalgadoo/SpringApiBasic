package br.com.CadastroPessoasSpring.Controller.form;

import br.com.CadastroPessoasSpring.Model.Pessoas;
import br.com.CadastroPessoasSpring.repository.UserRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AtualizacaoPessoasForm {
    @NotNull
    @NotEmpty
    private String nome;

    @NotNull @NotEmpty @Length(min = 2)
    private String endereco;

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Pessoas atualizar(Long id, UserRepository userRepository) {
        Pessoas pessoas = userRepository.getReferenceById(id);

        pessoas.setNome(this.nome);
        pessoas.setEndereco(this.endereco);

        return pessoas;
    }
}
