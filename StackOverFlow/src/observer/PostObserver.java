package observer;

import entities.Event;
import entities.Post;

public interface PostObserver
{
    public void onPostEvent(Event event) throws Exception;
    public void observePost(Post post);
    public void removeObservable(String postId);

}
