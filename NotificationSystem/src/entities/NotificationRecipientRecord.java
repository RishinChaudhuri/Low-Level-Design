package entities;


public record NotificationRecipientRecord(Recipient recipient, Notification notification)
{
    public Notification getNotification()
    {
        return notification;
    }

    public Recipient getRecipient()
    {
        return recipient;
    }
}
