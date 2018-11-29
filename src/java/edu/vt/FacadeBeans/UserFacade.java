/*
 * Created by Afiq Yusof on 2018.11.29  * 
 * Copyright © 2018 Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhda
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "CloudListPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    /**
     * @param id is the Primary Key of the User entity in a table row in the database.
     * @return object reference of the User object whose primary key is id
     */
    public User getUser(int id) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(User.class, id);
    }

    /**
     * @param username is the username attribute (column) value of the user
     * @return object reference of the User entity whose user name is username
     */
    public User findByUsername(String username) {
        if (em.createQuery("SELECT c FROM User c WHERE c.username = :username")
                .setParameter("username", username)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (User) (em.createQuery("SELECT c FROM User c WHERE c.username = :username")
                    .setParameter("username", username)
                    .getSingleResult());
        }
    }

    /**
     * Deletes the User entity whose primary key is id
     * @param id is the Primary Key of the User entity in a table row in the database.
     */
    public void deleteUser(int id) {
        
        // The find method is inherited from the parent AbstractFacade class
        User user = em.find(User.class, id);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(user); 
    }
    
}
