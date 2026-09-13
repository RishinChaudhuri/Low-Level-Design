package factory;

import entities.*;
import enumerations.NotificationType;

public class NotificationGatewayFactory
{
    public static NotificationGateway getNotificationGateway(NotificationType type, Recipient recipient) throws Exception
    {
        if(type == NotificationType.SMS)
        {
            return new SMSNotificationGateway(recipient);
        }
        else if(type == NotificationType.E_MAIL)
        {
            return new EmailNotificationGateway(recipient);
        }
        else if(type == NotificationType.PUSH_NOTIFICATION)
        {
            return new PushNotificationGateway(recipient);
        }
        else
        {
            throw new IllegalArgumentException("Not a valid notification type");
        }
    }
}
