/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collections;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

/**
 *
 * @author ALUMNEDAM
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, XMLDBException {
        Consultes cs = null;
        try {
            cs = new Consultes("ficherito.xml");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        m06uf3_exist.ConfigConnexio cc = new m06uf3_exist.ConfigConnexio();
        cs.conectar();

        //Obtenemos el directorio actual y lo muestra por pantalla
//        System.out.println(cs.obtenirColeccioActual());
        //Obtenemos el directorio padre y lo muestra por pantalla
//        System.out.println(cs.obtenirColeccioPare());
        //Obtenemos la/s coleccion/es hijos y lo/s muestra por pantalla
//        System.out.println(Arrays.toString(cs.obtenirCollecionsFill()));
        //Añade una coleccion con el nombre como parametro
//        cs.crearCollecio("Prueba1234");
        //Elimina una coleccion con el nombre como parametro
//        cs.eliminarCollecions("Prueba1234");
        //Comprueba si una collection tiene x recurso
//        cs.CollecionTeRecurs("/Practica6", "plantes.xml");
        //Afegir un recurs a la base de dades
        cs.AfegirRecurs("FicheroPrueba");
        //Obtenir recurs XML emmagatzemat a la base de dades en un DOM
        XMLResource ObtenirRecurs = cs.ObtenirRecurs("FicheroPrueba");
        System.out.println("Se ha obtenido: " + ObtenirRecurs.getDocumentId());
        cs.eliminarRecurs("FicheroPrueba");
    }

}
