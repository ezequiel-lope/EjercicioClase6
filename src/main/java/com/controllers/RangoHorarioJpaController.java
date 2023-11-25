/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controllers;

import com.controllers.exceptions.NonexistentEntityException;
import com.models.RangoHorario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ezelc
 */
public class RangoHorarioJpaController implements Serializable {

    public RangoHorarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RangoHorarioJpaController() {
        emf = Persistence.createEntityManagerFactory("clase6JPAPU");
    }
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RangoHorario rangoHorario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rangoHorario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RangoHorario rangoHorario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rangoHorario = em.merge(rangoHorario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = rangoHorario.getId();
                if (findRangoHorario(id) == null) {
                    throw new NonexistentEntityException("The rangoHorario with id " + id + " no longer exists.");
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
            RangoHorario rangoHorario;
            try {
                rangoHorario = em.getReference(RangoHorario.class, id);
                rangoHorario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rangoHorario with id " + id + " no longer exists.", enfe);
            }
            em.remove(rangoHorario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RangoHorario> findRangoHorarioEntities() {
        return findRangoHorarioEntities(true, -1, -1);
    }

    public List<RangoHorario> findRangoHorarioEntities(int maxResults, int firstResult) {
        return findRangoHorarioEntities(false, maxResults, firstResult);
    }

    private List<RangoHorario> findRangoHorarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RangoHorario.class));
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

    public RangoHorario findRangoHorario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RangoHorario.class, id);
        } finally {
            em.close();
        }
    }

    public int getRangoHorarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RangoHorario> rt = cq.from(RangoHorario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
