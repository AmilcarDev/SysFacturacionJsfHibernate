/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import sys.model.Factura;

/**
 *
 * @author Amilcar Cortez
 */
public class facturaDaoImp implements facturaDao{

    @Override
    public Factura obtenerUltimoRegistro(Session session) throws Exception {
       
        String hql="FROM Factura ORDER BY codFactura DESC";
        Query q=session.createQuery(hql).setMaxResults(1);
        return (Factura) q.uniqueResult();
    }

    @Override
    public Long obtenerTotalRegistroEnFactura(Session session) {
        String hql="SELECT COUNT(*) FROM Factura";
        Query q=session.createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public boolean guardarVentaFactura(Session session, Factura factura) throws Exception {
       session.save(factura);
       return true;
    }
    
}
