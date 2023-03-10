package model.estado.DAO;

import model.cidade.Cidade;
import model.estado.Estado;

import java.util.List;
import java.util.UUID;

public interface EstadoDAO {

    void cadastrar(Estado estado);

    List<Estado> listar();

    Estado buscar (UUID id);

    void atualizar (UUID id, Estado estado);

    Estado apagar (UUID id);

}
