package org.in5bv.carlosperezjoshuaalvarez.models;

/**
 *
 * @author Carlos Emmanuel Pérez Simón      2021299
 *         Joshua David Alvarez Calderon    2021317
 * @date 29/04/2022
 * @time 15:42:39
 *
 * Codigo Tecnico: IN5BV
 *
 */
public class Salones {
    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;
    
    public Salones(){
        
    }
    
    public Salones(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }
    
    public Salones(String descripcion, int capacidadMaxima, String edificio) {
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
    }

    public Salones(String codigoSalon, String descripcion, int capacidadMaxima, String edificio, int nivel) {
        this.codigoSalon = codigoSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }

    public String getCodigoSalon() {
        return codigoSalon;
    }

    public void setCodigoSalon(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Salones{" + "| " + codigoSalon + "| " + descripcion + "| " + capacidadMaxima + "| " + edificio + "| " + nivel + '}';
    }
    
}
