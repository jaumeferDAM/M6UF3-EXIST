/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m06uf3_exist;

import java.util.ArrayList;
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
    XQExpression xqe;
    XQPreparedExpression xqpe;

    public Consultes(XQConnection con) {
        this.con = con;
    }
    
    public List<Node> obtenirLlibres() {
        List<Node> plantes = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/m06uf3/libros.xml')//libro return $b/titulo";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantes.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return plantes;
    }
    
    
}
