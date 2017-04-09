package com.acme.crm.services;

import java.sql.SQLException;
import javafx.scene.control.TreeTableView;

import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;

public interface AppointmentService {
    
    public void loadAppointments(TreeTableView appointmentsTable)
            throws SQLException;
}
