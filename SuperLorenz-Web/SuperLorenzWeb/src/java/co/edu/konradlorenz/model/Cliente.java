package co.edu.konradlorenz.model;

import co.edu.konradlorenz.model.enums.*;
import java.util.Date;

public class Cliente extends Persona {
    
    private int clienteID;
    private int personaID;
    private TipoCliente tipoCliente;
    private String direccion;
    private Date fechaNacimiento;
    private EstadoCivil estadoCivil;
    private boolean autorizacionDeDatos;
    
    public Cliente() {
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public int getPersonaID() {
        return personaID;
    }

    public void setPersonaID(int personaID) {
        this.personaID = personaID;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public boolean isAutorizacionDeDatos() {
        return autorizacionDeDatos;
    }

    public void setAutorizacionDeDatos(boolean autorizacionDeDatos) {
        this.autorizacionDeDatos = autorizacionDeDatos;
    }
    
}