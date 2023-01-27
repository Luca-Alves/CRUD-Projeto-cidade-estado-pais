package view;

import controller.cidade.CidadeController;
import controller.cidade.exception.CidadeNaoEncontrada;
import model.cidade.Cidade;
import view.menu.MenuView;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CidadesView {

    private CidadeController controller;
    private Scanner scanner;

    public CidadesView(CidadeController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void create() {
        Cidade cidade = new Cidade();
        System.out.println("Digite o nome da Cidade: ");
        scanner.nextLine();
        String nome;
        nome = scanner.nextLine();
        cidade.setNome(nome);


        System.out.println("Digite o nome do Estado que a cidade pertence: ");
        String estado;
        estado = scanner.nextLine();
        cidade.setEstado(estado);

        controller.create(cidade);
        System.out.println("Cadastro realizado! Digite qualquer tecla para ir ao menu.\n\n");
    }

    public void listar(){
        List<Cidade> cidades = controller.listar();
        for (int i = 0; i < cidades.size(); i++) {
            System.out.print((i + 1) + " -> ");
            exibirCidade(cidades.get(i));
        }
    }

    public void exibirCidade(Cidade cidade){
        System.out.println("NOME : "+cidade.getNome() + "  ||  ESTADO: " + cidade.getEstado());

    }

    public void atualizar1(){
        listar();
        System.out.println("Digite o número do cliente que deseja atualizar:");
        Integer numCad = scanner.nextInt();
        Cidade cidade = controller.listar().get(numCad - 1);
        atualizar2(cidade);
    }

    public void atualizar2(Cidade cidade){
        exibirCidade(cidade);

        System.out.println("Informe o novo nome:");
        scanner.nextLine();
        String nome = scanner.nextLine();
        cidade.setNome(nome);

        System.out.println("Informe o novo estado:");
        String estado = scanner.nextLine();
        cidade.setEstado(estado);

        try {
            controller.update(cidade.getId(),cidade);
        } catch (CidadeNaoEncontrada ex){
            System.out.println("Cidade Não Existe!");
        }

    }

    public void apagar(){
        listar();
        System.out.println("Informe o ID para excluir:");
        Integer numId = scanner.nextInt();
        scanner.nextLine();
        Cidade cidade = controller.listar().get(numId - 1);
        apagar2(cidade.getId());
    }
    public void apagar2(UUID id){
        try {
            Cidade cidade = controller.delete(id);
            System.out.println("Cidade Apagada:");
            exibirCidade(cidade);
        } catch (CidadeNaoEncontrada ex){
            System.out.println("Cidade Não Localizada");
        }
    }
    public void menu(){

        System.out.println("--QUAL OPÇÃO DESEJA?--");
        System.out.println("[1] - Para CADASTRAR uma nova cidade.");
        System.out.println("[2] - Para LISTAR todas as cidades cadastradas.");
        System.out.println("[3] - Para ATUALIZAR uma cidade cadastrada.");
        System.out.println("[4] - Para APAGAR uma cidade cadastrada.");
        System.out.println("[0] - Para SAIR.");
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
