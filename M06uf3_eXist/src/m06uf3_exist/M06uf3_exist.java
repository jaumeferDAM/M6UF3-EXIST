package m06uf3_exist;

import java.util.ArrayList;
import java.util.List;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class M06uf3_exist {

    private static String[] AtributosES = {"NOMBRE", "PLANTA", "ZONA", "LUZ",
        "PRECIO", "DISPONIBILITAT"};
    private static String[] AtributosENG = {"COMMON", "BOTANICAL", "ZONE", "LIGHT",
        "PRICE", "AVAILABILITY"};

    public static void main(String[] args) {

        ConfigConnexio cc = new ConfigConnexio();
        Consultes cs = new Consultes(cc.getCon());

        //Lista de nodos donde guardaremos las plantas
        List<Node> Plantas = cs.obtenirPlantes();
        for (Node Planta : Plantas) {
            System.out.println(Planta.getTextContent());
        }

        imprimirPlantas(Plantas);
        //Traducir etiquetas
        cs.editarEtiqueta(AtributosENG, AtributosES);
        //Eliminar el dolar del precio
        cs.eliminarDolar();
        //Busqueda de planta por nombre y la imprime
        String Planta = cs.cercarNom("Bloodroot").getTextContent();
        System.out.println(Planta);
        //Añadimos una planta a la base de datos.
        cs.afegirPlanta("PRUEBA", "1", "HOLA", "MUNDO", "20", "2");

        //Añadimos un atributo a las plantas
        cs.afegirAtribut("prueba", "123");
        //Añadimos una etiqueta a las plantas.
        cs.afegirEtiqueta("Color", "verde");
        //Modificamos el precio de una plantañ
        cs.modificarPreuNode("1", "63");
        //Eliminar una etiqueta de las plantas
        cs.eliminarEtiqueta("Color");
        //Eliminar un atributo de las plantas
        cs.eliminarAtribut("prueba");
        //Eliminar una planta por el codigo
        cs.eliminarPlanta("2");
        //Buscamos plantas entre un rango de precio
        ArrayList<Node> Planta2 = cs.CercaPerPreu("1", "3");
        for (Node node : Planta2) {
            System.out.println(node.getTextContent());
        }

        //Buscamos plantas en una zona.
        System.out.println("ZONA");
        ArrayList<Node> PlantaZona = cs.cercaPerZona("4");
        for (Node node : PlantaZona) {
            System.out.println(node.getTextContent());
        }
    }
    //update rename doc/plantes.xml

    public static void imprimirPlantas(List<Node> ns) {
        for (Node n : ns) {
            System.out.println(n.getTextContent());
        }
    }

    private static void imprimirPlanta(Node libro) {
        if (libro != null) {
            NamedNodeMap attributes = libro.getAttributes();

            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println(attributes.item(i).getNodeValue());
            }

            System.out.println(libro.getTextContent());
        }
    }
}
