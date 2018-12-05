/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.Media;
import edu.vt.EntityBeans.PublicAlbum;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserAlbum;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.UserAlbumFacade;
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

@Named("userAlbumController")
@SessionScoped
public class UserAlbumController implements Serializable {
    
    private final String discogsApiKey = "api_key=iBFdeeVSOrdzUADEAaWP";

    @EJB
    private edu.vt.FacadeBeans.UserFacade userFacade;
    @EJB
    private edu.vt.FacadeBeans.UserAlbumFacade ejbFacade;
    private List<UserAlbum> items = null;
    private UserAlbum selected;

    public UserAlbumController() {
    }

    public UserAlbum getSelected() {
        return selected;
    }

    public void setSelected(UserAlbum selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserAlbumFacade getFacade() {
        return ejbFacade;
    }

    public UserAlbum prepareCreate() {
        selected = new UserAlbum();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserAlbumCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void create(User user) {
        selected.setUserId(user);
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserAlbumCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserAlbumUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserAlbumDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UserAlbum> getItems() {
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

    public UserAlbum getUserAlbum(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<UserAlbum> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<UserAlbum> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = UserAlbum.class)
    public static class UserAlbumControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserAlbumController controller = (UserAlbumController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userAlbumController");
            return controller.getUserAlbum(getKey(value));
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
            if (object instanceof UserAlbum) {
                UserAlbum o = (UserAlbum) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UserAlbum.class.getName()});
                return null;
            }
        }

    }

    public void removeIfMatchType(List<Media> medias) {
        for (int i = 0; i < medias.size(); i++) {
            Media curr = medias.get(i);
            if (curr.getType().equals("Album")) {
                UserAlbum foundAlbum = getFacade().find(curr.getUserVersionId());
                setSelected(foundAlbum);
                destroy();
            }
        }
    }
    
    public boolean isOwnItem(PublicAlbum pubAlbum) {
        UserAlbum userAlbum = getFacade().findByUserIdAndUserVersionId((int) Methods.sessionMap().get("user_id"), pubAlbum.getUserVersionId());
        if (userAlbum == null)
            return false;
        return true;
    }
}
