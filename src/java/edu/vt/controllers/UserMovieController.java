package edu.vt.controllers;

import edu.vt.EntityBeans.Media;
import edu.vt.EntityBeans.PublicMovie;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserMovie;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.UserMovieFacade;
import edu.vt.globals.Methods;

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

@Named("userMovieController")
@SessionScoped
public class UserMovieController implements Serializable {
    
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private final String tmdbMovieWebServicesBaseUrl = "http://api.themoviedb.org/3/movie/";
    private final String omdbMovieWebServicesBaseUrl = "http://www.omdbapi.com/?apikey=9f67dd7a&tomatoes=true&type=movie";

    private final String tmdbApiKey = "api_key=9b42c31eac0b25c9099cd017dfb9c2ae";

    @EJB
    private edu.vt.FacadeBeans.UserMovieFacade ejbFacade;
    private List<UserMovie> items = null;
    private UserMovie selected;

    public UserMovieController() {
    }

    public UserMovie getSelected() {
        return selected;
    }

    public void setSelected(UserMovie selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserMovieFacade getFacade() {
        return ejbFacade;
    }

    public UserMovie prepareCreate() {
        selected = new UserMovie();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserMovieCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void create(User user) {
        selected.setUserId(user);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserMovieCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserMovieUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserMovieDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UserMovie> getItems() {
        if (items == null) {
            items = getFacade().findByUserId((int) Methods.sessionMap().get("user_id"));
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

    public UserMovie getUserMovie(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<UserMovie> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UserMovie> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = UserMovie.class)
    public static class UserMovieControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserMovieController controller = (UserMovieController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userMovieController");
            return controller.getUserMovie(getKey(value));
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
            if (object instanceof UserMovie) {
                UserMovie o = (UserMovie) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UserMovie.class.getName()});
                return null;
            }
        }

    }

    public void removeIfMatchType(List<Media> medias) {
        for (int i = 0; i < medias.size(); i++) {
            Media curr = medias.get(i);
            if (curr.getType().equals("Movie")) {
                UserMovie foundMovie = getFacade().find(curr.getUserVersionId());
                setSelected(foundMovie);
                destroy();
            }
        }
    }
    
    public boolean isOwnItem(PublicMovie pubMovie) {
        UserMovie userMovie = getFacade().findByUserIdAndUserVersionId((int) Methods.sessionMap().get("user_id"), pubMovie.getUserVersionId());
        if (userMovie == null)
            return false;
        return true;
    }
}
