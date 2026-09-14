package entities;

import enumerations.NotificationType;

import java.util.HashSet;
import java.util.Set;

public class Recipient
{
    private final String id;
    private String name;
    private String emailId;
    private String phoneNumber;
    private final Set<NotificationType> optedNotificationGateways;

    public Recipient(String id, String name, String emailId, String phoneNumber, HashSet<NotificationType> optedTypes)
    {
        this.id = id;
        this.name = name;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
        this.optedNotificationGateways = optedTypes;
    }

    public Recipient()
    {
        this.id = "";
        this.optedNotificationGateways = new HashSet<>();
    }

    // getters
    public String getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getEmailId()
    {
        return this.emailId;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public Set<NotificationType> getOptedNotificationGateways()
    {
        return this.optedNotificationGateways;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void addNotificationGateway(NotificationType notificationType)
    {
        this.optedNotificationGateways.add(notificationType);
    }

    public boolean removeNotificationGateway(NotificationType notificationGateway)
    {
        return this.optedNotificationGateways.remove(notificationGateway);
    }

}