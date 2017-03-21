package com.domicilio.confiable.doco.model;

/**
 * Created by Juan.Cabuyales on 20/03/2017.
 * @descripcion: Clase que representa una persona en la aplicacion y en la base de datos
 */

public class Persona {

    private String id_persona;
    private String nombre;
    private String telefono;
    private String clave;
    private int tipo;

    /**
     * Metodo constructor para una persona
     * @param id_persona
     * @param nombre
     * @param telefono
     * @param clave
     * @param tipo, define si el usuario es un cliente o un conductor,
     *              toma valor de 1, si la persona es conductor
     *              toma valor de 0, si la persona es cliente.
     */
    public Persona(String id_persona, String nombre, String telefono, String clave, int tipo) {
        this.id_persona = id_persona;
        this.nombre = nombre;
        this.telefono = telefono;
        this.clave = clave;
        this.tipo = tipo;
    }

    public String getId_persona() {
        return id_persona;
    }

    public void setId_persona(String id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
