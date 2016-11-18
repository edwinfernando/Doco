package com.domicilio.confiable.doco.domain;

import com.domicilio.confiable.doco.model.Driver;
import com.domicilio.confiable.doco.repositorio.IDriverDAO;
import com.domicilio.confiable.doco.repositorio.SessionDAO;

import java.util.List;

/**
 * Created by edwinmunoz on 11/17/16.
 */

public class LDrivers {

    private IDriverDAO driverDAO;

    public LDrivers(){driverDAO = SessionDAO.getDriverDAO();}

    public void addDriver(String code, String name, String cel_driver, int satisfation_score){
        Driver driver = new Driver();
        driver.setCode_driver(code);
        driver.setName_driver(name);
        driver.setCel_driver(cel_driver);
        driver.setSatisfation_score(satisfation_score);

        driverDAO.insert(driver);
    }

    public List<Driver> getListDrivers(){
        return driverDAO.obtenerTodos();
    }
}
