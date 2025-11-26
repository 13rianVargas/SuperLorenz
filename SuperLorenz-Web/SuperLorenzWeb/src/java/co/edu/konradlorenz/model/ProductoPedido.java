
package co.edu.konradlorenz.model;


public class ProductoPedido {
private int productoPedidoID;
private int pedidoID;
private int productoID;
private int cantidad;


public ProductoPedido(){
}

    public int getProductoPedidoID() {
        return productoPedidoID;
    }

    public void setProductoPedidoID(int productoPedidoID) {
        this.productoPedidoID = productoPedidoID;
    }

    public int getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(int pedidoID) {
        this.pedidoID = pedidoID;
    }

    public int getProductoID() {
        return productoID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
