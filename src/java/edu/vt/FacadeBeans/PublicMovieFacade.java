/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.PublicMovie;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhda
 */
@Stateless
public class PublicMovieFacade extends AbstractFacade<PublicMovie> {

    @PersistenceContext(unitName = "CloudListPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PublicMovieFacade() {
        super(PublicMovie.class);
    }
    
    public List<PublicMovie> findByUserId(Integer userId) {
        if (em.createQuery("SELECT p FROM PublicMovie p WHERE p.userId = " + userId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (List<PublicMovie>) (em.createQuery("SELECT p FROM PublicMovie p WHERE p.userId = " + userId)
                    .getResultList());
        }
    }
    
    public PublicMovie findByUserIdAndUserVersionId(Integer userId, Integer userVersionId) {
        if (em.createQuery("SELECT p FROM PublicMovie p WHERE p.userId = " + userId + " AND p.userVersionId = " + userVersionId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (PublicMovie) (em.createQuery("SELECT p FROM PublicMovie p WHERE p.userId = " + userId + " AND p.userVersionId = " + userVersionId)
                    .getSingleResult());
        }
    }
}
