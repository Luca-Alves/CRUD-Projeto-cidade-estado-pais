package controller.pais.impl;

import controller.pais.PaisController;
import model.pais.DAO.PaisDAO;
import model.pais.Pais;

import java.util.List;
import java.util.UUID;

public class PaisControllerArmazDefinitivo implements PaisController {


   private PaisDAO dao;

    public PaisControllerArmazDefinitivo(PaisDAO dao) {
        this.dao = dao;
    }

    @Override
    public void create(Pais pais) {
        pais.setId(UUID.randomUUID());
        dao.cadastrar(pais);
    }

    @Override
    public List<Pais> listar() {
        return dao.listar();
    }

    @Override
    public Pais ler(UUID id) {
        return dao.buscar(id);
    }

    @Override
    public void update(UUID id, Pais pais) {
        dao.atualizar(id, pais);
    }

    @Override
    public Pais delete(UUID id) {
        return dao.apagar(id);
    }
}
