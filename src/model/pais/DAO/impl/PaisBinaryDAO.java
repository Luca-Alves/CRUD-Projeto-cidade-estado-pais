package model.pais.DAO.impl;

import model.cidade.DAO.DAOException;
import model.estado.Estado;
import model.pais.DAO.PaisDAO;
import model.pais.Pais;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PaisBinaryDAO implements PaisDAO {

    private String diretorioRaiz = "database/binario";
    private String diretorioPais = diretorioRaiz + "/paises";





    @Override
    public void cadastrar(Pais pais) {



        Path diretorio = Paths.get(diretorioPais);
        if (!diretorio.toFile().exists()){
            throw new RuntimeException("diretorio não existe");
        }

        File file = new File (diretorio.toAbsolutePath().toString(),
                pais.getId().toString() + ".luk");

        try (FileOutputStream fileOS = new FileOutputStream(file);
             ObjectOutputStream objectOS = new ObjectOutputStream(fileOS)) {
            objectOS.writeObject(pais);
            objectOS.flush();
        } catch (IOException ex){
            throw new DAOException("Falha ao trabalhar os arquivos", ex);
        }




    }

    @Override
    public List<Pais> listar() {


        FilenameFilter filter = (dir, nome) -> nome.endsWith(".luk");

        List<Pais> paises = new ArrayList<>();
        File diretorio = new File(diretorioPais);
        for (File arquivo : diretorio.listFiles(filter)){
            try (FileInputStream fileIS = new FileInputStream(arquivo);
                 ObjectInputStream objectIS =  new ObjectInputStream(fileIS)){
                Object obj = objectIS.readObject();
                if (obj instanceof Pais){
                    Pais pais = (Pais) obj;
                    paises.add(pais);
                }
            } catch (IOException | ClassNotFoundException ex){
                throw new DAOException("Falha", ex);
            }
        }
        return paises;
    }

    @Override
    public Pais buscar(UUID id) {


        File arquivo = new File(diretorioPais, id.toString() + ".luk");
        try (FileInputStream fileIS = new FileInputStream(arquivo);
             ObjectInputStream objectIS =  new ObjectInputStream(fileIS)){
            Object obj = objectIS.readObject();
            if (obj instanceof Pais){
                return (Pais) obj;
            } else {
                return null;

            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new DAOException("Falha na leitura do arquivo " + arquivo.getAbsolutePath(), ex);
        }

    }

    @Override
    public void atualizar(UUID id, Pais pais) {


        File file = new File(diretorioPais, id.toString() + ".luk");

        try (FileOutputStream fileOS = new FileOutputStream(file);
             ObjectOutputStream objectOS = new ObjectOutputStream(fileOS)){
            objectOS.writeObject(pais);
            objectOS.flush();
        } catch (IOException ex){
            throw new DAOException("Falha ao trabalhar os arquivos", ex);
        }


    }

    @Override
    public Pais apagar(UUID id) {


        Pais pais = buscar(id);
        if (pais != null){
            File arquivo = new File(diretorioPais, id.toString() + ".luk");
            arquivo.delete();
        }
        return pais;

    }
}
