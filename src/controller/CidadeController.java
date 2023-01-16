package controller;

import model.Cidade;

import java.util.List;

public interface CidadeController {

    void create(Cidade cidade);

    List<Cidade> listar();


}
