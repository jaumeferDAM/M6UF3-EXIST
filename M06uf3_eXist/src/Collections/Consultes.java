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

    Database dbDriver;
    Collection coll;
    CollectionManagementService cms;
    Service[] serveis;

    //DocumentBuilder,etc
    String file = "ficherito.xml";
    DocumentBuilder documentBuilder;
    Document doc;
    Transformer trns;
    StreamResult result;
    DOMSource source;
    String rutaDocument;

    public Consultes(String rutaDocument) throws ParserConfigurationException, SAXException, IOException {
        this.rutaDocument = rutaDocument;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbf.newDocumentBuilder();
        doc = dBuilder.parse(file);
    }

    public Consultes() {
    }

    public Collection conectar() {
        try {
            dbDriver = (Database) Class.forName("org.exist.xmldb.DatabaseImpl").newInstance();
            DatabaseManager.registerDatabase(dbDriver);
            coll = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db", "admin", "admin");
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

    public String obtenirColeccioActual() throws XMLDBException {
        String collection = coll.getName();
        return collection;
    }

    public Collection obtenirColeccioPare() throws XMLDBException {
        Collection parentCollection = coll.getParentCollection();
        return parentCollection;
    }

    public String[] obtenirCollecionsFill() throws XMLDBException {
        String[] listChildCollections = coll.listChildCollections();
        return listChildCollections;
    }

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

    public void CollecionTeRecurs(String coleccio, String nom) throws XMLDBException {
        if (coleccio.length() != 0) {
            coll = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/" + coleccio, "admin", "admin");
        }
        System.out.println("Trobat el recurs: " + coll.getResource(nom).getId());
    }

    public void AfegirRecurs(String args) throws XMLDBException {
        //pruebadepush
        XMLResource xMLResource = null;
        xMLResource = (XMLResource) coll.createResource(args, XMLResource.RESOURCE_TYPE);
        xMLResource.setContentAsDOM(doc);
        coll.storeResource(xMLResource);
        System.out.println("Añadido");
//        xMLResource = (XMLResource) coll.getResource(args);
    }

    public void DocumentBuilderFactor() {

    }

    public XMLResource ObtenirRecurs(String nombreFichero) {
        XMLResource xMLResource = null;
        try {
            xMLResource = (XMLResource) coll.getResource(nombreFichero);
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return xMLResource;
    }

    void eliminarRecurs(String ficheroPrueba) {
        try {
            XMLResource xMLResource = null;
            xMLResource = (XMLResource) coll.getResource(ficheroPrueba);
            coll.removeResource(xMLResource);
            System.out.println("Eliminado");
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void afegirFitxerBinario(String ruta) {
        try {
            BinaryResource binaryResource = null;
            binaryResource = (BinaryResource) coll.createResource(ruta, BinaryResource.RESOURCE_TYPE);
            File imatge = new File(ruta);
            binaryResource.setContent(imatge);
            coll.storeResource(binaryResource);
            System.out.println("Añadido");

        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void descarregaFitxer(String ruta) throws IOException {
        try {
            BinaryResource binaryResource = null;
            byte[] source = (byte[]) coll.getResource(ruta).getContent();
//            binaryResource = (BinaryResource) coll.createResource(ruta, BinaryResource.RESOURCE_TYPE);
            File imatge;
            Path p = Paths.get(ruta);
            Files.write(p, source);
            System.out.println("Descargado");
        } catch (XMLDBException ex) {
            Logger.getLogger(Consultes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
