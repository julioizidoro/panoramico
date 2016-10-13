/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.relatorios;

import java.util.List;

/**
 *
 * @author Kamila
 */
public class RelatorioDataNascimentoFactory {
    
    private static List<RelatorioDataNascimnetoBean> lista;

    public static List<RelatorioDataNascimnetoBean> getLista() {
        return lista;
    }

    public static void setLista(List<RelatorioDataNascimnetoBean> lista) {
        RelatorioDataNascimentoFactory.lista = lista;
    }

    public static List<RelatorioDataNascimnetoBean> listar(){
        return getLista();
    }
    
    
    
}
