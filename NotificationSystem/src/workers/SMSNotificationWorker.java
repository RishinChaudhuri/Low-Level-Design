package workers;

import entities.NotificationRecipientRecord;
import enumerations.NotificationType;
import rate_limiter.RateLimiter;

import java.util.concurrent.LinkedBlockingQueue;

public class SMSNotificationWorker extends NotificationGatewayWorker
{
    public SMSNotificationWorker(LinkedBlockingQueue<NotificationRecipientRecord> queue, RateLimiter rateLimiter)
    {
        super(queue, rateLimiter, NotificationType.SMS);
    }

}
