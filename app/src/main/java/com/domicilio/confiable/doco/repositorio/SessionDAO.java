package com.domicilio.confiable.doco.repositorio;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by edwinmunoz on 11/17/16.
 *
 * @modificada: Juan.Cabuyales- Se acomoda de tal manera que sirva como InformacionSession.
 */

public final class SessionDAO {

    private static SessionDAO sessionDAO;

    private IDriverDAO driverDAO;
    private String userDisplayName;
    private FirebaseUser firebaseUser;
    private LatLng locationUser;


    public static SessionDAO getInstance() {
        if (sessionDAO == null) {
            sessionDAO = new SessionDAO();
        }
        return sessionDAO;
    }
    public IDriverDAO getDriverDAO() {
        return driverDAO;
    }

    public void setDriverDAO(IDriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }

    public static SessionDAO getSessionDAO() {
        return sessionDAO;
    }

    public static void setSessionDAO(SessionDAO sessionDAO) {
        SessionDAO.sessionDAO = sessionDAO;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public FirebaseUser getFirebaseUser() {
        return firebaseUser;
    }

    public void setFirebaseUser(FirebaseUser firebaseUser) {
        this.firebaseUser = firebaseUser;
    }

    public LatLng getLocationUser() {
        return locationUser;
    }

    public void setLocationUser(LatLng locationUser) {
        this.locationUser = locationUser;
    }
}
