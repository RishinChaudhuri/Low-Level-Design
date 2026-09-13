package entities;

public class EmailNotificationGateway implements NotificationGateway
{
    private final Recipient recipient;

    public EmailNotificationGateway(Recipient recipient)
    {
        this.recipient = recipient;
    }

    @Override
    public boolean sendNotification(Notification notification)
    {
        try
        {
            IO.println("Sending Email to : " + this.recipient.getPhoneNumber() + "...");
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
