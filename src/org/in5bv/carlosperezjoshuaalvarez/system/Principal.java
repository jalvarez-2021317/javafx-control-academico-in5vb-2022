package org.in5bv.carlosperezjoshuaalvarez.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import org.in5bv.carlosperezjoshuaalvarez.controllers.AlumnosController;
import org.in5bv.carlosperezjoshuaalvarez.controllers.CarrerasTecnicasController;
import org.in5bv.carlosperezjoshuaalvarez.controllers.CursosController;
import org.in5bv.carlosperezjoshuaalvarez.controllers.MenuPrincipalController;
import org.in5bv.carlosperezjoshuaalvarez.controllers.SalonesController;
import org.in5bv.carlosperezjoshuaalvarez.controllers.AsignacionesAlumnosController;
import org.in5bv.carlosperezjoshuaalvarez.controllers.HorariosController;
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
public class Principal extends Application{
    
    private Stage escenarioPrincipal;
    private final String PAQUETE_IMAGES = "org/in5bv/carlosperezjoshuaalvarez/resources/images/";
    private final String PAQUETE_VIEW = "../views/";
    
    public static void main(String []args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage)throws Exception{
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Academico KINAL");
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGES + "pila-de-libros.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        
        
        
        mostrarEscenaPrincipal();
        
        /*
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Hola mundo");
        //Parent root = FXMLLoader.load(getClass().getResource("../Views/MenuPrincipalView.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../Views/AlumnosView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Control Academico");
        primaryStage.setResizable(false);
        //Agregar un icono a la barra de titulos y barra de tareas
        //primaryStage.getIcons().add(new Image("Ruta de ubicacion de la imagen"));
        Image anotherIcon = new Image(getClass().getResourceAsStream("..\\resources\\images\\pila-de-libros.png"));
        primaryStage.getIcons().add(anotherIcon);
        //Colocar un icono a la alerta
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(""));
        primaryStage.show();*/
    }
    public void mostrarEscenaAlumnos(){
        try{
            AlumnosController alumnosController = (AlumnosController)cambiarEscena("AlumnosView.fxml",1200,660);
            alumnosController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.err.println("\nSe produjo un error al intentar mostrar la vista de alumnos");
        }
    }
    
    public void mostrarEscenaSalones(){
        try{
            SalonesController salonesController = (SalonesController)cambiarEscena("SalonesView.fxml",1200,660);
            salonesController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.out.println("\nSe produjo un error al intentar mostrar la vista Salones");
        }
    }
    
    public void mostrarEscenaCursos(){
        try{
            CursosController cursosController = (CursosController)cambiarEscena("CursosView.fxml",1200,660);
            cursosController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.out.println("\nSe produjo un error al intentar mostrar la vista Cursos");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaCarrerasTecnicas(){
        try{
            CarrerasTecnicasController carrerasTecnicasController = (CarrerasTecnicasController)cambiarEscena("CarrerasTecnicasView.fxml",1200,660);
            carrerasTecnicasController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.out.println("\nSe produjo un error al intentar mostrar la vista Carreras Tecnicas");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaAsignacionesAlumnos(){
        try{
            AsignacionesAlumnosController asignacionesAlumnosController = (AsignacionesAlumnosController)cambiarEscena("AsignacionesAlumnosView.fxml",1200,660);
            asignacionesAlumnosController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.out.println("\nSe produjo un error al intentar mostrar la vista Carreras Tecnicas");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaHorarios(){
        try{
            HorariosController horariosController = (HorariosController)cambiarEscena("HorariosView.fxml",1200,660);
            horariosController.setEscenarioPrincipal(this);
        }catch(Exception ex){
            System.out.println("\nSe produjo un error al intentar mostrar la vista Carreras Tecnicas");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaPrincipal(){
        try {
            MenuPrincipalController menuController = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",1200,660);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al cargar la vista Menu Principal");
            
            //para mostrar todos los errores
            //ex.printStackTrace();
        }
    }
    /*
    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto)throws IOException{
        Initializable resultado = null;
        //Cargador del archivo FXML
        FXMLLoader cargadorFXML = new FXMLLoader();
        //Cargador de fabrica para cargar el archivo el archivo FXML
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        //Ubicacion del archivo FXML que se pintara en el escenario
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VIEW + vistaFxml));
        //Asignacion de la logica del archivo
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VIEW + vistaFxml);
        //Creacion del nodo raiz
        AnchorPane root = cargadorFXML.load(archivo);
        //Creacion de la escena
        Scene nuevaEscena = new Scene(root, ancho, alto); 
        //Cargar la escena en el escenario principal
        this.escenarioPrincipal.setScene(nuevaEscena);
        this.escenarioPrincipal.show();
        //Obtener el controlador del archivo FXML
        resultado = (Initializable)cargadorFXML.getController();
        //Devolver el controlador
        return resultado;
    }*/
    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto)throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene= new Scene((AnchorPane)cargadorFXML.load(), ancho, alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
    
        return (Initializable)cargadorFXML.getController();
    }
    
}
