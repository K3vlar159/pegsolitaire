package sk.tuke.gamestudio.game.pegsolitaire.service;

import org.junit.Test;
import sk.tuke.gamestudio.game.pegsolitaire.entity.Comment;
import sk.tuke.gamestudio.game.pegsolitaire.entity.Rating;


import static org.junit.Assert.*;

import java.util.Date;

public class RatingServiceTest {
    private RatingService ratingService = new RatingServiceJDBC();

    @Test
    public void testReset() {
        ratingService.reset();

        assertEquals(0, ratingService.getAverageRating("pegsolitaire"));
    }

    @Test
    public void testSetRating() {
        ratingService.reset();
        var date = new Date();

        ratingService.setRating(new Rating("pegsolitaire", "David", 1, date));
        var rating = ratingService.getRating("pegsolitaire","David");
        assertEquals(1, rating);

        ratingService.setRating(new Rating("pegsolitaire", "David", 5, date));
        rating = ratingService.getRating("pegsolitaire","David");
        assertEquals(5, rating);

        assertEquals(5, ratingService.getAverageRating("pegsolitaire"));
    }

    @Test
    public void testGetRating() {
        ratingService.reset();
        var date = new Date();

        ratingService.setRating(new Rating("pegsolitaire", "David", 1, date));
        ratingService.setRating(new Rating("pegsolitaire", "Jozo", 3, date));
        var rating = ratingService.getRating("pegsolitaire","David");
        assertEquals(1, rating);

        rating = ratingService.getRating("pegsolitaire","Jozo");
        assertEquals(3, rating);
    }

    @Test
    public void testGetAverageRating() {
        ratingService.reset();
        var date = new Date();

        ratingService.setRating(new Rating("pegsolitaire", "David", 1, date));
        ratingService.setRating(new Rating("pegsolitaire", "Jozo", 3, date));
        ratingService.setRating(new Rating("pegsolitaire", "Jano", 2, date));
        var rating = ratingService.getAverageRating("pegsolitaire");
        assertEquals(2, rating);
    }
}



