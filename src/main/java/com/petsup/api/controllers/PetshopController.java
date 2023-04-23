package com.petsup.api.controllers;

import com.petsup.api.entities.Agendamento;
import com.petsup.api.entities.ListaObj;
import com.petsup.api.entities.usuario.Usuario;
import com.petsup.api.entities.usuario.UsuarioPetshop;
import com.petsup.api.repositories.PetshopRepository;
import com.petsup.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PetshopController {

    @Autowired
    private PetshopRepository petshopRepository;
    
    @GetMapping("/report")
    public ResponseEntity<Void> report(@RequestBody Usuario usuario){
        if(usuario instanceof UsuarioPetshop){
            List<Agendamento> as = petshopRepository.findByAgendamentos(usuario);
            leituraNomeCsv(as.size());
        }
        return ResponseEntity.status(401).build();
    }

//    ListaObj<Agendamento> lista = new ListaObj(10);
//    Scanner leitorStr = new Scanner(System.in);
//    Scanner leitorNum = new Scanner(System.in);
//    int a = 0;

    public static void leituraNomeCsv(int tamLista){
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
                        a.getFkPet().getNome(), a.getFkPet().getEspecie(), a.getFkPet().getRaca(),
                        a.getFkPet().getSexo(), a.getFkServico(), a.getFkServico().getPreco());
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
    }

    public static void leArquivoCsv(String nomeArq) {
        FileReader arq = null;
        Scanner entrada = null;
        boolean bool = false;

        nomeArq += ".csv";

        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        } catch (FileNotFoundException fn) {
            System.out.println("Arquivo não encontrado");
            System.exit(1);
        }

        try {
            System.out.printf("%-4S %-11S %-20S %-20S %-15 %-15S %-15S %-1S %-10S %-6S\n",
                    "id", "dia/horario", "cliente", "email", "pet", "especie", "raca", "sexo", "servico", "valor");

            while (entrada.hasNext()) {
                int id = entrada.nextInt();
                String diaHorario = entrada.next();
                String cliente = entrada.next();
                String email = entrada.next();
                String pet = entrada.next();
                String especie = entrada.next();
                String sexo = entrada.next();
                String servico = entrada.next();
                double valor = entrada.nextDouble();

                System.out.printf("%04d  %-11S %-20S %-20S %-15 %-15S %-15S %-1S %-10S %-6.2f\n",
                        id, diaHorario, cliente, email, pet, especie, sexo, servico, valor);
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

    @GetMapping
    public ResponseEntity<List<Agendamento>> ordenarAgendamentosPorData(List<Agendamento> agendamentos) {

        List<Agendamento> listaLocal = agendamentos;

        for (int i = 0; i < listaLocal.size(); i++) {
            if (i < listaLocal.size()) {
                System.out.print(listaLocal.get(i) + ", ");
            } else {
                System.out.print(listaLocal.get(i));
            }
        }

        for (int i = 0; i < listaLocal.size(); i++) {
            int aux = i;
            for (int j = i + 1; j < listaLocal.size(); j++) {
                if (listaLocal.get(j).getDataHora().isBefore(listaLocal.get(aux).getDataHora())) {
                    aux = j;
                }
            }
            Agendamento ag = listaLocal.get(aux);

            ag = listaLocal.get(i);
            listaLocal.set(aux, listaLocal.get(i));
            listaLocal.set(i, ag);
        }
        
        for (int i = 0; i < listaLocal.size(); i++) {
            if (i < listaLocal.size()) {
                System.out.print(listaLocal.get(i) + ", ");
            } else {
                System.out.print(listaLocal.get(i));
            }
        }

        if (listaLocal.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(listaLocal);
    }
}
