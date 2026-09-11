// Import the necessary classes and dependencies
package entities;

import enumerations.EventType;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Question extends Post
{
    private final String title;
    private final Set<Tag> tagsList;
    private final List<Answer> answers = new CopyOnWriteArrayList<>();
    private Answer acceptedAnswer;

    public Question(String title, String body, User author, Set<Tag> tags)
    {
        super(UUID.randomUUID().toString(), body, author);
        this.title = title;
        this.tagsList = tags;

    }

    public void addAnswer(Answer answer)
    {
        this.answers.add(answer);
    }

    public List<Answer> getAnswers()
    {
        return this.answers;
    }

    public void acceptAnswer(Answer answer)
    {
        this.acceptedAnswer = answer;
        Event event =  new Event(EventType.ACCEPT_ANSWER, answer.author, false, this.getId());
        notifyObservers(event);
    }

    public String getTitle()
    {
        return this.title;
    }

    public Set<Tag> getTags()
    {
        return this.tagsList;
    }

    public Answer getAcceptedAnswer()
    {
        return this.acceptedAnswer;
    }

}