package controller.pais;

import Constantes.Constantes;
import model.estado.DAO.EstadoDAO;
import model.estado.DAO.PersistenciaTipoEstado;
import model.estado.DAO.impl.EstadoBinaryDAO;
import model.estado.DAO.impl.EstadoXMLDAO;
import model.pais.DAO.PaisDAO;
import model.pais.DAO.PersistenciaTipoPais;
import model.pais.DAO.impl.PaisBinaryDAO;
import model.pais.DAO.impl.PaisXMLDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PaisDAOFactory {

                        private static final PaisDAOFactory INSTANCE = new PaisDAOFactory();
                        private PaisDAOFactory() {

                        }
                        public static PaisDAOFactory getInstance() {
                            return INSTANCE;
                        }
                        private PersistenciaTipoPais tipo;
                        private static final String PERSISTENCIA_TIPO_PAIS = "pais.persistencia.tipo";




 public PaisDAO create(){


     if (tipo==null){
         carregarTipoPersistencia();
     }

     PaisDAO paisDAO = null;

     if (tipo == PersistenciaTipoPais.XML){
         paisDAO = new PaisXMLDAO();
     } else if (tipo == PersistenciaTipoPais.BI){
         paisDAO = new PaisBinaryDAO();
     }

     return paisDAO;
 }


    private void carregarTipoPersistencia(){

        try {
            Properties properties =  new Properties();
            properties.load(new FileInputStream(Constantes.ARQUIVO_PROPRIEDADES));

            String valorNoArquivo = properties.getProperty(PERSISTENCIA_TIPO_PAIS);
            tipo = PersistenciaTipoPais.valueOf(valorNoArquivo);
        } catch (IOException ex){
            throw new RuntimeException("não foi possivel ler o arquivo", ex);
        }


    }


}
