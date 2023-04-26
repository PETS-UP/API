package com.petsup.api.controllers;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.ClientePetshopSubscriber;
import com.petsup.api.entities.ListaObj;
import com.petsup.api.entities.Servico;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.*;
import com.petsup.api.service.UsuarioService;
import com.petsup.api.service.dto.ServicoDto;
import com.petsup.api.service.dto.UsuarioMapper;
import com.petsup.api.service.autentication.dto.PetshopLoginDto;
import com.petsup.api.service.autentication.dto.PetshopTokenDto;
import com.petsup.api.service.dto.UsuarioPetshopDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Tag(name = "Petshops", description = "Requisições relacionadas a petshops")
@RestController
@RequestMapping("/petshops")
public class PetshopController {

    @Autowired
    private PetshopRepository petshopRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClientePetshopSubscriberRepository clientePetshopSubscriberRepository;

    //Crud inicio
    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @ApiResponse(responseCode = "201", description =
            "Petshop cadastrado com sucesso.", content = @Content(schema = @Schema(hidden = true)))
    public ResponseEntity<Void> postUserPetshop(@RequestBody @Valid UsuarioPetshopDto usuarioDto) {
        this.usuarioService.criarPetshop(usuarioDto);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/login")
    public ResponseEntity<PetshopTokenDto> login(@RequestBody PetshopLoginDto usuarioLoginDto) {
        PetshopTokenDto usuarioTokenDto = this.usuarioService.autenticarPetshop(usuarioLoginDto);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @GetMapping
    @ApiResponse(responseCode = "204", description =
            "Não há petshops cadastrados.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshops encontrados.")
    public ResponseEntity<List<UsuarioPetshopDto>> getPetshops() {
        List<UsuarioPetshop> usuarios = this.petshopRepository.findAll();
        List<UsuarioPetshopDto> petshopsDto = new ArrayList<>();

        for (UsuarioPetshop petshop : usuarios) {
            petshopsDto.add(UsuarioMapper.ofPetshopDto(petshop));
        }

        return petshopsDto.isEmpty() ? ResponseEntity.status(204).build() : ResponseEntity.status(200).body(petshopsDto);
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "204", description =
            "Petshops não encontrado.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Petshop encontrado.")
    public ResponseEntity<UsuarioPetshop> getUserById(@PathVariable Integer id) {
        return ResponseEntity.of(this.petshopRepository.findById(id));
    }

    @DeleteMapping
    @ApiResponse(responseCode = "204", description = "Petshop deletado.")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        this.petshopRepository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Petshop atualizado.")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody UsuarioPetshop usuario){
        UsuarioPetshop updateUser = this.petshopRepository.save(usuario);
        return ResponseEntity.status(200).body(updateUser);
    }

    @ApiResponse(responseCode = "200", description = "Preço do serviço atualizado com sucesso.")
    @ApiResponse(responseCode = "404", description = "Serviço não encontrado.")
    @PatchMapping("/atualizar/preco")
    public ResponseEntity<Servico> updatePreco(@RequestBody ServicoDto servicoAtt, @RequestParam Integer idServico,
                                               @RequestParam Integer idPetshop) {
        Optional<Servico> servicoOptional = servicoRepository.findById(idServico);
        Optional<UsuarioPetshop> petshopOptional = servicoRepository.findByFkPetshop(idPetshop);
        Optional<ClientePetshopSubscriber> clientePetshopSubscriberOptional =
                clientePetshopSubscriberRepository.findByFkPetshopId(idPetshop);

        if (servicoOptional.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        Servico servico = servicoOptional.get();
        UsuarioPetshop petshop = petshopOptional.get();
        ClientePetshopSubscriber cps = clientePetshopSubscriberOptional.get();

        if (servico.getPreco() > servicoAtt.getPreco()){
            for (int i = 0; i < cps.getFkPetshop().getInscritos().size(); i++){
            petshop.notifica(petshop.getEmail(), cps.getFkCliente().getEmail());
            }
        }

        servico.setPreco(servicoAtt.getPreco());
        servicoRepository.save(servico);
        return ResponseEntity.status(200).body(servico);
    }

    //Crud fim

    @GetMapping("/report/arquivo/{usuario}")
    @ApiResponse(responseCode = "201", description = "Relatório gravado.")
    public ResponseEntity<Void> report(@PathVariable int usuario) {
        List<Agendamento> as = agendamentoRepository.findByFkPetshopId(usuario);

        ListaObj<Agendamento> listaLocal = new ListaObj<>(as.size());

        for (int i = 0; i < as.size(); i++) {
            listaLocal.adiciona(as.get(i));
        }
        gravaArquivoCsv(listaLocal, "agendamento");
        //return ResponseEntity.status(401).build();
        return ResponseEntity.status(201).build();
    }

//    ListaObj<Agendamento> lista = new ListaObj(10);
//    Scanner leitorStr = new Scanner(System.in);
//    Scanner leitorNum = new Scanner(System.in);
//    int a = 0;

    public static void leituraNomeCsv(int tamLista) {
        ListaObj<Agendamento> lista = new ListaObj(tamLista);
//        Scanner leitorStr = new Scanner(System.in);
//        Scanner leitorNum = new Scanner(System.in);
        String nomeArq = "agendamento";
//        int a = -1;

        lista.exibe();

        gravaArquivoCsv(lista, nomeArq);
        leArquivoCsv(nomeArq);

//        do {
//        System.out.println("\n*------------------------------*" +
//                "\n|    Exercício arquivo CSV     |" +
//                "\n|------------------------------|" +
//                "\n| Gravar um arquivo (digite 1) |" +
//                "\n| Ler um arquivo    (digite 2) |" +
//                "\n| Cancelar          (digite 0) |" +
//                "\n*------------------------------*" +
//                "\nEscolha uma opção:");
//        try {
//            a = leitorNum.nextInt();
//        } catch (Exception e){
//            System.out.println("Digite um número entre 0, 1 e 2");
//        }
//
//        if (a == 1){
//            System.out.println("Nome do arquivo: ");
//            String l = leitorStr.nextLine();
//            gravaArquivoCsv(lista, l);
//        } else if (a == 2) {
//            System.out.println("Nome do arquivo: ");
//            String l = leitorStr.nextLine();
//            leArquivoCsv(l);
//        } else if (a == 0) {
//            System.out.println("Execução finalizada");
//        } else {
//            System.out.println("Digite um número entre 0, 1 ou 2");
//        }
//    } while(a != 0);
    }

    public static void gravaArquivoCsv(ListaObj<Agendamento> list, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        boolean bool = false;

        nomeArq += ".csv";

        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException io) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
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
            bool = true;
        } finally {
            saida.close();

            try {
                arq.close();
            } catch (IOException io) {
                System.out.println("Erro ao fechar o arquivo");
                bool = true;
            }

            if (bool) {
                System.exit(1);
            }
        }
        leArquivoCsv(nomeArq);
    }

