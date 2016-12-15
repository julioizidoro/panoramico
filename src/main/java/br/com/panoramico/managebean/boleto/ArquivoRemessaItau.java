/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Proprietario;
import java.io.IOException;


public interface ArquivoRemessaItau {
    
    String gerarHeader(Contasreceber conta, int numeroSequencial, Proprietario proprietario)throws IOException;
    String gerarDetalhe(Contasreceber conta, int numeroSequencial, Proprietario proprietario)throws IOException, Exception;
    String gerarMulta(Contasreceber conta, int numeroSequencial, Proprietario proprietario)throws IOException, Exception;
    String gerarTrailer(int numeroSequencial)throws IOException;
}
