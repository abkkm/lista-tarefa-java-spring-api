package br.com.listatarefa.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.listatarefa.model.Tarefa;
import br.com.listatarefa.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private List<Tarefa> tarefas = new ArrayList<>();

    @Autowired
    private TarefaRepository tarefaRepository;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Tarefa>> seleciona_tarefas() {
        tarefas.clear();
        Iterable<Tarefa> tIterable = tarefaRepository.getAllTarefas();
        tIterable.forEach(tarefas::add);

        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @RequestMapping(value = "/id={id}", method = RequestMethod.GET)
    public ResponseEntity<List<Tarefa>> seleciona_tarefa(@PathVariable("id") String id) {
        tarefas.clear();
        Iterable<Tarefa> tIterable = tarefaRepository.getTarefa(Integer.parseInt(id));
        tIterable.forEach(tarefas::add);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/nova", method = RequestMethod.POST)
    public ResponseEntity<Boolean> novaTarefa(@RequestBody Map<String, Object> json) throws Exception {
        Boolean ok = false;
        String nometarefa = "";
        BigDecimal custo = null;
        String datalimite = "";
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "nometarefa":
                    nometarefa = entry.getValue().toString();
                    break;
                case "custo":
                    // custo = BigDecimal.valueOf(Long.parseLong(entry.getValue().toString()));
                    custo = BigDecimal.valueOf(Double.parseDouble(entry.getValue().toString().replace(',', '.')));
                    break;
                case "datalimite":
                    datalimite = entry.getValue().toString();
                    break;
            }
        }
        ok = tarefaRepository.nTarefa(nometarefa, custo, datalimite);
        return new ResponseEntity<>(ok, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/editar", method = RequestMethod.POST)
    public ResponseEntity<Boolean> editarTarefa(@RequestBody Map<String, Object> json) throws Exception {
        Boolean ok = false;

        Integer idtarefa = 0;
        String nometarefa = "";
        BigDecimal custo = null;
        String datalimite = "";
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "idtarefa":
                    idtarefa = Integer.valueOf(entry.getValue().toString());
                    break;
                case "nometarefa":
                    nometarefa = entry.getValue().toString();
                    break;
                case "custo":
                    custo = BigDecimal.valueOf(Double.parseDouble(entry.getValue().toString().replace(',', '.')));
                    break;
                case "datalimite":
                    datalimite = entry.getValue().toString();
                    break;
            }
        }

        ok = tarefaRepository.editarTarefa(idtarefa, nometarefa, custo, datalimite);

        return new ResponseEntity<>(ok, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/apagar", method = RequestMethod.POST)
    public ResponseEntity<Boolean> deleteTarefa(@RequestBody Map<String, Object> json) throws Exception {

        Boolean ok = false;

        Integer idtarefa = 0;
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "idtarefa":
                    idtarefa = Integer.valueOf(entry.getValue().toString());
                    break;
            }
        }

        ok = tarefaRepository.deleteTarefa(idtarefa);

        return new ResponseEntity<>(ok, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/ordem", method = RequestMethod.POST)
    public ResponseEntity<Boolean> ordemTarefa(@RequestBody Map<String, Object> json) throws Exception {

        Boolean ok = false;

        Integer id1 = 0;
        Integer id2 = 0;
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "id1":
                    id1 = Integer.parseInt(entry.getValue().toString());
                    break;
                case "id2":
                    id2 = Integer.parseInt(entry.getValue().toString());
                    break;
            }
        }

        ok = tarefaRepository.ordemTarefa(id1, id2);
    
        return new ResponseEntity<>(ok, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/ordembt", method = RequestMethod.POST)
    public ResponseEntity<Boolean> ordemTarefabotao(@RequestBody Map<String, Object> json) throws Exception {
        Boolean ok = false;

        Integer id = 0;
        String mov = "D";
        for (Map.Entry<String, Object> entry : json.entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    id = Integer.parseInt(entry.getValue().toString());
                    break;
                case "mov":
                    mov = entry.getValue().toString();
                    break;
            }
        }

        ok = tarefaRepository.ordemTarefabotao(id, mov);

        return new ResponseEntity<>(ok, HttpStatus.OK);
    }
}
