package com.petsup.api.services;

import com.petsup.api.models.Arquivo;
import com.petsup.api.repositories.ArquivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    //  private Path diretorioBase = Path.of(System.getProperty("user.dir") + "/arquivos"); // projeto
    private Path diretorioBase = Path.of(System.getProperty("java.io.tmpdir") + "/arquivos"); // temporario

    public Arquivo upload(MultipartFile file) {
        if (file.isEmpty()){
            throw new ResponseStatusException(400, "Erro de conversão lat/long -> endereco", null);
        }

        if (!this.diretorioBase.toFile().exists()) {
            this.diretorioBase.toFile().mkdir();
        }

        String nomeArquivoFormatado = formatarNomeArquivo(file.getOriginalFilename());

        String filePath = this.diretorioBase + "/" + nomeArquivoFormatado;
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Não foi possível salvar o arquivo", null);
        }

        Arquivo arquivo = new Arquivo();
        arquivo.setDataUpload(LocalDate.now());
        arquivo.setNomeArquivoOriginal(file.getOriginalFilename());
        arquivo.setNomeArquivoSalvo(nomeArquivoFormatado);
        Arquivo arquivoBanco = arquivoRepository.save(arquivo);

        return arquivoBanco;
    }

    private String formatarNomeArquivo(String nomeOriginal) {
        return String.format("%s_%s", UUID.randomUUID(), nomeOriginal);
    }

    public byte[] download(Integer idArquivo) {
        Arquivo arquivo = arquivoRepository.findById(idArquivo).orElseThrow(
                () -> new ResponseStatusException(404, "Arquivo não encontrado", null)
        );

        Arquivo arquivoBanco = arquivo;

        File file = this.diretorioBase.resolve(arquivoBanco.getNomeArquivoSalvo()).toFile();
        try {
            InputStream fileInputStream = new FileInputStream(file);

            return fileInputStream.readAllBytes();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Diretório não encontrado", null);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(422, "Não foi possível converter para byte[]", null);
        }
    }

    public String getNomeArquivoOriginal(Integer idArquivo){
        Arquivo arquivo = arquivoRepository.findById(idArquivo).orElseThrow(
                () -> new ResponseStatusException(404, "Arquivo não encontrado", null)
        );
        return arquivo.getNomeArquivoOriginal();
    }
}
