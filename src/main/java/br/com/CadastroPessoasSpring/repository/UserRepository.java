package br.com.CadastroPessoasSpring.repository;

import br.com.CadastroPessoasSpring.Model.Pessoas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<Pessoas, Long> {
    Page<Pessoas> findByNome(String nomePessoas, Pageable paginacao);
}
