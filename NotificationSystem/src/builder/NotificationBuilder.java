package builder;

import entities.Notification;

public class NotificationBuilder
{
    public String title = ""; // non-mandatory field
    public String body;// mandatory field

    public NotificationBuilder(String body)
    {
        this.body = body;
    }

    public NotificationBuilder setTitle(String title)
    {
        this.title = title;
        return this;
    }

    public Notification build()
    {
        return new Notification(this);
    }
}
