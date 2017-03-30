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

    /*Modificar el format dels preus traient-li el caràcter del $.
        ◦Obtenir un llistat de  totes les plantes.*/
    //    public void modificarPreuNode(String codigo, String precio) {
//        try {
//            xqe = con.createExpression();
//            String xq = "update value doc('plantes.xml')//libro[@codigo='" + codigo + "']/preu with '" + precio + "'";
//            xqe.executeCommand(xq);
//        } catch (XQException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
    public void eliminarDolar() {

        try {
            xqe = con.createExpression();
            /*update rename doc('/prova1/libros.xml')//libro/@anyo as 'anyEdicion'*/
            for (int i = 0; i < 10; i++) {
                /*update  value doc('/prova1/libros.xml')//libro/@anyEdicio with '2010*/
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

//            xqe = con.createExpression();
//            String xq = "update insert "
//                    + "    <libro codigo='" + codigo + "'>"
//                    + "        <categoria>" + categoria + "</categoria>"
//                    + "        <fecha_pub>" + fecha_pub + "</fecha_pub>"
//                    + "        <titulo>" + titulo + "</titulo>"
//                    + "        <ventas>" + ventas + "</ventas>"
//                    + "    </libro>\n"
//                    + "into doc('plantes.xml')/listadelibros";
//
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
//    

    public void afegirAtribut(String atributo, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert attribute " + atributo + " {'" + valor + "'} into doc('Practica6/plantes.xml')//PLANT";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
//    

    public void afegirEtiqueta(String etiqueta, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert <" + etiqueta + ">'" + valor + "'</" + etiqueta + "> into doc('Practica6/plantes.xml')//PLANT";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void modificarPreuNode(String COMMON, String precio) {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/Practica6/plantes.xml')//PLANT where every $a in $b/COMMON satisfies($a = '" + COMMON + "') return update value $b/PRICE with " + precio;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void eliminarPlanta(String nom){
        
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/Practica6/plantes.xml')//PLANT where every $a in $b/COMMON satisfies($a = '" + nom + "') return update delete $b";            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    

    public void eliminarEtiqueta(String etiqueta) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('plantes.xml')//libro/" + etiqueta;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

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
