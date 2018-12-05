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
    private double totalPrice;
    private final double salesTax = 0.053;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getSalesTax() {
        return salesTax;
    }

    // Methods
    
    /**
     * Adds an item to the cart. The object would then be converted into a Media
     * type and stored into the cart list as of type Media
     * @param obj The item to be added
     * @return refreshes the page
     */
    public String addToCart(Object obj) {

        Media media = new Media();
        String retStr = "";

        if (obj instanceof PublicAlbum) {

            PublicAlbum album = (PublicAlbum) obj;

            media.setId(album.getId());
            media.setArtist(album.getArtist());
            media.setGenres(album.getGenres());
            media.setReleaseYear(album.getReleaseYear());
            media.setTitle(album.getTitle());
            media.setTrackNum(album.getTrackNum());
            media.setAveragePrice(album.getAveragePrice());
            media.setUserId(album.getUserId());
            media.setUserVersionId(album.getUserVersionId());
            media.setType("Album");

            retStr = "/publicAlbum/List?faces-redirect=true";

        } else if (obj instanceof PublicBook) {

            PublicBook book = (PublicBook) obj;

            media.setId(book.getId());
            media.setAuthor(book.getAuthor());
            media.setGenres(book.getGenres());
            media.setPublicationYear(book.getPublicationYear());
            media.setTitle(book.getTitle());
            media.setIsbn(book.getIsbn());
            media.setAveragePrice(book.getAveragePrice());
            media.setUserId(book.getUserId());
            media.setUserVersionId(book.getUserVersionId());
            media.setType("Book");

            retStr = "/publicBook/List?faces-redirect=true";

        } else {

            PublicMovie movie = (PublicMovie) obj;

            media.setId(movie.getId());
            media.setTitle(movie.getTitle());
            media.setGenres(movie.getGenres());
            media.setReleaseDate(movie.getReleaseDate());
            media.setYoutubeTrailerId(movie.getYoutubeTrailerId());
            media.setDirector(movie.getDirector());
            media.setStars(movie.getStars());
            media.setFilmRating(movie.getFilmRating());
            media.setPercentLiked(movie.getPercentLiked());
            media.setAveragePrice(movie.getAveragePrice());
            media.setUserId(movie.getUserId());
            media.setUserVersionId(movie.getUserVersionId());
            media.setType("Movie");

            retStr = "/publicMovie/List?faces-redirect=true";
        }
        this.getCart().add(media);
        selected = media;
        Methods.showMessage("Information", "Success!", media.getTitle() + " was added to your cart!");
        Methods.preserveMessages();

        return retStr;
    }

    /**
     * Removes an item from the cart. Simply call remove on a media object
     * @param media The item to be removed from the cart
     * @return refreshes the page
     */
    public String removeFromCart(Media media) {

        if (!cart.remove(media)) {
            Methods.showMessage("Error", "Fail to remove item from cart!", "Fail to remove item from cart!");
        }
        selected = null;
        Methods.showMessage("Information", "Success!", media.getTitle() + " was removed from your cart!");

        return "/shoppingCart/Cart?faces-redirect=true";
    }

    /**
     * Empties the cart
     */
    public void clearCart() {
        cart.clear();
    }

    /**
     * Checks if obj is in the cart and returns true if yes. Otherwise, return
     * false
     * @param obj The item to be checked
     * @return true if obj is in the cart
     */
    public boolean isInCart(Object obj) {

        if (cart == null || cart.isEmpty() || obj == null) {
            return false;
        }

        int objId = -1;

        if (obj instanceof PublicAlbum) {
            PublicAlbum album = (PublicAlbum) obj;
            if (album.getId() == null) {
                return false;
            }
            objId = album.getId();
        } else if (obj instanceof PublicBook) {
            PublicBook book = (PublicBook) obj;
            if (book.getId() == null) {
                return false;
            }
            objId = book.getId();
        } else {
            PublicMovie movie = (PublicMovie) obj;
            if (movie.getId() == null) {
                return false;
            }
            objId = movie.getId();
        }

        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getId() == objId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the total price of all the items in the cart. It then
     * redirects the user to the confirmation page.
     * @return redirects to confirmation page
     */
    public String purchase() {

        totalPrice = 0;

        for (int i = 0; i < cart.size(); i++) {
            totalPrice += cart.get(i).getAveragePrice();
        }

        totalPrice = totalPrice + totalPrice * salesTax;
        double totalPriceRounded = Math.round(totalPrice * 100);
        totalPrice = totalPriceRounded / 100;

        return "/shoppingCart/Confirmation?faces-redirect=true";
    }

}
