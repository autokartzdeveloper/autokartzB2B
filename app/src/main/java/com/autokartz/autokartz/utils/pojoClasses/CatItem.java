package com.autokartz.autokartz.utils.pojoClasses;

/**
 * Created by user on 2/21/2018.
 */

public class CatItem {


    private String id;
    private String name;

    public CatItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CatItem() {};

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