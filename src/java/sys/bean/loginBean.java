/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.bean;



import java.awt.event.ActionEvent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import sys.dao.usuarioDao;
import sys.dao.usuarioDaoImp;
import sys.model.Usuario;

/**
 *
 * @author Amilcar Cortez
 */
@ManagedBean
@SessionScoped
public class loginBean{

    /**
     * Creates a new instance of loginBean
     */
    
    
    private Usuario usuario;
    
    private String nombreUsuario;
    private String password;

    public loginBean() {
        
        this.usuario=new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
       
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void login(ActionEvent event){
        
        RequestContext contex=RequestContext.getCurrentInstance();
        FacesMessage message=null;
        boolean loggedIn=false;
        String ruta="";
        
        usuarioDao uDao=new usuarioDaoImp();
        this.usuario=uDao.login(this.usuario);
        
        if (this.usuario!=null) {
            loggedIn=true;
            message=new FacesMessage(FacesMessage.SEVERITY_INFO,"Bienvenido", this.usuario.getNombreUsuario());
            ruta="/sysFacturacion/faces/Views/bienvenido.xhtml";
        }else{
            loggedIn=false;
             message=new FacesMessage(FacesMessage.SEVERITY_WARN,"Login Error", "Credenciales incorrectas");
             this.usuario=new Usuario();
        }
        
        FacesContext.getCurrentInstance().addMessage(null, message);
        contex.addCallbackParam("loggedIn",loggedIn);
        contex.addCallbackParam("ruta",ruta);
        
        
    }
    
    public String cerrarSession(){
        this.nombreUsuario=null;
        this.password=null;
        
        HttpSession httpSession=(HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        httpSession.invalidate();//para borrar la session
        return "/login";
    }
    
    
}
