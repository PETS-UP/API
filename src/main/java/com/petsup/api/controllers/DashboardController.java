package com.petsup.api.controllers;

import com.petsup.api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/ultima-semana")
    public ResponseEntity<List<Integer>> getAgendamentosUltimaSemana() {
        return ResponseEntity.ok(dashboardService.getAgendamentosUltimaSemana());
    }

    @GetMapping("/dia-mais-movimentado")
    public ResponseEntity<String> getDiaMaisMovimentado(){
        return ResponseEntity.ok(dashboardService.getDiaMaisMovimentado());
    }

//    @GetMapping("ultimo-mes")
//    public ResponseEntity<Integer>
//

}
