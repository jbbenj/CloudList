/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserFile;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author muhda
 */
@Stateless
public class UserFileFacade extends AbstractFacade<UserFile> {

    @PersistenceContext(unitName = "CloudListPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFileFacade() {
        super(UserFile.class);
    }
    
}
