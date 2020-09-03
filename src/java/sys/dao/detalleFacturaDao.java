/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.dao;

import org.hibernate.Session;
import sys.model.Detallefactura;

/**
 *
 * @author Amilcar Cortez
 */
public interface detalleFacturaDao {
    
    //metodo para guardar el registro en la tabla detalle factura de la bd
    public boolean gauradarVentaDetalleFactura(Session session, Detallefactura detallefactura)throws Exception;
}
