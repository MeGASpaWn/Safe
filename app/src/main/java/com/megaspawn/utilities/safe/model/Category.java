package com.megaspawn.utilities.safe.model;

import io.realm.RealmObject;

/**
 * Created by varun on 1/1/17.
 */

public class Category extends RealmObject {

    int id;

    String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
