package com.petsup.api.util;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.ListaObj;
import com.petsup.api.entities.enums.Especie;
import com.petsup.api.entities.enums.NomeServico;
import com.petsup.api.entities.enums.Raca;
import com.petsup.api.service.dto.AgendamentoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GeradorTxt {

    public static void gravaRegistro(String registro, String nomeArq) {
        BufferedWriter saida = null;

        // try-catch para abrir o arquivo
        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
        }

        // try-catch para gravar o registro e finalizar
        try {
            saida.append(registro + "\n");
            saida.close();
        }
        catch (IOException erro) {
            System.out.println("Erro ao gravar no arquivo");
        }
    }

    public static void gravaArquivoTxt(ListaObj<Agendamento> lista) {
        String nomeArq = "Agendamento.txt";
        int contaRegistroDado = 0;
        Path diretorioBase;

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos");
        }else{
            diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos");
        }

        if(!diretorioBase.toFile().exists()){
            diretorioBase.toFile().mkdir();
        }

        // Monta o registro de header
        String header = "00AGENDAMENTO";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";

        // Grava o registro de header
        gravaRegistro(header, nomeArq);

        // Monta e grava os registros de dados ou registros de corpo
        String corpo;
        for (int i = 0; i < lista.getTamanho(); i++) {
            corpo = "02";
            corpo += String.format("%05d",lista.getElemento(i).getId());
            corpo += String.format("%-19.19s",lista.getElemento(i).getDataHora());
            corpo += String.format("%-50.50s",lista.getElemento(i).getFkCliente().getNome());
            corpo += String.format("%-50.50s",lista.getElemento(i).getFkCliente().getEmail());
            corpo += String.format("%-50.50s",lista.getElemento(i).getFkPet().getNome());
            corpo += String.format("%-8.8s",lista.getElemento(i).getFkPet().getEspecie());
            corpo += String.format("%-50.50s",lista.getElemento(i).getFkPet().getRaca());
            corpo += String.format("%-1.1s",lista.getElemento(i).getFkPet().getSexo());
            corpo += String.format("%-20.20s",lista.getElemento(i).getFkServico().getNome());
            corpo += String.format("%08.2f",lista.getElemento(i).getFkServico().getPreco());
            gravaRegistro(corpo, nomeArq);
            contaRegistroDado++;
        }

        // Monta e grava o registro de trailer
        String trailer = "01";
        trailer += String.format("%010d",contaRegistroDado);
        gravaRegistro(trailer, nomeArq);
    }

    public static ResponseEntity<byte[]> buscaArquivoTxt() {
        File arquivoBuscado;

        if(System.getProperty("os.name").contains("Windows")){
            arquivoBuscado = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos/Agendamento.txt").toFile();
        }else{
            arquivoBuscado = Path.of(System.getProperty("user.dir") + "/arquivos/Agendamento.txt").toFile();
        }

        if(!arquivoBuscado.exists()){
            return ResponseEntity.status(404).build();
        }

        try {
            InputStream fileInputStream = new FileInputStream(arquivoBuscado);

            return ResponseEntity.status(200)
                    .header("Content-Disposition",
                            "attachment; filename=" + arquivoBuscado.getName())
                    .body(fileInputStream.readAllBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Diretório não encontrado", null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Não foi possível converter para byte[]", null);
        }

    }
}
