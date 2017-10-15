package com.apptuned.fantacybetting;

import java.io.Serializable;

/**
 * Created by davies on 9/9/17.
 */

public class League implements Serializable{
    /*
    This class holds the definition of leagues as stored
     */

    public League(int id,String iconURL, String name, String nameShort, String country){
        this.id = id;
        this.iconURL = iconURL;
        this.name = name;
        this.nameShort = nameShort;
        this.country = country;
    }
    private int id;
    private String iconURL, name, nameShort, country;

    public String getIconURL(){
        return this.iconURL;
    }

    public void setIconURL(String iconURL){
        this.iconURL = iconURL;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNameShort(){
        return this.nameShort;
    }

    public void setNameShort(String nameShort){
        this.nameShort = nameShort;
    }

    public String getCountry(){
        return this.country;
    }

    public void setCountry(String country){
        this.country = country;
    }



}
