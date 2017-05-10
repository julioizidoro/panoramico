package br.com.panoramico.uil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTPClient;

import org.primefaces.model.UploadedFile;

public class Ftp {

    private FTPClient ftpClient;
    private String host;
    private String user;
    private String password;

    public Ftp(String host, String user, String password) {
        ftpClient = new FTPClient();
        this.host = host;
        this.user = user;
        this.password = password;
    }

    public boolean conectar() throws IOException {
        ftpClient.connect(host);
        ftpClient.login(user, password);
        if (ftpClient.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public String enviarArquivo(UploadedFile uploadedFile, String arquivoFTP) throws IOException {
        ftpClient.changeWorkingDirectory("/panoramico/remessa/");
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        InputStream arquivo = null;
        arquivo = new BufferedInputStream(uploadedFile.getInputstream());
        arquivoFTP = new String(arquivoFTP.getBytes("ISO-8859-1"), "UTF-8");
        if (ftpClient.storeFile(arquivoFTP, arquivo)) {
            arquivo.close();
            return "Arquivo: " + arquivoFTP + " salvo com Sucesso";
        } else {
            arquivo.close();
            return "Erro Salvar Arquivo";
        }
    }  

    public void desconectar() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    public InputStream receberArquivo(String arquivoSalvar, String arquivoFTP, String pasta) throws IOException {
        ftpClient.changeWorkingDirectory(pasta);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        InputStream is = new BufferedInputStream(ftpClient.retrieveFileStream(arquivoFTP));
        return is;
    }

    public String enviarArquivo(File file, String arquivoFTP, String pasta) throws IOException {
        ftpClient.changeWorkingDirectory(pasta);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FileInputStream arqEnviar = new FileInputStream(file);
        arquivoFTP = new String(arquivoFTP.getBytes("ISO-8859-1"), "UTF-8");
        if (ftpClient.storeFile(arquivoFTP, arqEnviar)) {
            arqEnviar.close();
            return "Arquivo Salvo com Sucesso";
        } else {
            arqEnviar.close();
            return "Erro Salvar Arquivo";
        }
    }

    public String enviarArquivoRemessa(File file, String arquivoFTP, String pasta) throws IOException {
        ftpClient.changeWorkingDirectory(pasta);
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        FileInputStream arqEnviar = new FileInputStream(file);
        arquivoFTP = new String(arquivoFTP.getBytes("ISO-8859-1"), "UTF-8");
        if (ftpClient.storeFile(arquivoFTP, arqEnviar)) {
            arqEnviar.close();
            return "Arquivo Salvo com Sucesso";
        } else {
            arqEnviar.close();
            return "Erro Salvar Arquivo";
        }
    }
   
}
