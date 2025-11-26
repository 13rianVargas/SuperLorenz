
package co.edu.konradlorenz.model;

import co.edu.konradlorenz.model.enums.MotivoSalida;
import java.util.Date;


public class Kardex {
    private int kardexID;
    private int productoID;
    private int cantidadSalida;
    private int cantidadEntrada;
    private int cantidadDisponible;
    private MotivoSalida motivoSalida;
    private String otroMotivoSalida;
    private Date fecha;
    
    public Kardex(){
    }

    public int getKardexID() {
        return kardexID;
    }

    public void setKardexID(int kardexID) {
        this.kardexID = kardexID;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public int getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(int cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }

    public int getCantidadEntrada() {
        return cantidadEntrada;
    }

    public void setCantidadEntrada(int cantidadEntrada) {
        this.cantidadEntrada = cantidadEntrada;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public MotivoSalida getMotivoSalida() {
        return motivoSalida;
    }

    public void setMotivoSalida(MotivoSalida motivoSalida) {
        this.motivoSalida = motivoSalida;
    }

    public String getOtroMotivoSalida() {
        return otroMotivoSalida;
    }

    public void setOtroMotivoSalida(String otroMotivoSalida) {
        this.otroMotivoSalida = otroMotivoSalida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
