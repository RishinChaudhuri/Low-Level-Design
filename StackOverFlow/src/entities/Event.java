// Import all the necessary classes and modules here
package entities;
import enumerations.EventType;

public class Event
{
    private final EventType eventType;
    private final User actor;
    private final boolean reversal;
    private final String postId;

    public Event(EventType eventType, User actor, String id)
    {
        this(eventType, actor, false, id);
    }

    public Event(EventType eventType, User actor, boolean reversal, String id)
    {
        this.eventType = eventType;
        this.actor = actor;
        this.reversal = reversal;
        this.postId = id;
    }

    public EventType getType()
    {
        return this.eventType;
    }

    public User getActor()
    {
        return this.actor;
    }

    public boolean isReversal()
    {
        return this.reversal;
    }

    public String getPostId()
    {
        return this.postId;
    }
}