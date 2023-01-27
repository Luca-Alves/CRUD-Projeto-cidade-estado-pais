package view;

import controller.estado.exception.EstadoNaoEncontrado;
import controller.pais.PaisController;
import model.estado.Estado;
import model.pais.Pais;
import view.menu.MenuView;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PaisView {

                    private PaisController paisController;
                    private Scanner scanner;

                    public PaisView(PaisController paisController, Scanner scanner) {
                        this.paisController = paisController;
                        this.scanner = scanner;
                    }


    public void create(){

        Pais pais = new Pais();


        System.out.println("Digite o nome do Pais: ");
        scanner.nextLine();
        String nome;
        nome = scanner.nextLine();
        pais.setNome(nome);

        System.out.println("Digite a sigla do Pais: ");
        String sigla;
        sigla = scanner.nextLine();
        pais.setSigla(sigla);



        paisController.create(pais);
        System.out.println("Cadastro realizado! Digite qualquer tecla para ir ao menu.\n\n");
    }

    public void listar(){
        List<Pais> paises = paisController.listar();
        for (int i = 0; i < paises.size(); i++) {
            System.out.print((i + 1) + " -> ");
            exibirPais(paises.get(i));
        }
    }
    public void exibirPais(Pais pais){
        System.out.println("NOME : "+pais.getNome() + "  ||  SIGLA: " + pais.getSigla());

    }

    public void atualizar1(){
        listar();
        System.out.println("Digite o número do país que deseja atualizar:");
        Integer numCad = scanner.nextInt();
        Pais pais = paisController.listar().get(numCad - 1);
        atualizar2(pais);
    }

    public void atualizar2(Pais pais){
        exibirPais(pais);

        System.out.println("Informe o novo nome:");
        scanner.nextLine();
        String nome = scanner.nextLine();
        pais.setNome(nome);

        System.out.println("Informe a nova sigla:");
        String sigla = scanner.nextLine();
        pais.setSigla(sigla);


        try {
            paisController.update(pais.getId(),pais);
        } catch (EstadoNaoEncontrado ex){
            System.out.println("País Não Existe!");
        }

    }

    public void apagar(){
        listar();
        System.out.println("Informe o ID para excluir:");
        Integer numId = scanner.nextInt();
        scanner.nextLine();
        Pais pais = paisController.listar().get(numId - 1);
        apagar2(pais.getId());
    }
    public void apagar2(UUID id){
        try {
            Pais pais = paisController.delete(id);
            System.out.println("País Apagado:");
            exibirPais(pais);
        } catch (EstadoNaoEncontrado ex){
            System.out.println("País Não Localizado");
        }
    }


    public void menu(){

        System.out.println("--QUAL OPÇÃO DESEJA?--");
        System.out.println("[1] - Para CADASTRAR um novo país.");
        System.out.println("[2] - Para LISTAR todos os países cadastrados.");
        System.out.println("[3] - Para ATUALIZAR um país cadastrado.");
        System.out.println("[4] - Para APAGAR um país cadastrado.");
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
