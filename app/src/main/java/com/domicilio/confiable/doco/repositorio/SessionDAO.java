package com.domicilio.confiable.doco.repositorio;

/**
 * Created by edwinmunoz on 11/17/16.
 */

public class SessionDAO {
    private static IDriverDAO driverDAO;

    public static IDriverDAO getDriverDAO() {
        return driverDAO;
    }

    public static void setDriverDAO(IDriverDAO driverDAO) {
        SessionDAO.driverDAO = driverDAO;
    }
}
