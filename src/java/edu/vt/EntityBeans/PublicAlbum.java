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
@Table(name = "PublicAlbum")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PublicAlbum.findAll", query = "SELECT p FROM PublicAlbum p")
    , @NamedQuery(name = "PublicAlbum.findById", query = "SELECT p FROM PublicAlbum p WHERE p.id = :id")
    , @NamedQuery(name = "PublicAlbum.findByTitle", query = "SELECT p FROM PublicAlbum p WHERE p.title = :title")
    , @NamedQuery(name = "PublicAlbum.findByArtist", query = "SELECT p FROM PublicAlbum p WHERE p.artist = :artist")
    , @NamedQuery(name = "PublicAlbum.findByReleaseYear", query = "SELECT p FROM PublicAlbum p WHERE p.releaseYear = :releaseYear")
    , @NamedQuery(name = "PublicAlbum.findByTrackNum", query = "SELECT p FROM PublicAlbum p WHERE p.trackNum = :trackNum")
    , @NamedQuery(name = "PublicAlbum.findByGenres", query = "SELECT p FROM PublicAlbum p WHERE p.genres = :genres")
    , @NamedQuery(name = "PublicAlbum.findByAveragePrice", query = "SELECT p FROM PublicAlbum p WHERE p.averagePrice = :averagePrice")})
public class PublicAlbum implements Serializable {

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
    @Column(name = "artist")
    private String artist;
    @Basic(optional = false)
    @NotNull
    @Column(name = "release_year")
    private int releaseYear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "track_num")
    private int trackNum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "genres")
    private String genres;
    @Column(name = "average_price")
    private Integer averagePrice;

    public PublicAlbum() {
    }

    public PublicAlbum(Integer id) {
        this.id = id;
    }

    public PublicAlbum(Integer id, String title, String artist, int releaseYear, int trackNum, String genres) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.trackNum = trackNum;
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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getTrackNum() {
        return trackNum;
    }

    public void setTrackNum(int trackNum) {
        this.trackNum = trackNum;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicAlbum)) {
            return false;
        }
        PublicAlbum other = (PublicAlbum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.PublicAlbum[ id=" + id + " ]";
    }
    
}
