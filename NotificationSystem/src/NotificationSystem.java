import controllers.RecipientController;
import decorator.RetryNotificationGateway;
import entities.Notification;
import entities.NotificationGateway;
import entities.Recipient;
import enumerations.NotificationType;
import factory.NotificationGatewayFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.*;

public class NotificationSystem
{
    // Thread pool configuration
    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 8;
    private static final long KEEP_ALIVE_TIME = 60L;
    private static final int QUEUE_CAPACITY = 500;

    private final ExecutorService executorService;
    private final RecipientController recipientController = new RecipientController();

    public NotificationSystem() {
        this.executorService = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy() // Falls back to calling thread if queue fills up
        );
    }

    public void sendNotificationToRecipient(String recipientId, Notification notification)
    {
        this.executorService.execute(() -> {
           try
           {
               Recipient recipient = this.recipientController.getRecipientById(recipientId);
               Iterator<NotificationType> it = recipient.getOptedNotificationGateways().iterator();

               while(it.hasNext())
               {
                   NotificationType notificationType  = it.next();
                   NotificationGateway notificationGateway = NotificationGatewayFactory.getNotificationGateway(notificationType, recipient);
                   RetryNotificationGateway retry = new RetryNotificationGateway(notificationGateway);
                   retry.sendNotification(notification);
               }
           }
           catch(Exception exp)
           {
               System.err.println("Failed to send async notification to " + recipientId + ": " + exp.getMessage());
           }
        });
    }

    public void sendNotificationToAllRecipients(Notification notification)
    {
        this.executorService.execute(() -> {
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
        });
    }

    public void addRecipient(Recipient recipient)
    {
        this.recipientController.addRecipient(recipient);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
