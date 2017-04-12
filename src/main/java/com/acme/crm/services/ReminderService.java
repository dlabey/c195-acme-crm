package com.acme.crm.services;

public interface ReminderService {
    
    public void scheduleReminders();
    
    public boolean cancelReminder(int appointmentId);
}
