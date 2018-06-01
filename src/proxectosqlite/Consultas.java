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
public class Consultas
{

    private String url = "/home/local/DANIELCASTELAO/jdominguezmartinan/NetBeansProjects/proxectoSql/coches.db"; // ruta donde va a estar almacenada la base de datos , en caso de no existir la crea
    private Connection connect; // objeto de tipo connection, proporciona metodos para manexar a base de datos
    private PreparedStatement st; // objeto de tipo PreparedStatement, usado para enviar sentenzas sql a base de datos
    private ResultSet datos; // para traballar coa fila actual da taboa 
    private int resultado; // para devolver si se ha ejecutado la operacion con exito o no 

    /**
     * unha vez conectados a base de datos , pode ser que a taboa da que estamos
     * a falar non haxa sido creada nunca, para non usar un xestor a parte que
     * NetBeans o que fixen foi crear este mmetodo que crea a tabla en caso de
     * que non exista
     */
    public void crearTabla()
    {
        connect();
        try
        {
            st = connect.prepareStatement("create table if not exists coches(id integer primary key autoincrement, modelo varchar(40),marca varchar(40),motor varchar(40))"); // enviamos a sentenza na cal se di que se cree a taboa en caso de non existir
            st.execute();
        } catch(SQLException ex)
        { // en caso de erro salta a excepcion 
            System.out.println("No se pudo crear la tabla "+ex.getMessage());
        }
        close();
    }

    /**
     * metodo para conectase a base de datos
     */
    public void connect()
    {
        try
        { // lanzamos una excepcion en caso de non poder conectarse a  base de datos 
            connect = DriverManager.getConnection("jdbc:sqlite:"+url); // co obxeto Connection realizamos a conexion enviando a url da base de datos a que nos imos conectar 
            if(connect!=null)
            { //se conseguimos conectarnos con exito mostranos un mensaxe satisfactorio
                System.out.println("Conectado");
            }
        } catch(SQLException ex)
        { // se non se pode conectar salta a excepcion 
            System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
        }
    }

    /**
     * metodo para cerrar a conexion coa base de datos
     */
    public void close()
    {
        try
        {
            connect.close(); // cerramos a conexion 
            System.out.println("desconectado ");
        } catch(SQLException ex)
        { // en caso de erro lanzamos a excepcion
            System.out.println("ha ocurrido un error \n"+ex.getMessage());
        }

    }

    /**
     * metodo para insertar unha nova entrada na taboa coches que temos na nosa
     * base de datos executar o metodo connect para que se conecte a base de
     * datos
     *
     * @param coche obxeto de tipo coches que queremos insertar na base de datos
     */
    public void insertarCoches(Coches coche)
    {
        connect();
        try
        {
            st = connect.prepareStatement("insert into coches (marca, modelo, motor) values (?,?,?)"); // prepara unha sentenza na cal o que esta entre parentesis vai ser sustituido
            st.setString(1,coche.getMarca().toUpperCase()); // sustituimos cada un dos valores polo que nos interesa , ponemos la primera letra en mayuscula
            st.setString(2,coche.getModelo().toUpperCase());
            st.setString(3,coche.getMotor().toUpperCase());
            st.execute();
        } catch(SQLException ex)
        { // en caso de erro salta a excepcion 
            System.out.println("No se pudo insertar el vehiculo"+ex.getMessage());
        }
        close();
    }

    /**
     * metodo que busca os coches por un determinado campo, limpa o array co que
     * traballamos e mete as coincidencias dentro del
     *
     * @param parametroBusqueda string (ou int no caso do id) co valor do campo
     * co que buscamos
     */
    public ResultSet buscarCoches(String parametroBusqueda)
    {
        connect();
        try
        {
            st = connect.prepareStatement("select * from coches where id="+"'"+parametroBusqueda+"'"+" or "+"marca="+"'"+parametroBusqueda+"'"+" or "+"modelo="+"'"+parametroBusqueda+"'"+" or "+"motor="+"'"+parametroBusqueda+"'");// pasamos a orden de busqueda no que algun dos paramatros ten que coincidir 
            datos = st.executeQuery();//  executa a sentencia en st e devolve o resultado a datos , que vai recoller cada unha das filas 

        } catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,"error al buscar el coche, intentelo de nuevo"); // en caso de saltar a excepcion salta a mensaxe de erro
        }
        close();
        return datos;

    }

    /**
     * metodo para que no array aparezan todos os coches que se atopan na base
     * de datos
     *
     */
    public ResultSet insertarTodosLista()
    {
        connect(); // conectamos con la base de datos 
        try
        {
            st = connect.prepareStatement("select * from coches"); // seleccionamos todas las entradas de la tabla 
            datos = st.executeQuery(); // ejecutamos la orden 
            
        } catch(SQLException ex)
        {
            System.out.println("erro ao insertar os datos"); // lanzamos un mensaje de error en caso de que no se pueda realizar la operación 
        }
        //close(); // cerramos la conexion con la base de datos 
        return datos;

    }

    /**
     * metodo para borrar un coche determinado
     *
     * @param coche obxeto coche que queremos borrar da lista
     */
    public int borrarCoches(Coches coche)
    {
        connect(); // conectamonos como en cada operacion a base de datos 
        try
        {
            st = connect.prepareStatement("delete from coches where id='"+coche.getId()+"'");// enviamos a sentenza sql a base de datos 
            resultado=st.executeUpdate();
        } catch(SQLException ex)
        { // en caso de erro capturamos a excepcion co seguinte mensaxe de erro
            System.out.println("Error ao eliminar ao vehiculo, intenteo de novo ");
        }
        close(); // cerramos a conexion coa base de datos como en cada operacion 
        return resultado;
    }

    /**
     * metodo para actualizar os datos dun coche en concreto
     *
     * @param cocheUpdate obxeto que vamos a actualizar os datos
     */
    public void actualizarCoche(Coches cocheUpdate)
    {
        connect(); // realizamos a conexion coa base de datos 
        try
        {
            st = connect.prepareStatement("update coches set id="+"'"+cocheUpdate.getId()
                    +"'"+", marca="+"'"+cocheUpdate.getMarca().toUpperCase()+"'"
                    +", motor="+"'"+cocheUpdate.getMotor().toUpperCase()+"'"
                    +", modelo="+"'"+cocheUpdate.getModelo().toUpperCase()
                    +"'"+" where id="+"'"+cocheUpdate.getId()+"'"+";"); // enviamos a sentenza sql para facer o update
            st.executeUpdate(); // executamos a actualizacion 
        } catch(SQLException ex)
        { // en caso de erro lanzamos una mensaxe de error 
            JOptionPane.showMessageDialog(null,"Error al actualizar la tabla");
        }

        close(); // desconectamonos da base de datos 

    }
}
