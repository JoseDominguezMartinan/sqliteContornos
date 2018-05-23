/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proxectosqlite;

/**
 * taller multimarca que vende coches de exposicion, os cales non estan
 * matriculados, ordenados por un identificador id, e que van a ser tratados
 * nunha base de datos sqlite
 *
 * @author jdominguezmartinan
 */
public class Coches {

    private int id; //id do vehiculo autonumerico que se engadira conforme se engada na taboa da base de datos
    private String marca; // marca do modelo do vehiculo
    private String modelo; // modelo do vehiculo
    private String motor; // motor do vehiculo

    /**
     * constructor por defecto
     */
    public Coches() {
    }

    /**
     * constructor con todos os parametros menos o id, a hora de insertar os
     * coches na taboa o id xenerase solo por iso necesitamos un constructor asi
     *
     * @param marca
     * @param modelo
     * @param motor
     */
    public Coches(String marca, String modelo, String motor) {
        this.marca = marca;
        this.modelo = modelo;
        this.motor = motor;
    }

    /**
     * constructor con todos os parametros, para poder insertar vehiculos
     * existentes nunha lista de vehiculos e despois tratar esos datos
     *
     * @param id
     * @param marca
     * @param modelo
     * @param motor
     */
    public Coches(int id, String marca, String modelo, String motor) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.motor = motor;
    }

    /**
     * metodos de acceso e toString
     *
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    @Override
    public String toString() {
        return "Coches{" + "id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", motor=" + motor + '}';
    }

    /**
     * metodo para almacenar un obxeto de tipo coche na base de datos, sera
     * chamado co coche en cuestion que queremos almacenar
     */
    public void save() {
        Consultas con = new Consultas();
        con.connect();
        con.insertarCoches(this);
        con.close();
    }

}
