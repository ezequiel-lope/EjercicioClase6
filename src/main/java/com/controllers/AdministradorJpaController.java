/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.controllers.exceptions.NonexistentEntityException;
import com.models.Administrador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.models.Servicio;
import java.util.ArrayList;
import java.util.List;
import com.models.Cliente;
import com.models.Profesional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ezelc
 */
public class AdministradorJpaController implements Serializable {

    public AdministradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public AdministradorJpaController() {
        emf = Persistence.createEntityManagerFactory("clase6JPAPU");
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administrador administrador) {
        if (administrador.getServicios() == null) {
            administrador.setServicios(new ArrayList<Servicio>());
        }
        if (administrador.getClientes() == null) {
            administrador.setClientes(new ArrayList<Cliente>());
        }
        if (administrador.getProfesionales() == null) {
            administrador.setProfesionales(new ArrayList<Profesional>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Servicio> attachedServicios = new ArrayList<Servicio>();
            for (Servicio serviciosServicioToAttach : administrador.getServicios()) {
                serviciosServicioToAttach = em.getReference(serviciosServicioToAttach.getClass(), serviciosServicioToAttach.getId());
                attachedServicios.add(serviciosServicioToAttach);
            }
            administrador.setServicios(attachedServicios);
            List<Cliente> attachedClientes = new ArrayList<Cliente>();
            for (Cliente clientesClienteToAttach : administrador.getClientes()) {
                clientesClienteToAttach = em.getReference(clientesClienteToAttach.getClass(), clientesClienteToAttach.getId());
                attachedClientes.add(clientesClienteToAttach);
            }
            administrador.setClientes(attachedClientes);
            List<Profesional> attachedProfesionales = new ArrayList<Profesional>();
            for (Profesional profesionalesProfesionalToAttach : administrador.getProfesionales()) {
                profesionalesProfesionalToAttach = em.getReference(profesionalesProfesionalToAttach.getClass(), profesionalesProfesionalToAttach.getId());
                attachedProfesionales.add(profesionalesProfesionalToAttach);
            }
            administrador.setProfesionales(attachedProfesionales);
            em.persist(administrador);
            for (Servicio serviciosServicio : administrador.getServicios()) {
                Administrador oldAdministradorOfServiciosServicio = serviciosServicio.getAdministrador();
                serviciosServicio.setAdministrador(administrador);
                serviciosServicio = em.merge(serviciosServicio);
                if (oldAdministradorOfServiciosServicio != null) {
                    oldAdministradorOfServiciosServicio.getServicios().remove(serviciosServicio);
                    oldAdministradorOfServiciosServicio = em.merge(oldAdministradorOfServiciosServicio);
                }
            }
            for (Cliente clientesCliente : administrador.getClientes()) {
                Administrador oldAdministradorOfClientesCliente = clientesCliente.getAdministrador();
                clientesCliente.setAdministrador(administrador);
                clientesCliente = em.merge(clientesCliente);
                if (oldAdministradorOfClientesCliente != null) {
                    oldAdministradorOfClientesCliente.getClientes().remove(clientesCliente);
                    oldAdministradorOfClientesCliente = em.merge(oldAdministradorOfClientesCliente);
                }
            }
            for (Profesional profesionalesProfesional : administrador.getProfesionales()) {
                Administrador oldAdministradorOfProfesionalesProfesional = profesionalesProfesional.getAdministrador();
                profesionalesProfesional.setAdministrador(administrador);
                profesionalesProfesional = em.merge(profesionalesProfesional);
                if (oldAdministradorOfProfesionalesProfesional != null) {
                    oldAdministradorOfProfesionalesProfesional.getProfesionales().remove(profesionalesProfesional);
                    oldAdministradorOfProfesionalesProfesional = em.merge(oldAdministradorOfProfesionalesProfesional);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Administrador administrador) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador persistentAdministrador = em.find(Administrador.class, administrador.getId());
            List<Servicio> serviciosOld = persistentAdministrador.getServicios();
            List<Servicio> serviciosNew = administrador.getServicios();
            List<Cliente> clientesOld = persistentAdministrador.getClientes();
            List<Cliente> clientesNew = administrador.getClientes();
            List<Profesional> profesionalesOld = persistentAdministrador.getProfesionales();
            List<Profesional> profesionalesNew = administrador.getProfesionales();
            List<Servicio> attachedServiciosNew = new ArrayList<Servicio>();
            for (Servicio serviciosNewServicioToAttach : serviciosNew) {
                serviciosNewServicioToAttach = em.getReference(serviciosNewServicioToAttach.getClass(), serviciosNewServicioToAttach.getId());
                attachedServiciosNew.add(serviciosNewServicioToAttach);
            }
            serviciosNew = attachedServiciosNew;
            administrador.setServicios(serviciosNew);
            List<Cliente> attachedClientesNew = new ArrayList<Cliente>();
            for (Cliente clientesNewClienteToAttach : clientesNew) {
                clientesNewClienteToAttach = em.getReference(clientesNewClienteToAttach.getClass(), clientesNewClienteToAttach.getId());
                attachedClientesNew.add(clientesNewClienteToAttach);
            }
            clientesNew = attachedClientesNew;
            administrador.setClientes(clientesNew);
            List<Profesional> attachedProfesionalesNew = new ArrayList<Profesional>();
            for (Profesional profesionalesNewProfesionalToAttach : profesionalesNew) {
                profesionalesNewProfesionalToAttach = em.getReference(profesionalesNewProfesionalToAttach.getClass(), profesionalesNewProfesionalToAttach.getId());
                attachedProfesionalesNew.add(profesionalesNewProfesionalToAttach);
            }
            profesionalesNew = attachedProfesionalesNew;
            administrador.setProfesionales(profesionalesNew);
            administrador = em.merge(administrador);
            for (Servicio serviciosOldServicio : serviciosOld) {
                if (!serviciosNew.contains(serviciosOldServicio)) {
                    serviciosOldServicio.setAdministrador(null);
                    serviciosOldServicio = em.merge(serviciosOldServicio);
                }
            }
            for (Servicio serviciosNewServicio : serviciosNew) {
                if (!serviciosOld.contains(serviciosNewServicio)) {
                    Administrador oldAdministradorOfServiciosNewServicio = serviciosNewServicio.getAdministrador();
                    serviciosNewServicio.setAdministrador(administrador);
                    serviciosNewServicio = em.merge(serviciosNewServicio);
                    if (oldAdministradorOfServiciosNewServicio != null && !oldAdministradorOfServiciosNewServicio.equals(administrador)) {
                        oldAdministradorOfServiciosNewServicio.getServicios().remove(serviciosNewServicio);
                        oldAdministradorOfServiciosNewServicio = em.merge(oldAdministradorOfServiciosNewServicio);
                    }
                }
            }
            for (Cliente clientesOldCliente : clientesOld) {
                if (!clientesNew.contains(clientesOldCliente)) {
                    clientesOldCliente.setAdministrador(null);
                    clientesOldCliente = em.merge(clientesOldCliente);
                }
            }
            for (Cliente clientesNewCliente : clientesNew) {
                if (!clientesOld.contains(clientesNewCliente)) {
                    Administrador oldAdministradorOfClientesNewCliente = clientesNewCliente.getAdministrador();
                    clientesNewCliente.setAdministrador(administrador);
                    clientesNewCliente = em.merge(clientesNewCliente);
                    if (oldAdministradorOfClientesNewCliente != null && !oldAdministradorOfClientesNewCliente.equals(administrador)) {
                        oldAdministradorOfClientesNewCliente.getClientes().remove(clientesNewCliente);
                        oldAdministradorOfClientesNewCliente = em.merge(oldAdministradorOfClientesNewCliente);
                    }
                }
            }
            for (Profesional profesionalesOldProfesional : profesionalesOld) {
                if (!profesionalesNew.contains(profesionalesOldProfesional)) {
                    profesionalesOldProfesional.setAdministrador(null);
                    profesionalesOldProfesional = em.merge(profesionalesOldProfesional);
                }
            }
            for (Profesional profesionalesNewProfesional : profesionalesNew) {
                if (!profesionalesOld.contains(profesionalesNewProfesional)) {
                    Administrador oldAdministradorOfProfesionalesNewProfesional = profesionalesNewProfesional.getAdministrador();
                    profesionalesNewProfesional.setAdministrador(administrador);
                    profesionalesNewProfesional = em.merge(profesionalesNewProfesional);
                    if (oldAdministradorOfProfesionalesNewProfesional != null && !oldAdministradorOfProfesionalesNewProfesional.equals(administrador)) {
                        oldAdministradorOfProfesionalesNewProfesional.getProfesionales().remove(profesionalesNewProfesional);
                        oldAdministradorOfProfesionalesNewProfesional = em.merge(oldAdministradorOfProfesionalesNewProfesional);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = administrador.getId();
                if (findAdministrador(id) == null) {
                    throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador administrador;
            try {
                administrador = em.getReference(Administrador.class, id);
                administrador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.", enfe);
            }
            List<Servicio> servicios = administrador.getServicios();
            for (Servicio serviciosServicio : servicios) {
                serviciosServicio.setAdministrador(null);
                serviciosServicio = em.merge(serviciosServicio);
            }
            List<Cliente> clientes = administrador.getClientes();
            for (Cliente clientesCliente : clientes) {
                clientesCliente.setAdministrador(null);
                clientesCliente = em.merge(clientesCliente);
            }
            List<Profesional> profesionales = administrador.getProfesionales();
            for (Profesional profesionalesProfesional : profesionales) {
                profesionalesProfesional.setAdministrador(null);
                profesionalesProfesional = em.merge(profesionalesProfesional);
            }
            em.remove(administrador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Administrador> findAdministradorEntities() {
        return findAdministradorEntities(true, -1, -1);
    }

    public List<Administrador> findAdministradorEntities(int maxResults, int firstResult) {
        return findAdministradorEntities(false, maxResults, firstResult);
    }

    private List<Administrador> findAdministradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrador.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Administrador findAdministrador(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrador.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrador> rt = cq.from(Administrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
