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

    public static void main(String[] args) {

        ConfigConnexio cc = new ConfigConnexio();
        Consultes cs = new Consultes(cc.getCon());

        String codigo = "16-205";
        String categoria = "BBDD";
        String fecha_pub = "2017-03-19";
        String titulo = "BBDD XML con eXist";
        String ventas = "7";
        String codigo2 = "16-041";
        String precio = "50€";

        //cs.afegirNode(codigo, categoria, fecha_pub, titulo, ventas);
        
        //cs.afegirDisponibilitat();
        
        //cs.afegirPreu();
        
        cs.modificarPreuNode(codigo2, precio);
        
        //LLISTAR LLIBRES
//        List<Node> ns = cs.obtenirLlibres();
//        for (Node n : ns) {
//            System.out.println(n.getTextContent());
//        }
        
        //CERCAR PER TITOL
        Node libro = cs.cercarNom("instant html");
        if (libro != null) {
            NamedNodeMap attributes = libro.getAttributes();
            
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println(attributes.item(i).getNodeValue());
            }            
            System.out.println(libro.getTextContent());
        }

    }
}
