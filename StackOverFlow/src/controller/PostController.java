package controller;

import entities.*;
import enumerations.VoteType;
import observer.PostObserver;
import observer.ReputationManager;
import strategy.SearchStrategy;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostController
{
    private final Map<String, Post> posts = new ConcurrentHashMap<>();
    private final Map<String, Question> questions = new ConcurrentHashMap<>();
    private final PostObserver reputationManager = new ReputationManager();

    public Question postQuestion(User author, String title, String body, Set<Tag> tags)
    {
        Question ques = new Question(title, body, author, tags);
        ques.addObserver(this.reputationManager);
        questions.put(ques.getId(), ques);
        posts.put(ques.getId(), ques);
        return ques;
    }

    public Answer postAnswer(User author, String questionId, String body) {

        if(!this.questions.containsKey(questionId))
            return null;
        Answer ans = new Answer(body, author);
        questions.get(questionId).addAnswer(ans);
        posts.put(ans.getId(), ans);
        return ans;
    }

    public Comment addComment(User user, String postId, String body) {

        if(!posts.containsKey(postId))
        {
            return null;
        }
        Comment comment = new Comment(body, user);
        posts.get(postId).addComments(comment);
        return comment;
    }

    public void voteOnPost(User voter, String postId, VoteType voteType)
    {
        posts.get(postId).vote(voter, voteType);
    }

    public void acceptAnswer(String questionId, String answerId)
    {
        Answer accAnswer = (Answer) this.posts.get(answerId);
        Question ques = questions.get(questionId);
        if(accAnswer == null || ques == null)
            return;
        accAnswer.setAccepted(true);
        ques.acceptAnswer(accAnswer);

    }

    public List<Question> searchQuestions(List<SearchStrategy> strategies)
    {
        List<Question> matchingQues = new CopyOnWriteArrayList<>(questions.values());
        for(SearchStrategy strategy : strategies)
        {
            matchingQues = strategy.filter(matchingQues);
        }

        return matchingQues;
    }

    public Post findPostById(String postId) throws Exception
    {
        if(posts.get(postId) != null)
        {
            return posts.get(postId);
        }
        throw new Exception("Post does not exist");
    }
}
