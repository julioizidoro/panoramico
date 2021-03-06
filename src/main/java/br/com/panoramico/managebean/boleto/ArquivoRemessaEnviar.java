/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.panoramico.managebean.boleto;

import br.com.panoramico.dao.EmpresaDao;
import br.com.panoramico.dao.ProprietarioDao;
import br.com.panoramico.model.Banco;
import br.com.panoramico.model.Contasreceber;
import br.com.panoramico.model.Empresa;
import br.com.panoramico.model.Proprietario;
import br.com.panoramico.uil.Formatacao;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

public class ArquivoRemessaEnviar implements ArquivoRemessaItau {

    private String branco = "                                        ";
    private String zeros = "000000000000000000000";
    @EJB
    private ProprietarioDao proprietarioDao;
    @EJB
    private EmpresaDao empresaDao;
    private Proprietario proprietario;
    private List<Empresa> listaEmpresa;

    @PostConstruct
    public void init() {
    }

    public String getBranco() {
        return branco;
    }

    public void setBranco(String branco) {
        this.branco = branco;
    }

    public String getZeros() {
        return zeros;
    }

    public void setZeros(String zeros) {
        this.zeros = zeros;
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

    public String gerarHeader(Contasreceber conta, int numeroSequencial, Proprietario proprietario, Banco banco) throws IOException {
        String linha = "";
        linha = linha + ("0");
        linha = linha + ("1");
        linha = linha + ("REMESSA");
        linha = linha + ("01");
        linha = linha + ("COBRANCA       ");
        linha = linha + (banco.getAgencia());
        linha = linha + ("00");
        linha = linha + (banco.getConta());
        linha = linha + (banco.getDigitoconta());
        linha = linha + (branco.substring(0, 8));
        String nomeEmpresa = proprietario.getRazaosocial();
        if (nomeEmpresa == null) {
            nomeEmpresa = " Sem nome da empresa";
        }
        nomeEmpresa = nomeEmpresa.toUpperCase();
        if (nomeEmpresa.length() < 30) {
            nomeEmpresa = nomeEmpresa + branco.substring(0, 30 - nomeEmpresa.length());
        } else {
            nomeEmpresa = nomeEmpresa.substring(0, 30);
        }
        linha = linha + (nomeEmpresa);
        linha = linha + ("341");
        linha = linha + ("BANCO ITAU S.A.");
        linha = linha + (Formatacao.ConvercaoDataDDMMAA(new Date()));
        linha = linha + (branco + branco + branco + branco + branco + branco + branco + branco.substring(0, 14));
        String ns = "";
        if (numeroSequencial < 10) {
            ns = "00000" + String.valueOf(numeroSequencial);
        } else if (numeroSequencial < 100) {
            ns = "0000" + String.valueOf(numeroSequencial);
        } else {
            ns = "000" + String.valueOf(numeroSequencial);
        }
        linha = linha + (ns + "\r\n");
        return linha;
    }

    public String gerarDetalhe(Contasreceber conta, int numeroSequencial, Proprietario proprietario, Banco banco) throws IOException, Exception {
        String linha = "";
        linha = linha + ("1");
        linha = linha + ("02");
        linha = linha + (Formatacao.retirarPontos(proprietario.getCnpj()));
        linha = linha + (banco.getAgencia());
        linha = linha + ("00");
        linha = linha + (banco.getConta());
        linha = linha + (banco.getDigitoconta());
        linha = linha + (branco.substring(0, 4));
        linha = linha + ("0000");
        linha = linha + (branco.substring(0, 25));
        linha = linha + (conta.getNossonumero());
        linha = linha + ("0000000000000");
        linha = linha + ("109");
        linha = linha + ("000000000000000000000");
        linha = linha + ("I");
        linha = linha + ("01");
        linha = linha + (conta.getNossonumero() + "  ");
        linha = linha + (Formatacao.ConvercaoDataDDMMAA(conta.getDatavencimento()));
        String valor = Formatacao.foramtarFloatString(conta.getValorconta());
        valor = Formatacao.retirarPontos(valor);
        if (valor.length() < 13) {
            valor = zeros.substring(0, 13 - valor.length()) + valor;
        }
        linha = linha + (valor);
        linha = linha + ("341");
        linha = linha + ("00000");
        linha = linha + ("08");
        linha = linha + ("N");
        linha = linha + (Formatacao.ConvercaoDataDDMMAA(new Date()));
        linha = linha + ("00");
        linha = linha + ("00");
        linha = linha + ("0000000000000");
        float valorDesconto = 0.0f;
        if (conta.getIdempresa() == 0) {
            if (conta.getCliente().getAssociado().getDescotomensalidade() > 0) {
                valorDesconto = conta.getCliente().getAssociado().getDescotomensalidade();
            }
        }
        if (valorDesconto > 0) {
            linha = linha + (Formatacao.ConvercaoDataDDMMAA(conta.getDatavencimento()));
            valor = Formatacao.foramtarFloatString(conta.getCliente().getAssociado().getDescotomensalidade());
            valor = Formatacao.retirarPontos(valor);
            if (valor.length() < 13) {
                valor = zeros.substring(0, 13 - valor.length()) + valor;
            }
            linha = linha + (zeros.substring(0, 13));
        } else {
            linha = linha + (Formatacao.ConvercaoDataDDMMAA(new Date()));
            linha = linha + (zeros.substring(0, 13));
        }
        linha = linha + (zeros.substring(0, 13));
        linha = linha + (zeros.substring(0, 13));
        //dados Cliente
        if (conta.getIdempresa() == 0) {
            linha = linha + ("01");
            linha = linha + (Formatacao.retirarPontos(conta.getCliente().getCpf()) + "   ");
            String nomeCliente = conta.getCliente().getNome();
            if (nomeCliente == null) {
                nomeCliente = " Sem Cliente";
            }
            nomeCliente = nomeCliente.toUpperCase();
            if (nomeCliente.length() < 30) {
                nomeCliente = nomeCliente + branco.substring(0, 30 - nomeCliente.length());
            } else {
                nomeCliente.substring(0, 30);
            }
            linha = linha + (nomeCliente);
            linha = linha + (branco.substring(0, 10));
            String logradouro = conta.getCliente().getAssociado().getTipologradouro() + " " + conta.getCliente().getAssociado().getLogradouro()
                    + conta.getCliente().getAssociado().getNumero();
            if (logradouro.length() < 2) {
                logradouro = " Sem endereço ";
            }
            logradouro = logradouro.toUpperCase();
            if (logradouro.length() < 40) {
                logradouro = logradouro + branco.substring(0, 40 - logradouro.length());
            } else {
                logradouro = logradouro.substring(0, 40);
            }
            linha = linha + (logradouro);
            String bairro = conta.getCliente().getAssociado().getBairro();
            if (bairro == null) {
                bairro = " Sem bairro ";
            }
            bairro = bairro.toUpperCase();
            if (bairro.length() < 12) {
                bairro = bairro + branco.substring(0, 12 - bairro.length());
            } else {
                bairro = bairro.substring(0, 12);
            }
            linha = linha + (bairro);
            linha = linha + (Formatacao.retirarPontos(conta.getCliente().getAssociado().getCep()));
            String cidade = conta.getCliente().getAssociado().getCidade();
            if (cidade == null) {
                cidade = " Sem cidade ";
            }
            cidade = cidade.toUpperCase();
            if (cidade.length() < 15) {
                cidade = cidade + branco.substring(0, 15 - cidade.length());
            } else {
                cidade = cidade.substring(0, 15);
            }
            linha = linha + (cidade);
            linha = linha + (conta.getCliente().getAssociado().getEstado().toUpperCase());

        } else {
            //Dados empresa
            Empresa empresa = conta.getEmpresa();
            linha = linha + ("02");
            linha = linha + (Formatacao.retirarPontos(empresa.getCnpj()));
            String nomeCliente = empresa.getRazaosocial();
            if (nomeCliente == null) {
                nomeCliente = " Sem Cliente ";
            }
            nomeCliente = nomeCliente.toUpperCase();
            if (nomeCliente.length() < 30) {
                nomeCliente = nomeCliente + branco.substring(0, 30 - nomeCliente.length());
            } else {
                nomeCliente.substring(0, 30);
            }
            linha = linha + (nomeCliente);
            linha = linha + (branco.substring(0, 10));
            String logradouro = empresa.getTipologradouro() + " " + empresa.getLogradouro()
                    + empresa.getNumero();
            if (logradouro.length() < 2) {
                logradouro = " Sem logradouro ";
            }
            logradouro = logradouro.toUpperCase();
            if (logradouro.length() < 40) {
                logradouro = logradouro + branco.substring(0, 40 - logradouro.length());
            } else {
                logradouro = logradouro.substring(0, 40);
            }
            linha = linha + (logradouro);
            String bairro = empresa.getBairro();
            if (bairro == null) {
                bairro = " Sem bairro ";
            }
            bairro = bairro.toUpperCase();
            if (bairro.length() < 12) {
                bairro = bairro + branco.substring(0, 12 - bairro.length());
            } else {
                bairro = bairro.substring(0, 12);
            }
            linha = linha + (bairro);
            linha = linha + (Formatacao.retirarPontos(empresa.getCep()));
            String cidade = empresa.getCidade();
            if (cidade == null) {
                cidade = " Sem cidade ";
            }
            cidade = cidade.toUpperCase();
            if (cidade.length() < 15) {
                cidade = cidade + branco.substring(0, 15 - cidade.length());
            } else {
                cidade = cidade.substring(0, 15);
            }
            linha = linha + (cidade);
            linha = linha + (empresa.getEstado().toUpperCase());
        }

        linha = linha + (branco.substring(0, 30));
        linha = linha + ("    ");
        linha = linha + (Formatacao.SubtarirDatas(conta.getDatavencimento(), -1, "ddMMyy"));
        linha = linha + ("13");
        linha = linha + (" ");
        String ns;
        if (numeroSequencial < 10) {
            ns = "00000" + String.valueOf(numeroSequencial);
        } else if (numeroSequencial < 100) {
            ns = "0000" + String.valueOf(numeroSequencial);
        } else {
            ns = "000" + String.valueOf(numeroSequencial);
        }
        linha = linha + (ns + "\r\n");
        return linha;
    }

    public String gerarMulta(Contasreceber conta, int numeroSequencial, Banco banco) throws IOException, Exception {

        String linha = "";
        linha = linha + ("2");
        linha = linha + ("1");
        linha = linha + (Formatacao.SubtarirDatas(conta.getDatavencimento(), -1, "ddMMyyyy"));
        linha = linha + (valorJuros(0f, 0f));
        linha = linha + (branco + branco + branco + branco + branco + branco + branco + branco + branco + branco.substring(0, 11));
        String ns;
        if (numeroSequencial < 10) {
            ns = "00000" + String.valueOf(numeroSequencial);
        } else if (numeroSequencial < 100) {
            ns = "0000" + String.valueOf(numeroSequencial);
        } else {
            ns = "000" + String.valueOf(numeroSequencial);
        }
        linha = linha + (ns + "\r\n");
        return linha;
    }

    public String gerarTrailer(int numeroSequencial) throws IOException {
        String linha = "";
        linha = linha + ("9");
        linha = linha + (branco + branco + branco + branco + branco + branco + branco + branco + branco + branco.substring(0, 33));
        String ns;
        if (numeroSequencial < 10) {
            ns = "00000" + String.valueOf(numeroSequencial);
        } else if (numeroSequencial < 100) {
            ns = "0000" + String.valueOf(numeroSequencial);
        } else {
            ns = "000" + String.valueOf(numeroSequencial);
        }
        linha = linha + (ns + "\r\n");
        return linha;
    }

    private String valorJuros(Float valorConta, float juros) {
        Float valorJuros = valorConta * (juros / 100);
        String valor = Formatacao.retirarPontos(Formatacao.foramtarFloatString(valorJuros));
        if (valor.length() < 13) {
            valor = zeros.substring(0, 13 - valor.length()) + valor;
        }
        return valor;
    }

}
