/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.dao.ContasReceberDao;
import br.com.panoramico.dao.RecebimentoDao;
import br.com.panoramico.dao.RetornoArquivoDao;
import br.com.panoramico.dao.RetornoContasDao;
import br.com.panoramico.managebean.UsuarioLogadoMB;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Recebimento;
import br.com.panoramico.model.Retornoarquivo;
import br.com.panoramico.model.Retornocontas;
import br.com.panoramico.uil.Formatacao;
import java.io.BufferedReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;

public class LerRetornoItauBean {

    @EJB
    private ContasReceberDao contasReceberDao;
    @EJB
    private RecebimentoDao recebimentoDao;
    private Recebimento recebimento;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @EJB
    private RetornoArquivoDao retornoArquivoDao;
    @EJB
    private RetornoContasDao retornoContasDao;
    private String nomeArquivo;

    public LerRetornoItauBean(BufferedReader retorno, String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
        try {
            lerArquivo(retorno);
        } catch (Exception ex) {
            Logger.getLogger(LerRetornoItauBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void lerArquivo(BufferedReader retorno) throws Exception {
        String linha = "";
        while (linha != null) {
            linha = retorno.readLine();
            registarDados(linha);
        }

    }

    private void registarDados(String linha) {
        if (linha != null) {
            String registro = linha.substring(0, 1);
            if (registro.equalsIgnoreCase("1")) {
                String nossoNumero = linha.substring(62, 71);
                nossoNumero = nossoNumero.trim();
                String dataPagamento = linha.substring(110, 116);
                dataPagamento.trim();
                String juros = linha.substring(266, 279);
                juros.trim();
                juros = Formatacao.colcoarVirgulaValor(juros);
                String valorPago = linha.substring(153, 165);
                valorPago.trim();
                valorPago = Formatacao.colcoarVirgulaValor(valorPago);
                String codigoOcorrencia = linha.substring(108, 110);
                codigoOcorrencia.trim();
                registarRecebimento(nossoNumero, dataPagamento, valorPago, juros, codigoOcorrencia);
            }
        }
    }

    public void registarRecebimento(String nossoNumero, String dataPagamento, String valorPago, String juros, String ocorrencia) {
        Retornoarquivo retornoarquivo;
        Retornocontas retornocontas;
        String sql = "select c from Contasreceber c where c.nossonumero='" + nossoNumero + "'";
        Contasreceber conta = contasReceberDao.find(sql);
        recebimento = new Recebimento();
        if (conta != null) {
            if (ocorrencia.equalsIgnoreCase("06")) {
                Float vJuros = Formatacao.formatarStringfloat(juros);
                if (vJuros > 0) {
                    recebimento.setJuros(vJuros);
                }
                recebimento.setValorrecebido(Formatacao.formatarStringfloat(valorPago));
                recebimento.setDatarecebimento(converterData(dataPagamento));
                recebimento.setContasreceber(conta);
                recebimento.setFormarecebimento(conta.getTipopagamento());
                recebimento.setUsuario(usuarioLogadoMB.getUsuario());
                recebimento = recebimentoDao.update(recebimento);
            }
            if (recebimento.getIdrecebimento() != null) {
                retornoarquivo = new Retornoarquivo();
                retornocontas = new Retornocontas();
                retornoarquivo.setDataretorno(new Date());
                retornoarquivo.setNomeaquivo(nomeArquivo);
                retornoarquivo.setUsuario(usuarioLogadoMB.getUsuario());
                retornoarquivo = retornoArquivoDao.update(retornoarquivo);
                retornocontas.setCodigoocorrencia(ocorrencia);
                retornocontas.setContasreceber(conta);
                retornocontas.setRetornoarquivo(retornoarquivo);
                retornoContasDao.update(retornocontas);
            }
        }
    }

    public Date converterData(String sData) {
        String dataPagamento = sData.substring(0, 6) + "20" + sData.substring(6, 8);
        return Formatacao.ConvercaoStringData(dataPagamento);
    }

    public Float converterJuros(String juros) {
        juros = juros.substring(0, juros.length() - 2) + "," + juros.substring(juros.length() - 2, juros.length());
        return Formatacao.formatarStringfloat(juros);
    }

    public Float converterValorPago(String valorPago) {
        valorPago = valorPago.substring(0, valorPago.length() - 2) + "," + valorPago.substring(valorPago.length() - 2, valorPago.length());
        return Formatacao.formatarStringfloat(valorPago);
    }
}
