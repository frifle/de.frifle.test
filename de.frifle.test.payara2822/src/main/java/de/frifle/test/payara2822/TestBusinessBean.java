package de.frifle.test.payara2822;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Named
public class TestBusinessBean {

    @PersistenceContext( unitName="testPU")
    private EntityManager em;

    public String testAction() {
        return em.createNativeQuery("select count(1) from cat")
                 .getSingleResult()
                 .toString();
    }

//    @Resource( name="jdbc/myTestDS" )
//    private DataSource ds;
//
//    public String testAction() {
//        try( Connection con = ds.getConnection();
//             Statement stmnt = con.createStatement();
//             ResultSet rs = stmnt.executeQuery("select count(1) from cat");
//           ) {
//            return rs.next() ? rs.getString(1) : "no result";
//        } catch (SQLException e) {
//            return e.getMessage();
//        }
//    }
}
