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
public class Coches
{
    private int id;
    private String marca;
    private String modelo;
    private String motor;

    public Coches()
    {
    }

    public Coches(String marca,String modelo,String motor)
    {
        this.marca = marca;
        this.modelo = modelo;
        this.motor = motor;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getMarca()
    {
        return marca;
    }

    public void setMarca(String marca)
    {
        this.marca = marca;
    }

    public String getModelo()
    {
        return modelo;
    }

    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }

    public String getMotor()
    {
        return motor;
    }

    public void setMotor(String motor)
    {
        this.motor = motor;
    }

    @Override
    public String toString()
    {
        return "Coches{"+"id="+id+", marca="+marca+", modelo="+modelo+", motor="+motor+'}';
    }
    
   
    
    /**
     * metodo para almacenar un coche en la base de datos 
     */
    public void save(){
        Consultas con = new Consultas();
        con.connect();
        con.insertarCoches(this);
        con.close();
    }
    
}
