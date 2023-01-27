package controller.estado.impl;

import controller.estado.EstadoController;
import model.estado.DAO.EstadoDAO;
import model.estado.Estado;

import java.util.List;
import java.util.UUID;

public class EstadoControllerArmazDefinitivo implements EstadoController {


    private EstadoDAO dao;

    public EstadoControllerArmazDefinitivo(EstadoDAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(Estado estado) {
        estado.setId(UUID.randomUUID());
        dao.cadastrar(estado);
    }

    @Override
    public List<Estado> listar() {
        return dao.listar();
    }

    @Override
    public Estado ler(UUID id) {
        return dao.buscar(id);
    }

    @Override
    public void update(UUID id, Estado estado) {
        dao.atualizar(id, estado);
    }

    @Override
    public Estado delete(UUID id) {
        return dao.apagar(id);
    }
}
