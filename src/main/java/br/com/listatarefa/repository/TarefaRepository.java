package br.com.listatarefa.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.listatarefa.model.Tarefa;

public interface TarefaRepository extends CrudRepository<Tarefa, Integer>, TarefaRepositoryCustom {
}
