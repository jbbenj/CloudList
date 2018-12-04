/*
 * Created by Muhammad Afiq Yusof on 2018.12.03  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.managers;

import edu.vt.EntityBeans.Media;
import edu.vt.EntityBeans.PublicAlbum;
import edu.vt.EntityBeans.PublicBook;
import edu.vt.EntityBeans.PublicMovie;
import edu.vt.globals.Methods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named(value = "cartManager")
@SessionScoped

/**
 *
 * @author muhda
 */
public class CartManager implements Serializable {
    
    // Fields
    
    private List<Media> cart = null;
    private Media selected;
    
    // Constructor
    
    public CartManager() {
        
    }
    
    // Setters and Getters

    public List<Media> getCart() {
        if (cart == null) {
            return cart = new ArrayList<>();
        }
        return cart;
    }

    public void setCart(List<Media> cart) {
        this.cart = cart;
    }

    public Media getSelected() {
        return selected;
    }

    public void setSelected(Media selected) {
        this.selected = selected;
    }
    
    // Methods
    
    public void addToCart(Object obj) {
        
        Media media = new Media();
        
        if (obj instanceof PublicAlbum) {
            PublicAlbum album = (PublicAlbum) obj;
            media.setId(album.getId());
            media.setName(album.getTitle());
            media.setPrice(album.getAveragePrice());
        }
        else if (obj instanceof PublicBook) {
            PublicBook book = (PublicBook) obj;
            media.setId(book.getId());
            media.setName(book.getTitle());
            media.setPrice(book.getAveragePrice());
        }
        else {
            PublicMovie movie = (PublicMovie) obj;
            media.setId(movie.getId());
            media.setName(movie.getTitle());
            media.setPrice(movie.getAveragePrice());
        }
        
        this.getCart().add(media);
    }
    
    public void removeFromCart(Media media) {
        
        if (!cart.remove(media)) {
            Methods.showMessage("Error", "Fail to remove item from cart!", "Fail to remove item from cart!");
        }
    }
    
    public void clearCart() {
        cart.clear();
    }
    
    public void purchase() {
        
    }
}
