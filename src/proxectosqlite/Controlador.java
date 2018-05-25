/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosqlite;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jdominguezmartinan
 */
public  class Controlador
{
    static DefaultTableModel tabla;
    // objeto del metodo consultas para acceder a sus metodos
    static Consultas obxConsultas=new Consultas();
    
/**
 * constructor de la clase donde:
 * damos valores a las columnas del modelo de tabla 
 */
    public Controlador()
    {
        Controlador.tabla = new DefaultTableModel();
        tabla.addColumn("ID");
        tabla.addColumn("Marca");
        tabla.addColumn("Modelo");
        tabla.addColumn("Motor");
    }
    
     
    
    /**
     * metodo para limpiar de datos la tabla
     * @return tabla modelo de tabla que recogeremos para dar formato a las tablas de la gui
     */
    
    public static DefaultTableModel borrarTabla() {
        for (int i = 0; i < tabla.getRowCount(); i++) {
            tabla.removeRow(i);
            i -= 1;
        }
         return tabla;

    }
    /**
     * metodo para insertar los datos en el modelo de la tabla, para despues meter los datos en las tablas de la gui
     * @return tabla modelo de tabla para la gui
     */

    public static DefaultTableModel mostrarTabla() {

        borrarTabla();

        for (Coches elemento : obxConsultas.cochesNuevos) {

            String engadirCoche[] = new String[4];
            engadirCoche[0] = String.valueOf(elemento.getId());
            engadirCoche[1] = elemento.getMarca();
            engadirCoche[2] = elemento.getModelo();
            engadirCoche[3] = elemento.getMotor();

            tabla.addRow(engadirCoche);

        }

        return tabla;

    }
    
}
