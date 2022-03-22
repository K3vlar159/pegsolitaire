package sk.tuke.gamestudio.game.pegsolitaire.service;

import org.junit.Test;
import sk.tuke.gamestudio.game.pegsolitaire.entity.Score;

import static org.junit.Assert.*;

import java.util.Date;

public class ScoreServiceTest {
    private ScoreService scoreService = new ScoreServiceJDBC();

    @Test
    public void testReset() {
        scoreService.reset();

        assertEquals(0, scoreService.getTopScores("pegsolitaire").size());
    }

    @Test
    public void testAddScore() {
        scoreService.reset();
        var date = new Date();

        scoreService.addScore(new Score("pegsolitaire", "David", 100, date));

        var scores = scoreService.getTopScores("pegsolitaire");
        assertEquals(1, scores.size());
        assertEquals("pegsolitaire", scores.get(0).getGame());
        assertEquals("David", scores.get(0).getPlayer());
        assertEquals(100, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());
    }

    @Test
    public void testGetTopScores() {
        scoreService.reset();
        var date = new Date();
        scoreService.addScore(new Score("pegsolitaire", "Jozo", 120, date));
        scoreService.addScore(new Score("pegsolitaire", "Jano", 150, date));
        scoreService.addScore(new Score("pegsolitaire", "Michal", 180, date));
        scoreService.addScore(new Score("pegsolitaire", "David", 100, date));

        var scores = scoreService.getTopScores("pegsolitaire");

        assertEquals(4, scores.size());

        assertEquals("pegsolitaire", scores.get(0).getGame());
        assertEquals("Michal", scores.get(0).getPlayer());
        assertEquals(180, scores.get(0).getPoints());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("pegsolitaire", scores.get(1).getGame());
        assertEquals("Jano", scores.get(1).getPlayer());
        assertEquals(150, scores.get(1).getPoints());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("pegsolitaire", scores.get(2).getGame());
        assertEquals("Jozo", scores.get(2).getPlayer());
        assertEquals(120, scores.get(2).getPoints());
        assertEquals(date, scores.get(2).getPlayedOn());
    }
}

