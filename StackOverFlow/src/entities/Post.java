// Import the necessary classes and dependencies here

package entities;

import enumerations.EventType;
import enumerations.VoteType;
import observer.PostObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Post extends Content
{
    private int voteCount = 0;
    private final ConcurrentHashMap<String, VoteType> voteMap = new ConcurrentHashMap<>();
    private final List<Comment> comments = new ArrayList<>();
    private final List<PostObserver> observers = new ArrayList<>();

    public Post(String id, String body, User author)
    {
        super(id, body, author);
    }

    public List<Comment> getComments()
    {
        return this.comments;
    }

    public void addComments(Comment comment)
    {
        this.comments.add(comment);
    }

    public void addObserver(PostObserver observer)
    {
        observer.observePost(this);
        this.observers.add(observer);
    }

    public boolean removeObserver(PostObserver observer)
    {
        observer.removeObservable(this.getId());
        return this.observers.remove(observer);
    }

    public int getUpvoteCount()
    {
        int upVotes = 0;
        for(Map.Entry<String, VoteType> e: voteMap.entrySet())
        {
            VoteType vt = e.getValue();
            if(vt == VoteType.UPVOTE)
            {
                upVotes++;
            }
        }
        return upVotes;
    }

    public int getDownVote()
    {
        int downVotes = 0;
        for(Map.Entry<String, VoteType> e: voteMap.entrySet())
        {
            VoteType vt = e.getValue();
            if(vt == VoteType.DOWNVOTE)
            {
                downVotes++;
            }
        }
        return downVotes;
    }

    protected void notifyObservers(Event event)
    {

        observers.forEach(obs -> {
            try {
                obs.onPostEvent(event);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    public synchronized void vote(User user, VoteType voteType)
    {
        // Ignore if the author self votes
        if(this.author.getId().equals(user.getId()))
        {
            return;
        }
        if(!voteMap.containsKey(user.getId()))
        {
            this.voteCount++;
        }
        else
        {
            // if the vote is same, no need to update the observers
            if(this.voteMap.get(user.getId()) == voteType)
            {
                return;
            }
        }
        this.voteMap.put(user.getId(), voteType);
        EventType eventType = voteType == VoteType.UPVOTE ? EventType.UPVOTE : EventType.DOWNVOTE;
        Event event = new Event(eventType, this.author, this.id);
        this.notifyObservers(event);

    }

}