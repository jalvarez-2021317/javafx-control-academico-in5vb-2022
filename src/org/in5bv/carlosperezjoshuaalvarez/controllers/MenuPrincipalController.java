package org.in5bv.carlosperezjoshuaalvarez.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bv.carlosperezjoshuaalvarez.system.Principal;
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
public class MenuPrincipalController implements Initializable{
    
    private Principal escenarioPrincipal;
        
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicAlumnos(){
        escenarioPrincipal.mostrarEscenaAlumnos();
    }
    @FXML
    public void clicSalones(){
        escenarioPrincipal.mostrarEscenaSalones();
    }
    @FXML
    public void clicCarrerasTecnicas(){
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }
    
    @FXML
    public void clicCursos (){
        escenarioPrincipal.mostrarEscenaCursos();
    }
    
    
    @FXML
    public void clicAsignacionesAlumnos (){
        escenarioPrincipal.mostrarEscenaAsignacionesAlumnos();
    }
    
    @FXML
    public void clicHorarios (){
        escenarioPrincipal.mostrarEscenaHorarios();
    }
}
