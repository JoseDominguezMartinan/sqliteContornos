/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosqlite;

import Gui.Principal;

/**
 *
 * @author jdominguezmartinan
 */
public class ProxectoSqlite
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        Consultas.crearTabla(); // en caso de no existir la tabla en cuestion este metodo la genera, en caso de existir no hace nada 
        Principal ventana=new Principal(); // abrimos la ventana del menu principal 
        
    }
    
}
