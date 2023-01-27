package view.menu;

import controller.cidade.CidadeController;
import controller.cidade.CidadeControllerFactory;
import controller.estado.EstadoController;
import controller.estado.EstadoControllerFactory;
import controller.pais.PaisController;
import controller.pais.PaisControllerFactory;
import view.CidadesView;
import view.EstadosView;
import view.PaisView;

import java.util.Scanner;

public class MenuView {

    public void menu(){


        System.out.println("Qual CRUD deseja acessar?");
        System.out.println("[1] CIDADES");
        System.out.println("[2] ESTADOS");
        System.out.println("[3] PAÍSES");
        System.out.println("[0] ENCERRAR PROGRAMA");

        int resposta = new Scanner(System.in).nextInt();

        switch (resposta){
            case 1:
                CidadeController cidadeController = CidadeControllerFactory.getInstance().create();
                CidadesView cidadesView =  new CidadesView(cidadeController, new Scanner(System.in));

                cidadesView.menu();
                break;
            case 2:
                EstadoController estadoController = EstadoControllerFactory.getInstance().create();
                EstadosView estadosView = new EstadosView(estadoController, new Scanner(System.in));

                estadosView.menu();
                break;
            case 3:

                PaisController paisController = PaisControllerFactory.getInstance().create();
                PaisView paisView = new PaisView(paisController, new Scanner(System.in));

                paisView.menu();
                break;
            case 0:
                System.exit(0);
                break;





        }


    }

}
