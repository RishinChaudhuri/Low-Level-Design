package entities;

import builder.NotificationBuilder;

public class Notification
{
    private String title; // non-mandatory field
    private String body; // mandatory field

    public Notification(NotificationBuilder builder)
    {
        this.body = builder.body;
        this.title = builder.title;
    }

    public String getTitle()
    {
        return this.title;
    }

    public String getBody()
    {
        return this.body;
    }

}
