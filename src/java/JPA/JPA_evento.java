/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JPA;

import Entidades.Evento;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Gianluca Bensabat Calvano
 */
public class JPA_evento {

    private EntityManager em;

    public JPA_evento() {
    }

    public void setPersistir(Evento evento) {
        em = EMF.getEM();
        em.persist(evento);
        em.close();
    }

    public Evento getEventoPorID(Long id) {
        em = EMF.getEM();
        Evento evento = em.find(Evento.class, id);
        em.close();
        return evento;
    }

    public void setModificarEvento(Long id, Evento evento_novo) {
        em = EMF.getEM();
        Evento evento = em.find(Evento.class, id);
        if (evento_novo.getNome() != null && !evento_novo.getNome().isEmpty()) {
            evento.setNome(evento_novo.getNome());
        }
        if (evento_novo.getSigla() != null && !evento_novo.getSigla().isEmpty()) {
            evento.setSigla(evento_novo.getSigla());
        }
        if (evento_novo.getArea() != null && !evento_novo.getArea().isEmpty()) {
            evento.setArea(evento_novo.getArea());
        }
        if (evento_novo.getInst_org() != null && !evento_novo.getInst_org().isEmpty()) {
            evento.setInst_org(evento_novo.getInst_org());
        }
        em.close();
    }

    public void setApagarEvento(Long id) {
        em = EMF.getEM();
        Evento evento = em.find(Evento.class, id);
        em.remove(evento);
        em.close();
    }

    public List<Evento> getNomeEventos(String nome) {
        String jpqlQuery = "SELECT e FROM Evento e where e.nome = :n";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        query.setParameter("n", nome);
        List<Evento> eventos = query.getResultList();
        em.close();
        return eventos;
    }

    public List<Evento> getTodosEventos() {
        String jpqlQuery = "SELECT e FROM Evento e";
        em = EMF.getEM();
        Query query = em.createQuery(jpqlQuery);
        List<Evento> eventos = query.getResultList();
        em.close();
        return eventos;
    }
}
