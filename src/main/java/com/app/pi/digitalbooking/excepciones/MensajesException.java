package com.app.pi.digitalbooking.excepciones;

public enum MensajesException {
    NOMBRE_EXISTENTE("El nombre ya se encuentra en la Base de datos"),
    USUARIO_EXISTENTE("El correo ya se encuentra registrado"),
    REGISTRO_NO_ENCONTRADO("Registro no encontrado"),
    REGISTRO_EXISTENTE("El registro ya se encuentra en la Base de datos"),
    CAMPOS_INVALIDOS("Campos inválidos");

    private String mensaje;

    MensajesException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
