package com.acme.crm.services;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import com.acme.crm.dao.AppointmentDAO;
import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;

public class AppointmentServiceImpl implements AppointmentService {

    @Inject
    private ContextService contextService;
    
    @Inject
    private AppointmentDAO appointmentDAO;

    @Override
    public void loadAppointments(TreeTableView appointmentsTable)
            throws SQLException {
        Map<String, Object> appointmentsTableFilters = this.contextService
                .getAppointmentsTableFilters();
        Timestamp start;
        Timestamp end;
        
        if (appointmentsTableFilters.get("week") != null) {
            WeekEntity week = (WeekEntity) appointmentsTableFilters.get("week");
            
            start = Timestamp.valueOf(week.getStartDateTime()
                    .toLocalDateTime());
            end = Timestamp.valueOf(week.getEndDateTime().toLocalDateTime());
        } else if (appointmentsTableFilters.get("month") != null) {
            MonthEntity month = (MonthEntity) appointmentsTableFilters.get("month");
            
            start = Timestamp.valueOf(month.getStartDateTime()
                    .toLocalDateTime());
            end = Timestamp.valueOf(month.getEndDateTime().toLocalDateTime());
        } else if (appointmentsTableFilters.get("year") != null) {
            YearEntity year = (YearEntity) appointmentsTableFilters.get("year");
            
            start = Timestamp.valueOf(year.getStartDateTime()
                    .toLocalDateTime());
            end = Timestamp.valueOf(year.getEndDateTime().toLocalDateTime());
        } else {
            start = null;
            end = null;
        }
        
        List<AppointmentEntity> appointments
                = this.appointmentDAO.getAppointments(start, end);
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
