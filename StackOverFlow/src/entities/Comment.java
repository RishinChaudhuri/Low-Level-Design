// Import the necessary classes and dependencies here

package entities;
import java.util.UUID;

public class Comment extends Content
{
    public Comment(String body, User author)
    {
        super(UUID.randomUUID().toString(), body, author);
    }
}