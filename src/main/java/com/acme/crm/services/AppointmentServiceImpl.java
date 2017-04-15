package com.acme.crm.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Collectors;
import java.util.TreeSet;
import javax.inject.Inject;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import com.acme.crm.dao.AppointmentDAO;
import com.acme.crm.dao.ReminderDAO;
import com.acme.crm.entities.AppointmentEntity;
import com.acme.crm.entities.AppointmentTypeByMonthEntity;
import com.acme.crm.entities.MonthEntity;
import com.acme.crm.entities.WeekEntity;
import com.acme.crm.entities.YearEntity;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;

public class AppointmentServiceImpl implements AppointmentService {

    private static final org.apache.logging.log4j.Logger LOGGER =
            LogManager.getLogger(AppointmentServiceImpl.class);
    
    @Inject
    private DatabaseService dbService;
    
    @Inject
    private ContextService contextService;
    
    @Inject
    private AppointmentDAO appointmentDAO;
    
    @Inject
    private ReminderDAO reminderDAO;
    
    @Override
    public int createAppointment(int customerId, String title,
            String description, String location, String contact, String url,
            LocalDateTime startRaw, LocalDateTime endRaw, String createdBy)
            throws SQLException {
        int appointmentId = 0;
        
        try {
            this.dbService.getConnection().setAutoCommit(false);
            
            PreparedStatement appointmentPs = this.appointmentDAO
                    .createAppointment(customerId, title, description, location,
                            contact, url, startRaw, endRaw, createdBy);
            
            appointmentPs.executeUpdate();

            ResultSet generatedKeys = appointmentPs.getGeneratedKeys();
            int newId = -1;

            if (generatedKeys.next()) {
                newId = generatedKeys.getInt(1);
            }
            
            appointmentId = newId;
            
            AppointmentEntity appointment = this.appointmentDAO.getAppointment(
                    appointmentId);
            
            PreparedStatement reminderPs = this.reminderDAO.createReminder(
                    appointment, this.contextService.getUser().getUserName());
            
            reminderPs.executeUpdate();
            
            this.dbService.getConnection().commit();
        } catch (SQLException ex) {
            this.dbService.getConnection().rollback();

            LOGGER.error(ex.getMessage());
        } finally {
            this.dbService.getConnection().setAutoCommit(true);

            LOGGER.debug(appointmentId);
        }
        
        return appointmentId;
    }
    
    @Override
    public boolean editAppointment(int appointmentId, int customerId,
            String title, String description, String location, String contact,
            String url, LocalDateTime startRaw, LocalDateTime endRaw,
            String updatedBy) throws SQLException {
        boolean updated = false;
        
        try {
            this.dbService.getConnection().setAutoCommit(false);
            
            PreparedStatement appointmentPs = this.appointmentDAO
                    .updateAppointment(appointmentId, customerId, title,
                            description, location, contact, url, startRaw,
                            endRaw, updatedBy);
            appointmentPs.executeUpdate();
            
            AppointmentEntity appointment = this.appointmentDAO.getAppointment(
                    appointmentId);
            
            LOGGER.debug(appointment.getStart());
            
            PreparedStatement reminderPs = this.reminderDAO.updateReminder(
                    appointment);
            reminderPs.executeUpdate();
            
            this.dbService.getConnection().commit();
            
            updated = true;
        } catch (SQLException ex) {
            this.dbService.getConnection().rollback();

            LOGGER.error(ex.getMessage());
        } finally {
            this.dbService.getConnection().setAutoCommit(true);

            LOGGER.debug(updated);
        }

        return updated;
    }
    
    @Override
    public boolean deleteAppointment(int appointmentId) throws SQLException {
        boolean deleted = false;
        
        try {
            this.dbService.getConnection().setAutoCommit(false);
            
            PreparedStatement appointmentPs = this.appointmentDAO
                    .deleteAppointment(appointmentId);
            appointmentPs.executeUpdate();
            
            PreparedStatement reminderPs = this.reminderDAO.deleteReminder(
                    appointmentId);
            reminderPs.executeUpdate();
            
            this.dbService.getConnection().commit();
            
            deleted = true;
        } catch (SQLException ex) {
            this.dbService.getConnection().rollback();

            LOGGER.error(ex.getMessage());
        } finally {
            this.dbService.getConnection().setAutoCommit(true);

            LOGGER.debug(deleted);
        }

        return deleted;
    }

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
    
    @Override
    public void loadAppointmentScheduleByUserName(TableView scheduleTable,
            String userName) throws SQLException {
        List<AppointmentEntity> appointments
                = this.appointmentDAO.getAppointmentScheduleByUserName(userName);
        
        if (appointments.size() > 0) {
            scheduleTable.getItems().setAll(
                FXCollections.observableList(appointments));
        } else {
            scheduleTable.getItems().clear();
            scheduleTable.setPlaceholder(new Label("No schedule"));
        }
    }
    
    @Override
    public void loadAppointmentScheduleByCustomerId(TableView scheduleTable,
            String customerId) throws SQLException {
        List<AppointmentEntity> appointments
                = this.appointmentDAO
                        .getAppointmentScheduleByCustomerId(customerId);
        
        if (appointments.size() > 0) {
            scheduleTable.getItems().setAll(
                FXCollections.observableList(appointments));
        } else {
            scheduleTable.getItems().clear();
            scheduleTable.setPlaceholder(new Label("No schedule"));
        }
    }
    
    @Override
    public List<XYChart.Series> loadAppointmenTypesByMonth() throws SQLException {
        LOGGER.debug("loadAppointmenTypesByMonth");
        
        String offset = OffsetDateTime.now(ZoneId.systemDefault()).getOffset()
                .toString();
        
        LOGGER.debug(offset);
        
        List<AppointmentTypeByMonthEntity> appointmenTypesByMonth = 
                this.appointmentDAO.getAppointmentTypesByMonth(offset);
        
        SortedSet<String> types = new TreeSet<>();
        
        appointmenTypesByMonth.forEach(t -> types.add(t.getType()));
        
        List<XYChart.Series> seriesList = new LinkedList<>();
        
        types.forEach(t -> {
            XYChart.Series series = new XYChart.Series();
            
            series.setName(String.valueOf(t)); // TODO i18n
            
            List<AppointmentTypeByMonthEntity> data =
                    appointmenTypesByMonth.stream()
                            .filter(d -> d.getType().equals(t))
                            .collect(Collectors.toList());
            
            data.forEach(d -> {
                series.getData()
                        .add(new XYChart.Data(String.valueOf(d.getMonth()), d.getCount()));
            });
            
            seriesList.add(series);
        });
        
        return seriesList;
    }
}
