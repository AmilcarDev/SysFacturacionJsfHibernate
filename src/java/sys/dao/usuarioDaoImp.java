/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sys.dao;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import sys.clasesAuxiliares.encriptarPassword;
import sys.model.Usuario;
import sys.util.HibernateUtil;

/**
 *
 * @author Amilcar Cortez
 */
public class usuarioDaoImp implements usuarioDao{

    @Override
    public Usuario obtenerDatosPorUsuario(Usuario usuario) {
       Session session= HibernateUtil.getSessionFactory().openSession();
       String hql="FROM Usuario WHERE nombreUsuario=:nombreUsuario";
       Query q=session.createQuery(hql);
       q.setParameter("nombreUsuario", usuario.getNombreUsuario());
       return (Usuario) q.uniqueResult();
    }

    @Override
    public Usuario login(Usuario usuario) {
       Usuario user=(this.obtenerDatosPorUsuario(usuario));
       if(user!=null){
           if(!user.getPassword().equals(encriptarPassword.sha512(usuario.getPassword()) )){
               user=null;
           }
       }
       return user;
    }
    
}
