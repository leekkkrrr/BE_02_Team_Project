package com.b2.supercoding_prj01.role;

public enum Role {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
