package com.hellochain.paperoutapplication.data;

import io.realm.RealmObject;

/**
 * Created by boxfox on 2017-09-02.
 */

public class User extends RealmObject {
    private String id, password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
