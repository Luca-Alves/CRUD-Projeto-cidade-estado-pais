package controller.pais;

import Constantes.Constantes;
import controller.estado.EstadoArmazTipo;
import controller.estado.impl.EstadoControllerArmazDefinitivo;
import controller.pais.impl.PaisControllerArmazDefinitivo;
import model.estado.DAO.EstadoDAO;
import model.pais.DAO.PaisDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PaisControllerFactory {

                        private static final PaisControllerFactory INSTANCE = new PaisControllerFactory();
                        private PaisControllerFactory(){

                        }
                        public static PaisControllerFactory getInstance() {
                            return INSTANCE;
                        }
                        private static final String CHAVE_CONTROLLER_TIPO = "pais.controller.tipo";
                        private PaisArmazTipo tipo;



  public PaisController create(){

      if (tipo == null){
          carregarTipoArmaz();
      }

      PaisDAOFactory daoFactory = PaisDAOFactory.getInstance();
      PaisController controller =  null;


      if (tipo == PaisArmazTipo.VOLATIL){
          PaisDAO paisDAO = daoFactory.create();
          controller = new PaisControllerArmazDefinitivo(paisDAO);
      }
      else if (tipo== PaisArmazTipo.DEFINITIVO){
          PaisDAO paisDAO = daoFactory.create();
          controller = new PaisControllerArmazDefinitivo(paisDAO);
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
            tipo = PaisArmazTipo.valueOf(valorDoArquivo);

        } catch (IOException ex){
            throw new RuntimeException("ERRO", ex);
        }

    }



}
