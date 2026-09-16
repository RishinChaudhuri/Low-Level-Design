package decorator;

import entities.Notification;
import entities.NotificationGateway;
import entities.NotificationRecipientRecord;
import entities.Recipient;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;

public class RetryNotificationGateway implements NotificationGateway
{
    private final NotificationGateway notificationGateway;
    private static final int RETRIES = 3;
    private static final int MAX_TIMEOUT = 7500;
    private final Queue<NotificationRecipientRecord> deadLetterQueue = new ConcurrentLinkedQueue<>();

    public RetryNotificationGateway(NotificationGateway notificationGateway)
    {
        this.notificationGateway = notificationGateway;
    }

    @Override
    public boolean sendNotification(Recipient recipient, Notification notification)
    {
        long base = 1000;
        int retryAttempt = 0;
        for(int i=1; i<=RetryNotificationGateway.RETRIES; i++)
        {
            if(this.notificationGateway.sendNotification(recipient, notification))
            {
                return true;
            }
            else
            {
                retryAttempt++;
                long maxCap = (long) Math.min(MAX_TIMEOUT, base * Math.pow(2, retryAttempt));
                long sleepTime = ThreadLocalRandom.current().nextLong(0, maxCap + 1);
                try {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return false;
                }

            }
        }
        this.deadLetterQueue.add(new NotificationRecipientRecord(recipient, notification));
        return false;
    }
}