import builder.NotificationBuilder;
import entities.Notification;
import entities.Recipient;
import enumerations.NotificationType;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class NotificationSystemDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1. Setup the notification service
        NotificationSystem notificationSystem = new NotificationSystem();

        // 2. Define recipients
        Recipient recipient1 = new Recipient(UUID.randomUUID().toString(), "user123", "john.doe@example.com", "9876543210", new HashSet<>(List.of(NotificationType.PUSH_NOTIFICATION, NotificationType.SMS)));
        Recipient recipient2 = new Recipient(UUID.randomUUID().toString(), "user456", "abc.xyz@example.com", "8765432190", new HashSet<>(List.of(NotificationType.E_MAIL)));

        notificationSystem.addRecipient(recipient1);
        notificationSystem.addRecipient(recipient2);
        // 3. Send various notifications using the Facade (NotificationSystem)

        // Scenario 1: Send a welcome email
        Notification welcomeEmail = new NotificationBuilder("Welcome to notification system").setTitle("Welcome!").build();
        notificationSystem.sendNotificationToAllRecipients(welcomeEmail);

        // Scenario 2: Send a direct push notification
        Notification pushNotification = new NotificationBuilder("You have a new message from Jane.").setTitle("New Message").build();
        notificationSystem.sendNotificationToRecipient(recipient1.getId(), pushNotification);

        // Scenario 3: Send order confirmation SMS
        Notification orderSms = new NotificationBuilder( "Your order for Digital Clock is confirmed").build();
        notificationSystem.sendNotificationToRecipient(recipient2.getId(), orderSms);

        // Wait for a moment to allow the queue processor to work
        Thread.sleep(1000);

        // 4. Shutdown the system
        System.out.println("\nShutting down the notification system...");
        notificationSystem.shutdown();
        System.out.println("System shut down successfully.");
    }
}