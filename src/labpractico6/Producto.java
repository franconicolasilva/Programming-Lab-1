/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labpractico6;

/**
 *
 * @author patri
 */
public class Producto {
    private String nombre;
    private double precio;
    private int Stock; 
    private String categoria;
    

    public Producto(String nombre, double precio, int Stock, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.Stock = Stock;
        this.categoria = categoria;
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

  
   
}
