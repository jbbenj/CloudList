/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserBook;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhda
 */
@Stateless
public class UserBookFacade extends AbstractFacade<UserBook> {

    @PersistenceContext(unitName = "CloudListPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserBookFacade() {
        super(UserBook.class);
    }
    
    public List<UserBook> findByUserId(Integer userId) {
        if (em.createQuery("SELECT p FROM UserBook p WHERE p.userId.id = " + userId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (List<UserBook>) (em.createQuery("SELECT p FROM UserBook p WHERE p.userId.id = " + userId)
                    .getResultList());
        }
    }
    
    public UserBook findByUserIdAndUserVersionId(Integer userId, Integer userVersionId) {
        if (em.createQuery("SELECT p FROM UserBook p WHERE p.userId.id = " + userId + " AND p.id = " + userVersionId)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (UserBook) (em.createQuery("SELECT p FROM UserBook p WHERE p.userId.id = " + userId + " AND p.id = " + userVersionId)
                    .getSingleResult());
        }
    }
}
