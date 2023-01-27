package controller.cidade;

import Constantes.Constantes;
import controller.cidade.impl.CidadeControllerArmazDefinitivo;
import controller.cidade.impl.CidadeControllerArmazVolatil;
import model.cidade.DAO.CidadeDAO;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CidadeControllerFactory {

                private static final CidadeControllerFactory INSTANCE = new CidadeControllerFactory();
                private CidadeControllerFactory(){

                }
                public static CidadeControllerFactory getInstance(){
                        return INSTANCE;
                }

                private static final String CHAVE_CONTROLLER_TIPO = "cidade.controller.tipo";


private CidadeArmazTipo tipo;



    public CidadeController create()
    {
        if (tipo == null){
            carregarTipoArmaz();
        }

        CidadeDAOFactory daoFactory = CidadeDAOFactory.getInstance();
        CidadeController controller =  null;


    if (tipo == CidadeArmazTipo.VOLATIL){
        controller = new CidadeControllerArmazVolatil();
    }
    else if (tipo== CidadeArmazTipo.DEFINITIVO){
        CidadeDAO cidadeDAO = daoFactory.create();
        controller = new CidadeControllerArmazDefinitivo(
                cidadeDAO);
    }
    else {
    throw new RuntimeException("Não Encontrado!");
    }

return controller;
}

private void carregarTipoArmaz(){

        try {
            Properties properties =new Properties();
            properties.load(new FileInputStream(Constantes.ARQUIVO_PROPRIEDADES));

            String valorDoArquivo = properties.getProperty(CHAVE_CONTROLLER_TIPO);
            tipo = CidadeArmazTipo.valueOf(valorDoArquivo);

        } catch (IOException ex){
            throw new RuntimeException("ERRO", ex);
        }

}


}
