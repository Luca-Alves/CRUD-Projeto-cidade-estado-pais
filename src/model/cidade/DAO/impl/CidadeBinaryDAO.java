package model.cidade.DAO.impl;

import model.cidade.Cidade;
import model.cidade.DAO.CidadeDAO;
import model.cidade.DAO.DAOException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CidadeBinaryDAO implements CidadeDAO {

    private String diretorioRaiz = "database/binario";
    private String diretorioCidade = diretorioRaiz + "/cidades";





    @Override
    public void cadastrar(Cidade cidade) {

        Path diretorio = Paths.get(diretorioCidade);
        if (!diretorio.toFile().exists()){
            throw new RuntimeException("diretorio n existe");
            }

        File file = new File (diretorio.toAbsolutePath().toString(),
                        cidade.getId().toString() + ".luk");

        try (FileOutputStream fileOS = new FileOutputStream(file);
             ObjectOutputStream objectOS = new ObjectOutputStream(fileOS)) {
            objectOS.writeObject(cidade);
            objectOS.flush();
        } catch (IOException ex){
            throw new DAOException("Falha ao trabalhar os arquivos", ex);
        }


    }





    @Override
    public List<Cidade> listar() {

        FilenameFilter filter = (dir, nome) -> nome.endsWith(".luk");

        List<Cidade> cidades = new ArrayList<>();
        File diretorio = new File(diretorioCidade);
    for (File arquivo : diretorio.listFiles(filter)){
    try (FileInputStream fileIS = new FileInputStream(arquivo);
        ObjectInputStream objectIS =  new ObjectInputStream(fileIS)){
    Object obj = objectIS.readObject();
    if (obj instanceof Cidade){
    Cidade cidade = (Cidade) obj;
    cidades.add(cidade);
}
    } catch (IOException | ClassNotFoundException ex){
        throw new DAOException("Falha", ex);
    }
}
        return cidades;
    }

    @Override
    public Cidade buscar(UUID id) {
        File arquivo = new File(diretorioCidade, id.toString() + ".luk");
        try (FileInputStream fileIS = new FileInputStream(arquivo);
                ObjectInputStream objectIS =  new ObjectInputStream(fileIS)){
                Object obj = objectIS.readObject();
                if (obj instanceof Cidade){
                    return (Cidade) obj;
                } else {
                    return null;

                }
        } catch (IOException | ClassNotFoundException ex) {
            throw new DAOException("Falha na leitura do arquivo " + arquivo.getAbsolutePath(), ex);
        }

    }

    @Override
    public void atualizar(UUID id, Cidade cidade) {
        File file = new File(diretorioCidade, id.toString() + ".luk");

        try (FileOutputStream fileOS = new FileOutputStream(file);
            ObjectOutputStream objectOS = new ObjectOutputStream(fileOS)){
                objectOS.writeObject(cidade);
                objectOS.flush();
        } catch (IOException ex){
            throw new DAOException("Falha ao trabalhar os arquivos", ex);
        }


    }

    @Override
    public Cidade apagar(UUID id) {
            Cidade cidade = buscar(id);
            if (cidade != null){
                File arquivo = new File(diretorioCidade, id.toString() + ".luk");
                arquivo.delete();
            }
            return cidade;


         }
}
