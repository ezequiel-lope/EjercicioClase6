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
import com.models.Profesional;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ezelc
 */
public class ProfesionalJpaController implements Serializable {

    public ProfesionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public ProfesionalJpaController() {
        
        emf = Persistence.createEntityManagerFactory("clase6JPAPU");
    }
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profesional profesional) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Administrador administrador = profesional.getAdministrador();
            if (administrador != null) {
                administrador = em.getReference(administrador.getClass(), administrador.getId());
                profesional.setAdministrador(administrador);
            }
            em.persist(profesional);
            if (administrador != null) {
                administrador.getProfesionales().add(profesional);
                administrador = em.merge(administrador);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profesional profesional) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Profesional persistentProfesional = em.find(Profesional.class, profesional.getId());
            Administrador administradorOld = persistentProfesional.getAdministrador();
            Administrador administradorNew = profesional.getAdministrador();
            if (administradorNew != null) {
                administradorNew = em.getReference(administradorNew.getClass(), administradorNew.getId());
                profesional.setAdministrador(administradorNew);
            }
            profesional = em.merge(profesional);
            if (administradorOld != null && !administradorOld.equals(administradorNew)) {
                administradorOld.getProfesionales().remove(profesional);
                administradorOld = em.merge(administradorOld);
            }
            if (administradorNew != null && !administradorNew.equals(administradorOld)) {
                administradorNew.getProfesionales().add(profesional);
                administradorNew = em.merge(administradorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = profesional.getId();
                if (findProfesional(id) == null) {
                    throw new NonexistentEntityException("The profesional with id " + id + " no longer exists.");
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
            Profesional profesional;
            try {
                profesional = em.getReference(Profesional.class, id);
                profesional.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profesional with id " + id + " no longer exists.", enfe);
            }
            Administrador administrador = profesional.getAdministrador();
            if (administrador != null) {
                administrador.getProfesionales().remove(profesional);
                administrador = em.merge(administrador);
            }
            em.remove(profesional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Profesional> findProfesionalEntities() {
        return findProfesionalEntities(true, -1, -1);
    }

    public List<Profesional> findProfesionalEntities(int maxResults, int firstResult) {
        return findProfesionalEntities(false, maxResults, firstResult);
    }

    private List<Profesional> findProfesionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profesional.class));
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

    public Profesional findProfesional(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profesional.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfesionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profesional> rt = cq.from(Profesional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
