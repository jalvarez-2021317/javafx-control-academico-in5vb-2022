package org.in5bv.carlosperezjoshuaalvarez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.in5bv.carlosperezjoshuaalvarez.db.Conexion;
import org.in5bv.carlosperezjoshuaalvarez.system.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joshua David Alvarez Calderon
 */
public class LoginController implements Initializable {

    private Principal escenarioPrincipal;
    private final String PAQUETE_IMAGES = "org/in5bv/carlosperezjoshuaalvarez/resources/images/";

    @FXML
    private PasswordField tpfContaseña;

    @FXML
    private TextField txtUsuario;

    @FXML
    private Button btnIngresar;

    @FXML
    private Label lblMensaje;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    
    

    @FXML
    public void clicIngresar() {
       escenarioPrincipal.mostrarEscenaPrincipal();
                                                    
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    
}