    public static void leArquivoCsv(String nomeArq) {
        FileReader arq = null;
        Scanner entrada = null;
        boolean bool = false;

        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        } catch (FileNotFoundException fn) {
            System.out.println("Arquivo não encontrado");
            System.exit(1);
        }

        try {
            System.out.printf("%-4S %-11S %-20S %-20S %-15S %-15S %-15S %-1S %-10S %-6S\n",
                    "id", "dia/horario", "cliente", "email", "pet", "especie", "raca", "sexo", "servico", "valor");

            while (entrada.hasNext()) {
                int id = entrada.nextInt();
                String diaHorario = entrada.next();
                //LocalDate.parse(diaHorario);
                String cliente = entrada.next();
                String email = entrada.next();
                String pet = entrada.next();
                int especie = entrada.nextInt();
                int raca = entrada.nextInt();
                String sexo = entrada.next();
                int servico = entrada.nextInt();
                double valor = entrada.nextDouble();

                System.out.printf("%04d %-11S %-20S %-20S %-15S %-15d %-15d %-1S %-10d %-6.2f\n",
                        id, diaHorario, cliente, email, pet, especie, raca, sexo, servico, valor);
            }

        } catch (NoSuchElementException ns) {
            System.out.println("Arquivo com problema");
            bool = true;
        } catch (IllegalStateException is) {
            System.out.println("Erro na leitura do arquivo");
            bool = true;
        } finally {
            entrada.close();

            try {
                arq.close();
            } catch (IOException io) {
                System.out.println("Erro ao fechar o arquivo");
                bool = true;
            }

            if (bool) {
                System.exit(1);
            }
        }

    }

    @GetMapping("/report/{usuario}")
    @ApiResponse(responseCode = "204", description =
            "Não há petshops com esse identificador.", content = @Content(schema = @Schema(hidden = true)))
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos em ordem crescente de data.")
    public ResponseEntity<ListaObj<Agendamento>> ordenarAgendamentosPorData(@PathVariable Integer usuario) {

        Optional<UsuarioPetshop> usuarioPetshopOptional = petshopRepository.findById(usuario);

        if (usuarioPetshopOptional.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        UsuarioPetshop usuarioPetshop = usuarioPetshopOptional.get();

        List<Agendamento> listaAgendamentos = usuarioPetshop.getAgendamentos();

        ListaObj<Agendamento> listaLocal = new ListaObj<>(listaAgendamentos.size());

        if (listaAgendamentos.size() == 0) {
            return ResponseEntity.status(204).build();
        }

        for (int i = 0; i < listaAgendamentos.size(); i++) {
            listaLocal.adiciona(listaAgendamentos.get(i));
        }

        int i, j, indMenor;
        for (i = 0; i < listaAgendamentos.size() - 1; i++) {
            indMenor = i;
            for (j = i + 1; j < listaAgendamentos.size(); j++) {
                if (listaLocal.getElemento(j).getDataHora().isBefore(listaLocal.getElemento(indMenor).getDataHora())) {
                    indMenor = j;
                }
            }
            Agendamento ag = listaLocal.getElemento(indMenor);
            //ag = listaLocal.getElemento(i);
            listaLocal.removeDeixaNulo(indMenor);
            listaLocal.adicionaNoNulo(indMenor, listaLocal.getElemento(i));
            listaLocal.removeDeixaNulo(i);
            listaLocal.adicionaNoNulo(i, ag);
        }


//        for (int i = 0; i < listaLocal.getTamanho() - 1; i++) {
//            int aux = i;
//            for (int j = i + 1; j < listaLocal.getTamanho() - 1; j++) {
//                if (listaLocal.getElemento(j).getDataHora().isBefore(listaLocal.getElemento(aux).getDataHora())) {
//                    aux = j;
//                }
//            }
//            Agendamento ag = listaLocal.getElemento(aux);
//            //ag = listaLocal.getElemento(i);
//            listaLocal.removeDeixaNulo(aux);
//            listaLocal.adicionaNoNulo(aux, listaLocal.getElemento(i));
//            listaLocal.removeDeixaNulo(i);
//            listaLocal.adicionaNoNulo(i, ag);
//
//        }

        return ResponseEntity.status(200).body(listaLocal);
    }
}
