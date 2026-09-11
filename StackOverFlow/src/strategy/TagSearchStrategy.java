package strategy;

import entities.Question;
import entities.Tag;
import java.util.ArrayList;
import java.util.List;

public class TagSearchStrategy implements SearchStrategy
{
    private final Tag tag;

    public TagSearchStrategy(Tag tag) {
        this.tag = tag;
    }

    @Override
    public List<Question> filter(List<Question> questions) {
        List<Question> matchQues = new ArrayList<>();
        questions.forEach(ques -> { if (ques.getTags().contains(this.tag)){
        matchQues.add(ques);
        }});
        return matchQues;
    }
}
