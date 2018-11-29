/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author muhda
 */
@Entity
@Table(name = "UserBook")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserBook.findAll", query = "SELECT u FROM UserBook u")
    , @NamedQuery(name = "UserBook.findById", query = "SELECT u FROM UserBook u WHERE u.id = :id")
    , @NamedQuery(name = "UserBook.findByTitle", query = "SELECT u FROM UserBook u WHERE u.title = :title")
    , @NamedQuery(name = "UserBook.findByAuthor", query = "SELECT u FROM UserBook u WHERE u.author = :author")
    , @NamedQuery(name = "UserBook.findByPublicationYear", query = "SELECT u FROM UserBook u WHERE u.publicationYear = :publicationYear")
    , @NamedQuery(name = "UserBook.findByIsbn", query = "SELECT u FROM UserBook u WHERE u.isbn = :isbn")
    , @NamedQuery(name = "UserBook.findByGenres", query = "SELECT u FROM UserBook u WHERE u.genres = :genres")
    , @NamedQuery(name = "UserBook.findByAveragePrice", query = "SELECT u FROM UserBook u WHERE u.averagePrice = :averagePrice")})
public class UserBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "author")
    private String author;
    @Basic(optional = false)
    @NotNull
    @Column(name = "publication_year")
    private int publicationYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isbn")
    private int isbn;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "genres")
    private String genres;
    @Column(name = "average_price")
    private Integer averagePrice;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public UserBook() {
    }

    public UserBook(Integer id) {
        this.id = id;
    }

    public UserBook(Integer id, String title, String author, int publicationYear, int isbn, String genres) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.genres = genres;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Integer getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserBook)) {
            return false;
        }
        UserBook other = (UserBook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.UserBook[ id=" + id + " ]";
    }
    
}
