package com.petsup.api.util;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.ListaObj;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GeradorCsv {

    public static ResponseEntity<Void> gravaArquivoCsv(ListaObj<Agendamento> list) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean error = false;
        Path diretorioBase;

        String nomeArq = "Agendamento.csv";

        if(list.getTamanho() <= 0){
            System.out.println("Lista vazia, nada a gravar");
            return ResponseEntity.status(204).build();
        }

        if(System.getProperty("os.name").contains("Windows")){
            diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos");
        }else{
            diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos");
        }

        if(!diretorioBase.toFile().exists()){
            diretorioBase.toFile().mkdir();
        }

        try {
            arq = new FileWriter(diretorioBase + "/" + nomeArq);
            saida = new Formatter(arq);
        } catch (IOException io) {
            System.out.println("Erro ao abrir o arquivo");
            return ResponseEntity.status(400).build();
        }

        try {
            for (int i = 0; i < list.getTamanho(); i++) {
                Agendamento a = list.getElemento(i);
                saida.format("%d;%s;%s;%s;%s;%s;%s;%s;%s;%.2f\n", a.getId(), a.getDataHora(),
                        a.getFkPet().getFkCliente().getNome(), a.getFkPet().getFkCliente().getEmail(),
                        a.getFkPet().getNome(), a.getFkPet().getEspecie().toString(), a.getFkPet().getRaca().toString(),
                        a.getFkPet().getSexo(), a.getFkServico().getNome(), a.getFkServico().getPreco());
            }
        } catch (FormatterClosedException fc) {
            System.out.println("Erro ao gravar o arquivo");
            error = true;
        } finally {
            saida.close();

            try {
                arq.close();
            } catch (IOException io) {
                System.out.println("Erro ao fechar o arquivo");
                error = true;
            }
            if (error) {
                return ResponseEntity.status(400).build();
            }
        }
        return ResponseEntity.status(200).build();
    }

    public static ResponseEntity<byte[]> buscaArquivoCsv() {
        File arquivoBuscado;

        if(System.getProperty("os.name").contains("Windows")){
            arquivoBuscado = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos/Agendamento.csv").toFile();
        }else{
            arquivoBuscado = Path.of(System.getProperty("user.dir") + "/arquivos/Agendamento.csv").toFile();
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
