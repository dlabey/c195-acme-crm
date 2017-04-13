package com.acme.crm.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.ZoneOffset;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import com.acme.crm.dao.ReminderDAO;
import com.acme.crm.entities.ReminderEntity;
import org.apache.logging.log4j.LogManager;

@Singleton
public class ReminderServiceImpl implements ReminderService {

    private static final org.apache.logging.log4j.Logger LOGGER
            = LogManager.getLogger(ReminderServiceImpl.class);

    @Inject
    private ContextService contextService;

    @Inject
    private ReminderDAO reminderDAO;

    private final ConcurrentHashMap<Integer, ScheduledFuture> scheduledReminders;

    private final CopyOnWriteArraySet<Integer> shownReminders;

    private final ScheduledExecutorService scheduler;

    public ReminderServiceImpl() {
        this.scheduledReminders = new ConcurrentHashMap<>();
        this.shownReminders = new CopyOnWriteArraySet<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void scheduleReminders() {
        LOGGER.debug("scheduleReminders");

        String consultantName = this.contextService.getUser().getUserName();

        try {
            // Only schedule unseen and unscheduled reminders.
            List<ReminderEntity> reminders = this.reminderDAO
                    .getRemindersByUser(consultantName)
                    .stream()
                    .filter(r -> !shownReminders.contains(r.getAppointmentId()))
                    .filter(r -> !scheduledReminders.containsKey(
                    r.getAppointmentId()))
                    .collect(Collectors.toList());

            LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

            reminders.forEach(r -> {
                Long secondsUntilReminder = now.until(
                        r.getReminderDate().toLocalDateTime(),
                        ChronoUnit.SECONDS);
                
                LOGGER.debug(secondsUntilReminder);

                Runnable reminder = () -> {
                    ReminderServiceImpl.this.shownReminders
                            .add(r.getAppointmentId());
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Appointment Reminder");
                    alert.setHeaderText(
                            String.format(
                                    "Appointment from %s to %s",
                                    r.getAppointment().getStart(),
                                    r.getAppointment().getEnd()
                            )
                    );
                    alert.setContentText(
                            String.format(
                                    "%s: %s%n%s: %s%n%s: %s%n%s: %s%n%s: %s",
                                    "Description",
                                    r.getAppointment().getDescription(),
                                    "Consultant",
                                    r.getAppointment().getCreatedBy(),
                                    "Location",
                                    r.getAppointment().getLocation(),
                                    "Contact",
                                    r.getAppointment().getContact(),
                                    "URL",
                                    r.getAppointment().getUrl()
                            )
                    );
                    alert.show();
                };

                ScheduledFuture scheduledFuture = this.scheduler
                        .schedule(() -> {
                            Platform.runLater(reminder);
                        }, secondsUntilReminder, TimeUnit.SECONDS);

                this.scheduledReminders.put(r.getAppointmentId(), scheduledFuture);
            });
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    @Override
    public boolean cancelReminder(int appointmentId) {
        LOGGER.debug("cancelReminder");

        boolean cancelled = false;

        ScheduledFuture scheduledFuture = this.scheduledReminders
                .get(appointmentId);

        if (scheduledFuture != null) {
            cancelled = scheduledFuture.cancel(false);
        }
        
        this.scheduledReminders.remove(appointmentId);

        return cancelled;
    }
}
