package br.com.CadastroPessoasSpring.Controller;


import br.com.CadastroPessoasSpring.Controller.dto.PessoaDto;
import br.com.CadastroPessoasSpring.Controller.form.AtualizacaoPessoasForm;
import br.com.CadastroPessoasSpring.Controller.form.PessoasForm;
import br.com.CadastroPessoasSpring.Model.Pessoas;
import br.com.CadastroPessoasSpring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoasController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Cacheable(value = "listadepessoas")
    public Page<PessoaDto> lista(@RequestParam(required = false) String nomePessoas, @PageableDefault(sort = "id", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable paginacao) {

        if(nomePessoas == null) {
            Page<Pessoas> pessoa = userRepository.findAll(paginacao);
            return PessoaDto.converter(pessoa);
        } else {
            Page<Pessoas> pessoa = userRepository.findByNome(nomePessoas, paginacao);
            return PessoaDto.converter(pessoa);
        }
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "listadepessoas", allEntries = true)
    public ResponseEntity<PessoaDto> cadastrar(@RequestBody @Valid PessoasForm form, UriComponentsBuilder uribuilder) {
        Pessoas pessoas = form.converter();
        userRepository.save(pessoas);


        URI uri = uribuilder.path("/users/{id}").buildAndExpand(pessoas.getId()).toUri();
        return ResponseEntity.created(uri).body(new PessoaDto(pessoas));

    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<PessoaDto> detalhar(@PathVariable Long id) {
        Optional<Pessoas> pessoas  = userRepository.findById(id);
        if (pessoas.isPresent()) {
            return ResponseEntity.ok(new PessoaDto(pessoas.get()));
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listadepessoas", allEntries = true)
    public ResponseEntity<PessoaDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoPessoasForm form) {
        Optional<Pessoas> optional  = userRepository.findById(id);
        if (optional.isPresent()) {
            Pessoas pessoas = form.atualizar(id, userRepository);
            return ResponseEntity.ok(new PessoaDto(optional.get()));
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listadepessoas", allEntries = true)
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Pessoas> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
