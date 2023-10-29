package com.petsup.api.controllers.petshop;

import com.petsup.api.repositories.AgendamentoRepository;
import com.petsup.api.services.petshop.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 GET:    /dashboard/{idPetshop}/ultima-semana
 GET:    /dashboard/{idPetshop}/dia-mais-movimentado
 GET:    /dashboard/{idPetshop}/dia-menos-movimentado
 GET:    /dashboard/{idPetshop}/renda-ultimos-meses
 GET:    /dashboard/{idPetshop}/renda-este-mes
 GET:    /dashboard/{idPetshop}/servico-mais-agendado
*/

@RestController
@RequestMapping("/api/dashboard/{idPetshop}")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping("/ultima-semana")
    public ResponseEntity<List<Integer>> getAgendamentosUltimaSemana(@PathVariable Integer idPetshop) {
        return ResponseEntity.ok(dashboardService.getAgendamentosUltimaSemana(idPetshop));
    }

    @GetMapping("/dia-mais-movimentado")
    public ResponseEntity<String> getDiaMaisMovimentado(@PathVariable Integer idPetshop){
        return ResponseEntity.ok(dashboardService.getDiaMaisMovimentado(idPetshop));
    }

    @GetMapping("/dia-menos-movimentado")
    public ResponseEntity<String> getDiaMenosMovimentado(@PathVariable Integer idPetshop){
        return ResponseEntity.ok(dashboardService.getDiaMenosMovimentado(idPetshop));
    }

    @GetMapping("/renda-ultimos-meses")
    public ResponseEntity<List<Double>> getRendaUltimosMeses(@PathVariable Integer idPetshop){
        return ResponseEntity.ok(dashboardService.getAgendamentosUltimosMeses(idPetshop));
    }

    @GetMapping("/renda-este-mes")
    public ResponseEntity<Double> getRendaEsteMes(@PathVariable Integer idPetshop){
        return ResponseEntity.ok(dashboardService.getRendaEsteMes(idPetshop));
    }

    @GetMapping("/servico-mais-agendado")
    public ResponseEntity<String> getServicoMaisAgendadoMesAtual(@PathVariable Integer idPetshop){
        return ResponseEntity.ok(dashboardService.getServicoMaisAgendadoMesAtual(idPetshop));
    }
}