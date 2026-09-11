package strategy;

import entities.Question;
import entities.User;
import java.util.ArrayList;
import java.util.List;

public class UserSearchStrategy implements SearchStrategy {
    private final User user;

    public UserSearchStrategy(User user) {
        this.user = user;
    }

    @Override
    public List<Question> filter(List<Question> questions) {
        List<Question> matchQues = new ArrayList<>();
        questions.forEach(ques -> { if (ques.getAuthor().getId().equalsIgnoreCase(this.user.getId())){
            matchQues.add(ques);
        }});
        return matchQues;
    }

}
