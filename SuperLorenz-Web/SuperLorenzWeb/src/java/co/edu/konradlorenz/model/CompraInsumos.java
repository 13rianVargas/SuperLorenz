
package co.edu.konradlorenz.model;

import co.edu.konradlorenz.model.enums.Estado;
import java.util.Date;


public class CompraInsumos {
    private int compraInsumosID; 
    private int proveedorProductoID;
    private int cantidad;
    private Estado estado;
    private Date fechaCompra;
    private Date fechaEntrega;
    private double valorTotal;
    
    public CompraInsumos(){
        
    }

    public int getCompraInsumosID() {
        return compraInsumosID;
    }

    public void setCompraInsumosID(int compraInsumosID) {
        this.compraInsumosID = compraInsumosID;
    }

    public int getProveedorProductoID() {
        return proveedorProductoID;
    }

    public void setProveedorProductoID(int proveedorProductoID) {
        this.proveedorProductoID = proveedorProductoID;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
}
