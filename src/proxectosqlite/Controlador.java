/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jdominguezmartinan
 */
public class Controlador
{

    static DefaultTableModel tabla;
    
    // objeto del metodo consultas para acceder a sus metodos
    static Consultas obxConsultas = new Consultas();
    public static ArrayList<Coches> cochesNuevos = new ArrayList(); // este array sera empregado para almacenar os coches a hora de buscalos e asi poder visualizalos mellor na taboa da interfaz grafica 
    private static ResultSet datos; // para traballar coas filas das taboas

    /**
     * constructor de la clase donde: damos valores a las columnas del modelo de
     * tabla
     */
    public static void  modeloTabla()
    {
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
    public static DefaultTableModel borrarTabla()
    {
        if(tabla!=null){
        for(int i = 0; i<tabla.getRowCount(); i++)
        {
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

    public static DefaultTableModel mostrarTabla()
    {
        modeloTabla();
        borrarTabla();

        for(Coches elemento : cochesNuevos)
        {

            String engadirCoche[] = new String[4];
            engadirCoche[0] = String.valueOf(elemento.getId());
            engadirCoche[1] = elemento.getMarca();
            engadirCoche[2] = elemento.getModelo();
            engadirCoche[3] = elemento.getMotor();

            tabla.addRow(engadirCoche);

        }

        return tabla;

    }

    public static void borrar(Coches coche)
    {
        int resultado = obxConsultas.borrarCoches(coche); // metodo del modelo para borrar el coche de la base de datos , devuleve el numero de la fila borrada, 0 si no borra nada 

        if(resultado==0)
        { // si no borra nada saca un mensaje de error
            JOptionPane.showMessageDialog(null,"Error, no se ha podido realizar la operación");
        } else
        { // si se ha realizado con exito notifica de ello 
            JOptionPane.showConfirmDialog(null,"Operación realizada con exito");
        }
    }

    public static void insertarLista()
    {
        
            datos = obxConsultas.insertarTodosLista();
            
            cochesNuevos.clear(); // limpiamos el array
            try
            {
                while (datos.next()) // insertamos cada uno de los coches devueltos en el array que tenemos
                {
                    cochesNuevos.add(new Coches(datos.getInt("id"),datos.getString("marca").toUpperCase(),datos.getString("modelo").toUpperCase(),datos.getString("motor").toUpperCase()));
                }
            } catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "error al realizar la operación "+ex.getMessage() ); // en caso de erro lanzamos a excepcion
            }
           obxConsultas.close(); 
        
      
    }

}
