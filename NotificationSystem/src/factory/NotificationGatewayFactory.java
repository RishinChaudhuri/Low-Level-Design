package factory;

import entities.*;
import enumerations.NotificationType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class NotificationGatewayFactory
{
    // Lazy loading the Notification Gateways
    private static final Map<NotificationType, NotificationGateway> notificationGatewayMap = new ConcurrentHashMap<>();

    public static NotificationGateway getNotificationGateway(NotificationType type, Recipient recipient) throws Exception
    {
        if(type == NotificationType.SMS)
        {
            return buildIfAbsent(type, () -> new SMSNotificationGateway(recipient));
        }
        else if(type == NotificationType.E_MAIL)
        {
            return buildIfAbsent(type, () -> new EmailNotificationGateway(recipient));
        }
        else if(type == NotificationType.PUSH_NOTIFICATION)
        {
            return buildIfAbsent(type, () -> new PushNotificationGateway(recipient));
        }
        else
        {
            throw new IllegalArgumentException("Not a valid notification type");
        }
    }

    private static NotificationGateway buildIfAbsent(NotificationType type, Supplier<NotificationGateway> gatewaySupplier)
    {
        return notificationGatewayMap.computeIfAbsent(type, key -> gatewaySupplier.get());
    }
}
