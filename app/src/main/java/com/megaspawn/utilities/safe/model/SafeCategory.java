package com.megaspawn.utilities.safe.model;

import io.realm.RealmObject;

/**
 * Created by varun on 1/1/17.
 */

public class SafeCategory extends RealmObject {

    int id;

    String name;

    int parentId;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
