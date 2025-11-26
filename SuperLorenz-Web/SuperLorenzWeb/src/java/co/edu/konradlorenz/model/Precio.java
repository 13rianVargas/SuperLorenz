
package co.edu.konradlorenz.model;

import java.util.Date;


public class Precio {
    private int precioID;
    private int productoID;
    private double valorUnitario;
    private double iva;
    private double precioUnitarioTotal;
    private Date fecha;
    
    
    public Precio(){
}

    public int getPrecioID() {
        return precioID;
    }

    public void setPrecioID(int precioID) {
        this.precioID = precioID;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getPrecioUnitarioTotal() {
        return precioUnitarioTotal;
    }

    public void setPrecioUnitarioTotal(double precioUnitarioTotal) {
        this.precioUnitarioTotal = precioUnitarioTotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}