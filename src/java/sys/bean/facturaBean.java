/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import sys.dao.clienteDao;
import sys.dao.clienteDaoImp;
import sys.dao.detalleFacturaDao;
import sys.dao.detalleFacturaDaoImp;
import sys.dao.facturaDao;
import sys.dao.facturaDaoImp;
import sys.dao.productoDao;
import sys.dao.productoDaoImp;
import sys.model.Cliente;
import sys.model.Detallefactura;
import sys.model.Factura;
import sys.model.Producto;
import sys.model.Vendedor;
import sys.util.HibernateUtil;

/**
 *
 * @author Amilcar Cortez
 */
@ManagedBean
@ViewScoped
public class facturaBean {

    /**
     * Creates a new instance of facturaBean
     */
    Session session = null;
    Transaction transaction = null;

    private Cliente cliente;
    private Integer codCliente;

    private Producto producto;
    private String codigoBarra;
    private List<Detallefactura> listaDetalleFactura;

    private String cantidadProducto;
    private String productoSeleccionado;
    private Factura factura;

    private String cantidadProducto2;
    
    private Long numeroFactura;
    
    private BigDecimal totalVentaFactura;
    
    private Vendedor vendedor;
    
    //recuperar fecha del sistema
    private String fechaSistema;

    public facturaBean() {
        this.factura = new Factura();
        this.listaDetalleFactura = new ArrayList<>();
        this.vendedor=new Vendedor();
        this.cliente=new Cliente();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Integer codCliente) {
        this.codCliente = codCliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(String codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public List<Detallefactura> getListaDetalleFactura() {
        return listaDetalleFactura;
    }

    public void setListaDetalleFactura(List<Detallefactura> listaDetalleFactura) {
        this.listaDetalleFactura = listaDetalleFactura;
    }

    public String getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(String cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public String getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(String productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getCantidadProducto2() {
        return cantidadProducto2;
    }

    public void setCantidadProducto2(String cantidadProducto2) {
        this.cantidadProducto2 = cantidadProducto2;
    }

    public Long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public BigDecimal getTotalVentaFactura() {
        return totalVentaFactura;
    }

    public void setTotalVentaFactura(BigDecimal totalVentaFactura) {
        this.totalVentaFactura = totalVentaFactura;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public String getFechaSistema() {
        Calendar fecha=new GregorianCalendar();
        
        int anio=fecha.get(Calendar.YEAR);
        int mes=fecha.get(Calendar.MONTH);
        int dia=fecha.get(Calendar.DAY_OF_MONTH);
        
        this.fechaSistema=(dia+"/"+(mes+1)+"/"+anio);
        
        return fechaSistema;
    }
    
    
    
    
    
    
    
    //metodo para agregar los datos de los clientes por medio del dialogClientes
    public void agregarDatosCliente(Integer codCliente) {
        this.session = null;
        this.transaction = null;

        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            clienteDao cDao = new clienteDaoImp();
            this.transaction = this.session.beginTransaction();
            //obtener los datos en la variable objeto cliente segun el codigo del cliente

            this.cliente = cDao.obtenerClientePorCodigo(this.session, codCliente);
            this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del cliente agregados"));

        } catch (Exception e) {
            if (this.transaction != null) {
                System.err.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    //metodo para agregar los datos del cliente por codigo
    public void agregarDatosCliente2() {
        this.session = null;
        this.transaction = null;

        try {
            try {
                if (this.codCliente == null) {
                    return;
                }
            } catch (Exception e) {
            }
            this.session = HibernateUtil.getSessionFactory().openSession();
            clienteDao cDao = new clienteDaoImp();
            this.transaction = this.session.beginTransaction();
            //obtener los datos en la variable objeto cliente segun el codigo del cliente

            this.cliente = cDao.obtenerClientePorCodigo(this.session, codCliente);
            if (this.cliente != null) {
                this.codCliente = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Datos del cliente agregados"));
            } else {
                this.codCliente = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cliente no encontrado"));
            }

            this.transaction.commit();

        } catch (Exception e) {
            if (this.transaction != null) {
                System.err.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    //metodo para solicitar la cantidad de producto a vender
    public void pedirCantidadProducto(String codBarra) {
        this.productoSeleccionado = codBarra;
    }

    //metodo para agregar los datos del producto por medio del dialog producto
    public void agregarDatosProducto() {
        this.session = null;
        this.transaction = null;

        try {
            if (!(this.cantidadProducto.matches("[0-9]*")) || this.cantidadProducto.equals("0") || this.cantidadProducto.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
                this.cantidadProducto = "";
            } else {
                this.session = HibernateUtil.getSessionFactory().openSession();
                productoDao pDao = new productoDaoImp();
                this.transaction = this.session.beginTransaction();
                //obtener los datos en la variable objeto producto segun el codigo de barra (codBar)

                this.producto = pDao.obtenerProductoPorCodBarra(this.session, this.productoSeleccionado);
                //ahoara agregamos a un arraylist los productos para que se muestren en la tabla
                this.listaDetalleFactura.add(new Detallefactura(null, null, this.producto.getCodBarra(),
                        this.producto.getNombreProducto(), Integer.parseInt(this.cantidadProducto),
                        this.producto.getPrecioVenta(),
                        BigDecimal.valueOf(Integer.parseInt(this.cantidadProducto) * this.producto.getPrecioVenta().floatValue())));
                this.transaction.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Producto agregado al detalle"));
                this.cantidadProducto = "";
//llamada al metodo calcular totalFacturaVenta
                this.totalFacturaVenta();
            }

        } catch (Exception e) {
            if (this.transaction != null) {
                System.err.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }

    //metodo para mostrar el dialog cantidad producto2
    public void mostrarCantidadProducto2() {
        this.session = null;
        this.transaction = null;

        try {
            try {
                if (this.codigoBarra == null) {
                    return;
                }
            } catch (Exception e) {
            }
            this.session = HibernateUtil.getSessionFactory().openSession();
            productoDao pDao = new productoDaoImp();
            this.transaction = this.session.beginTransaction();
            //obtener los datos en la variable objeto producto segun el codigo de Barra

            this.producto = pDao.obtenerProductoPorCodBarra(this.session, this.codigoBarra);
            if (this.producto != null) {
                //solicitar mostrar el dialogCantidadProducto2
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('dialogCantidadProducto2').show();");
                this.codigoBarra = null;

            } else {
                this.codigoBarra = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "producto no encontrado"));
            }

            this.transaction.commit();

        } catch (Exception e) {
            if (this.transaction != null) {
                System.err.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }

    }

    //metodo para agregar los datos del producto por codBarra
    public void agregarDatosProducto2() {
        if (!(this.cantidadProducto2.matches("[0-9]*")) || this.cantidadProducto2.equals("0") || this.cantidadProducto2.equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cantidad es incorrecta"));
            this.cantidadProducto2 = "";
        } else {
            this.listaDetalleFactura.add(new Detallefactura(null, null, this.producto.getCodBarra(),
                    this.producto.getNombreProducto(), Integer.parseInt(this.cantidadProducto2),
                    this.producto.getPrecioVenta(),
                    BigDecimal.valueOf(Integer.parseInt(this.cantidadProducto2) * this.producto.getPrecioVenta().floatValue())));
            this.cantidadProducto2 = "";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "producto agregado al detalle"));

            //llamada al metodo calcular totalFactura
            this.totalFacturaVenta();
        }
    }

    //metodo para obtener total a vender en la factura
    public void totalFacturaVenta() {
        this.totalVentaFactura = new BigDecimal("0");
        try {
            for (Detallefactura item : listaDetalleFactura) {
                BigDecimal totalVentaPorProducto = item.getPrecioVenta().multiply(new BigDecimal(item.getCantidad()));
                item.setTotal(totalVentaPorProducto);
                totalVentaFactura = totalVentaFactura.add(totalVentaPorProducto);
            }
            this.factura.setTotalVenta(totalVentaFactura);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //metodo para quitar producto de la factura
    public void quitarProductoDetalleFactura(String codBarra, Integer filaSeleccionada) {

        try {
            int i = 0;
            for (Detallefactura item : this.listaDetalleFactura) {
                if (item.getCodBarra().equals(codBarra) && filaSeleccionada.equals(i)) {
                    this.listaDetalleFactura.remove(i);
                    break;
                }
                i++;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Información", "Se retiró el producto de la factura"));
            //despues de retirar el producto se recalcula el total
            this.totalFacturaVenta();

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", "no se retiro el producto"));

        }
    }

    //metodos para editar cantidad en tabla productos factura
    public void onRowEdit(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "se modifico la cantidad"));
        //despues de retirar el producto se recalcula el total
            this.totalFacturaVenta();
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion", "no se realizo ningun cambio"));

    }
    
    //metodo para generar el numero de factura
    
    public void numeracionFactura(){
        
        this.session=null;
        this.transaction=null;
        
        try {
            this.session=HibernateUtil.getSessionFactory().openSession();
            this.transaction=this.session.beginTransaction();
            facturaDao fDao=new facturaDaoImp();
            //verificar si hay registros en la tabala factura de la bd
            this.numeroFactura=fDao.obtenerTotalRegistroEnFactura(this.session);
            if (this.numeroFactura<=0 || this.numeroFactura==null) {
                this.numeroFactura=Long.valueOf("1");
            }else{
                //recuperamos el ultimo registro que exista en la tabla factura de la DB
                this.factura=fDao.obtenerUltimoRegistro(this.session);
                this.numeroFactura=Long.valueOf(this.factura.getNumeroFactura()+1);
                //limpiar totalventafactura
                   this.totalVentaFactura=new BigDecimal("0");
                   
            }
            transaction.commit();
        } catch (Exception e) {
            if (this.transaction != null) {
                System.err.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
    
    //metodo cancelar (limpia el form factura)
    
    public void limpiarFactura(){
        
        this.cliente=new Cliente();
        this.factura=new Factura();
        this.listaDetalleFactura=new ArrayList<>();
        this.numeroFactura=null;
        this.totalVentaFactura=null;   
        this.disableButton();
    }
    
    //metodo para guardar venta
    
    public void guardarVenta(){
        
        this.session=null;
        this.transaction=null;
        
        this.vendedor.setCodVendedor(2);
        try {
            this.session=HibernateUtil.getSessionFactory().openSession();
            productoDao pDao=new productoDaoImp();
            facturaDao fDao=new facturaDaoImp();
            detalleFacturaDao dFDao=new detalleFacturaDaoImp();
            this.transaction=session.beginTransaction();
            
            //datos para guardar en la tabla factura de la bd
            this.factura.setNumeroFactura(this.numeroFactura.intValue());
            this.factura.setCliente(this.cliente);
            this.factura.setVendedor(vendedor);
            
            //hacemos el insert en la tabla factura de la BD            
            fDao.guardarVentaFactura(this.session, this.factura);
            
            //recuperar el ultimo registro d ela tabla factura            
             this.factura= fDao.obtenerUltimoRegistro(this.session);
             
             //recorremos el arraylist para guardar cada registro en la bd
             
             for (Detallefactura item : listaDetalleFactura) {
                this.producto= pDao.obtenerProductoPorCodBarra(this.session, item.getCodBarra());
                item.setFactura(this.factura);
                item.setProducto(this.producto);
                
                //hacemos el insert en la tabla detallefactura
                dFDao.gauradarVentaDetalleFactura(this.session, item);
            }
             this.transaction.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Venta Registrada"));
            this.limpiarFactura();
        }catch (Exception e) {
            if (this.transaction != null) {
                System.err.println(e.getMessage());
                transaction.rollback();
            }
        } finally {
            if (this.session != null) {
                this.session.close();
            }
        }
    }
    
    //metodos para activar/desactivar controles en la factura
    private boolean enabled;
    
    public boolean isEnabled(){
        
        return enabled;
    }
    
    public void enableButton(){
        enabled=true;
    }
    
    public void disableButton(){
        enabled=false;
    }
    
    
    

}
