/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collections;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import m06uf3_exist.Put;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.BinaryResource;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

/**
 *
 * @author ALUMNEDAM
 */
public class Consultes {

    //Database a la que queremos conectar.
    Database dbDriver;
    //Colecciones en la base de datos.
    Collection coll;
    //Obtenir la llista de serveis
    CollectionManagementService cms;
    //Array de Service donde recogeremos los servicios
    Service[] serveis;

    String file = "ficherito.xml";
    //DocumentBuilder i doc para leer el archivo que subiremos.
    DocumentBuilder documentBuilder;
    Document doc;
    Transformer trns;
    StreamResult result;
    DOMSource source;
    String rutaDocument;

    //Constructor.
    public Consultes(String rutaDocument) throws ParserConfigurationException, SAXException, IOException {
        this.rutaDocument = rutaDocument;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbf.newDocumentBuilder();
        doc = dBuilder.parse(file);
    }

    public Consultes() {
    }

    /*
     * Metodo para conectar a la base de datos e instanciar los objetos.
     */
    public Collection conectar() {
        try {
            dbDriver = (Database) Class.forName("org.exist.xmldb.DatabaseImpl").newInstance();
            DatabaseManager.registerDatabase(dbDriver);
            coll = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db", "admin", "admin");
            //Metodo para instanciar el cms
            InicializarCollectionManagement();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLDBException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coll;
    }

    /*
     * Obtenemos el nombre de la coleccion actual y lo guardamos en collection.
     */
    public String obtenirColeccioActual() throws XMLDBException {
        String collection = coll.getName();
        return collection;
    }

    /*
     * Obtenemos la colecció padre que tiene esta actualmente.
     */
    public Collection obtenirColeccioPare() throws XMLDBException {
        Collection parentCollection = coll.getParentCollection();
        return parentCollection;
    }

    /*
     * Obtenemos la lista de nombre de colecciones hijo que la coleccion actualmente.
     */
    public String[] obtenirCollecionsFill() throws XMLDBException {
        String[] listChildCollections = coll.listChildCollections();
        return listChildCollections;
    }

    /*
    * Creamos una coleccion con el nombre que nos llega por parametro.
     */
    public void crearCollecio(String nom) throws XMLDBException {
        cms.createCollection(nom);
    }

    public void eliminarCollecions(String nom) {
        try {
            cms.removeCollection(nom);
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    * Metodo para instaciar el objeto cms con los servicios.
     */
    public void InicializarCollectionManagement() {
        try {
            serveis = coll.getServices();
            for (Service servei : serveis) {
                if (servei.getName().equals("CollectionManagementService")) {
                    cms = (CollectionManagementService) servei;
                }
            }
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*
    * Hacemos una consulta sobre la base de datos y si el nombre que llega por 
    *  parametro no esta vacio devuelve la coleccion que cumpla ese nombre. 
     */
    public void CollecionTeRecurs(String coleccio, String nom) throws XMLDBException {
        if (coleccio.length() != 0) {
            coll = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/" + coleccio, "admin", "admin");
        }
        System.out.println("Trobat el recurs: " + coll.getResource(nom).getId());
    }

    /*
    * Metodo que recibe un string con el nombre y instancia un objeto XMLResource
    * que subiremos a la base de datos.
     */
    public void AfegirRecurs(String args) throws XMLDBException {
        //Crear el objeto vacio
        XMLResource xMLResource = null;
        //Igualar el objeto a la coleccion que queremos crear con el nombre y tipo especificado.
        xMLResource = (XMLResource) coll.createResource(args, XMLResource.RESOURCE_TYPE);
        //Llenamos el resource con el documento local.
        xMLResource.setContentAsDOM(doc);
        //Guardamos en la base de datos con storeResource
        coll.storeResource(xMLResource);
        System.out.println("Añadido");
    }

    /*
    * Metodo para obtener un recurso con un nombre que pasamos por parametro.
     */
    public XMLResource ObtenirRecurs(String nombreFichero) {
        XMLResource xMLResource = null;
        try {
            xMLResource = (XMLResource) coll.getResource(nombreFichero);
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xMLResource;
    }

    /*
    * Metodo para eliminar un recurso con el nombre que pasamos por parametro
     */
    void eliminarRecurs(String ficheroPrueba) {
        try {
            //Cremos un xmlResource y lo obtenemos, entonces le pasamos ese xmlresource a el metodo removeResource(r);
            XMLResource xMLResource = null;
            xMLResource = (XMLResource) coll.getResource(ficheroPrueba);
            coll.removeResource(xMLResource);
            System.out.println("Eliminado");
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    * Añadimos un fichero binario con la ruta que pasamos por parametro
     */
    void afegirFitxerBinario(String ruta) {
        try {
            /*Creamos un fichero vacio y lo igualamos al resource que nos 
            devuelve el collection, casteado a fichero binario, con el nombre
            y el tipo de fichero.*/
            BinaryResource binaryResource = null;
            binaryResource = (BinaryResource) coll.createResource(ruta, BinaryResource.RESOURCE_TYPE);
            //Creamos un fichero con la ruta para guardar la imagen.
            File imatge = new File(ruta);
            //Llenamos el contenido del fichero.
            binaryResource.setContent(imatge);
            //Y lo guardamos en la base de datos.
            coll.storeResource(binaryResource);
            System.out.println("Añadido");

        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    * Metodo para descargar un fichero binario.
     */
    void descarregaFitxer(String ruta) throws IOException {
        try {
            //Creamos el fichero donde guardaremos el contenido.
            BinaryResource binaryResource = null;
            //Inicializamos un array de bytes para recoger el contenido del recurso(bites).
            byte[] source = (byte[]) coll.getResource(ruta).getContent();
            File imatge;
            //Creamos un path para crear el archivo.
            Path p = Paths.get(ruta);
            //Escribimos el fichero convertido en la ruta p, con el array de bytes.
            Files.write(p, source);
            System.out.println("Descargado");
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
