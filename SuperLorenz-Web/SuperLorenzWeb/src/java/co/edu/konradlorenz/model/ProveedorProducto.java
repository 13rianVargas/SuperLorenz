
package co.edu.konradlorenz.model;


public class ProveedorProducto {
    private int proveedorProductoID;
    private int productoID;
    private int proveedorID;
    private double valorMinimo;
    private double valorMaximo;
 
    public ProveedorProducto(){
    }

    public int getProveedorProductoID() {
        return proveedorProductoID;
    }

    public void setProveedorProductoID(int proveedorProductoID) {
        this.proveedorProductoID = proveedorProductoID;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public int getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(int proveedorID) {
        this.proveedorID = proveedorID;
    }

    public double getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }
    
}
