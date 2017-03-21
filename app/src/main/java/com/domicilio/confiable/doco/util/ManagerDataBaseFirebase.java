package com.domicilio.confiable.doco.util;

import com.domicilio.confiable.doco.model.Persona;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Juan.Cabuyales on 20/03/2017.
 *
 * @descripcion: Clase encargada de gestionar las operaciones en la base de datos Firebase
 */

public class ManagerDataBaseFirebase {

    private static ManagerDataBaseFirebase managerDataBaseFirebase;

    private static FirebaseDatabase database;
    DatabaseReference reference;

    public static ManagerDataBaseFirebase getInstance() {
        if (managerDataBaseFirebase == null) {
            managerDataBaseFirebase = new ManagerDataBaseFirebase();
        }
        database = FirebaseDatabase.getInstance();
        return managerDataBaseFirebase;
    }

    public void agregarPersona(String referencia, String id_persona, String nombre, String telefono, String clave, int tipo) {
        reference = database.getReference(referencia);
        reference.push().setValue(new Persona(id_persona, nombre, telefono, clave, tipo));
    }


}
