/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.contaspagar;

import br.com.panoramico.model.Contaspagar;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class CadContasPagarMB implements Serializable{
    
    private Contaspagar contaspagar;
    private List<Contaspagar> listaContasPagar;
}
