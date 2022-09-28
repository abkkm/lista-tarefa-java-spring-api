package br.com.listatarefa.repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.QueryTimeoutException;
import javax.persistence.StoredProcedureQuery;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import br.com.listatarefa.model.Tarefa;

public class TarefaRepositoryImpl implements TarefaRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<Tarefa> getAllTarefas() {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createNamedStoredProcedureQuery("getAllTarefas");
            return storedProcedureQuery.getResultList();
        } catch (QueryTimeoutException ex) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Tarefa> getTarefa(Integer id) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createNamedStoredProcedureQuery("getTarefa");
            storedProcedureQuery.setParameter("idtarefa", id);
            return storedProcedureQuery.getResultList();
        } catch (QueryTimeoutException ex) {
            return null;
        }
    }

    @Override
    public Boolean nTarefa(String nometarefa, BigDecimal custo, String datalimite) {
        Boolean ok = false;
        try {
            Date dt = Date.valueOf(datalimite);
            StoredProcedureQuery storedProcedureQuery = em
                .createStoredProcedureQuery("nova_tarefa", Tarefa.class)
                .registerStoredProcedureParameter("nometarefa", String.class, ParameterMode.IN)
                .registerStoredProcedureParameter("custo", BigDecimal.class, ParameterMode.IN)
                .registerStoredProcedureParameter("datalimite", Date.class, ParameterMode.IN)
                .registerStoredProcedureParameter("ok", Boolean.class, ParameterMode.OUT);
            storedProcedureQuery.setParameter("nometarefa", nometarefa);
            storedProcedureQuery.setParameter("custo", custo);
            storedProcedureQuery.setParameter("datalimite", dt);
            storedProcedureQuery.execute();
            ok = (Boolean) storedProcedureQuery.getOutputParameterValue("ok");
            return ok;
        } catch (QueryTimeoutException ex) {
            return false;
        }
    }

    @Override
    public Boolean editarTarefa(Integer id, String nometarefa, BigDecimal custo, String datalimite) {
        Boolean ok = false;
        try {
            Date dt = Date.valueOf(datalimite);
            StoredProcedureQuery storedProcedureQuery = em
                    .createStoredProcedureQuery("editar_tarefa", Tarefa.class)
                    .registerStoredProcedureParameter("idtarefa", Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("nometarefa", String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("custo", BigDecimal.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("datalimite", Date.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("ok", Boolean.class, ParameterMode.OUT);
            storedProcedureQuery.setParameter("idtarefa", id);
            storedProcedureQuery.setParameter("nometarefa", nometarefa);
            storedProcedureQuery.setParameter("custo", custo);
            storedProcedureQuery.setParameter("datalimite", dt);
            storedProcedureQuery.execute();
            ok = (Boolean) storedProcedureQuery.getOutputParameterValue("ok");
            return ok;
        } catch (QueryTimeoutException ex) {
            return false;
        }
    }

    @Override
    public Boolean deleteTarefa(Integer id) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createNamedStoredProcedureQuery("deleteTarefa");
            storedProcedureQuery.setParameter("idtarefa", id);
            storedProcedureQuery.execute();
            return true;
        } catch (QueryTimeoutException ex) {
            return false;
        }
    }

    @Override
    public Boolean ordemTarefa(Integer id1, Integer id2) {
        try {
            StoredProcedureQuery storedProcedureQuery = em.createNamedStoredProcedureQuery("ordemTarefa");
            storedProcedureQuery.setParameter("idtarefa", id1);
            storedProcedureQuery.setParameter("idtarefa1", id2);
            storedProcedureQuery.execute();
            return true;
        } catch (QueryTimeoutException ex) {
            return false;
        }
    }

    @Override
    public Boolean ordemTarefabotao(Integer id, String mov) {
        try {
            StoredProcedureQuery storedProcedureQuery = em
                .createStoredProcedureQuery("altera_ordem_aparesentacao_botao", Tarefa.class)
                .registerStoredProcedureParameter("idtarefa", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("mov", String.class, ParameterMode.IN);
            storedProcedureQuery.setParameter("idtarefa", id);
            storedProcedureQuery.setParameter("mov", mov);
            storedProcedureQuery.execute();
            return true;
        } catch (QueryTimeoutException ex) {
            return false;
        }
    }
}
