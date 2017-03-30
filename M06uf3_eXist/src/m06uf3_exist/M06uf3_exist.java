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

    public static void main(String[] args) {

        ConfigConnexio cc = new ConfigConnexio();
        Consultes cs = new Consultes(cc.getCon());

        List<Node> Plantas = cs.obtenirPlantes();
        for (Node Planta : Plantas) {
            System.out.println(Planta.getTextContent());
        }
//        System.out.println(Plantas);

//        imprimirPlantas(Plantas);
//        cs.editarEtiqueta(AtributosENG, AtributosES);
//        cs.eliminarDolar();
        String Planta = cs.cercarNom("Bloodroot").getTextContent();
        System.out.println(Planta);
//                cs.afegirPlanta("PRUEBA", "PRUEBA", "PRUEBA", "PRUEBA", "PRUEBA", "PRUEBA");
//        //Llibre
//        String codigo = "16-205";
//        String categoria = "BBDD";
//        String fecha_pub = "2017-03-19";
//        String titulo = "BBDD XML con eXist";
//        String ventas = "7";
//
//        String codigo2 = "16-041";
//        String precio = "50€";
//        String etiqueta = "preu";
//        String atributo = "disponible";
//        String valor = "S";
//        String valor1 = "0€";
//
////        cs.afegirPlanta(codigo, categoria, fecha_pub, titulo, ventas);
////        cs.afegirAtribut(atributo, valor);
////
////        cs.afegirEtiqueta(etiqueta, valor1);
////        cs.modificarPreuNode(codigo2, precio);
//        
//        //LLISTAR LLIBRES
//        //imprimirLibros(cs.obtenirPlantes());
//
//        //CERCAR PER TITOL
//        imprimirPlanta(cs.cercarNom("instant html"));
//
////        cs.eliminarEtiqueta(etiqueta);
////        cs.eliminarAtribut(atributo);
//        cs.eliminarPlanta(codigo);
//        imprimirPlantas(cs.obtenirPlantes());
//        cs.eliminarDolar();
//        cs.afegirPlanta("Planta1", "NombrePlanta1", "Zona1", "Luz1", "Precio1", "Disponibilidad1");
//        cs.afegirAtribut("Hoja", "Perenne");
//        cs.afegirEtiqueta("Vida", "1");
        System.out.println("PREU");
        ArrayList<Node> Planta2 = cs.CercaPerPreu("1", "3");
        for (Node node : Planta2) {
            System.out.println(node.getTextContent());
        }
        System.out.println("ZONA");
        ArrayList<Node> PlantaZona = cs.cercaPerZona("4");
        for (Node node : PlantaZona) {
            System.out.println(node.getTextContent());
        }
        System.out.println("ModificarPreu");
        cs.modificarPreuNode("Mayapple", "8.99");
        System.out.println("EliminarPlanta");
        cs.eliminarPlanta("Bloodroot");
    }
}
