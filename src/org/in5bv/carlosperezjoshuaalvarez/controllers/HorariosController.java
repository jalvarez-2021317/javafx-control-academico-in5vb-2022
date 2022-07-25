package org.in5bv.carlosperezjoshuaalvarez.controllers;

import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bv.carlosperezjoshuaalvarez.db.Conexion;
import org.in5bv.carlosperezjoshuaalvarez.models.Horarios;
import org.in5bv.carlosperezjoshuaalvarez.system.Principal;

/**
 * FXML Controller class
 *
 * @author Joshua David Alvarez Calderon
 */
public class HorariosController implements Initializable {

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
   

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    private Operacion operacion = Operacion.NINGUNO;

    private ObservableList<Horarios> listaAlumnos;

    private final String PAQUETE_IMAGES = "org/in5bv/carlosperezjoshuaalvarez/resources/images/";

    private Principal escenarioPrincipal;
    
    private ObservableList<Horarios> listHorarios;
    @FXML
    private Button btnNuevo;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private ImageView imgModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private ImageView imgReporte;

    @FXML
    private TextField txtId;

    @FXML
    private JFXTimePicker tmkHinicio;

    @FXML
    private JFXTimePicker tmkHf;

    @FXML
    private CheckBox cbLunes;

    @FXML
    private CheckBox cbMartes;

    @FXML
    private CheckBox cbMiercoles;

    @FXML
    private CheckBox cbJueves;

    @FXML
    private CheckBox cbViernes;

    @FXML
    private TableView<?> tblHorarios;

    @FXML
    private TableColumn<Horarios, String> colId;

    @FXML
    private TableColumn<Horarios,String> colHinicio;

    @FXML
    private TableColumn<Horarios, String> colHf;

    @FXML
    private TableColumn<Horarios, String> colLunes;

    @FXML
    private TableColumn<Horarios, String> colMartes;

    @FXML
    private TableColumn<Horarios, String> colMiercoles;

    @FXML
    private TableColumn<Horarios, String> colJueves;

    @FXML
    private TableColumn<Horarios, String> colViernes;

