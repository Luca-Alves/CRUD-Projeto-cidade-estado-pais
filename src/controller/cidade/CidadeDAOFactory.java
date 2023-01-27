package controller.cidade;

import Constantes.Constantes;
import model.cidade.DAO.CidadeDAO;
import model.cidade.DAO.PersistenciaTipo;
import model.cidade.DAO.impl.CidadeBinaryDAO;
import model.cidade.DAO.impl.CidadeXMLDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CidadeDAOFactory {

    private static final String PERSISTENCIA_TIPO = "cidade.persistencia.tipo";


private PersistenciaTipo tipo;





            private static final CidadeDAOFactory INSTANCE = new CidadeDAOFactory();
            private CidadeDAOFactory(){
            }
            public static CidadeDAOFactory getInstance(){
            return INSTANCE;
            }



    public CidadeDAO create(){
        if (tipo==null){
        carregarTipoPersistencia();
        }

        CidadeDAO cidadeDAO = null;

        if (tipo == PersistenciaTipo.XML){
        cidadeDAO = new CidadeXMLDAO();
        } else if (tipo == PersistenciaTipo.BI){
            cidadeDAO = new CidadeBinaryDAO();
        }

        return cidadeDAO;
    }


    private void carregarTipoPersistencia(){

       try {
           Properties properties =  new Properties();
           properties.load(new FileInputStream(Constantes.ARQUIVO_PROPRIEDADES));

           String valorNoArquivo = properties.getProperty(PERSISTENCIA_TIPO);
            tipo = PersistenciaTipo.valueOf(valorNoArquivo);
       } catch (IOException ex){
           throw new RuntimeException("não foi possivel ler o arquivo", ex);
       }


    }

}
