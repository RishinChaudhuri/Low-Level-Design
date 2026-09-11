package observer;

import entities.Event;
import entities.Post;
import entities.User;
import enumerations.EventType;

import java.util.concurrent.ConcurrentHashMap;

public class ReputationManager implements PostObserver
{
    private static final int AUTHOR_UPVOTE_POINT = 10;
    private static final int AUTHOR_DOWNVOTE_POINT = -2;
    private static final int AUTHOR_ANSWER_ACCEPTED_POINT = 100;
    private static final int VOTER_UPVOTE_POINT = 2;
    private static final int VOTER_DOWNVOTE_POINT = -1;

    private final ConcurrentHashMap<String, Post> posts = new ConcurrentHashMap<>();

    @Override
    public void observePost(Post post)
    {
        posts.put(post.getId(), post);
    }

    @Override
    public void removeObservable(String postId)
    {
        posts.remove(postId);
    }

    @Override
    public void onPostEvent(Event event) throws Exception
    {
        User author = posts.get(event.getPostId()).getAuthor();
        User voter = event.getActor();

        // update the author and voter reputations
        EventType eventType = event.getType();
        boolean isEventReversal = event.isReversal();
        int authorReputationChange = 0, voterReputationChange = 0;
        switch(eventType)
        {
            case EventType.UPVOTE:
                authorReputationChange = isEventReversal ? -AUTHOR_UPVOTE_POINT : AUTHOR_UPVOTE_POINT;
                voterReputationChange = isEventReversal ? -VOTER_UPVOTE_POINT: VOTER_UPVOTE_POINT;
                break;

            case EventType.DOWNVOTE:
                authorReputationChange = isEventReversal ? AUTHOR_DOWNVOTE_POINT : -AUTHOR_DOWNVOTE_POINT;
                voterReputationChange = isEventReversal ? VOTER_DOWNVOTE_POINT : -VOTER_DOWNVOTE_POINT;

                break;

            case EventType.ACCEPT_ANSWER:
                authorReputationChange = isEventReversal ? -AUTHOR_ANSWER_ACCEPTED_POINT : AUTHOR_ANSWER_ACCEPTED_POINT;
                break;

            default:
                IO.println("Not a valid Event Type");
                throw new Exception("Not a valid Event Type");
        }
        author.updateReputation(authorReputationChange);
        voter.updateReputation(voterReputationChange);

    }

}