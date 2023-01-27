package controller.pais;

import model.estado.Estado;
import model.pais.Pais;

import java.util.List;
import java.util.UUID;

public interface PaisController {

    void create(Pais pais);

    List<Pais> listar();

    Pais ler(UUID id);

    void update(UUID id, Pais pais);

    Pais delete(UUID id);


}
