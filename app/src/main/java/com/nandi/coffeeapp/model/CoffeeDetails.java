package com.nandi.coffeeapp.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by nandi_000 on 09-11-2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoffeeDetails {

    public String last_updated_at;
    public String desc;
    public String image_url;
    public String id;
    public String name;

    public String getLast_updated_at() {
        return last_updated_at;
    }

    public void setLast_updated_at(String last_updated_at) {
        this.last_updated_at = last_updated_at;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
