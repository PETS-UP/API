package com.petsup.api.util;

import com.petsup.api.entities.Agendamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.FormatterClosedException;

public class GeradorCsv {

    public static File gravaArquivoCsv(ListaObj<Agendamento> list) {
        Formatter saida = null;
        File file = null;
        FileWriter arq = null;
        Boolean error = false;
        String nomeArq = "Agendamento";
        try {
            file = File.createTempFile(nomeArq, ".csv");
            nomeArq += ".csv";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
        }


        if (list.getTamanho() <= 0) {
            System.out.println("Lista vazia, nada a gravar");
            error = true;
        }

        try {
            for (int i = 0; i < list.getTamanho(); i++) {
                Agendamento a = list.getElemento(i);
                String dados = String.format("%d;%s;%s;%s;%s;%s;%s;%s;%.2f\n", a.getId(), a.getDataHora(),
                        a.getFkPet().getFkCliente().getNome(), a.getFkPet().getFkCliente().getEmail(),
                        a.getFkPet().getNome(), a.getFkPet().getEspecie().toString(),
                        a.getFkPet().getSexo(), a.getFkServico().getNome(), a.getFkServico().getPreco());
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write(dados);
                    writer.write(System.lineSeparator()); // Adicionar quebra de linha
                } catch (IOException e) {
                    System.out.println("Erro ao adicionar conteúdo ao arquivo: " + e.getMessage());
                }
            }
        } catch (FormatterClosedException fc) {
            System.out.println("Erro ao gravar o arquivo");
            error = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                error = true;
            }
            if (error) {
                System.out.println("Error" + error);
            }
            return file;
        }
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
