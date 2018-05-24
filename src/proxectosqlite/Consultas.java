/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosqlite;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jdominguezmartinan 
 */
public class Consultas {

    private String url = "/home/local/DANIELCASTELAO/jdominguezmartinan/NetBeansProjects/proxectoSql/coches.db"; // ruta donde va a estar almacenada la base de datos , en caso de no existir la crea
    private Connection connect; // objeto de tipo connection, proporciona metodos para manexar a base de datos
    private PreparedStatement st; // objeto de tipo PreparedStatement, usado para enviar sentenzas sql a base de datos 
    public ArrayList<Coches> cochesNuevos = new ArrayList(); // este array sera empregado para almacenar os coches a hora de buscalos e asi poder visualizalos mellor na taboa da interfaz grafica 
    private ResultSet datos; // para traballar coa fila actual da taboa 

    /**
     * unha vez conectados a base de datos , pode ser que a taboa da que estamos
     * a falar non haxa sido creada nunca, para non usar un xestor a parte que
     * NetBeans o que fixen foi crear este mmetodo que crea a tabla en caso de
     * que non exista
     */
    public void crearTabla() {
        try {
            st = connect.prepareStatement("create table if not exists coches(id integer primary key autoincrement, modelo varchar(40),marca varchar(40),motor varchar(40))"); // enviamos a sentenza na cal se di que se cree a taboa en caso de non existir
            st.execute();
        } catch (SQLException ex) { // en caso de erro salta a excepcion 
            System.out.println("No se pudo crear la tabla " + ex.getMessage());
        }
    }

    /**
     * metodo para conectase a base de datos
     */
    public void connect() {
        try { // lanzamos una excepcion en caso de non poder conectarse a  base de datos 
            connect = DriverManager.getConnection("jdbc:sqlite:" + url); // co obxeto Connection realizamos a conexion enviando a url da base de datos a que nos imos conectar 
            if (connect != null) { //se conseguimos conectarnos con exito mostranos un mensaxe satisfactorio
                System.out.println("Conectado");
            }
        } catch (SQLException ex) { // se non se pode conectar salta a excepcion 
            System.err.println("No se ha podido conectar a la base de datos\n" + ex.getMessage());
        }
    }

    /**
     * metodo para cerrar a conexion coa base de datos
     */
    public void close() {
        try {
            connect.close(); // cerramos a conexion 
            System.out.println("desconectado ");
        } catch (SQLException ex) { // en caso de erro lanzamos a excepcion
            System.out.println("ha ocurrido un error \n" + ex.getMessage());
        }

    }

    /**
     * metodo para crear un obxeto de tipo Coches
     *
     * @param marca marca do coche a crear
     * @param modelo mdoelo do coche a crear
     * @param motor motor do coche a crear
     * @return coche con todos os datos
     */
    public Coches crearCoche(String marca, String modelo, String motor) {
        Coches coche = new Coches(marca, modelo, motor);
        return coche;
    }

    /**
     * metodo para insertar unha nova entrada na taboa coches que temos na nosa
     * base de datos executar o metodo connect para que se conecte a base de
     * datos
     *
     * @param coche obxeto de tipo coches que queremos insertar na base de datos
     */
    public void insertarCoches(Coches coche) {
        try {
            st = connect.prepareStatement("insert into coches (marca, modelo, motor) values (?,?,?)"); // prepara unha sentenza na cal o que esta entre parentesis vai ser sustituido
            st.setString(1, coche.getMarca().toUpperCase()); // sustituimos cada un dos valores polo que nos interesa , ponemos la primera letra en mayuscula
            st.setString(2, coche.getModelo().toUpperCase());
            st.setString(3, coche.getMotor().toUpperCase());
            st.execute();
        } catch (SQLException ex) { // en caso de erro salta a excepcion 
            System.out.println("No se pudo insertar el vehiculo" + ex.getMessage());
        }
    }

    /**
     * metodo que busca os coches por un determinado campo, limpa o array co que
     * traballamos e mete as coincidencias dentro del
     *
     * @param parametroBusqueda string (ou int no caso do id) co valor do campo
     * co que buscamos
     */
    public void buscarCoches(String parametroBusqueda) {
        connect();
        try {
            st = connect.prepareStatement("select * from coches where id=" + "'" + parametroBusqueda + "'" + " or " + "marca=" + "'" + parametroBusqueda + "'" + " or " + "modelo=" + "'" + parametroBusqueda + "'" + " or " + "motor=" + "'" + parametroBusqueda + "'");// pasamos a orden de busqueda no que algun dos paramatros ten que coincidir 
            datos = st.executeQuery();//  executa a sentencia en st e devolve o resultado a datos , que vai recoller cada unha das filas 
            cochesNuevos.clear(); // limpamos o array para mostrar solo o que acabamos de consultar e que non haxa coches que estiveran de antes no array 
            while (datos.next()) {
                cochesNuevos.add(new Coches(datos.getInt("id"), datos.getString("marca"), datos.getString("modelo"), datos.getString("motor")));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al buscar el coche, intentelo de nuevo"); // en caso de saltar a excepcion salta a mensaxe de erro
        }
        close();
    }

    /**
     * metodo para que no array aparezan todos os coches que se atopan na base
     * de datos
     *
     */
    public void insertarTodosLista() {
        connect();
        try {
            st = connect.prepareStatement("select * from coches");
            datos = st.executeQuery();
            cochesNuevos.clear();
            while (datos.next()) {
                cochesNuevos.add(new Coches(datos.getInt("id"), datos.getString("marca"), datos.getString("modelo"), datos.getString("motor")));
            }
            datos.close();
        } catch (SQLException ex) {
            System.out.println("erro ao insertar os datos");
        }
        close();

    }

    /**
     * metodo para borrar un coche determinado
     *
     * @param coche obxeto coche que queremos borrar da lista
     */

    public void borrarCoches(Coches coche) {
        connect(); // conectamonos como en cada operacion a base de datos 
        try {
            st = connect.prepareStatement("delete from coches where id='" + coche.getId() + "'");// enviamos a sentenza sql a base de datos 
            st.executeUpdate();
        } catch (SQLException ex) { // en caso de erro capturamos a excepcion co seguinte mensaxe de erro
            System.out.println("Error ao eliminar ao vehiculo, intenteo de novo ");
        }
        close(); // cerramos a conexion coa base de datos como en cada operacion 
    }

    /**
     * metodo para actualizar os datos dun coche en concreto
     *
     * @param cocheUpdate obxeto que vamos a actualizar os datos
     */
    public void actualizarCoche(Coches cocheUpdate) {
        connect(); // realizamos a conexion coa base de datos 
        try {
            st = connect.prepareStatement("update coches set id=" + "'" + cocheUpdate.getId() + "'" + ", marca=" + "'" + cocheUpdate.getMarca() + "'" + ", motor=" + "'" + cocheUpdate.getMotor() + "'" + ", modelo=" + "'" + cocheUpdate.getModelo() + "'" + " where id=" + "'" + cocheUpdate.getId() + "'" + ";"); // enviamos a sentenza sql para facer o update
            st.executeUpdate(); // executamos a actualizacion 
        } catch (SQLException ex) { // en caso de erro lanzamos una mensaxe de error 
            JOptionPane.showMessageDialog(null, "Error al actualizar la tabla");
        }

        close(); // desconectamonos da base de datos 

    }
}
