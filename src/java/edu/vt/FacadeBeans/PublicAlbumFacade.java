/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.PublicAlbum;
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
    /*
    public PublicAlbum findById(Integer id) {
        if (em.createQuery("SELECT p FROM PublicVideo p WHERE p.title LIKE '%" + title + "%'")
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (List<PublicVideo>) (em.createQuery("SELECT p FROM PublicVideo p WHERE p.title LIKE '%" + title + "%'")
                    .getResultList());
        }
    }
    */
}
