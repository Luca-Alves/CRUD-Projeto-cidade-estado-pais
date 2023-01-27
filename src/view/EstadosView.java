package view;

import controller.estado.EstadoController;
import controller.estado.exception.EstadoNaoEncontrado;
import model.estado.Estado;
import view.menu.MenuView;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class EstadosView {

    private EstadoController controller;
    private Scanner scanner;

    public EstadosView(EstadoController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }


    public void create(){

        Estado estado = new Estado();
        System.out.println("Digite o nome do Estado: ");
        scanner.nextLine();
        String nome;
        nome = scanner.nextLine();
        estado.setNome(nome);

        System.out.println("Digite a sigla do Estado: ");
        String sigla;
        sigla = scanner.nextLine();
        estado.setSigla(sigla);

        System.out.println("Digite o nome do pais que o estado pertence: ");
        String pais;
        pais = scanner.nextLine();
        estado.setPais(pais);


        controller.create(estado);
        System.out.println("Cadastro realizado! Digite qualquer tecla para ir ao menu.\n\n");

    }


    public void listar(){
        List<Estado> estados = controller.listar();
        for (int i = 0; i < estados.size(); i++) {
            System.out.print((i + 1) + " -> ");
            exibirEstado(estados.get(i));
        }
    }
    public void exibirEstado(Estado estado){
        System.out.println("NOME : "+estado.getNome() + "  ||  SIGLA: " + estado.getSigla() +
                "  ||  PAÍS: " + estado.getPais());

    }

    public void atualizar1(){
        listar();
        System.out.println("Digite o número do estado que deseja atualizar:");
        Integer numCad = scanner.nextInt();
        Estado estado = controller.listar().get(numCad - 1);
        atualizar2(estado);
    }

    public void atualizar2(Estado estado){
        exibirEstado(estado);

        System.out.println("Informe o novo nome:");
        scanner.nextLine();
        String nome = scanner.nextLine();
        estado.setNome(nome);

        System.out.println("Informe a nova sigla:");
        String sigla = scanner.nextLine();
        estado.setSigla(sigla);

        System.out.println("Informe o novo país:");
        String pais = scanner.nextLine();
        estado.setPais(pais);

        try {
            controller.update(estado.getId(),estado);
        } catch (EstadoNaoEncontrado ex){
            System.out.println("Estado Não Existe!");
        }

    }

    public void apagar(){
        listar();
        System.out.println("Informe o ID para excluir:");
        Integer numId = scanner.nextInt();
        scanner.nextLine();
        Estado estado = controller.listar().get(numId - 1);
        apagar2(estado.getId());
    }
    public void apagar2(UUID id){
        try {
            Estado estado = controller.delete(id);
            System.out.println("Estado Apagado:");
            exibirEstado(estado);
        } catch (EstadoNaoEncontrado ex){
            System.out.println("Estado Não Localizado");
        }
    }


    public void menu(){

        System.out.println("--QUAL OPÇÃO DESEJA?--");
        System.out.println("[1] - Para CADASTRAR um novo estado.");
        System.out.println("[2] - Para LISTAR todos os estados cadastrados.");
        System.out.println("[3] - Para ATUALIZAR um estado cadastrado.");
        System.out.println("[4] - Para APAGAR um estado cadastrado.");
        System.out.println("[0] - Para VOLTAR.");
        int res = scanner.nextInt();
        switch (res){
            case 1:

                create();

                break;
            case 2:
                listar();
                break;
            case 3:
                atualizar1();
                break;
            case 4:
                apagar();
                break;
            case 0:
                MenuView menuView = new MenuView();
                menuView.menu();
                break;
            default:
                System.out.println("OPÇÃO INVÁLIDA!");
        }
        menu();
    }



}
