/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosqlite;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jdominguezmartinan
 */
public class Consultas
{
    String url = "/home/local/DANIELCASTELAO/jdominguezmartinan/NetBeansProjects/proxectoSqlite/test.db"; // ruta donde va a estar almacenada la base de datos 
    Connection connect; // comando para conectar con la base de datos 
    
    public void connect(){ // metodo para conectarse a la base de datos
       try { // lanzamos una excepcion en caso de no poder conectarse a la base de datos 
     connect = DriverManager.getConnection("jdbc:sqlite:"+url); // intentamos realizar la conexion con el objeto Connection 
     if (connect!=null) { // si se consiguio la conexion nos lo muestra con este mensaje 
         System.out.println("Conectado");
     }
 }catch (SQLException ex) { // si salta la excepcion y no se puede conectar 
     System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
 }
}
 public void close(){ // metodo para cerrar la conexion con la base de datos 
        try {
            connect.close(); // cerramos la conexion 
            System.out.println("desconectado ");
        } catch (SQLException ex) { // en caso de error lanzamos la excepcion 
            System.out.println("ha ocurrido un error \n"+ex.getMessage());
        }
  
    }
    
    
}
