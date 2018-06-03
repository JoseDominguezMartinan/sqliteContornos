/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jdominguezmartinan
 */
public class Controlador {

    static DefaultTableModel tabla; // modelo de tabla que sera enviado a vista para dar formato a tabla da interfaz grafica
    public static ArrayList<Coches> cochesNuevos = new ArrayList(); // este array sera empregado para almacenar os coches a hora de buscalos e asi poder visualizalos mellor na taboa da interfaz grafica 
    private static ResultSet datos; // para traballar coas filas das taboas

    /**
     * constructor de la clase donde: damos valores a las columnas del modelo de
     * tabla
     */
    public static void modeloTabla() {
        Controlador.tabla = new DefaultTableModel();
        tabla.addColumn("ID");
        tabla.addColumn("Marca");
        tabla.addColumn("Modelo");
        tabla.addColumn("Motor");
    }

    /**
     * metodo para limpiar de datos la tabla
     *
     * @return tabla modelo de tabla que recogeremos para dar formato a las
     * tablas de la gui
     */
    public static DefaultTableModel borrarTabla() {
        if (tabla != null) {
            for (int i = 0; i < tabla.getRowCount(); i++) {
                tabla.removeRow(i);
                i -= 1;
            }
        }
        return tabla;
    }

    /**
     * metodo para insertar los datos en el modelo de la tabla, para despues
     * meter los datos en las tablas de la gui
     *
     * @return tabla modelo de tabla para la gui
     */
    public static DefaultTableModel mostrarTabla() {
        modeloTabla();
        borrarTabla();

        for (Coches elemento : cochesNuevos) {

            String engadirCoche[] = new String[4];
            engadirCoche[0] = String.valueOf(elemento.getId());
            engadirCoche[1] = elemento.getMarca();
            engadirCoche[2] = elemento.getModelo();
            engadirCoche[3] = elemento.getMotor();

            tabla.addRow(engadirCoche);
        }
        return tabla;
    }

    /**
     * metodo para borrar los datos de la tabla, llamamos al metodo borrar
     * coches del modelo y en caso de recibir error mostramos un mensaje por
     * pantalla
     *
     * @param coche
     */

    public static void borrar(Coches coche) {
        int resultado = Consultas.borrarCoches(coche); // metodo del modelo para borrar el coche de la base de datos , devuleve el numero de la fila borrada, 0 si no borra nada 

        if (resultado == 0) { // si no borra nada saca un mensaje de error
            JOptionPane.showMessageDialog(null, "Error, no se ha podido realizar la operación");
        } else { // si se ha realizado con exito notifica de ello 
            JOptionPane.showMessageDialog(null, "Entrada eliminada con exito");
        }
    }

    /**
     * metodo para insertar datos dentro del array, este array será usado por la
     * vista para visualizar los datos por pantalla
     */
    public static void insertarLista() {

        datos = Consultas.consultarTodos(); // consultamos todos los datos con el metodo del modelo, y nos devuelve el result set

        cochesNuevos.clear(); // limpiamos el array
        try {
            while (datos.next()) // insertamos cada uno de los coches devueltos en el array que tenemos
            {
                cochesNuevos.add(new Coches(datos.getInt("id"), datos.getString("marca").toUpperCase(), datos.getString("modelo").toUpperCase(), datos.getString("motor").toUpperCase()));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al realizar la operación " + ex.getMessage()); // en caso de erro lanzamos a excepcion
        }
        Consultas.close(); // cerramos la conexion con la base de datos 

    }

    /**
     * metodo para buscar en la base de datos por un campo cualquiera
     *
     * @param parametroBusqueda dato que enviamos al metodo para realizar la
     * busqueda
     */
    public static void buscar(String parametroBusqueda) {
        datos = Consultas.buscarCoches(parametroBusqueda);
        cochesNuevos.clear(); // limpiamos el array
        try {
            while (datos.next()) // insertamos cada uno de los coches devueltos en el array que tenemos
            {
                cochesNuevos.add(new Coches(datos.getInt("id"), datos.getString("marca").toUpperCase(), datos.getString("modelo").toUpperCase(), datos.getString("motor").toUpperCase()));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "error al realizar la operación " + ex.getMessage()); // en caso de erro lanzamos a excepcion
        }
        Consultas.close(); // cerramos la conexion con la base de datos, se abrio en el metodo buscarCoches del modelo
    }

    /**
     * metodo para generar un objeto de tipo coches con los datos insertados en
     * la vista y enviarselo a insertarCoches para que se inserte en la base de
     * datos
     *
     * @param parametros arraylist con los parametros para poder crear el objeto
     * en cuestion
     */
    public static void insertarCoche(ArrayList parametros) {
        Coches coche = new Coches((String) parametros.get(0), (String) parametros.get(1), (String) parametros.get(2)); //creamos el objeto
        Consultas.insertarCoches(coche); // se lo enviamos al modelo para que lo inserte en la base de datos 
    }

    /**
     * metodo para generar un objeto de tipo coche que sera enviado al modelo
     * para modificar datos de un coche en la base de datos
     *
     * @param id identificador del coche que queremos modificar , es de tipo int
     * @param parametros arraylist de tipo String con el resto de datos del
     * coche
     */
    public static void modificarCoche(int id, ArrayList parametros) {
        Coches coche = new Coches(id, (String) parametros.get(0), (String) parametros.get(1), (String) parametros.get(2)); // creamos el coche
        Consultas.actualizarCoche(coche); // llamamos al metodo correspodiente enviando el objeto a ser modificado
    }

}
