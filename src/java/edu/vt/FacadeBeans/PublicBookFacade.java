/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.PublicBook;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhda
 */
@Stateless
public class PublicBookFacade extends AbstractFacade<PublicBook> {

    @PersistenceContext(unitName = "CloudListPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PublicBookFacade() {
        super(PublicBook.class);
    }
    
    public List<PublicBook> findByUserId(Integer userId) {
        if (em.createQuery("SELECT p FROM PublicBook p WHERE p.userId = " + userId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (List<PublicBook>) (em.createQuery("SELECT p FROM PublicBook p WHERE p.userId = " + userId)
                    .getResultList());
        }
    }
    
    public PublicBook findByUserIdAndUserVersionId(Integer userId, Integer userVersionId) {
        if (em.createQuery("SELECT p FROM PublicBook p WHERE p.userId = " + userId + " AND p.userVersionId = " + userVersionId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (PublicBook) (em.createQuery("SELECT p FROM PublicBook p WHERE p.userId = " + userId + " AND p.userVersionId = " + userVersionId)
                    .getSingleResult());
        }
    }
}
