package com.domicilio.confiable.doco.model;

/**
 * Created by edwinmunoz on 12/21/16.
 */

public class UserDoco {

    private boolean is_active;
    private String email;
    private String user_name;
    private String name;

    public boolean is_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
