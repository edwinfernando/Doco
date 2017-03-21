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

    public LDrivers(){driverDAO = SessionDAO.getInstance().getDriverDAO();}

    public void addDriver(String code, String name, String cel_driver, int satisfation_score){
        Driver driver = new Driver();
        driver.setCode(code);
        driver.setName(name);
        driver.setMovilNumber(cel_driver);
        driver.setSatisfationScore(satisfation_score);

        driverDAO.insert(driver);
    }

    public List<Driver> getListDrivers(){
        return driverDAO.obtenerTodos();
    }
}
