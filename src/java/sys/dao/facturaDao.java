 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.dao;

import org.hibernate.Session;
import sys.model.Factura;

/**
 *
 * @author Amilcar Cortez
 */
public interface facturaDao {
    
    //obtener el ultimo registro de la tabla factura en la bd
    public Factura obtenerUltimoRegistro(Session session)throws Exception;
    
    //averuguar si la tabala factura tiene registros
    public Long obtenerTotalRegistroEnFactura(Session session);
    
    //metodo para guardar el registro en la tabla factura de la bd
    public boolean guardarVentaFactura(Session session, Factura factura)throws Exception;
}
