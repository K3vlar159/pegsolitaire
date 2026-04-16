package pegsolitaire;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;
import pegsolitaire.game.consoleui.ConsoleUI;
import pegsolitaire.game.core.GameField;
import pegsolitaire.service.*;

@SpringBootApplication
@Configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "pegsolitaire.server.*"))
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
        new SpringApplicationBuilder(SpringClient.class).web(WebApplicationType.NONE).run(args);
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
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public GameField field(){
        return new GameField(standardtest);
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceRestClient();
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceRestClient();
    }

    @Bean
    public RatingService ratingService(){
        return new RatingServiceRestClient();
    }
}

