package org.in5bv.carlosperezjoshuaalvarez.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import org.in5bv.carlosperezjoshuaalvarez.reports.GenerarReporte;
import org.in5bv.carlosperezjoshuaalvarez.system.Principal;

/**
 *
 * @author Carlos Emmanuel Pérez Simón 2021299 Joshua David Alvarez Calderon
 * 2021317
 * @date 29/04/2022
 * @time 15:42:39
 *
 * Codigo Tecnico: IN5BV
 *
 */
public class MenuPrincipalController implements Initializable {

    private Principal escenarioPrincipal;
    private final String PAQUETE_IMAGES = "org/in5bv/carlosperezjoshuaalvarez/resources/images/";
   

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void clicAlumnos() {
        escenarioPrincipal.mostrarEscenaAlumnos();
    }

    @FXML
    public void clicSalones() {
        escenarioPrincipal.mostrarEscenaSalones();
    }

    @FXML
    public void clicCarrerasTecnicas() {
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }

    @FXML
    public void clicCursos() {
        escenarioPrincipal.mostrarEscenaCursos();
    }

    @FXML
    public void clicAsignacionesAlumnos() {
        escenarioPrincipal.mostrarEscenaAsignacionesAlumnos();
    }

    @FXML
    public void clicHorarios() {
        escenarioPrincipal.mostrarEscenaHorarios();
    }

    @FXML
    public void clicReporteAsiganacionesPorId(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "asignacion .png");
        TextInputDialog dialog  = new TextInputDialog();
        dialog.setHeaderText("Ingresa el id");
        dialog.setContentText("Id Asignacion ");
        Optional<String> result = dialog.showAndWait();
        System.out.println(result +"Numero");
        if (result.isPresent()) {
            System.out.println("Numero: " + result.get());
            parametros.put("idAsignacion", Integer.parseInt(result.get()));
        }
        GenerarReporte.getInstance().mostrarReporte("ReporteAsignaciones.jasper", parametros, "Reporte de Asignaciones Alumnos ");
    }

    @FXML
    public void clicReporteAsignaciones(ActionEvent event) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "asignacion .png");
        GenerarReporte.getInstance().mostrarReporte("ReporteAsignaciones.jasper", parametros, "Reporte de Asignaciones Alumnos ");
    }

    @FXML
    public void clicReporteCarreras(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "Carreras.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteCarreras.jasper",parametros, "Reporte de Carreras Tecnicas");
    }

    @FXML
    public void clicReporteCursos(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "cursos.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteCursos.jasper", parametros, "Reporte de Cursos");
    }

    @FXML
    public void clicReporteCursosPorId(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "cursos.png");
        TextInputDialog dialog  = new TextInputDialog();
        dialog.setHeaderText("Ingresa el id");
        dialog.setContentText("Id Asignacion ");
        Optional<String> result = dialog.showAndWait();
        System.out.println(result +"Numero");
        if (result.isPresent()) {
            System.out.println("Numero: " + result.get());
            parametros.put("idAsignacion", Integer.parseInt(result.get()));
        }
        GenerarReporte.getInstance().mostrarReporte("ReporteCursosById.jasper", parametros, "Reporte de Cursos por id");
    }

    @FXML
    public void clicReporteHorarios(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "calendario.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteHorarios.jasper",parametros, "Reporte de Horarios");
    }

    @FXML
    public void clicReporteInstructores(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "instructor.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteInstructores.jasper",parametros, "Reporte de Instructores");
    }

    @FXML
    public void clicReporteSalones(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "salon-de-clases.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper", parametros, "Reporte Salones");
    }

    @FXML
    public void clicSalones(ActionEvent event) {

    }

    @FXML
    public void clickReporteAlumnos(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "estudiantes.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteAlumnos.jasper",parametros, "Reporte Alumnos");

    }
    
    @FXML
    public void clicAcercade(){
        escenarioPrincipal.mostrarEscenaAcercade();
    }
    
    @FXML
    public void clicCerrar(){
        Platform.exit();
    }
    
    @FXML
    public void clicCerrarSesion(){
        escenarioPrincipal.mostrarEscenaLogin();
    }

}
