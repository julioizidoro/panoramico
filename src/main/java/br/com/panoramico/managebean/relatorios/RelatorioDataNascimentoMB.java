/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.relatorios;

import br.com.panoramico.dao.ClienteDao;
import br.com.panoramico.model.Cliente;
import br.com.panoramico.uil.Formatacao;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class RelatorioDataNascimentoMB {
    
    @EJB
    private ClienteDao clienteDao;
    private String mes;
    private String tipo;  
    private RelatorioDataNascimentoFactory relatorioData;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
 
    public void iniciarConsulta(){
        relatorioData = new RelatorioDataNascimentoFactory();
        relatorioData.setLista(new ArrayList<RelatorioDataNascimnetoBean>());
        String sqlCliente="";
        if(tipo.equalsIgnoreCase("Passaporte")){
            if(mes.equalsIgnoreCase("todos")){
                sqlCliente = "SELECT c Cliente FROM c order by c.datanascimento, c.nome";
            }else {
                sqlCliente = "SELECT c Cliente FROM c where MONTH(c.datanascimento) order by c.datanascimento, c.nome";
            }
        }
        List<Cliente> listaCliente = clienteDao.list(sqlCliente);
        if (listaCliente!=null){
            for(int i=0;i<listaCliente.size();i++){
                RelatorioDataNascimnetoBean relatorio = new RelatorioDataNascimnetoBean();
                relatorio.setDatanascimento(Formatacao.ConvercaoDataPadrao(listaCliente.get(i).getDatanascimento()));
            }
        }
    }
    
    
    
    
    
    
}
