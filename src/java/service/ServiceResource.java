/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entidades.Edicao;
import Entidades.Evento;
import JPA.JPA_edicao;
import JPA.JPA_evento;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.servlet.http.HttpServletResponse;

/**
 * REST Web Service
 *
 * @author Gianluca Bensabat Calvano
 */
@Stateless
@Path("service")
public class ServiceResource {

    @PersistenceContext(unitName = "tarefa03PU")
    private EntityManager em;

    @Context
    private UriInfo context;

    public ServiceResource() {
    }

    @POST
    @Path("/evento")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_XML})
    public Evento create_Form_Evento(@FormParam("nome") String nome, @FormParam("sigla") String sigla,
            @FormParam("area") String area, @FormParam("inst_org") String inst_org) {

        JPA_evento dao = new JPA_evento();
        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setSigla(sigla);
        evento.setArea(area);
        evento.setInst_org(inst_org);
        dao.setPersistir(evento);
        return evento;
    }

    @POST
    @Path("/edicao/{id}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_XML})
    public Edicao create_Form_Edicao(@PathParam("id") Long id_evento, @FormParam("numero") String numero,
            @FormParam("cidade") String cidade, @FormParam("pais") String pais, @FormParam("ano") String ano,
            @FormParam("data_ini") String dataini_Str, @FormParam("data_fim") String datafim_Str,
            @FormParam("id_evento_form") String id_evento_form, @Context HttpServletResponse response) throws IOException {

        JPA_evento dao_evento = new JPA_evento();
        if (id_evento == -123 && id_evento_form.isEmpty()) {
            id_evento = (long)-123;
        } else if (id_evento == -123){
            id_evento = Long.parseLong(id_evento_form);
        }
        Evento evento = dao_evento.getEventoPorID(id_evento);
        if (evento == null) {
            String msg=("Não foi localizado evento para o ID: "+id_evento+" - Retorne a página e informe um novo ID.");
            response.sendError(400, msg);
            return null;
        } else {
            JPA_edicao dao = new JPA_edicao();
            Edicao edicao = new Edicao();
            edicao.setNumero(Integer.parseInt(numero));
            edicao.setCidade(cidade);
            edicao.setPais(pais);
            edicao.setAno(ano);
            edicao.setDataini(Date.valueOf(dataini_Str));
            edicao.setDatafim(Date.valueOf(datafim_Str));
            edicao.setEvento(evento);
            dao.setPersistir(edicao);
            return edicao;
        }
    }

    @PUT
    @Path("/evento/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit_Evento(@PathParam("id") Long id, Evento entity) {
        JPA_evento dao = new JPA_evento();
        dao.setModificarEvento(id, entity);
    }

    @PUT
    @Path("/evento/{id}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void edit_Evento_Form(@PathParam("id") Long id, @FormParam("nome") String nome,
            @FormParam("sigla") String sigla, @FormParam("area") String area, @FormParam("inst_org") String inst_org) {

        JPA_evento dao = new JPA_evento();
        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setSigla(sigla);
        evento.setArea(area);
        evento.setInst_org(inst_org);
        dao.setModificarEvento(id, evento);
    }

    @PUT
    @Path("/edicao/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit_Edicao(@PathParam("id") Long id, Edicao entity) {
        JPA_edicao dao = new JPA_edicao();
        dao.setModificarEdicao(id, entity);
    }

    @PUT
    @Path("/edicao/{id}")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void edit_Edicao_Form(@PathParam("id") Long id, @FormParam("numero") String numero,
            @FormParam("cidade") String cidade, @FormParam("pais") String pais, @FormParam("ano") String ano,
            @FormParam("data_ini") String dataini_Str, @FormParam("data_fim") String datafim_Str) {

        JPA_edicao dao = new JPA_edicao();
        Edicao edicao = new Edicao();
        final Date DATA_NULA = Date.valueOf("1970-01-01");
        edicao.setNumero(Integer.MIN_VALUE);
        edicao.setDataini(DATA_NULA);
        edicao.setDatafim(DATA_NULA);
        edicao.setCidade(cidade);
        edicao.setPais(pais);
        edicao.setAno(ano);

        if (numero != null && !numero.isEmpty()) {
            edicao.setNumero(Integer.parseInt(numero));
        }
        if (dataini_Str != null && !dataini_Str.isEmpty()) {
            edicao.setDataini(Date.valueOf(dataini_Str));
        }
        if (datafim_Str != null && !datafim_Str.isEmpty()) {
            edicao.setDatafim(Date.valueOf(datafim_Str));
        }

        dao.setModificarEdicao(id, edicao);
    }

    @DELETE
    @Path("/evento/{id}")
    public void remove_Evento(@PathParam("id") Long id) {
        JPA_evento dao = new JPA_evento();
        dao.setApagarEvento(id);
    }

    @DELETE
    @Path("/edicao/{id}")
    public void remove_Edicao(@PathParam("id") Long id) {
        JPA_edicao dao = new JPA_edicao();
        dao.setApagarEdicao(id);
    }

    @GET
    @Path("/evento/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Evento find_Evento_XML(@PathParam("id") Long id) {
        JPA_evento dao = new JPA_evento();
        Evento evento = dao.getEventoPorID(id);
        return evento;
    }

    @GET
    @Path("/evento/{id}/edicoes")
    @Produces({MediaType.APPLICATION_XML})
    public List<Edicao> find_Edicoes_unicEvento_XML(@PathParam("id") Long id) {
        JPA_edicao dao = new JPA_edicao();
        return dao.getEdicoes_IdEvento(id);
    }

    @GET
    @Path("/edicao/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Edicao find_Edicao_XML(@PathParam("id") Long id) {
        JPA_edicao dao = new JPA_edicao();
        Edicao edicao = dao.getEdicaoPorID(id);
        return edicao;
    }

    @GET
    @Path("/evento")
    @Produces({MediaType.APPLICATION_XML})
    public List<Evento> find_All_Evento_XML() {
        JPA_evento dao = new JPA_evento();
        return dao.getTodosEventos();
    }

    @GET
    @Path("/edicao")
    @Produces({MediaType.APPLICATION_XML})
    public List<Edicao> find_All_Edicao_XML() {
        JPA_edicao dao = new JPA_edicao();
        return dao.getTodasEdicoes();
    }

    @GET
    @Path("/data/{data}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Edicao> find_Edicao_Evento_DataInicial_XML(@PathParam("data") Date data) {
        JPA_edicao dao = new JPA_edicao();
        return dao.getEdicoesPorData(data);
    }

    @GET
    @Path("/data/{data1}/{data2}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Edicao> find_Edicao_Evento_Entre_Datas_XML(@PathParam("data1") Date data1,
            @PathParam("data2") Date data2) {

        JPA_edicao dao = new JPA_edicao();
        return dao.getEdicoesPorDataAteData(data1, data2);
    }

    @GET
    @Path("/cidade/{cidade}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Edicao> find_Edicao_Evento_Cidade_XML(@PathParam("cidade") String cidade) {
        JPA_edicao dao = new JPA_edicao();
        return dao.getEdicoesPorCidade(cidade);
    }

    @GET
    @Path("/cidade/{cidade}/{data}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Edicao> find_Edicao_Evento_Cidade_DataInicial_XML(@PathParam("cidade") String cidade,
            @PathParam("data") Date data) {

        JPA_edicao dao = new JPA_edicao();
        return dao.getEdicoesPorCidadePorData(cidade, data);
    }

    @GET
    @Path("/cidade/{cidade}/{data1}/{data2}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Edicao> find_Edicao_Evento_Cidade_Entre_Datas_XML(@PathParam("cidade") String cidade,
            @PathParam("data1") Date data1, @PathParam("data2") Date data2) {

        JPA_edicao dao = new JPA_edicao();
        return dao.getEdicoesPorCidadePorDataAteData(cidade, data1, data2);
    }

    protected EntityManager getEntityManager() {
        return em;
    }

}
