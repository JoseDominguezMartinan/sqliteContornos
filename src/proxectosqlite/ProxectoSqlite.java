/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosqlite;

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
        Consultas obxConsultas=new Consultas();
        obxConsultas.connect();
        obxConsultas.close();
        
    }
    
}
