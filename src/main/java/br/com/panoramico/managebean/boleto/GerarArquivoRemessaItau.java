/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Empresa;
import br.com.panoramico.model.Proprietario;
import br.com.panoramico.uil.Formatacao;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class GerarArquivoRemessaItau {

    private List<Contasreceber> listaContas;
    private FileWriter remessa;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private int numeroSequencial = 0;
    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private ProprietarioDao proprietarioDao;
    private Proprietario proprietario;
    private List<Empresa> listaEmpresa;
    private StreamedContent stream;
    private String nomeArquivo;

    public GerarArquivoRemessaItau(List<Contasreceber> lista, UsuarioLogadoMB usuarioLogadoMB, Proprietario proprietario, StreamedContent stream, List<Contasreceber> listaContas) {
        this.listaContas = lista;
        this.usuarioLogadoMB = usuarioLogadoMB;
        this.proprietario = proprietario;
        this.stream = stream;
        this.listaContas = listaContas;
        iniciarRemessa();
    }

    public List<Contasreceber> getListaContas() {
        return listaContas;
    }

    public void setListaContas(List<Contasreceber> listaContas) {
        this.listaContas = listaContas;
    }

    public FileWriter getRemessa() {
        return remessa;
    }

    public void setRemessa(FileWriter remessa) {
        this.remessa = remessa;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public int getNumeroSequencial() {
        return numeroSequencial;
    }

    public void setNumeroSequencial(int numeroSequencial) {
        this.numeroSequencial = numeroSequencial;
    }

    public ContasReceberDao getContasReceberDao() {
        return contasReceberDao;
    }

    public void setContasReceberDao(ContasReceberDao contasReceberDao) {
        this.contasReceberDao = contasReceberDao;
    }

    public ProprietarioDao getProprietarioDao() {
        return proprietarioDao;
    }

    public void setProprietarioDao(ProprietarioDao proprietarioDao) {
        this.proprietarioDao = proprietarioDao;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public List<Empresa> getListaEmpresa() {
        return listaEmpresa;
    }

    public void setListaEmpresa(List<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
    }

    public StreamedContent getStream() {
        return stream;
    }

    public void setStream(StreamedContent stream) {
        this.stream = stream;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    private void iniciarRemessa() {
        if (this.listaContas != null) {
            String nome = System.getProperty("user.name");
            String nomeA = "C:\\remessa\\" + gerarNomeArquivo();
            nomeArquivo = nomeA;
            File arquivo = new File(nomeA);
            try {
                remessa = new FileWriter(arquivo);
                try {
                    lerConta();
                    InputStream inputStream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(nomeA);
                    stream = new DefaultStreamedContent(inputStream, "application/REM", "downloaded_optimus.jpg");
                } catch (Exception ex) {
                    Logger.getLogger(ArquivoRemessaEnviar.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(GerarArquivoRemessaItau.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public String gerarNomeArquivo() {
        String data = Formatacao.ConvercaoDataPadrao(new Date());
        data = data.substring(6, 10) + data.substring(3, 5) + data.substring(0, 2);
        data = data + ".REM";
        return data;
    }

    private void lerConta() throws IOException, Exception {
        for (int i = 0; i < listaContas.size(); i++) {
            if (listaContas.get(i).getSituacaoboleto().equalsIgnoreCase("Alterado")) {
                atualizarBoleto(listaContas.get(i));
            } else if (listaContas.get(i).getSituacaoboleto().equalsIgnoreCase("Cancelado")) {
                cancelarBoleto(listaContas.get(i));
            } else {
                enviarBoleto(listaContas.get(i));
            }
        }
    }

    private void atualizarBoleto(Contasreceber conta) throws IOException, Exception {
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

    private void enviarBoleto(Contasreceber conta) throws IOException, Exception {
        numeroSequencial++;
        ArquivoRemessaEnviar arquivoRemessaNormal = new ArquivoRemessaEnviar();
        remessa.write(arquivoRemessaNormal.gerarHeader(conta, numeroSequencial, proprietario));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarDetalhe(conta, numeroSequencial, proprietario));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarMulta(conta, numeroSequencial, proprietario));
        numeroSequencial++;
        remessa.write(arquivoRemessaNormal.gerarTrailer(numeroSequencial));
    }

    private void confirmarContas() {
        for (int i = 0; i < listaContas.size(); i++) {
            Contasreceber conta = listaContas.get(i);
            conta.setEnviado(true);
            conta.setSituacaoboleto("Enviado");
            contasReceberDao.update(conta);
        }
    }
}
