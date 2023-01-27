package controller.cidade;

import model.cidade.Cidade;

import java.util.List;
import java.util.UUID;

public interface CidadeController {

    void create(Cidade cidade);

    List<Cidade> listar();

    Cidade ler(UUID id);

    void update(UUID id, Cidade cidade);

    Cidade delete(UUID id);
}
