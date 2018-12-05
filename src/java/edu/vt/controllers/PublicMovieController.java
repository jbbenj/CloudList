/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.Media;
import edu.vt.EntityBeans.PublicMovie;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserMovie;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.PublicMovieFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("publicMovieController")
@SessionScoped
public class PublicMovieController implements Serializable {
    
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private final String tmdbMovieWebServicesBaseUrl = "http://api.themoviedb.org/3/movie/";
    private final String omdbMovieWebServicesBaseUrl = "http://www.omdbapi.com/?apikey=9f67dd7a&tomatoes=true&type=movie";

    private final String tmdbApiKey = "api_key=9b42c31eac0b25c9099cd017dfb9c2ae";

    @EJB
    private edu.vt.FacadeBeans.PublicMovieFacade ejbFacade;
    private List<PublicMovie> items = null;
    private PublicMovie selected;

    public PublicMovieController() {
    }

    public PublicMovie getSelected() {
        return selected;
    }

    public void setSelected(PublicMovie selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PublicMovieFacade getFacade() {
        return ejbFacade;
    }

    public PublicMovie prepareCreate() {
        selected = new PublicMovie();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PublicMovieCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PublicMovieUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PublicMovieDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PublicMovie> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public PublicMovie getPublicMovie(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PublicMovie> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PublicMovie> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PublicMovie.class)
    public static class PublicMovieControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PublicMovieController controller = (PublicMovieController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "publicMovieController");
            return controller.getPublicMovie(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PublicMovie) {
                PublicMovie o = (PublicMovie) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PublicMovie.class.getName()});
                return null;
            }
        }

    }

    public void sell(UserMovie movieToSell) {

        PublicMovie newMovie = new PublicMovie();

        newMovie.setTitle(movieToSell.getTitle());
        newMovie.setGenres(movieToSell.getGenres());
        newMovie.setReleaseDate(movieToSell.getReleaseDate());
        newMovie.setYoutubeTrailerId(movieToSell.getYoutubeTrailerId());
        newMovie.setDirector(movieToSell.getDirector());
        newMovie.setStars(movieToSell.getStars());
        newMovie.setFilmRating(movieToSell.getFilmRating());
        newMovie.setPercentLiked(movieToSell.getPercentLiked());
        newMovie.setAveragePrice(movieToSell.getAveragePrice());
        newMovie.setUserId(movieToSell.getUserId().getId());
        newMovie.setUserVersionId(movieToSell.getId());

        setSelected(newMovie);
        create();
    }

    public void unsell(UserMovie movieToUnsell) {
        PublicMovie foundMovie = getFacade().findByUserIdAndUserVersionId(movieToUnsell.getUserId().getId(), movieToUnsell.getId());
        
        setSelected(foundMovie);
        destroy();
    }
    
    public boolean isPublic(UserMovie movieToCheck) {
        
        User user = movieToCheck.getUserId();
        if (user == null) {
            return false;
        }
        
        PublicMovie movie = getFacade().findByUserIdAndUserVersionId(user.getId(), movieToCheck.getId());
        
        if (movie != null)
            return true;
        return false;
    }
    
    public void removeIfMatchType(List<Media> medias) {
        for (int i = 0; i < medias.size(); i++) {
            Media curr = medias.get(i);
            if (curr.getType().equals("Movie")) {
                PublicMovie foundMovie = getFacade().findByUserIdAndUserVersionId(curr.getUserId(), curr.getUserVersionId());
                setSelected(foundMovie);
                destroy();
            }
        }
    }
    
}
