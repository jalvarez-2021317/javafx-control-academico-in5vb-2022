package org.in5bv.carlosperezjoshuaalvarez.db;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
/**
 *
 * @author Carlos Emmanuel Pérez Simón
 * @date 3/05/2022
 * @time 16:59:06
 *
 * Codigo Tecnico: IN5BV
 *
 */
public class Conexion {
    private final String IP_SERVER = "localhost";
    private final String PORT = "3306";
    private final String DB = "db_control_academico_in5bv";
    private final String USER = "kinal"; 
    private final String PASSWORD = "admin";
    private final String URL;
    private Connection conexion;
    
    private static Conexion instancia;
    
    public static Conexion getInstance(){
        
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
    
    public Conexion(){
        URL = "jdbc:mysql://"+ IP_SERVER +":"+ PORT +"/" + DB + "?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
        
        try{
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa!");
            
            DatabaseMetaData dma = conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("\nDriver: " + dma.getDriverName());
            System.out.println("\nVersion: " + dma.getDriverVersion());
       /* 
         Excepciones especificas para una instancia
            
        }catch(InstantiationException ex){
            System.err.println("No se puede crear una instancia del objeto");
            ex.printStackTrace();
        }catch(IllegalAccessException ex){
            System.err.println("No se tiene los permisos para acceder al paquete");
            ex.printStackTrace();
      */
        }catch(ClassNotFoundException ex){
            System.err.println("No se encuentra ninguna definición para la clase: ");
            ex.printStackTrace();
        }catch(CommunicationsException ex){
            System.err.println("No se puede establecer comunicacion con el servidor de MySQL"
                    + "\nRecomendación:"
                    + "\nVerifique que el nombre del HOST o la IP_SERVER sea correcta"
                    + "\n");
        }catch(SQLException e){
            System.err.println("Se produjo un error de tipo SQLException ");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: "+ e.getErrorCode());
            System.err.println("SQLState: "+ e.getSQLState());
        }catch(Exception e){
            System.err.println("Se produjo un error al hacer conexion con la base de datos");
            e.printStackTrace();
        }
    }
    
    public Connection getConexion(){
        return conexion;
    }
   
}
