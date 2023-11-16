package com.app.pi.digitalbooking.util.email.impl;

public enum DatosCorreo {
    CONFIRMAR("Notificación de regístro exitoso DigitalBooking", "confirmar-template"),
    VALIDAR("Activación de cuenta DigitalBooking", "validar-template");

    private String plantilla;
    private String asunto;


    DatosCorreo(String asunto, String plantilla) {
        this.plantilla = plantilla;
        this.asunto = asunto;
    }
    public String getPlantilla() {
        return plantilla;
    }


    public String getAsunto() {
        return asunto;
    }


}
