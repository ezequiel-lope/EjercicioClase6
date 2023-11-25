/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.models.Administrador;
import com.models.Servicio;
import com.models.Tarea;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ezelc
 */
public class ServicioJpaController implements Serializable {

    public ServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ServicioJpaController() {
        emf = Persistence.createEntityManagerFactory("clase6JPAPU");
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Servicio servicio) {
        if (servicio.getTareas() == null) {
            servicio.setTareas(new ArrayList<Tarea>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador administrador = servicio.getAdministrador();
            if (administrador != null) {
                administrador = em.getReference(administrador.getClass(), administrador.getId());
                servicio.setAdministrador(administrador);
            }
            List<Tarea> attachedTareas = new ArrayList<Tarea>();
            for (Tarea tareasTareaToAttach : servicio.getTareas()) {
                tareasTareaToAttach = em.getReference(tareasTareaToAttach.getClass(), tareasTareaToAttach.getId());
                attachedTareas.add(tareasTareaToAttach);
            }
            servicio.setTareas(attachedTareas);
            em.persist(servicio);
            if (administrador != null) {
                administrador.getServicios().add(servicio);
                administrador = em.merge(administrador);
            }
            for (Tarea tareasTarea : servicio.getTareas()) {
                Servicio oldServicioOfTareasTarea = tareasTarea.getServicio();
                tareasTarea.setServicio(servicio);
                tareasTarea = em.merge(tareasTarea);
                if (oldServicioOfTareasTarea != null) {
                    oldServicioOfTareasTarea.getTareas().remove(tareasTarea);
                    oldServicioOfTareasTarea = em.merge(oldServicioOfTareasTarea);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Servicio servicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio persistentServicio = em.find(Servicio.class, servicio.getId());
            Administrador administradorOld = persistentServicio.getAdministrador();
            Administrador administradorNew = servicio.getAdministrador();
            List<Tarea> tareasOld = persistentServicio.getTareas();
            List<Tarea> tareasNew = servicio.getTareas();
            if (administradorNew != null) {
                administradorNew = em.getReference(administradorNew.getClass(), administradorNew.getId());
                servicio.setAdministrador(administradorNew);
            }
            List<Tarea> attachedTareasNew = new ArrayList<Tarea>();
            for (Tarea tareasNewTareaToAttach : tareasNew) {
                tareasNewTareaToAttach = em.getReference(tareasNewTareaToAttach.getClass(), tareasNewTareaToAttach.getId());
                attachedTareasNew.add(tareasNewTareaToAttach);
            }
            tareasNew = attachedTareasNew;
            servicio.setTareas(tareasNew);
            servicio = em.merge(servicio);
            if (administradorOld != null && !administradorOld.equals(administradorNew)) {
                administradorOld.getServicios().remove(servicio);
                administradorOld = em.merge(administradorOld);
            }
            if (administradorNew != null && !administradorNew.equals(administradorOld)) {
                administradorNew.getServicios().add(servicio);
                administradorNew = em.merge(administradorNew);
            }
            for (Tarea tareasOldTarea : tareasOld) {
                if (!tareasNew.contains(tareasOldTarea)) {
                    tareasOldTarea.setServicio(null);
                    tareasOldTarea = em.merge(tareasOldTarea);
                }
            }
            for (Tarea tareasNewTarea : tareasNew) {
                if (!tareasOld.contains(tareasNewTarea)) {
                    Servicio oldServicioOfTareasNewTarea = tareasNewTarea.getServicio();
                    tareasNewTarea.setServicio(servicio);
                    tareasNewTarea = em.merge(tareasNewTarea);
                    if (oldServicioOfTareasNewTarea != null && !oldServicioOfTareasNewTarea.equals(servicio)) {
                        oldServicioOfTareasNewTarea.getTareas().remove(tareasNewTarea);
                        oldServicioOfTareasNewTarea = em.merge(oldServicioOfTareasNewTarea);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = servicio.getId();
                if (findServicio(id) == null) {
                    throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.");
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
            Servicio servicio;
            try {
                servicio = em.getReference(Servicio.class, id);
                servicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The servicio with id " + id + " no longer exists.", enfe);
            }
            Administrador administrador = servicio.getAdministrador();
            if (administrador != null) {
                administrador.getServicios().remove(servicio);
                administrador = em.merge(administrador);
            }
            List<Tarea> tareas = servicio.getTareas();
            for (Tarea tareasTarea : tareas) {
                tareasTarea.setServicio(null);
                tareasTarea = em.merge(tareasTarea);
            }
            em.remove(servicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Servicio> findServicioEntities() {
        return findServicioEntities(true, -1, -1);
    }

    public List<Servicio> findServicioEntities(int maxResults, int firstResult) {
        return findServicioEntities(false, maxResults, firstResult);
    }

    private List<Servicio> findServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Servicio.class));
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

    public Servicio findServicio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Servicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Servicio> rt = cq.from(Servicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
