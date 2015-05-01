package ru.guar7387.clientserverwithvolleysample.server.callbacks;

import java.util.List;
import java.util.Map;

import ru.guar7387.clientserverwithvolleysample.data.Article;
import ru.guar7387.clientserverwithvolleysample.data.Comment;
import ru.guar7387.clientserverwithvolleysample.data.User;
import ru.guar7387.clientserverwithvolleysample.data.Vote;

public interface ArticleLoadedCallback {

    void onArticleLoaded(Article article,
                         String htmlText,
                         List<Comment> comments,
                         Map<Integer, User> commentsAuthors,
                         List<Vote> articleVotes,
                         Map<Integer, List<Vote>> commentsVotes);

}
