package controller.estado;

import Constantes.Constantes;

import controller.estado.impl.EstadoControllerArmazDefinitivo;

import model.estado.DAO.EstadoDAO;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EstadoControllerFactory {

    private static final EstadoControllerFactory INSTANCE = new EstadoControllerFactory();
    private EstadoControllerFactory(){

    }
    public static EstadoControllerFactory getInstance(){
        return INSTANCE;
    }

    private static final String CHAVE_CONTROLLER_TIPO = "estado.controller.tipo";


    private EstadoArmazTipo tipo;



    public EstadoController create()
    {
        if (tipo == null){
            carregarTipoArmaz();
        }

        EstadoDAOFactory daoFactory = EstadoDAOFactory.getInstance();
        EstadoController controller =  null;


        if (tipo == EstadoArmazTipo.VOLATIL){
            EstadoDAO estadoDAO = daoFactory.create();
            controller = new EstadoControllerArmazDefinitivo(estadoDAO);
        }
        else if (tipo== EstadoArmazTipo.DEFINITIVO){
            EstadoDAO estadoDAO = daoFactory.create();
            controller = new EstadoControllerArmazDefinitivo(
                    estadoDAO);
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
            tipo = EstadoArmazTipo.valueOf(valorDoArquivo);

        } catch (IOException ex){
            throw new RuntimeException("ERRO", ex);
        }

    }


}
