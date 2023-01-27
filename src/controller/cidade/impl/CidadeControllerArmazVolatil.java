package controller.cidade.impl;

import controller.cidade.CidadeController;
import controller.cidade.exception.CidadeNaoEncontrada;
import model.cidade.Cidade;

import java.util.*;

public class CidadeControllerArmazVolatil implements CidadeController {

    private Map<UUID, Cidade> cidades = new HashMap<>();

    @Override
    public void create(Cidade cidade) {
    cidade.setId(UUID.randomUUID());
    cidades.put(cidade.getId(), cidade);
    }

    @Override
    public List<Cidade> listar() {
        return new ArrayList<>(cidades.values());
    }

    @Override
    public Cidade ler(UUID id) {
        return null;
    }

    @Override
    public void update(UUID id, Cidade cidade) {
        if (cidades.containsKey(id)){
            cidades.put(id, cidade);
        } else {
            throw new CidadeNaoEncontrada();
        }
    }

    @Override
    public Cidade delete(UUID id) {
        Cidade apagada = cidades.remove(id);
        if (apagada == null){
            throw new CidadeNaoEncontrada();
        }
        return apagada;
    }
}
