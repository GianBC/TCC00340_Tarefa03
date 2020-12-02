/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import Entidades.Edicao;
import Entidades.Evento;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Gianluca Bensabat Calvano
 */
public class JPA_edicao {

    private EntityManager em;

    public void setPersistir(Edicao edicao) {
        em = EMF.getEM();
        em.persist(edicao);
        em.close();
    }

    public void setModificarEdicao(long id, Edicao edicao_nova) {
        em = EMF.getEM();
        Edicao edicao = em.find(Edicao.class, id);
        final Date DATA_NULA = Date.valueOf("1970-01-01");
        
        if (edicao_nova.getNumero() != Integer.MIN_VALUE) {
            edicao.setNumero(edicao_nova.getNumero());
        }
        if (edicao_nova.getAno() != null && !edicao_nova.getAno().isEmpty()) {
            edicao.setAno(edicao_nova.getAno());
        }
        if (!edicao_nova.getDataini().equals(DATA_NULA)) {
            edicao.setDataini(edicao_nova.getDataini());
        }
        if (!edicao_nova.getDatafim().equals(DATA_NULA)) {
            edicao.setDatafim(edicao_nova.getDatafim());
        }
        if (edicao_nova.getCidade() != null && !edicao_nova.getCidade().isEmpty()) {
            edicao.setCidade(edicao_nova.getCidade());
        }
        if (edicao_nova.getPais() != null && !edicao_nova.getPais().isEmpty()) {
            edicao.setPais(edicao_nova.getPais());
        }
        
        em.close();
    }

    public Evento getEventoUnico(String nome) {
        try {
            String jpqlQuery = "SELECT e FROM Evento e where e.nome = :n";
            em = EMF.getEM();
            Query query = em.createQuery(jpqlQuery);
            query.setParameter("n", nome);
            List<Evento> eventos = query.getResultList();
            em.close();
            return eventos.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public void setApagarEdicao(long id) {
        try {
            em = EMF.getEM();
            Edicao edicao = em.find(Edicao.class, id);

            em.remove(edicao);

            em.close();
        } catch (Exception e) {

        }
    }

    public Edicao getEdicaoPorID(long id) {
        Edicao edicao = null;
        try {
            em = EMF.getEM();
            edicao = em.find(Edicao.class, id);
            em.close();
            return edicao;
        } catch (Exception e) {

        }
        return edicao;
    }

    public List<Edicao> getTodasEdicoes() {
        String jpqlQuery = "SELECT e FROM Edicao e";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        List<Edicao> edicoes = query.getResultList();
        em.close();
        return edicoes;
    }

    public List<Edicao> getEdicoes_IdEvento(Long id_evento) {
        String jpqlQuery = "SELECT e FROM Edicao e WHERE e.evento.id = :id";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        query.setParameter("id", id_evento);
        List<Edicao> edicoes = query.getResultList();
        em.close();
        return edicoes;
    }

    public List<Edicao> getEdicoesPorData(Date data) {
        String jpqlQuery = "SELECT e FROM Edicao e WHERE e.dataini >= :datas";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        query.setParameter("datas", data, TemporalType.DATE);
        List<Edicao> edicoes = query.getResultList();
        em.close();
        return edicoes;
    }

    public List<Edicao> getEdicoesPorDataAteData(Date data1, Date data2) {
        String jpqlQuery = "SELECT e FROM Edicao e WHERE e.dataini BETWEEN :data1 AND :data2";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        query.setParameter("data1", data1, TemporalType.DATE);
        query.setParameter("data2", data2, TemporalType.DATE);
        List<Edicao> edicoes = query.getResultList();
        em.close();
        return edicoes;
    }

    public List<Edicao> getEdicoesPorCidade(String cidade) {
        String jpqlQuery = "SELECT e FROM Edicao e WHERE e.cidade LIKE :cid";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        query.setParameter("cid", "%" + cidade + "%");
        List<Edicao> edicoes = query.getResultList();
        em.close();
        return edicoes;
    }

    public List<Edicao> getEdicoesPorCidadePorData(String cidade, Date data) {
        String jpqlQuery = "SELECT e FROM Edicao e WHERE e.cidade LIKE :cid AND e.dataini >= :dat";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        query.setParameter("cid", "%" + cidade + "%");
        query.setParameter("dat", data, TemporalType.DATE);
        List<Edicao> edicoes = query.getResultList();
        em.close();
        return edicoes;
    }

    public List<Edicao> getEdicoesPorCidadePorDataAteData(String cidade, Date data1, Date data2) {
        String jpqlQuery = "SELECT e FROM Edicao e WHERE e.cidade LIKE :cid AND e.dataini BETWEEN :data1 AND :data2";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        query.setParameter("cid", "%" + cidade + "%");
        query.setParameter("data1", data1, TemporalType.DATE);
        query.setParameter("data2", data2, TemporalType.DATE);
        List<Edicao> edicoes = query.getResultList();
        em.close();
        return edicoes;
    }

}
