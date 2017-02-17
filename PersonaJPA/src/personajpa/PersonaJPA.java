/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package personajpa;

import controlador.*;
import modelo.*;

/**
 *
 * @author Jorge
 */
public class PersonaJPA {

    public static void main(String[] args) {
        try {

            // Crea una nova persona
            Persona persona1 = new Persona();
            persona1.setNombre("Jorge");
            persona1.setApellidos("Rubio");
            persona1.setEmail("jorge@rubio.net");
            persona1.setTelefono("987654321");

            Persona persona2 = new Persona();
            persona2.setNombre("Emilio");
            persona2.setApellidos("Garcia");
            persona2.setEmail("emilio@garcia.net");
            persona2.setTelefono("876543219");

            Direccio direccio1 = new Direccio();
            direccio1.setCarrer("Carrer1");
            direccio1.setCiutat("Montcada");
            direccio1.setCp("21345");
            direccio1.setPais("Espanya");

            Direccio direccio2 = new Direccio();
            direccio2.setCarrer("Carrer2");
            direccio2.setCiutat("Reixac");
            direccio2.setCp("21346");
            direccio2.setPais("Espanya");

            persona1.setDireccio(direccio1);
            persona2.setDireccio(direccio2);
            
            Pesona_Controller pc = new Pesona_Controller();
            
            pc.Insetar(persona1);
            pc.Insetar(persona2);
            
            pc.Consulta();        

            System.out.println("FI");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
