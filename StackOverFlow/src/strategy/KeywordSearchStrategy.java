package strategy;

import entities.Question;
import java.util.ArrayList;
import java.util.List;

public class KeywordSearchStrategy implements SearchStrategy {
    private final String keyword;

    public KeywordSearchStrategy(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public List<Question> filter(List<Question> questions) {
        List<Question> matchQues = new ArrayList<>();
        questions.forEach(ques -> { if (ques.getTitle().contains(keyword) || ques.getBody().contains(keyword)){
            matchQues.add(ques);
        }});
        return matchQues;
    }
}