/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.managebean.boleto.ArquivoRemessaEnviar;
import br.com.panoramico.managebean.boleto.ArquivoRemessaCancelar;
import br.com.panoramico.managebean.boleto.ArquivoRemessaAtualizar;
import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.uil.Formatacao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;


public class GerarArquivoRemessaItau {
    
    private List<Contasreceber> listaContas;
    private FileWriter remessa;
    private UsuarioLogadoMB usuarioLogadoMB;
    private int numeroSequencial=0;
    @EJB
    private ContasReceberDao contasReceberDao;

    public GerarArquivoRemessaItau(List<Contasreceber> lista, UsuarioLogadoMB usuarioLogadoMB) {
        this.listaContas = lista;
        this.usuarioLogadoMB = usuarioLogadoMB;
        iniciarRemessa();
    }
    
    private void iniciarRemessa(){
        if (listaContas==null){
            String sql = "Select c from Contasreceber c";
            listaContas = contasReceberDao.list(sql);
        }
        if (listaContas!=null){
            String nomeArquivo =  gerarNomeArquivo();
            //usuarioLogadoMB.getUsuario().getLocalsalvar() + "\\" +
            try {
                remessa = new FileWriter(new File(nomeArquivo));
                try {
                    lerConta();
                } catch (Exception ex) {
                    Logger.getLogger(ArquivoRemessaEnviar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(ArquivoRemessaEnviar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String gerarNomeArquivo(){
        String data = Formatacao.ConvercaoDataPadrao(new Date());
        data = data.substring(6, 10) + data.substring(3, 5) + data.substring(0, 2);
        data = data + ".REM";
        return data;
    }
    
    private void lerConta() throws IOException, Exception{
        for(int i=0;i<listaContas.size();i++){
       //     if (listaContas.get(i).get()){
       //         atualizarBoleto(listaContas.get(i));
       //     }else if (listaContas.get(i).getBoletocancelado()){
       //          cancelarBoleto(listaContas.get(i));
       //    }else {
       //         enviarBoleto(listaContas.get(i));
        //    }
        }
        remessa.close();
        confirmarContas();
    }
    
    private void atualizarBoleto(Contasreceber conta) throws IOException, Exception{
        numeroSequencial++;
        ArquivoRemessaAtualizar arquivoRemessaAtualizar = new ArquivoRemessaAtualizar();
        remessa.write(arquivoRemessaAtualizar.gerarHeader(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaAtualizar.gerarDetalhe(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaAtualizar.gerarTrailer(numeroSequencial));
    }
    
    private void cancelarBoleto(Contasreceber conta) throws IOException, Exception {
        numeroSequencial++;
        ArquivoRemessaCancelar arquivoRemessaCancelar = new ArquivoRemessaCancelar();
        remessa.write(arquivoRemessaCancelar.gerarHeader(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaCancelar.gerarDetalhe(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaCancelar.gerarTrailer(numeroSequencial));
    }
    
    private void enviarBoleto(Contasreceber conta) throws IOException, Exception{
        numeroSequencial++;
        ArquivoRemessaEnviar arquivoRemessaNormal = new ArquivoRemessaEnviar();
        remessa.write(arquivoRemessaNormal.gerarHeader(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarDetalhe(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarMulta(conta, numeroSequencial));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarTrailer(numeroSequencial));
    }
    
    private void confirmarContas(){
        for(int i=0;i<listaContas.size();i++){
            Contasreceber conta = listaContas.get(i);
            conta.setSituacao("vd");
            contasReceberDao.update(conta);
        }
    }
}