    @FXML
    private ImageView imgRegresar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            cargarDatos();
    }
    
    public ObservableList getHorarios() {

        ArrayList<Horarios> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt("id"));
                horario.setHorarioInicio(rs.getTime("horario_inicio").toLocalTime());
                horario.setHorarioFinal(rs.getTime("horario_final").toLocalTime());
                horario.setLunes(rs.getBoolean("lunes"));
                horario.setMartes(rs.getBoolean("martes"));
                horario.setMiercoles(rs.getBoolean("miercoles"));
                horario.setJueves(rs.getBoolean("jueves"));
                horario.setViernes(rs.getBoolean("viernes"));
                
                System.out.println(horario.toString());

                lista.add(horario);

            }

            listHorarios = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Carreras Tecnicas");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listHorarios;
    }

    public void cargarDatos() {
        tblHorarios.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHinicio.setCellValueFactory(new PropertyValueFactory<>("HorarioInicio"));
        colHf.setCellValueFactory(new PropertyValueFactory<>("HorarioFinal"));
        colLunes.setCellValueFactory(new PropertyValueFactory<>("Lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<>("Martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<>("Miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<>("Jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<>("Viernes"));
    }
    
    private boolean existeElementoSeleccionado() {
        return (tblHorarios.getSelectionModel().getSelectedItem() != null);
    }
    
    public void seleccionarElemento() {
        if(existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getId()));
            tmkHinicio.setValue(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioInicio());
            tmkHf.setValue(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioFinal());
            cbLunes.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isLunes()); 
            cbMartes.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMartes());
            cbMiercoles.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles());
            cbJueves.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isJueves());
            cbViernes.setSelected(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isViernes());
        }
    }
    
     private boolean agregarHorario() {
        Horarios horario = new Horarios();
        horario.setHorarioInicio(tmkHinicio.getValue()); 
        horario.setHorarioFinal(tmkHf.getValue());
        horario.setLunes(cbLunes.isSelected());
        horario.setMartes(cbMartes.isSelected());
        horario.setMiercoles(cbMiercoles.isSelected());
        horario.setJueves(cbJueves.isSelected());
        horario.setViernes(cbViernes.isSelected());
        
        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_create(?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setTime(1, Time.valueOf(horario.getHorarioInicio()));
            pstmt.setTime(2, Time.valueOf(horario.getHorarioFinal()));
            pstmt.setBoolean(3, horario.isLunes());
            pstmt.setBoolean(4, horario.isMartes());
            pstmt.setBoolean(5, horario.isMiercoles());
            pstmt.setBoolean(6, horario.isJueves());
            pstmt.setBoolean(7, horario.isViernes());
            System.out.println(pstmt.toString());
            pstmt.execute();
            listHorarios.add(horario);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjó un error al intentar insertar el siguiente registro" + horario.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private boolean actualizarHorario() {
        if (existeElementoSeleccionado()) {
            Horarios horario = new Horarios();
            horario.setId(Integer.parseInt(txtId.getText()));
            horario.setHorarioInicio(tmkHinicio.getValue());
            horario.setHorarioFinal(tmkHf.getValue());
            horario.setLunes(cbLunes.isSelected());
            horario.setMartes(cbMartes.isSelected());
            horario.setMiercoles(cbMiercoles.isSelected());
            horario.setJueves(cbJueves.isSelected());
            horario.setViernes(cbViernes.isSelected());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_update(?, ?, ?, ?, ?, ?, ?, ?)}");
                pstmt.setInt(1, horario.getId());
                pstmt.setTime(2, Time.valueOf(horario.getHorarioInicio()));
                pstmt.setTime(3, Time.valueOf(horario.getHorarioFinal()));
                pstmt.setBoolean(4, horario.isLunes());
                pstmt.setBoolean(5, horario.isMartes());
                pstmt.setBoolean(6, horario.isMiercoles());
                pstmt.setBoolean(7, horario.isJueves());
                pstmt.setBoolean(8, horario.isViernes());
                System.out.println(pstmt.toString());
                pstmt.execute();
                listHorarios.add(horario);
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjó un error al intentar insertar el siguiente registro" + horario.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    private boolean eliminarHorario() {
        if (existeElementoSeleccionado()) {
            Horarios horario = (Horarios) tblHorarios.getSelectionModel().getSelectedItem();
            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_delete(?)}");
                pstmt.setInt(1, horario.getId());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjó un error al eliminar el siguiente registro" + horario.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
   
    
    private void habilitarCampos() {
        txtId.setEditable(true);
        
        
        txtId.setDisable(false);
        tmkHinicio.setDisable(false);
        tmkHf.setDisable(false);
        cbLunes.setDisable(false);
        cbMartes.setDisable(false);
        cbMiercoles.setDisable(false);
        cbJueves.setDisable(false);
        cbViernes.setDisable(false); 
    } 

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        
        
        txtId.setDisable(true);
        tmkHinicio.setDisable(true);
        tmkHf.setDisable(true);
        cbLunes.setDisable(true);
        cbMartes.setDisable(true);
        cbMiercoles.setDisable(true);
        cbJueves.setDisable(true);
        cbViernes.setDisable(true);
    }

    private void limpiarCampos() {
        //txtCarne.setText("");
        //Forma 2
        txtId.clear();
        tmkHinicio.getEditor().clear();
        tmkHf.getEditor().clear();
        cbLunes.setSelected(false);
        cbMartes.setSelected(false);
        cbMiercoles.setSelected(false);
        cbJueves.setSelected(false);
        cbViernes.setSelected(false);
    } 
    
    @FXML
    private void clicNuevo() {
        switch (operacion) {

            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                
                tblHorarios.setDisable(true);
                
                txtId.setEditable(false);
                txtId.setDisable(true);
                
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "disquete.png"));

                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);

                btnReporte.setDisable(true);
                btnReporte.setVisible(false);

                operacion = Operacion.GUARDAR;
            break;

            case GUARDAR:
                    if (agregarHorario()) {
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();
                        
                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("CONTROL ACADÉMICO");
                        information.setHeaderText(null);
                        information.setContentText("Registro Insertado Exitosamente");
                        Stage stageInformation = (Stage) information.getDialogPane().getScene().getWindow();
                        stageInformation.getIcons().add(new Image(PAQUETE_IMAGES+ "advertencia.png"));
                        information.show();

                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                        btnEliminar.setDisable(false);
                        btnEliminar.setVisible(true);

                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);

                        tblHorarios.setDisable(false);

                        operacion = Operacion.NINGUNO;
                    }
            break;
        }
    }
    
    @FXML
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    txtId.setEditable(false);
                    txtId.setDisable(true);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "disquete.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("CONTROL ACADÉMICO");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecciona un registro");
                    Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                    stageAlert.getIcons().add(new Image(PAQUETE_IMAGES +"advertencia.png"));
                    alert.show();
                }
            break;
            
            case GUARDAR: //Cancelar Inserción
                habilitarCampos();
                limpiarCampos();
                
                tblHorarios.setDisable(false);
                
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
                
                tblHorarios.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                deshabilitarCampos();
            break;
            
            case ACTUALIZAR: //Actualizar un registro
                
                    if (actualizarHorario()) {
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();

                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("CONTROL ACADÉMICO");
                        information.setHeaderText(null);
                        information.setContentText("Registro Actualizado Exitosamente");
                        Stage stageInformation = (Stage) information.getDialogPane().getScene().getWindow();
                        stageInformation.getIcons().add(new Image(PAQUETE_IMAGES +"advertencia.png"));
                        information.show();

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                        btnNuevo.setDisable(false);
                        btnNuevo.setVisible(true);

                        btnReporte.setDisable(false);
                        btnReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;
                    }
            break;
        }
    }
    
    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case NINGUNO: //Eliminar un registro
                //habilitarCampos();
              
                  if (existeElementoSeleccionado()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("CONTROL ACADEMICO");
                    alert.setContentText("¿Está seguro que desea eliminar este registro?");
                    Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                    stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.png"));
                    Optional<ButtonType> delete = alert.showAndWait();

                    if (delete.get().equals(ButtonType.OK)) {
                        if (eliminarHorario()) {
                            listHorarios.remove(tblHorarios.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            Alert information = new Alert(Alert.AlertType.INFORMATION);
                            information.setTitle("CONTROL ACADÉMICO");
                            information.setHeaderText(null);
                            information.setContentText("Registro Eliminado Exitosamente");
                            Stage stageInformation = (Stage) information.getDialogPane().getScene().getWindow();
                            stageInformation.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.png"));
                            information.show();
                        }
                    } else {
                        alert.close();
                        limpiarCampos();
                        tblHorarios.getSelectionModel().clearSelection();
                    }
                } else {
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("CONTROL ACADÉMICO");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Antes de continuar selecciona un registro");
                    Stage stageAlerta = (Stage) alerta.getDialogPane().getScene().getWindow();
                    stageAlerta.getIcons().add(new Image(PAQUETE_IMAGES+"advertencia.png"));
                    alerta.show();
                }
            break;

            case ACTUALIZAR: //cancelar una modificacion
                habilitarCampos();
                limpiarCampos();

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
                
                tblHorarios.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                deshabilitarCampos();
                break;
        }
        
    }
    
    @FXML
    private void clicReporte() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¡AVISO!");
        //alerta.setHeaderText("CONTROL ACADÉMICO");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta opcion no esta diponible compra la version Premium");
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(PAQUETE_IMAGES+"advertencia.png"));
        alerta.show();
    }
    
    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

}
