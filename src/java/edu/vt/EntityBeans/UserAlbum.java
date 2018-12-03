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
@Table(name = "UserAlbum")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAlbum.findAll", query = "SELECT u FROM UserAlbum u")
    , @NamedQuery(name = "UserAlbum.findById", query = "SELECT u FROM UserAlbum u WHERE u.id = :id")
    , @NamedQuery(name = "UserAlbum.findByTitle", query = "SELECT u FROM UserAlbum u WHERE u.title = :title")
    , @NamedQuery(name = "UserAlbum.findByArtist", query = "SELECT u FROM UserAlbum u WHERE u.artist = :artist")
    , @NamedQuery(name = "UserAlbum.findByAlbum", query = "SELECT u FROM UserAlbum u WHERE u.album = :album")
    , @NamedQuery(name = "UserAlbum.findByReleaseYear", query = "SELECT u FROM UserAlbum u WHERE u.releaseYear = :releaseYear")
    , @NamedQuery(name = "UserAlbum.findByTrackNum", query = "SELECT u FROM UserAlbum u WHERE u.trackNum = :trackNum")
    , @NamedQuery(name = "UserAlbum.findByGenres", query = "SELECT u FROM UserAlbum u WHERE u.genres = :genres")
    , @NamedQuery(name = "UserAlbum.findByAveragePrice", query = "SELECT u FROM UserAlbum u WHERE u.averagePrice = :averagePrice")})
public class UserAlbum implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "average_price")
    private Float averagePrice;

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
    @Size(min = 1, max = 255)
    @Column(name = "album")
    private String album;
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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public UserAlbum() {
    }

    public UserAlbum(Integer id) {
        this.id = id;
    }

    public UserAlbum(Integer id, String title, String artist, String album, int releaseYear, int trackNum, String genres) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
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
        if (!(object instanceof UserAlbum)) {
            return false;
        }
        UserAlbum other = (UserAlbum) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.UserAlbum[ id=" + id + " ]";
    }

    public Float getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Float averagePrice) {
        this.averagePrice = averagePrice;
    }
    
}
