import controllers.RecipientController;
import entities.Notification;
import entities.NotificationRecipientRecord;
import entities.Recipient;
import enumerations.NotificationType;
import factory.NotificationWorkerFactory;
import rate_limiter.RateLimiter;
import rate_limiter.TokenBucketRateLimiter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.*;

public class NotificationSystem
{
    // scheduled thread pool configuration
    private static final int QUEUE_CAPACITY = 500;
    private static final int SCHEDULED_POOL_SIZE = 2;

    private static final int SMS_WORKERS = 2;

    private static final int EMAIL_WORKERS = 4;

    private static final int PUSH_WORKERS = 2;

    private final LinkedBlockingQueue<NotificationRecipientRecord> smsQueue;

    private final LinkedBlockingQueue<NotificationRecipientRecord> emailQueue;

    private final LinkedBlockingQueue<NotificationRecipientRecord> pushQueue;

    private final RateLimiter smsRateLimiter;

    private final RateLimiter emailRateLimiter;

    private final RateLimiter pushRateLimiter;

    private final ScheduledExecutorService scheduledExecutorService;
    private final RecipientController recipientController = new RecipientController();

    public NotificationSystem() {

        this.smsQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
        this.emailQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);
        this.pushQueue = new LinkedBlockingQueue<>(QUEUE_CAPACITY);

        this.smsRateLimiter = new TokenBucketRateLimiter(5, 5);
        this.emailRateLimiter = new TokenBucketRateLimiter(10, 10);
        this.pushRateLimiter = new TokenBucketRateLimiter(20, 20);

        this.scheduledExecutorService = Executors.newScheduledThreadPool(SCHEDULED_POOL_SIZE);
        try {
            this.startWorkers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void startWorkers() throws Exception {

        // SMS
        this.startWorkers(this.smsQueue, smsRateLimiter, SMS_WORKERS, NotificationType.SMS);

        // EMAIL
        this.startWorkers(emailQueue, emailRateLimiter, EMAIL_WORKERS, NotificationType.E_MAIL);

        // PUSH
        this.startWorkers(pushQueue, pushRateLimiter, PUSH_WORKERS, NotificationType.PUSH_NOTIFICATION);

    }

    private void startWorkers(LinkedBlockingQueue<NotificationRecipientRecord> queue, RateLimiter rateLimiter, int workerCount, NotificationType type) throws Exception {

        for (int i = 0; i < workerCount; i++) {
            Runnable worker = NotificationWorkerFactory.getNotificationGatewayWorker(type, queue, rateLimiter);
            Thread workerThread = new Thread(worker, type.name() + "-Worker-" + i);
            workerThread.setDaemon(true); // Allows JVM to exit cleanly, or manage them in a dedicated list
            workerThread.start();
        }

    }

    public void sendNotificationToRecipient(String recipientId, Notification notification)
    {
           try
           {
               Recipient recipient = this.recipientController.getRecipientById(recipientId);
               Iterator<NotificationType> it = recipient.getOptedNotificationGateways().iterator();

               while(it.hasNext())
               {
                   NotificationType notificationType  = it.next();
                   LinkedBlockingQueue<NotificationRecipientRecord> queue = this.getQueue(notificationType);
                   queue.put(new NotificationRecipientRecord(this.recipientController.getRecipientById(recipientId), notification));

               }
           }
           catch(Exception exp)
           {
               System.err.println("Failed to send async notification to " + recipientId + ": " + exp.getMessage());
           }
    }

    public void sendNotificationToAllRecipients(Notification notification)
    {
        try
        {
            ArrayList<Recipient> recipients = this.recipientController.getAllRecipients();

            for(Recipient recipient : recipients)
            {
                this.sendNotificationToRecipient(recipient.getId(), notification);
            }

        }
        catch(Exception exp)
        {
            System.err.println("Failed to send async notification to all recipients due to "+ exp.getMessage());
        }

    }

    public void scheduleNotification(String recipientId, Notification notification, long delaySeconds) {
       this.scheduledExecutorService.schedule(
                () -> sendNotificationToRecipient(recipientId, notification),
                delaySeconds,
                TimeUnit.SECONDS);
    }

    public void addRecipient(Recipient recipient)
    {
        this.recipientController.addRecipient(recipient);
    }

    public void shutdown() {

        scheduledExecutorService.shutdown();
        try {
            if(!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)){
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {

            scheduledExecutorService.shutdownNow();  
            Thread.currentThread().interrupt();
        }
    }

    private LinkedBlockingQueue<NotificationRecipientRecord> getQueue(NotificationType type)
    {
        if(type == NotificationType.SMS)
        {
            return this.smsQueue;
        }
        else if(type == NotificationType.PUSH_NOTIFICATION)
        {
            return this.pushQueue;
        }
        else
        {
            return this.emailQueue;
        }

    }

}
