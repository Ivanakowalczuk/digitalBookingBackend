package com.app.pi.digitalbooking.model.enums;

public enum Role {
    ADMIN("ADMIN"),
    CLIENTE("CLIENTE");

    private String codigo;

    Role(String codigo) {
        this.codigo = codigo;
    }
    public String getCodigo() {
        return codigo;
    }
}
