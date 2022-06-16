package org.in5bv.carlosperezjoshuaalvarez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bv.carlosperezjoshuaalvarez.db.Conexion;
import org.in5bv.carlosperezjoshuaalvarez.models.CarrerasTecnicas;
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
public class CarrerasTecnicasController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;

    private final String PAQUETE_IMAGES = "org/in5bv/carlosperezjoshuaalvarez/resources/images/";

    private Principal escenarioPrincipal;

    @FXML
    private TableView tblCarrerasTecnicas;
    @FXML
    private TextField txtCodigoTecnico;
    @FXML
    private TextField txtCarrera;
    @FXML
    private TextField txtGrado;
    @FXML
    private TextField txtSeccion;
    @FXML
    private TextField txtJornada;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private TableColumn colCodigoTecnico;
    @FXML
    private TableColumn colCarrera;
    @FXML
    private TableColumn colGrado;
    @FXML
    private TableColumn colSeccion;
    @FXML
    private TableColumn colJornada;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private ImageView imgRegresar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public ObservableList getCarrerasTecnicas() {

        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CarrerasTecnicas ct = new CarrerasTecnicas();
                ct.setCodigoTecnico(rs.getString(1));
                ct.setCarrera(rs.getString(2));
                ct.setGrado(rs.getString(3));
                ct.setSeccion(rs.getString(4).charAt(0));
                ct.setJornada(rs.getString(5));
                System.out.println(ct.toString());
                lista.add(ct);
            }

            listaCarrerasTecnicas = FXCollections.observableArrayList(lista);

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
        return listaCarrerasTecnicas;
    }

    public void cargarDatos() {
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
    }

    private boolean existeElementoSeleccionado() {
        return (tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado()) {
            txtCodigoTecnico.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            //txtSeccion.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion());
            txtJornada.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
        }
    }

    private boolean agregarCarreraTecnica() {
        CarrerasTecnicas carrerast = new CarrerasTecnicas();
        carrerast.setCodigoTecnico(txtCodigoTecnico.getText());
        carrerast.setCarrera(txtCarrera.getText());
        carrerast.setGrado(txtGrado.getText());
        //carrerast.setSeccion(txtSeccion.getText());
        carrerast.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_create(?, ?, ?, ?, ?)}");

            pstmt.setString(1, carrerast.getCodigoTecnico());
            pstmt.setString(2, carrerast.getCarrera());
            pstmt.setString(3, carrerast.getGrado());
            //pstmt.setString(4, carrerast.getSeccion());
            pstmt.setString(5, carrerast.getJornada());

            System.out.println(pstmt.toString());
            pstmt.execute();

            listaCarrerasTecnicas.add(carrerast);
            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + carrerast.toString());
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

    private boolean actualizarCarreraTecnica() {
        if (existeElementoSeleccionado()) {
            CarrerasTecnicas ct = new CarrerasTecnicas();
            ct.setCodigoTecnico(txtCodigoTecnico.getText());
            ct.setCarrera(txtCarrera.getText());
            ct.setGrado(txtGrado.getText());
            //ct.setSeccion(txtSeccion.getText());
            ct.setJornada(txtJornada.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_update(?, ?, ?, ?, ?)}");

                pstmt.setString(1, ct.getCodigoTecnico());
                pstmt.setString(2, ct.getCarrera());
                pstmt.setString(3, ct.getGrado());
                //pstmt.setString(4, ct.getSeccion());
                pstmt.setString(5, ct.getJornada());

                System.out.println(pstmt.toString());
                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro: " + ct.toString());
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

    private boolean eliminarCarreraTecnica() {
        if (existeElementoSeleccionado()) {
            CarrerasTecnicas carrerast = (CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem();
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_delete(?)}");
                pstmt.setString(1, carrerast.getCodigoTecnico());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + carrerast.toString());
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

    private void validarCamposObligatorios() {
        if ((txtCodigoTecnico.getText().trim().isEmpty()) || ((txtCarrera.getText().trim().isEmpty()))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("¡Alerta!");
            alert.setHeaderText("Campos obligatorios: Código técnico y carrera técnica");
            alert.setContentText("Por favor! ingrese los datos necesarios en los campos obligatorios");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGES + "alerta.png"));
            alert.show();
        } else {
            agregarCarreraTecnica();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Control Academico Kinal");
            alert.setHeaderText(null);
            alert.setContentText("Registro guardado correctamente!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGES + "anadir.png"));
            alert.show();
        }
    }

    private void habilitarCampos() {
        txtCodigoTecnico.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtCarrera.setEditable(true);
        txtJornada.setEditable(true);

        txtCodigoTecnico.setDisable(false);
        txtCarrera.setDisable(false);
        txtSeccion.setDisable(false);
        txtGrado.setDisable(false);
        txtJornada.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtCodigoTecnico.setEditable(false);
        txtCarrera.setEditable(false);
        txtSeccion.setEditable(false);
        txtGrado.setEditable(false);
        txtJornada.setEditable(false);

        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(true);
        txtSeccion.setDisable(true);
        txtGrado.setDisable(true);
        txtJornada.setDisable(true);
    }

    private void limpiarCampos() {
        txtCodigoTecnico.clear();
        txtCarrera.clear();
        txtGrado.clear();
        txtSeccion.clear();
        txtJornada.clear();
    }

    // -----------------------------------------------------------------
    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();

                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "disquete.png"));

                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));

                btnEliminar.setDisable(true);
                btnEliminar.setVisible(false);

                btnReporte.setDisable(true);
                btnReporte.setVisible(false);

                tblCarrerasTecnicas.setDisable(true);

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                validarCamposObligatorios();
                limpiarCampos();
                deshabilitarCampos();
                cargarDatos();

                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);
                tblCarrerasTecnicas.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
        }
    }

    @FXML
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO: // Modificar registro
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    txtCodigoTecnico.setDisable(true);
                    txtCodigoTecnico.setEditable(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "disquete.png"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, selecciona un registro");
                    alert.show();
                }
                break;
            case GUARDAR: //Cancelar Inserción        
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "nuevo.png"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setDisable(false);
                btnEliminar.setVisible(true);

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblCarrerasTecnicas.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if (actualizarCarreraTecnica()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    btnNuevo.setDisable(false);
                    btnNuevo.setVisible(true);

                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case ACTUALIZAR: //Cancelar una modificacion
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblCarrerasTecnicas.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO: //Eliminar un registro
                if (existeElementoSeleccionado()) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Está seguro que deseas eliminar el registro seleccionado?");

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "advertencia.png"));

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarCarreraTecnica()) {
                            listaCarrerasTecnicas.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();

                            Alert alerts = new Alert(Alert.AlertType.INFORMATION);
                            alerts.setTitle("Control Académico Kinal");
                            alerts.setHeaderText(null);
                            alerts.setContentText("El registro ha sido eliminado exitosamente");
                            alerts.show();
                        }
                    } else {
                        alert.close();
                        limpiarCampos();
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                }
                break;
        }
    }

    @FXML
    private void clicReporte() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Hola mundo");
        alerta.setHeaderText(null);
        alerta.setContentText("Esta funcion solo esta disponible en el nuevo mundo");
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "alerta.png"));
        alerta.show();
    }

    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
