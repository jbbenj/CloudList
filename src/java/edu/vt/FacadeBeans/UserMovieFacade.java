/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserMovie;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhda
 */
@Stateless
public class UserMovieFacade extends AbstractFacade<UserMovie> {

    @PersistenceContext(unitName = "CloudListPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserMovieFacade() {
        super(UserMovie.class);
    }
    
    public List<UserMovie> findByUserId(Integer userId) {
        if (em.createQuery("SELECT p FROM UserMovie p WHERE p.userId.id = " + userId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (List<UserMovie>) (em.createQuery("SELECT p FROM UserMovie p WHERE p.userId.id = " + userId)
                    .getResultList());
        }
    }
    
    public UserMovie findByUserIdAndUserVersionId(Integer userId, Integer userVersionId) {
        if (em.createQuery("SELECT p FROM UserMovie p WHERE p.userId.id = " + userId + " AND p.id = " + userVersionId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (UserMovie) (em.createQuery("SELECT p FROM UserMovie p WHERE p.userId.id = " + userId + " AND p.id = " + userVersionId)
                    .getSingleResult());
        }
    }
}
