package model.pais.DAO;

import model.estado.Estado;
import model.pais.Pais;

import java.util.List;
import java.util.UUID;

public interface PaisDAO {

    void cadastrar(Pais pais);

    List<Pais> listar();

    Pais buscar (UUID id);

    void atualizar (UUID id, Pais pais);

    Pais apagar (UUID id);


}
