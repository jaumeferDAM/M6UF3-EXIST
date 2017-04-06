/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m06uf3_exist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Node;

/**
 *
 * @author Jorge
 */
public class Consultes {

    private final XQConnection con;
    private XQExpression xqe;
    private XQPreparedExpression xqpe;

    /*
    * Metodo para eliminar el $ del precio
    */
    public void eliminarDolar() {

        try {
            xqe = con.createExpression();
            for (int i = 0; i < 10; i++) {
                //Devuelve un string con el valor de las letras desde 2-final
                 String xq = "for $b in doc('/Practica6/plantes.xml')//PLANT/PRICE return update value $b with substring($b,2)";
                xqe.executeCommand(xq);
            }

        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Consultes(XQConnection con) {
        this.con = con;
    }

    /*
    * Metodo para traducir las etiquetas
    */
    public void editarEtiqueta(String[] anterior, String[] nuevo) {
        try {
            xqe = con.createExpression();
            /*update rename doc('/prova1/libros.xml')//libro/@anyo as 'anyEdicion'*/
            for (int i = 0; i < anterior.length; i++) {
                String xq = "update rename doc('Practica6/plantes.xml')//PLANT/" + anterior[i] + " as '" + nuevo[i] + "'";
                xqe.executeCommand(xq);
            }

        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
    * Metodo que devuelve un arrayList con las plantas
    */
    public List<Node> obtenirPlantes() {
        List<Node> libros = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/Practica6/plantes.xml')//PLANT return $b/COMMON";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                libros.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return libros;
    }
//

    /*
    * Metodo que devuelve un nodo planta, que tenga el mismo nombre que pasado 
    * por parametro
    */
    public Node cercarNom(String nom) {
        Node planta = null;
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('Practica6/plantes.xml')"
                    + "//PLANT where every $a in $b/COMMON satisfies ($a = '" + nom + "') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            rs.next();
            planta = rs.getItem().getNode();
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return planta;
    }

    /*
    * Metodo que devuelve una lista de plantas que estan dentro de una condicion(zona)
    */
    public ArrayList<Node> cercaPerZona(String zona) {
        ArrayList<Node> plantas = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/Practica6/plantes.xml')//PLANT where every $a in $b/ZONE satisfies($a = '" + zona + "') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantas.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {

            System.out.println(ex.getMessage());
        }
        return plantas;
    }

    /*
    * Metodo que devuelve una lista de plantas que estan dentro de una condicion(precio)
    */
    public ArrayList<Node> CercaPerPreu(String preuMinim, String preuMaxim) {
        Node planta;
        ArrayList<Node> plantas = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('Practica6/plantes.xml')//PLANT where every $a in $b/PRICE satisfies($a >= '" + preuMinim + "' and $a <= '" + preuMaxim + "') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantas.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return plantas;
    }

    /*
    * Metodo que añade una planta en archivo con los datos que llegan por parametro.
    */
    public void afegirPlanta(String nombre, String nombreBotanico, String Zona, String luz, String precio, String disponibilidad) {
        try {

            xqe = con.createExpression();
            String xq = "update insert "
                    + "     <PLANT COMMON='" + nombre + "'>"
                    + "             <BOTANICAL>" + nombreBotanico + "</BOTANICAL>"
                    + "             <ZONE>" + Zona + "</ZONE>"
                    + "             <LIGHT>" + luz + "</LIGHT>"
                    + "             <PRICE>" + precio + "</PRICE>"
                    + "             <AVAILABILITY>" + disponibilidad + "</AVAILABILITY>"
                    + "         </PLANT>\n"
                    + " preceding doc('Practica6/plantes.xml')//PLANT[1]";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
 

    /*
    * Metodo que añade un atributo x con un valor y en las plantas.
    */
    public void afegirAtribut(String atributo, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert attribute " + atributo + " {'" + valor + "'} into doc('Practica6/plantes.xml')//PLANT";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
    * Metodo que añade una etiqueta x con un valor y en las plantas.
    */
    public void afegirEtiqueta(String etiqueta, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert <" + etiqueta + ">'" + valor + "'</" + etiqueta + "> into doc('Practica6/plantes.xml')//PLANT";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /*
    * Metodo para modificar el precio de una planta con un nombre que llega por parametro.
    */
    public void modificarPreuNode(String COMMON, String precio) {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/Practica6/plantes.xml')//PLANT where every $a in $b/COMMON satisfies($a = '" + COMMON + "') return update value $b/PRICE with " + precio;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /*
    * Metodo que elimina una planta con nombre pasado por parametro
    */
    public void eliminarPlanta(String nom){
        
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/Practica6/plantes.xml')//PLANT where every $a in $b/COMMON satisfies($a = '" + nom + "') return update delete $b";            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    /*
    * Metodo que elimina una eitqueta con el nombre igual al pasado por parametro.
    */
    public void eliminarEtiqueta(String etiqueta) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('plantes.xml')//libro/" + etiqueta;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /*
    * Metodo que elimina un atributo con el nombre igual al pasado por parametro.
    */
    void eliminarAtribut(String atributo) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('plantes.xml')//libro/@" + atributo;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
