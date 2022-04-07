package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.game.pegsolitaire.consoleui.ConsoleUI;
import sk.tuke.gamestudio.game.pegsolitaire.core.GameField;
import sk.tuke.gamestudio.service.*;


@SpringBootApplication
@Configuration
public class SpringClient {

    private static final String[][] standardtest = {
            {"null", "null", "empty", "empty", "empty", "null","null"},
            {"null", "null", "empty", "empty", "empty", "null","null"},
            {"empty", "empty", "empty", "empty", "empty", "empty", "empty"},
            {"empty", "empty", "peg", "empty", "peg", "peg", "empty"},
            {"empty", "empty", "empty", "empty", "empty", "empty", "empty"},
            {"null", "null", "empty", "empty", "empty", "null","null"},
            {"null", "null", "empty", "empty", "empty", "null","null"}};

    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI console){
        return s -> console.play();
    }

    @Bean
    public ConsoleUI console(GameField field){
        return new ConsoleUI(field);
    }

    @Bean
    public GameField field(){
        return new GameField(standardtest);
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceJPA();
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceJPA();
    }
}

