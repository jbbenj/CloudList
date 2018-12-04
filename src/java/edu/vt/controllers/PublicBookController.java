package edu.vt.controllers;

import edu.vt.EntityBeans.Media;
import edu.vt.EntityBeans.PublicBook;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserBook;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.FacadeBeans.PublicBookFacade;

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

@Named("publicBookController")
@SessionScoped
public class PublicBookController implements Serializable {
    
    private final String goodreadsApiKey = "api_key=RRjzK1W8TyINyDz8VzOqcA";

    @EJB
    private edu.vt.FacadeBeans.PublicBookFacade ejbFacade;
    private List<PublicBook> items = null;
    private PublicBook selected;

    public PublicBookController() {
    }

    public PublicBook getSelected() {
        return selected;
    }

    public void setSelected(PublicBook selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PublicBookFacade getFacade() {
        return ejbFacade;
    }

    public PublicBook prepareCreate() {
        selected = new PublicBook();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PublicBookCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PublicBookUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PublicBookDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PublicBook> getItems() {
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

    public PublicBook getPublicBook(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PublicBook> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PublicBook> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PublicBook.class)
    public static class PublicBookControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PublicBookController controller = (PublicBookController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "publicBookController");
            return controller.getPublicBook(getKey(value));
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
            if (object instanceof PublicBook) {
                PublicBook o = (PublicBook) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PublicBook.class.getName()});
                return null;
            }
        }

    }

    public void sell(UserBook bookToSell) {

        PublicBook newBook = new PublicBook();

        newBook.setAuthor(bookToSell.getAuthor());
        newBook.setGenres(bookToSell.getGenres());
        newBook.setPublicationYear(bookToSell.getPublicationYear());
        newBook.setTitle(bookToSell.getTitle());
        newBook.setIsbn(bookToSell.getIsbn());
        newBook.setAveragePrice(bookToSell.getAveragePrice());
        newBook.setUserId(bookToSell.getUserId().getId());
        newBook.setUserVersionId(bookToSell.getId());

        setSelected(newBook);
        create();
        
    }
    
    public void unsell(UserBook bookToUnsell) {
        PublicBook foundBook = getFacade().findByUserIdAndUserVersionId(bookToUnsell.getUserId().getId(), bookToUnsell.getId());
        
        setSelected(foundBook);
        destroy();
    }
    
    public boolean isPublic(UserBook bookToCheck) {
        
        User user = bookToCheck.getUserId();
        if (user == null) {
            return false;
        }
        
        PublicBook book = getFacade().findByUserIdAndUserVersionId(user.getId(), bookToCheck.getId());
        
        if (book != null)
            return true;
        return false;
    }
    
    public void removeIfMatchType(List<Media> medias) {
        for (int i = 0; i < medias.size(); i++) {
            Media curr = medias.get(i);
            if (curr.getType().equals("Book")) {
                PublicBook foundBook = getFacade().findByUserIdAndUserVersionId(curr.getUserId(), curr.getUserVersionId());
                setSelected(foundBook);
                destroy();
            }
        }
    }
}
