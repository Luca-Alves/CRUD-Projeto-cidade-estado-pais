package controller.impl;

import controller.CidadeController;
import model.Cidade;

import java.util.*;

public class CidadeControllerArmaz implements CidadeController {

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
}
