/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.PublicAlbum;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhda
 */
@Stateless
public class PublicAlbumFacade extends AbstractFacade<PublicAlbum> {

    @PersistenceContext(unitName = "CloudListPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PublicAlbumFacade() {
        super(PublicAlbum.class);
    }
    
    public List<PublicAlbum> findByUserId(Integer userId) {
        if (em.createQuery("SELECT p FROM PublicAlbum p WHERE p.userId = " + userId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (List<PublicAlbum>) (em.createQuery("SELECT p FROM PublicAlbum p WHERE p.userId = " + userId)
                    .getResultList());
        }
    }
    
    public PublicAlbum findByUserIdAndUserVersionId(Integer userId, Integer userVersionId) {
        if (em.createQuery("SELECT p FROM PublicAlbum p WHERE p.userId = " + userId + " AND p.userVersionId = " + userVersionId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (PublicAlbum) (em.createQuery("SELECT p FROM PublicAlbum p WHERE p.userId = " + userId + " AND p.userVersionId = " + userVersionId)
                    .getSingleResult());
        }
    }
}
