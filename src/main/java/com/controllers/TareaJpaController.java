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
import com.models.Servicio;
import com.models.Tarea;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ezelc
 */
public class TareaJpaController implements Serializable {

    public TareaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public TareaJpaController() {
        
        emf = Persistence.createEntityManagerFactory("clase6JPAPU");
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tarea tarea) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Servicio servicio = tarea.getServicio();
            if (servicio != null) {
                servicio = em.getReference(servicio.getClass(), servicio.getId());
                tarea.setServicio(servicio);
            }
            em.persist(tarea);
            if (servicio != null) {
                servicio.getTareas().add(tarea);
                servicio = em.merge(servicio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tarea tarea) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tarea persistentTarea = em.find(Tarea.class, tarea.getId());
            Servicio servicioOld = persistentTarea.getServicio();
            Servicio servicioNew = tarea.getServicio();
            if (servicioNew != null) {
                servicioNew = em.getReference(servicioNew.getClass(), servicioNew.getId());
                tarea.setServicio(servicioNew);
            }
            tarea = em.merge(tarea);
            if (servicioOld != null && !servicioOld.equals(servicioNew)) {
                servicioOld.getTareas().remove(tarea);
                servicioOld = em.merge(servicioOld);
            }
            if (servicioNew != null && !servicioNew.equals(servicioOld)) {
                servicioNew.getTareas().add(tarea);
                servicioNew = em.merge(servicioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tarea.getId();
                if (findTarea(id) == null) {
                    throw new NonexistentEntityException("The tarea with id " + id + " no longer exists.");
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
            Tarea tarea;
            try {
                tarea = em.getReference(Tarea.class, id);
                tarea.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tarea with id " + id + " no longer exists.", enfe);
            }
            Servicio servicio = tarea.getServicio();
            if (servicio != null) {
                servicio.getTareas().remove(tarea);
                servicio = em.merge(servicio);
            }
            em.remove(tarea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tarea> findTareaEntities() {
        return findTareaEntities(true, -1, -1);
    }

    public List<Tarea> findTareaEntities(int maxResults, int firstResult) {
        return findTareaEntities(false, maxResults, firstResult);
    }

    private List<Tarea> findTareaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tarea.class));
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

    public Tarea findTarea(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tarea.class, id);
        } finally {
            em.close();
        }
    }

    public int getTareaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tarea> rt = cq.from(Tarea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
