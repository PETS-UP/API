package com.petsup.api.controllers;

import com.petsup.api.models.Arquivo;
import com.petsup.api.services.ArquivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/*
 POST:   /arquivos/upload
 GET:    /arquivos/download/{idArquivo}
*/

@RestController
@RequestMapping("/arquivos")
public class ArquivoController { // Controller com endpoints para download e upload de arquivos

    @Autowired
    private ArquivoService arquivoService;

    @PostMapping("/upload")
    public ResponseEntity<Arquivo> upload(@RequestParam("arquivo") MultipartFile file) {
        return ResponseEntity.status(200).body(arquivoService.upload(file));
    }

    @GetMapping("/download/{idArquivo}")
    public ResponseEntity<byte[]> download(@PathVariable Integer idArquivo){
            return ResponseEntity.status(200).header("Content-Disposition",
                            "attachment; filename=" + arquivoService.getNomeArquivoOriginal(idArquivo))
                    .body(arquivoService.download(idArquivo));
    }
}
