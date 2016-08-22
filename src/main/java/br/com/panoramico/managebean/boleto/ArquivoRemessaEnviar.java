/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.uil.Formatacao;
import java.io.IOException;
import java.util.Date;


public class ArquivoRemessaEnviar implements ArquivoRemessaItau{
    
    private String branco = "                                        ";
    private String zeros = "000000000000000000000";
    
    public String gerarHeader(Contasreceber conta, int numeroSequencial) throws IOException{
        String linha="";
        linha = linha  + ("0");
        linha = linha  + ("1");
        linha = linha  + ("REMESSA");
        linha = linha  + ("01");
        linha = linha  + ("COBRANCA       ");
        //linha = linha  + (conta.getVendas().getUnidadenegocio().getBanco().getAgencia());
        linha = linha  + ("00");
        //linha = linha  + (conta.getVendas().getUnidadenegocio().getBanco().getConta());
        //linha = linha  + (conta.getVendas().getUnidadenegocio().getBanco().getDigitoconta());
        linha = linha  + (branco.substring(0, 8));
        String nomeEmpresa = "";
        //conta.getVendas().getUnidadenegocio().getRazaoSocial();
        nomeEmpresa = nomeEmpresa.toUpperCase();
        if (nomeEmpresa.length()<30){
            nomeEmpresa = nomeEmpresa + branco.substring(0, 30 - nomeEmpresa.length());
        }else nomeEmpresa = nomeEmpresa.substring(0,30);
        linha = linha  + (nomeEmpresa);
        linha = linha  + ("341");
        linha = linha  + ("BANCO ITAU S.A.");
        linha = linha  + (Formatacao.ConvercaoDataDDMMAA(new Date()));
        linha = linha  + (branco + branco + branco + branco + branco + branco + branco + branco.substring(0,14));
        String ns = "";
        if (numeroSequencial<10){
            ns = "00000" + String.valueOf(numeroSequencial);
        }else if (numeroSequencial<100){
            ns = "0000" + String.valueOf(numeroSequencial);
        }else ns = "000" + String.valueOf(numeroSequencial);
        linha = linha  + (ns + "\r\n");
        return linha;
    }
    
