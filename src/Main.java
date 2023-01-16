import controller.impl.CidadeControllerArmaz;
import view.CidadesView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        CidadesView view =  new CidadesView(new CidadeControllerArmaz(),new Scanner(System.in));
        view.menu();

    }
}
