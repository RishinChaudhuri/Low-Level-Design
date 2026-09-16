package entities;

public interface NotificationGateway
{
    public boolean sendNotification(Recipient recipient, Notification notification);
}
