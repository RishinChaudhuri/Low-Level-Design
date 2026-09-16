package workers;

import entities.NotificationRecipientRecord;
import enumerations.NotificationType;
import rate_limiter.RateLimiter;

import java.util.concurrent.LinkedBlockingQueue;

public class PushNotificationWorker extends NotificationGatewayWorker
{
    public PushNotificationWorker(LinkedBlockingQueue<NotificationRecipientRecord> queue, RateLimiter rateLimiter)
    {
        super(queue, rateLimiter, NotificationType.PUSH_NOTIFICATION);
    }
}
