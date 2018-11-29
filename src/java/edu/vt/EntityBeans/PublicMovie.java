/*
 * Created by Muhammad Afiq Yusof on 2018.11.28  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author muhda
 */
@Entity
@Table(name = "PublicMovie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PublicMovie.findAll", query = "SELECT p FROM PublicMovie p")
    , @NamedQuery(name = "PublicMovie.findById", query = "SELECT p FROM PublicMovie p WHERE p.id = :id")
    , @NamedQuery(name = "PublicMovie.findByTitle", query = "SELECT p FROM PublicMovie p WHERE p.title = :title")
    , @NamedQuery(name = "PublicMovie.findByYoutubeTrailerId", query = "SELECT p FROM PublicMovie p WHERE p.youtubeTrailerId = :youtubeTrailerId")
    , @NamedQuery(name = "PublicMovie.findByGenres", query = "SELECT p FROM PublicMovie p WHERE p.genres = :genres")
    , @NamedQuery(name = "PublicMovie.findByReleaseDate", query = "SELECT p FROM PublicMovie p WHERE p.releaseDate = :releaseDate")
    , @NamedQuery(name = "PublicMovie.findByDirector", query = "SELECT p FROM PublicMovie p WHERE p.director = :director")
    , @NamedQuery(name = "PublicMovie.findByStars", query = "SELECT p FROM PublicMovie p WHERE p.stars = :stars")
    , @NamedQuery(name = "PublicMovie.findByFilmRating", query = "SELECT p FROM PublicMovie p WHERE p.filmRating = :filmRating")
    , @NamedQuery(name = "PublicMovie.findByPercentLiked", query = "SELECT p FROM PublicMovie p WHERE p.percentLiked = :percentLiked")
    , @NamedQuery(name = "PublicMovie.findByAveragePrice", query = "SELECT p FROM PublicMovie p WHERE p.averagePrice = :averagePrice")})
public class PublicMovie implements Serializable {

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
    @Size(min = 1, max = 32)
    @Column(name = "youtube_trailer_id")
    private String youtubeTrailerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "genres")
    private String genres;
    @Basic(optional = false)
    @NotNull
    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "director")
    private String director;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "stars")
    private String stars;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "film_rating")
    private String filmRating;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "percent_liked")
    private String percentLiked;
    @Column(name = "average_price")
    private Integer averagePrice;

    public PublicMovie() {
    }

    public PublicMovie(Integer id) {
        this.id = id;
    }

    public PublicMovie(Integer id, String title, String youtubeTrailerId, String genres, Date releaseDate, String director, String stars, String filmRating, String percentLiked) {
        this.id = id;
        this.title = title;
        this.youtubeTrailerId = youtubeTrailerId;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.director = director;
        this.stars = stars;
        this.filmRating = filmRating;
        this.percentLiked = percentLiked;
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

    public String getYoutubeTrailerId() {
        return youtubeTrailerId;
    }

    public void setYoutubeTrailerId(String youtubeTrailerId) {
        this.youtubeTrailerId = youtubeTrailerId;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getFilmRating() {
        return filmRating;
    }

    public void setFilmRating(String filmRating) {
        this.filmRating = filmRating;
    }

    public String getPercentLiked() {
        return percentLiked;
    }

    public void setPercentLiked(String percentLiked) {
        this.percentLiked = percentLiked;
    }

    public Integer getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Integer averagePrice) {
        this.averagePrice = averagePrice;
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
        if (!(object instanceof PublicMovie)) {
            return false;
        }
        PublicMovie other = (PublicMovie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.PublicMovie[ id=" + id + " ]";
    }
    
}
