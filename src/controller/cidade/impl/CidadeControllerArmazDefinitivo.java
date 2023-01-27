package controller.cidade.impl;

import controller.cidade.CidadeController;
import model.cidade.Cidade;
import model.cidade.DAO.CidadeDAO;

import java.util.List;
import java.util.UUID;

public class CidadeControllerArmazDefinitivo implements CidadeController {

    private CidadeDAO dao;

    public CidadeControllerArmazDefinitivo(CidadeDAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(Cidade cidade) {
        cidade.setId(UUID.randomUUID());
        dao.cadastrar(cidade);
    }

    @Override
    public List<Cidade> listar() {
        return dao.listar();
    }

    @Override
    public Cidade ler(UUID id) {
        return dao.buscar(id);
    }

    @Override
    public void update(UUID id, Cidade cidade) {
        dao.atualizar(id, cidade);
    }

    @Override
    public Cidade delete(UUID id) {

        return dao.apagar(id);
    }
}
