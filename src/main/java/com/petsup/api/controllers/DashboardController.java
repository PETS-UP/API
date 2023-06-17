package com.petsup.api.controllers;

import com.petsup.api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/ultima-semana/{idPetshop}")
    public ResponseEntity<List<Integer>> getAgendamentosUltimaSemana(@PathVariable Integer idPetshop) {
        return ResponseEntity.ok(dashboardService.getAgendamentosUltimaSemana(idPetshop));
    }

    @GetMapping("/dia-mais-movimentado/{idPetshop}")
    public ResponseEntity<String> getDiaMaisMovimentado(@PathVariable Integer idPetshop){
        return ResponseEntity.ok(dashboardService.getDiaMaisMovimentado(idPetshop));
    }

    @GetMapping("/dia-menos-movimentado/{idPetshop}")
    public ResponseEntity<String> getDiaMenosMovimentado(@PathVariable Integer idPetshop){
        return ResponseEntity.ok(dashboardService.getDiaMenosMovimentado(idPetshop));
    }

//    @GetMapping("ultimo-mes")
//    public ResponseEntity<Integer>
//

}
