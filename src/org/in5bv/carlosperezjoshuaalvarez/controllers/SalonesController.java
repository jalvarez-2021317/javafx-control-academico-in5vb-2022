package org.in5bv.carlosperezjoshuaalvarez.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.in5bv.carlosperezjoshuaalvarez.models.Salones;
import org.in5bv.carlosperezjoshuaalvarez.system.Principal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bv.carlosperezjoshuaalvarez.db.Conexion;
import org.in5bv.carlosperezjoshuaalvarez.reports.GenerarReporte;

/**
 *
 * @author Carlos Emmanuel Pérez Simón 2021299 Joshua David Alvarez Calderon
 * 2021317
 *
 * @date 29/04/2022
 * @time 15:42:39
 *
 * Codigo Tecnico: IN5BV
 *
 */
public class SalonesController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR
    }
    private Operacion operacion = Operacion.NINGUNO;

    private final String PAQUETE_IMAGES = "org/in5bv/carlosperezjoshuaalvarez/resources/images/";

    private Principal escenarioPrincipal;
    private ObservableList<Salones> listaSalones;

    @FXML
    private TextField txtCodigoSalon;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtCapacidadMaxima;

    @FXML
    private TextField txtEdificio;

    @FXML
    private TextField txtNivel;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblSalones;

    @FXML
    private TableColumn colCodigoSalon;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCapacidadMaxima;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public ObservableList getSalones() {

        ArrayList<Salones> listaS = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_read()");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Salones salones = new Salones();
                salones.setCodigoSalon(rs.getString(1));
                salones.setDescripcion(rs.getString(2));
                salones.setCapacidadMaxima(rs.getInt(3));
                salones.setEdificio(rs.getString(4));
                salones.setNivel(rs.getInt(5));
                System.out.println(salones.toString());
                listaS.add(salones);
            }

            listaSalones = FXCollections.observableArrayList(listaS);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Salones");
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
        return listaSalones;
    }

    private boolean existeElementoSeleccionado() {
        return (tblSalones.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {

        if (existeElementoSeleccionado() == true) {
            txtCapacidadMaxima.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));
            txtCodigoSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            txtNivel.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel()));
        }
    }

    public void cargarDatos() {
        tblSalones.setItems(getSalones());
        colCodigoSalon.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidadMaxima.setCellValueFactory(new PropertyValueFactory<Salones, String>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, String>("nivel"));
    }

    private void habilitarCampos() {
        txtCodigoSalon.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCapacidadMaxima.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);

        txtCodigoSalon.setDisable(false);
        txtDescripcion.setDisable(false);
        txtCapacidadMaxima.setDisable(false);
        txtEdificio.setDisable(false);
        txtNivel.setDisable(false);

    }

    private boolean agregarSalon() {
        Salones salon = new Salones();
        salon.setCapacidadMaxima(Integer.parseInt((txtCapacidadMaxima.getText())));
        salon.setCodigoSalon(txtCodigoSalon.getText());
        salon.setDescripcion(txtDescripcion.getText());
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel(Integer.parseInt(txtNivel.getText()));

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_create(?, ?, ?, ?, ?)}");

            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

            System.out.println(pstmt.toString());
            pstmt.execute();

            listaSalones.add(salon);
            return true;

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + salon.toString());
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

    private boolean eliminarSalon() {
        if (existeElementoSeleccionado()) {
            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_delete(?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un erro al intentar eliminar el siguiente registro: " + salon.toString());
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

    private boolean actualizarSalon() {
        if (existeElementoSeleccionado()) {
            Salones salon = new Salones();
            salon.setCapacidadMaxima(Integer.parseInt((txtCapacidadMaxima.getText())));
            salon.setCodigoSalon(txtCodigoSalon.getText());
            salon.setDescripcion(txtDescripcion.getText());
            salon.setEdificio(txtEdificio.getText());
            salon.setNivel(Integer.parseInt(txtNivel.getText()));

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_update(?, ?, ?, ?, ?)}");

                pstmt.setString(1, salon.getCodigoSalon());
                pstmt.setString(2, salon.getDescripcion());
                pstmt.setInt(3, salon.getCapacidadMaxima());
                pstmt.setString(4, salon.getEdificio());
                pstmt.setInt(5, salon.getNivel());

                System.out.println(pstmt.toString());
                pstmt.execute();

                listaSalones.add(salon);
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro: " + salon.toString());
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

    private void ValidarCamposObligatorios() {
        if ((txtCodigoSalon.getText().trim().isEmpty()) || ((txtCapacidadMaxima.getText().trim().isEmpty()))) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("!Alerta!");
            alert.setHeaderText("Recuerda que los campos Capacidad Maxima y Codigo Salon Son Obligatorios");
            alert.setContentText("Por fabor ingresa los datos Obligatios");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGES + "alerta.png"));
            alert.show();

        } else {
            agregarSalon();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Control Academico Kinal");
            alert.setHeaderText(null);
            alert.setContentText("El registro se a agregado exitosamente");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PAQUETE_IMAGES + "editar.png"));
            alert.show();

        }
    }

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

                tblSalones.setDisable(true);

                operacion = Operacion.GUARDAR;
                break;

            case GUARDAR:
                ValidarCamposObligatorios();
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
                tblSalones.setDisable(false);

                operacion = Operacion.NINGUNO;

                break;

        }
    }

    @FXML
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    txtCodigoSalon.setDisable(true);
                    txtCodigoSalon.setEditable(false);

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
            case GUARDAR:
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

                tblSalones.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;

            case ACTUALIZAR:
                if (actualizarSalon()) {
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
    public void clicEliminar() {
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

                tblSalones.getSelectionModel().clearSelection();

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
                        if (eliminarSalon()) {
                            listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
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
                        tblSalones.getSelectionModel().clearSelection();
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
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("SALUDO", PAQUETE_IMAGES + "salon-de-clases.png");
        GenerarReporte.getInstance().mostrarReporte("ReporteSalones.jasper",parametros, "Reporte Salones");
    }

    @FXML
    private void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    private void deshabilitarCampos() {
        txtCapacidadMaxima.setEditable(false);
        txtCodigoSalon.setEditable(false);
        txtDescripcion.setEditable(false);
        txtEdificio.setEditable(false);
        txtNivel.setEditable(false);

        txtCapacidadMaxima.setDisable(true);
        txtCodigoSalon.setDisable(true);
        txtDescripcion.setDisable(true);
        txtEdificio.setDisable(true);
        txtNivel.setDisable(true);
    }

    private void limpiarCampos() {
        txtCapacidadMaxima.setText("");
        txtCodigoSalon.setText("");
        txtDescripcion.clear();
        txtEdificio.clear();
        txtNivel.clear();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}


//https://es.stackoverflow.com/questions/146564/como-mostrar-datos-en-un-textfield-en-javafx
