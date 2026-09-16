package workers;

import decorator.RetryNotificationGateway;
import entities.NotificationGateway;
import entities.NotificationRecipientRecord;
import enumerations.NotificationType;
import factory.NotificationGatewayFactory;
import rate_limiter.RateLimiter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NotificationGatewayWorker implements Runnable {
    private final BlockingQueue<NotificationRecipientRecord> queue;
    private final RateLimiter rateLimiter;
    private final NotificationType notificationType;

    public NotificationGatewayWorker(LinkedBlockingQueue<NotificationRecipientRecord> queue, RateLimiter rateLimiter, NotificationType type)
    {
        this.queue = queue;
        this.rateLimiter = rateLimiter;
        this.notificationType = type;
    }

    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            try {

                NotificationRecipientRecord notificationRecipientRecord = this.queue.take();

                this.rateLimiter.acquire();

                NotificationGateway notificationGateway = NotificationGatewayFactory.getNotificationGateway(this.notificationType, notificationRecipientRecord.getRecipient());
                RetryNotificationGateway retry = new RetryNotificationGateway(notificationGateway);
                retry.sendNotification(notificationRecipientRecord.getRecipient(), notificationRecipientRecord.getNotification());

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }
}
