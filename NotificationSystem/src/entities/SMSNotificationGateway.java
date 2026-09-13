package entities;

public class SMSNotificationGateway implements NotificationGateway
{
    private final Recipient recipient;

    public SMSNotificationGateway(Recipient recipient)
    {
        this.recipient = recipient;
    }

    @Override
    public boolean sendNotification(Notification notification)
    {
        try
        {
            IO.println("Sending SMS to : " + this.recipient.getPhoneNumber() + "...");
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
