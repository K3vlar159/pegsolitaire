package sk.tuke.gamestudio.game.pegsolitaire;



import sk.tuke.gamestudio.game.pegsolitaire.entity.Score;
import sk.tuke.gamestudio.game.pegsolitaire.service.ScoreService;
import sk.tuke.gamestudio.game.pegsolitaire.service.ScoreServiceJDBC;

import java.util.Date;

public class TestJDBC {
    public static void main(String[] args) throws Exception {
        ScoreService service = new ScoreServiceJDBC();
        service.reset();
        service.addScore(new Score("pegsolitaire", "jaro", 110, new Date()));
        service.addScore(new Score("pegsolitaire", "jano", 112, new Date()));
        service.addScore(new Score("pegsolitaire", "jozo", 116, new Date()));
        service.addScore(new Score("pegsolitaire", "david", 120, new Date()));
        service.addScore(new Score("pegsolitaire", "tiles", 100, new Date()));

        var scores = service.getTopScores("pegsolitaire");
        System.out.println(scores);
    }
}
