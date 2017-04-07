package com.acme.crm.services;

import java.sql.SQLException;
import javafx.scene.control.TreeTableView;

public interface AppointmentService {
    
    public void loadAppointments(TreeTableView appointmentsTable)
            throws SQLException;
}
