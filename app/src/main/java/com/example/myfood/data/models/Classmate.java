package com.example.myfood.data.models;

public class Classmate {

    private Long id;

    String email, password, name;
    Long groupId;
    boolean is_chargeable;

    public Classmate(String email, String password, String name, Long group_id, boolean is_chargeable) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.groupId = group_id;
        this.is_chargeable = is_chargeable;
    }

    public Classmate() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long group_id) {
        this.groupId = group_id;
    }

    public boolean isIs_chargeable() {
        return is_chargeable;
    }

    public void setIs_chargeable(boolean is_chargeable) {
        this.is_chargeable = is_chargeable;
    }
}
