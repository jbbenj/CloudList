/*
 * Created by Muhammad Afiq Yusof on 2018.12.03  * 
 * Copyright © 2018 Muhammad Afiq Yusof. All rights reserved. * 
 */
package edu.vt.EntityBeans;

/**
 *
 * @author muhda
 */
public class Media {
    
    // Fields
    
    private Integer id;
    private String name;
    private Float price;

    // Constructors
    
    public Media() {
        
    }
    
    // Setters & Getters
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    
}
