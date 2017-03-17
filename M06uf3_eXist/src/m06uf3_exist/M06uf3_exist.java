package m06uf3_exist;

import java.util.ArrayList;
import java.util.List;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Node;

public class M06uf3_exist {

    public static void main(String[] args) {

        ConfigConnexio cc = new ConfigConnexio();
        Consultes cs = new Consultes(cc.getCon());

        List<Node> ns = cs.obtenirLlibres();
        for (Node n : ns) {
            System.out.println(n.getTextContent());
        }

    }
}
