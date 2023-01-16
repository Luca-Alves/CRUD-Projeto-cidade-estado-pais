package view;

import controller.CidadeController;
import model.Cidade;

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
        String nome;
        nome = scanner.next();
        cidade.setNome(nome);

        System.out.println("Digite o nome do Estado que a cidade pertence: ");
        String estado;
        estado = scanner.next();
        cidade.setEstado(estado);

        controller.create(cidade);
        System.out.println("Cadastro realizado! Digite qualquer tecla para ir ao menu.");
        scanner.next();
    }

    public void listar(){
        List<Cidade> cidades = controller.listar();
        for (int i = 0; i < cidades.size(); i++) {
            System.out.print((i + 1) + " -> ");
            exibirCidade(cidades.get(i));
        }
    }

    public void exibirCidade(Cidade cidade){
        System.out.println("NOME : "+cidade.getNome() + "|| ESTADO: " + cidade.getEstado());

    }

    public void menu(){

        System.out.println("--QUAL OPÇÃO DESEJA?--");
        System.out.println("[1] - Para CADASTRAR uma nova cidade.");
        System.out.println("[2] - Para LISTAR todas as cidades cadastradas.");
        System.out.println("[0] - Para SAIR.");
        int res = scanner.nextInt();
        switch (res){
            case 1:
                create();
                break;
            case 2:
                listar();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("OPÇÃO INVÁLIDA!");
        }
        menu();
    }
}
