import controller.PostController;
import controller.UserController;
import entities.*;
import enumerations.VoteType;
import strategy.SearchStrategy;

import java.util.List;
import java.util.Set;

public class StackOverFlowService
{
    private final UserController userController = new UserController();
    private final PostController postController = new PostController();

    public User createUser(String name)
    {
        User user = this.userController.createUser(name);
        return user;
    }

    public User getUser(String id)
    {
        return this.userController.getUser(id);
    }

    public Question postQuestion(User author, String title, String body, Set<Tag> tags)
    {
        return this.postController.postQuestion(author, title, body, tags);
    }

    public Answer postAnswer(User author, String questionId, String body)
    {
        return this.postController.postAnswer(author, questionId, body);
    }

    public Comment addComment(User user, String postId, String body)
    {
        return this.postController.addComment(user, postId, body);
    }

    public void voteOnPost(User user, String postId, VoteType voteType)
    {
        this.postController.voteOnPost(user, postId, voteType);
    }

    public void acceptAnswer(String questionId, String answerId)
    {
        this.postController.acceptAnswer(questionId, answerId);
    }

    public List<Question> searchQuestions(List<SearchStrategy> strategies)
    {

        return this.postController.searchQuestions(strategies);
    }

    public Post findPostById(String postId)
    {
        try
        {
            return this.postController.findPostById(postId);
        }
        catch (Exception e)
        {
            IO.println("No posts found");
            return null;
        }
    }
}