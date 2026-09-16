package factory;

import entities.NotificationRecipientRecord;
import enumerations.NotificationType;
import rate_limiter.RateLimiter;
import workers.EmailNotificationWorker;
import workers.NotificationGatewayWorker;
import workers.PushNotificationWorker;
import workers.SMSNotificationWorker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

public class NotificationWorkerFactory
{
    // Lazy loading the Notification Workers
    private static final Map<NotificationType, NotificationGatewayWorker> notificationGatewayWorkerMap = new ConcurrentHashMap<>();

    public static NotificationGatewayWorker getNotificationGatewayWorker(NotificationType type, LinkedBlockingQueue<NotificationRecipientRecord> queue, RateLimiter rateLimiter) throws Exception
    {
        if(type == NotificationType.SMS)
        {
            return buildIfAbsent(type, () -> new SMSNotificationWorker(queue, rateLimiter));
        }
        else if(type == NotificationType.E_MAIL)
        {
            return buildIfAbsent(type, () -> new EmailNotificationWorker(queue, rateLimiter));
        }
        else if(type == NotificationType.PUSH_NOTIFICATION)
        {
            return buildIfAbsent(type, () -> new PushNotificationWorker(queue, rateLimiter));
        }
        else
        {
            throw new IllegalArgumentException("Not a valid notification type");
        }
    }

    private static NotificationGatewayWorker buildIfAbsent(NotificationType type, Supplier<NotificationGatewayWorker> gatewaySupplier)
    {
        return notificationGatewayWorkerMap.computeIfAbsent(type, key -> gatewaySupplier.get());
    }
}
