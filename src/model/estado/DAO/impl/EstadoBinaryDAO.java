package model.estado.DAO.impl;

import model.cidade.Cidade;
import model.cidade.DAO.DAOException;
import model.estado.DAO.EstadoDAO;
import model.estado.Estado;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EstadoBinaryDAO implements EstadoDAO {

    private String diretorioRaiz = "database/binario";
    private String diretorioEstado = diretorioRaiz + "/estados";





    @Override
    public void cadastrar(Estado estado) {



        Path diretorio = Paths.get(diretorioEstado);
        if (!diretorio.toFile().exists()){
            throw new RuntimeException("diretorio n existe");
        }

        File file = new File (diretorio.toAbsolutePath().toString(),
                estado.getId().toString() + ".luk");

        try (FileOutputStream fileOS = new FileOutputStream(file);
             ObjectOutputStream objectOS = new ObjectOutputStream(fileOS)) {
            objectOS.writeObject(estado);
            objectOS.flush();
        } catch (IOException ex){
            throw new DAOException("Falha ao trabalhar os arquivos", ex);
        }




    }

    @Override
    public List<Estado> listar() {

        FilenameFilter filter = (dir, nome) -> nome.endsWith(".luk");

        List<Estado> estados = new ArrayList<>();
        File diretorio = new File(diretorioEstado);
        for (File arquivo : diretorio.listFiles(filter)){
            try (FileInputStream fileIS = new FileInputStream(arquivo);
                 ObjectInputStream objectIS =  new ObjectInputStream(fileIS)){
                Object obj = objectIS.readObject();
                if (obj instanceof Estado){
                    Estado estado = (Estado) obj;
                    estados.add(estado);
                }
            } catch (IOException | ClassNotFoundException ex){
                throw new DAOException("Falha", ex);
            }
        }
        return estados;
    }

    @Override
    public Estado buscar(UUID id) {

        File arquivo = new File(diretorioEstado, id.toString() + ".luk");
        try (FileInputStream fileIS = new FileInputStream(arquivo);
             ObjectInputStream objectIS =  new ObjectInputStream(fileIS)){
            Object obj = objectIS.readObject();
            if (obj instanceof Estado){
                return (Estado) obj;
            } else {
                return null;

            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new DAOException("Falha na leitura do arquivo " + arquivo.getAbsolutePath(), ex);
        }

    }

    @Override
    public void atualizar(UUID id, Estado estado) {

        File file = new File(diretorioEstado, id.toString() + ".luk");

        try (FileOutputStream fileOS = new FileOutputStream(file);
             ObjectOutputStream objectOS = new ObjectOutputStream(fileOS)){
            objectOS.writeObject(estado);
            objectOS.flush();
        } catch (IOException ex){
            throw new DAOException("Falha ao trabalhar os arquivos", ex);
        }


    }

    @Override
    public Estado apagar(UUID id) {

        Estado estado = buscar(id);
        if (estado != null){
            File arquivo = new File(diretorioEstado, id.toString() + ".luk");
            arquivo.delete();
        }
        return estado;

    }
}
