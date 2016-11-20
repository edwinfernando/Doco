package com.domicilio.confiable.doco.repositorio;

import com.domicilio.confiable.doco.model.Driver;

import java.util.List;

/**
 * Created by edwinmunoz on 11/17/16.
 */

public interface IDriverDAO {
    void insert(Driver driver);
    List<Driver> obtenerTodos();
    Driver obtenerPorCodigo(String code);
}
