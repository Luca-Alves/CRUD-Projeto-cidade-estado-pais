package controller.estado;

import model.cidade.Cidade;
import model.estado.Estado;

import java.util.List;
import java.util.UUID;

public interface EstadoController {

    void create(Estado estado);

    List<Estado> listar();

    Estado ler(UUID id);

    void update(UUID id, Estado estado);

    Estado delete(UUID id);



}
