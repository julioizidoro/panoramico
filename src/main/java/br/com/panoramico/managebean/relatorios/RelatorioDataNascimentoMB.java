/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.model.Cliente;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author Kamila
 */
public class RelatorioDataNascimentoMB {
    
    @EJB
    private ClienteDao clienteDao;
    private String mes;
    private boolean selassociado;
    private boolean seldependente;
    private boolean selpassaporte;
    

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public boolean isSelassociado() {
        return selassociado;
    }

    public void setSelassociado(boolean selassociado) {
        this.selassociado = selassociado;
    }

    public boolean isSeldependente() {
        return seldependente;
    }

    public void setSeldependente(boolean seldependente) {
        this.seldependente = seldependente;
    }

    public boolean isSelpassaporte() {
        return selpassaporte;
    }

    public void setSelpassaporte(boolean selpassaporte) {
        this.selpassaporte = selpassaporte;
    }
    
    public void iniciarConsulta(){
        String sqlCliente="";
        if(mes.equalsIgnoreCase("todos")){
            sqlCliente = "SELECT c Cliente FROM c order by c.datanascimento, c.nome";
        }else {
            sqlCliente = "SELECT c Cliente FROM c where MONTH(c.datanascimento order by c.datanascimento, c.nome";
        }
        List<Cliente> listaCliente = clienteDao.list(sqlCliente);
    }
    
    
    
    
    
    
}