    public String gerarDetalhe(Contasreceber conta, int numeroSequencial) throws IOException, Exception{
        String linha="";
        linha = linha  + ("1");
        linha = linha  + ("02");
        //linha = linha  + (Formatacao.retirarPontos(conta.getVendas().getUnidadenegocio().getCnpj()));
        //linha = linha  + (conta.getVendas().getUnidadenegocio().getBanco().getAgencia());
        linha = linha  + ("00");
        //linha = linha  + (conta.getVendas().getUnidadenegocio().getBanco().getConta());
        //linha = linha  + (conta.getVendas().getUnidadenegocio().getBanco().getDigitoconta());
        linha = linha  + (branco.substring(0, 4));
        linha = linha  + ("0000");
        linha = linha  + (branco.substring(0, 25));
        //linha = linha  + (conta.getNossonumero());
        linha = linha  + ("0000000000000");
        linha = linha  + ("109");
        linha = linha  + ("000000000000000000000");
        linha = linha  + ("I");
        linha = linha  + ("01");
        //linha = linha  + (conta.getNossonumero() + "  ");
        linha = linha  + (Formatacao.ConvercaoDataDDMMAA(conta.getDatalancamento()));
        String valor = Formatacao.foramtarFloatString(conta.getValorconta());
        valor = Formatacao.retirarPontos(valor);
        if (valor.length()<13){
            valor = zeros.substring(0, 13 - valor.length()) + valor;
        }
        linha = linha  + (valor);
        linha = linha  + ("341");
        linha = linha  + ("00000");
        linha = linha  + ("01");
        linha = linha  + ("N");
        linha = linha  + (Formatacao.ConvercaoDataDDMMAA(new Date()));
        linha = linha  + ("00");
        linha = linha  + ("00");
        linha = linha  + (valorJuros(conta.getValorconta(), 0f));
        //conta.getVendas().getUnidadenegocio().getBanco().getValorjuros())
        linha = linha  + (Formatacao.ConvercaoDataDDMMAA(new Date()));
        linha = linha  + (zeros.substring(0, 13));
        linha = linha  + (zeros.substring(0, 13));
        linha = linha  + (zeros.substring(0, 13));
        linha = linha  + ("01");
        //linha = linha  + (Formatacao.retirarPontos(conta.getVendas().getCliente().getCpf())+ "   ");
        String nomeCliente = conta.getCliente().getNome();
        nomeCliente = nomeCliente.toUpperCase();
        if (nomeCliente.length()<30){
            nomeCliente = nomeCliente + branco.substring(0, 30 -nomeCliente.length());
        }else nomeCliente.substring(0, 30);
        linha = linha  + (nomeCliente);
        linha = linha  + (branco.substring(0, 10));
        //String logradouro = conta.getCliente().get + " " + conta.getVendas().getCliente().getLogradouro() +
       //         conta.getVendas().getCliente().getNumero();
       // logradouro = logradouro.toUpperCase();
       // if (logradouro.length()<40){
       //     logradouro = logradouro + branco.substring(0, 40 - logradouro.length());
      //  }else logradouro = logradouro.substring(0, 40);
      //  linha = linha  + (logradouro);
       // String bairro = conta.getVendas().getCliente().getBairro();
      //  bairro = bairro.toUpperCase();
       // if (bairro.length()<12){
       //     bairro = bairro + branco.substring(0, 12 - bairro.length());
       // }else bairro = bairro.substring(0,12);
      //  linha = linha  + (bairro);
       // linha = linha  + (Formatacao.retirarPontos(conta.getVendas().getCliente().getCep()));
       // String cidade = conta.getVendas().getCliente().getCidade();
       // cidade = cidade.toUpperCase();
       // if (cidade.length()<15){
       //     cidade = cidade + branco.substring(0, 15 - cidade.length());
       // }else cidade = cidade.substring(0, 15);
       // linha = linha  + (cidade);
       // linha = linha  + (conta.getVendas().getCliente().getEstado().toUpperCase());
        linha = linha  + (branco.substring(0,30));
        linha = linha  + ("    ");
        linha = linha  + (Formatacao.SubtarirDatas(conta.getDatalancamento(), -1, "ddMMyy"));
        linha = linha  + ("00");
        linha = linha  + (" ");
        String ns;
        if (numeroSequencial<10){
            ns = "00000" + String.valueOf(numeroSequencial);
        }else if (numeroSequencial<100){
            ns = "0000" + String.valueOf(numeroSequencial);
        }else ns = "000" + String.valueOf(numeroSequencial);
        linha = linha  + (ns + "\r\n");
        return linha;
    }
    
    public String gerarMulta(Contasreceber conta, int numeroSequencial) throws IOException, Exception{
        String linha="";
        linha = linha  + ("2");
        linha = linha  + ("1");
        linha = linha  + (Formatacao.SubtarirDatas(conta.getDatalancamento(), -1, "ddMMyyyy"));
        linha = linha  + (valorJuros(conta.getValorconta(), 0f));
        //conta.getVendas().getUnidadenegocio().getBanco().getValormulta()
        linha = linha  + (branco + branco + branco + branco + branco + branco + branco + branco + branco + branco.substring(0,11));
        String ns;
        if (numeroSequencial<10){
            ns = "00000" + String.valueOf(numeroSequencial);
        }else if (numeroSequencial<100){
            ns = "0000" + String.valueOf(numeroSequencial);
        }else ns = "000" + String.valueOf(numeroSequencial);
        linha = linha  + (ns + "\r\n");
        return linha;
    }
    
    public String gerarTrailer(int numeroSequencial) throws IOException{
        String linha="";
        linha = linha  + ("9");
        linha = linha  + (branco + branco + branco + branco + branco + branco + branco + branco + branco + branco.substring(0,33));
        String ns;
        if (numeroSequencial<10){
            ns = "00000" + String.valueOf(numeroSequencial);
        }else if (numeroSequencial<100){
            ns = "0000" + String.valueOf(numeroSequencial);
        }else ns = "000" + String.valueOf(numeroSequencial);
        linha = linha  + (ns + "\r\n");
        return linha;
    }
    
    private String valorJuros(Float valorConta, float juros){
        Float valorJuros = valorConta * (juros/100);
        String valor = Formatacao.retirarPontos(Formatacao.foramtarFloatString(valorJuros));
        if (valor.length()<13){
            valor = zeros.substring(0, 13 - valor.length()) + valor;
        }
        return valor;
    }
    
}
