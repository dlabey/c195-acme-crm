package com.acme.crm.services;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import com.acme.crm.dao.AppointmentDAO;
import com.acme.crm.entities.AppointmentEntity;

public class AppointmentServiceImpl implements AppointmentService {

    @Inject
    private AppointmentDAO appointmentDAO;

    @Override
    public void loadAppointments(TreeTableView appointmentsTable)
            throws SQLException {
        List<AppointmentEntity> appointments
                = this.appointmentDAO.getAppointments();
        List<TreeItem<AppointmentEntity>> appointmentRows = appointments.stream()
                .map(appointment -> {
                    TreeItem<AppointmentEntity> rootItem
                            = new TreeItem<>(appointment);
                    TreeItem<AppointmentEntity> childItem
                            = new TreeItem<>(appointment);

                    rootItem.getChildren().setAll(childItem);

                    return rootItem;
                })
                .collect(Collectors.toList());

        if (appointmentRows.size() > 0) {
            appointmentsTable.getRoot().getChildren().setAll(
                    FXCollections.observableList(appointmentRows));
        } else {
            appointmentsTable.getRoot().getChildren().clear();
            appointmentsTable.setPlaceholder(new Label("No appointments"));
        }
    }
}
