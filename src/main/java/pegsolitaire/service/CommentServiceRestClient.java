package pegsolitaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import pegsolitaire.entity.Comment;

import java.util.Arrays;
import java.util.List;

public class CommentServiceRestClient implements CommentService {

    private String url = "http://localhost:8080/api/comments";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void addComment(Comment comment) throws CommentException {
        restTemplate.postForEntity(url, comment, Comment.class);
    }

    @Override
    public List<Comment> getComments(String game) throws CommentException {
        return Arrays.asList(restTemplate.getForEntity(url + "/" + game, Comment[].class).getBody());
    }

    @Override
    public void reset() throws CommentException {
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }
}

