package entities;

public class EmailNotificationGateway implements NotificationGateway
{
    @Override
    public boolean sendNotification(Recipient recipient, Notification notification)
    {
        try
        {
            IO.println("Sending Email to : " + recipient.getPhoneNumber() + "...");
            IO.println("Message");
            IO.println("Title : " + notification.getTitle());
            IO.println("Body : " + notification.getBody());
            return true;
        }
        catch(Exception exp)
        {
            return false;
        }
    }
}
