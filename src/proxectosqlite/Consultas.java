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
    String url = "/home/local/DANIELCASTELAO/jdominguezmartinan/NetBeansProjects/proxectoSqlite/coches.db"; // ruta donde va a estar almacenada la base de datos 
    Connection connect; // comando para conectar con la base de datos 
    
    public void crearTabla(){
     try {
            PreparedStatement st=connect.prepareStatement("create table if not exists coches(id integer primary key autoincrement, modelo varchar(40),marca varchar(40),motor varchar(40))");
            st.execute();
        } catch (SQLException ex) { // en caso de erro salta a excepcion 
            System.out.println("No se pudo crear la tabla "+ex.getMessage());
        }
 }
    
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
  /**
  * metodo para crear un obxeto de tipo Coches
  * @param marca marca do coche a crear 
  * @param modelo mdoelo do coche a crear 
  * @param motor motor do coche a crear 
  * @return coche con todos os datos 
  */
  public Coches crearCoche(String marca,String modelo,String motor){
     Coches coche=new Coches(marca,modelo,motor);
     return coche;
 }
 /**
  * metodo para insertar un coche novo na base de datos , hai que primeiro executar o metodo connect para que se conecte a base de datos 
  * @param coche obxeto de tipo coches que queremos insertar na base de datos 
  */

 public void insertarCoches(Coches coche){
     try {
            PreparedStatement st = connect.prepareStatement("insert into coches (marca, modelo, motor) values (?,?,?)"); // prepara unha sentenza na cal o que esta entre parentesis vai ser sustituido
            st.setString(1, coche.getMarca()); // sustituimos cada un dos valores polo que nos interesa 
            st.setString(2, coche.getModelo());
            st.setString(3, coche.getMotor());
            st.execute();
        } catch (SQLException ex) { // en caso de erro salta a excepcion 
            System.out.println("No se pudo insertar el vehiculo"+ex.getMessage());
        }
 }
    
    
}
