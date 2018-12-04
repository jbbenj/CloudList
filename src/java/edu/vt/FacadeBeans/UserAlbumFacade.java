/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserAlbum;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhda
 */
@Stateless
public class UserAlbumFacade extends AbstractFacade<UserAlbum> {

    @PersistenceContext(unitName = "CloudListPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserAlbumFacade() {
        super(UserAlbum.class);
    }
    
    public List<UserAlbum> findByUserId(Integer userId) {
        if (em.createQuery("SELECT p FROM UserAlbum p WHERE p.userId.id = " + userId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (List<UserAlbum>) (em.createQuery("SELECT p FROM UserAlbum p WHERE p.userId.id = " + userId)
                    .getResultList());
        }
    }
    
    public UserAlbum findByUserIdAndUserVersionId(Integer userId, Integer userVersionId) {
        if (em.createQuery("SELECT p FROM UserAlbum p WHERE p.userId.id = " + userId + " AND p.id = " + userVersionId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (UserAlbum) (em.createQuery("SELECT p FROM UserAlbum p WHERE p.userId.id = " + userId + " AND p.id = " + userVersionId)
                    .getSingleResult());
        }
    }
}
