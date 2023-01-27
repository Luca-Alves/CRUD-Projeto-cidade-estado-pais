package controller.estado;

import Constantes.Constantes;
import model.cidade.DAO.CidadeDAO;
import model.cidade.DAO.PersistenciaTipo;
import model.cidade.DAO.impl.CidadeBinaryDAO;
import model.cidade.DAO.impl.CidadeXMLDAO;
import model.estado.DAO.EstadoDAO;
import model.estado.DAO.PersistenciaTipoEstado;
import model.estado.DAO.impl.EstadoBinaryDAO;
import model.estado.DAO.impl.EstadoXMLDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EstadoDAOFactory {

                private static final EstadoDAOFactory INSTANCE = new EstadoDAOFactory();
                private EstadoDAOFactory() {
                }
                public static EstadoDAOFactory getInstance(){
                    return INSTANCE;
                }

                private PersistenciaTipoEstado tipo;

                private static final String PERSISTENCIA_TIPO_ESTADO = "estado.persistencia.tipo";



public EstadoDAO create(){

    if (tipo==null){
        carregarTipoPersistencia();
    }

    EstadoDAO estadoDAO = null;

    if (tipo == PersistenciaTipoEstado.XML){
        estadoDAO = new EstadoXMLDAO();
    } else if (tipo == PersistenciaTipoEstado.BI){
        estadoDAO = new EstadoBinaryDAO();
    }

    return estadoDAO;
}

    private void carregarTipoPersistencia(){

        try {
            Properties properties =  new Properties();
            properties.load(new FileInputStream(Constantes.ARQUIVO_PROPRIEDADES));

            String valorNoArquivo = properties.getProperty(PERSISTENCIA_TIPO_ESTADO);
            tipo = PersistenciaTipoEstado.valueOf(valorNoArquivo);
        } catch (IOException ex){
            throw new RuntimeException("não foi possivel ler o arquivo", ex);
        }


    }

}
