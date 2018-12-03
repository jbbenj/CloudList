package edu.vt.controllers;

import edu.vt.EntityBeans.PublicAlbum;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserAlbum;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.PublicAlbumFacade;

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

@Named("publicAlbumController")
@SessionScoped
public class PublicAlbumController implements Serializable {

    @EJB
    private edu.vt.FacadeBeans.UserFacade userFacade;
    
    @EJB
    private edu.vt.FacadeBeans.PublicAlbumFacade ejbFacade;
    private List<PublicAlbum> items = null;
    private PublicAlbum selected;

    public PublicAlbumController() {
    }

    public PublicAlbum getSelected() {
        return selected;
    }

    public void setSelected(PublicAlbum selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PublicAlbumFacade getFacade() {
        return ejbFacade;
    }

    public PublicAlbum prepareCreate() {
        selected = new PublicAlbum();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PublicAlbumCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PublicAlbumUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PublicAlbumDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PublicAlbum> getItems() {
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

    public PublicAlbum getPublicAlbum(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PublicAlbum> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PublicAlbum> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PublicAlbum.class)
    public static class PublicAlbumControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PublicAlbumController controller = (PublicAlbumController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "publicAlbumController");
            return controller.getPublicAlbum(getKey(value));
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
            if (object instanceof PublicAlbum) {
                PublicAlbum o = (PublicAlbum) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PublicAlbum.class.getName()});
                return null;
            }
        }

    }

    public void sell(UserAlbum albumToSell) {

        PublicAlbum newAlbum = new PublicAlbum();

        newAlbum.setArtist(albumToSell.getArtist());
        newAlbum.setGenres(albumToSell.getGenres());
        newAlbum.setReleaseYear(albumToSell.getReleaseYear());
        newAlbum.setTitle(albumToSell.getTitle());
        newAlbum.setTrackNum(albumToSell.getTrackNum());
        newAlbum.setAveragePrice(albumToSell.getAveragePrice());
        newAlbum.setUserId(albumToSell.getUserId().getId());
        newAlbum.setUserVersionId(albumToSell.getId());

        setSelected(newAlbum);
        create();
    }

    public void unsell(UserAlbum albumToUnsell) {
        PublicAlbum foundAlbum = getFacade().findByUserIdAndUserVersionId(albumToUnsell.getUserId().getId(), albumToUnsell.getId());
        
        setSelected(foundAlbum);
        destroy();
    }
    
    public boolean isPublic(UserAlbum albumToCheck) {
        
        User user = albumToCheck.getUserId();
        if (user == null) {
            return false;
        }
        
        PublicAlbum album = getFacade().findByUserIdAndUserVersionId(user.getId(), albumToCheck.getId());
        
        if (album != null)
            return true;
        return false;
    }
}
