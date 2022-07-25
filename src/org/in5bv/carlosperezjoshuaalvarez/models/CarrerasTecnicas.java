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
public class CarrerasTecnicas {
    private String codigoTecnico;
    private String carrera;
    private String grado;
    private char seccion;
    private String jornada;
    
    public CarrerasTecnicas(){
        
    }
    
    public CarrerasTecnicas(String codigoTecnico, String carrera){
        this.codigoTecnico = codigoTecnico;
        this.carrera = carrera;
    }
    public CarrerasTecnicas(String grado, char seccion, String jornada){
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
    }
    
    public CarrerasTecnicas(String codigoTecnico, String carrera, String grado, char seccion, String jornada){
        this.codigoTecnico = codigoTecnico;
        this.carrera = carrera;
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
    }

    public String getCodigoTecnico() {
        return codigoTecnico;
    }
    
    public void setCodigoTecnico(String codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public String getCarrera() {
        return carrera;
    }
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGrado() {
        return grado;
    }
    
    public void setGrado(String grado) {
        this.grado = grado;
    }

    public char getSeccion() {
        return seccion;
    }
    
    public void setSeccion(char seccion) {
        this.seccion = seccion;
    }

    public String getJornada() {
        return jornada;
    }
    
    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Override
    public String toString() {
        return "CarrerasTecnicas" + "| " + codigoTecnico + " | " + carrera + " | " + grado + " | " + seccion + " | " + jornada + '|';
    }
    
}
