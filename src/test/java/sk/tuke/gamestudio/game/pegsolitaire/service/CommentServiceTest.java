package sk.tuke.gamestudio.game.pegsolitaire.service;

import org.junit.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.CommentServiceJDBC;


import static org.junit.Assert.*;

import java.util.Date;

public class CommentServiceTest {
    private CommentService commentService = new CommentServiceJDBC();

    @Test
    public void testReset() {
        commentService.reset();

        assertEquals(0, commentService.getComments("pegsolitaire").size());
    }

    @Test
    public void testAddComment() {
        commentService.reset();
        var date = new Date();

        commentService.addComment(new Comment("pegsolitaire", "David", "test", date));

        var comments = commentService.getComments("pegsolitaire");
        assertEquals(1, comments.size());
        assertEquals("pegsolitaire", comments.get(0).getGame());
        assertEquals("David", comments.get(0).getPlayer());
        assertEquals("test", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getCommentedOn());
    }

    @Test
    public void testGetComments() {
        commentService.reset();
        var date1 = new Date();
        var date2 = new Date(1000000000);
        commentService.addComment(new Comment("pegsolitaire", "David", "test1", date1));
        commentService.addComment(new Comment("pegsolitaire", "Jozo", "test2", date2));

        var comments = commentService.getComments("pegsolitaire");

        assertEquals(2,comments.size());

        assertEquals("pegsolitaire", comments.get(1).getGame());
        assertEquals("Jozo", comments.get(1).getPlayer());
        assertEquals("test2", comments.get(1).getComment());
        assertEquals(date2, comments.get(1).getCommentedOn());

        assertEquals("pegsolitaire", comments.get(0).getGame());
        assertEquals("David", comments.get(0).getPlayer());
        assertEquals("test1", comments.get(0).getComment());
        assertEquals(date1, comments.get(0).getCommentedOn());
    }
}


