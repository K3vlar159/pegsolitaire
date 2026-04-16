package pegsolitaire.game.consoleui;

import org.springframework.beans.factory.annotation.Autowired;
import pegsolitaire.game.core.FieldState;
import pegsolitaire.game.core.GameField;
import pegsolitaire.game.core.MoveDirection;
import pegsolitaire.game.core.Tile;
import pegsolitaire.entity.Comment;
import pegsolitaire.entity.Rating;
import pegsolitaire.entity.Score;
import pegsolitaire.service.*;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleUI {

    private final GameField field;
    private final Scanner scanner = new Scanner(System.in);
    private static final Pattern INPUT_PATTERN = Pattern.compile("([A-G])([1-7])([UDLR])");

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;


    public ConsoleUI(GameField field) {
        this.field = field;
    }

    public void play() {
        printTopScores();
        while (field.getState() == FieldState.PLAYING) {
            show(this.field);
            handleInput();
            field.updateState();
        }
        show(this.field);
        if(field.getState() == FieldState.SOLVED)
        {
            scoreService.addScore(new Score("pegsolitaire", System.getProperty("user.name"), field.getScore(), new Date()));
            System.out.println("Congratulations, you solved the puzzle :)");
        }
        else{
            System.out.println("Sorry, you failed :(");
        }
        stopOrRestart();
    }

    private void stopOrRestart()
    {
        System.out.println("Do you want to play again ? Y/N");
        String input = scanner.nextLine().toUpperCase();
        if ("N".equals(input)) {
            rateAndComment();
            printLatestComments();
            printAvgRating();
            System.out.println("Thanks for playing !");
            System.exit(0);
        }
        else if ("Y".equals(input)) {
            this.field.reset();
            this.play();
        }
        else {
            System.out.println("Wrong input !");
            stopOrRestart();
        }
    }

    private void show(GameField field) {
        printHeader(field);
        printBody(field);
    }

    private void printBody(GameField field) {
        for (int row = 0; row < field.getRowCount(); row++) {
            System.out.print((char) (row + 'A'));
            for (int col = 0; col < field.getColumnCount(); col++) {
                Tile tile = field.getTile(row, col);
                printTile(tile);
            }
            System.out.println();
        }
    }

    private void printTile(Tile tile) {
        System.out.print("  ");
        switch (tile.getState()) {
            case CLOSED:
                System.out.print(' ');
                break;
            case PEG:
                System.out.print('O');
                break;
            case EMPTY:
                System.out.print('-');
                break;
        }
    }

    private void printHeader(GameField field) {
        System.out.print(' ');
        for (int col = 0; col < field.getColumnCount(); col++) {
            System.out.print("  ");
            System.out.print(col + 1);
        }
        System.out.println();
    }

    private void handleInput() {
        System.out.println("Commands: X - exit, A1L - move peg, directions - U=UP, D=DOWN, L=LEFT, R=RIGHT");
        String input = scanner.nextLine().toUpperCase();
        if ("X".equals(input)) {
            stopOrRestart();
        }
        Matcher matcher = INPUT_PATTERN.matcher(input);
        processMatcher(matcher);
    }

    private void processMatcher(Matcher matcher) {
        if (matcher.matches()) {
            int row = matcher.group(1).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(2)) - 1;
            switch (matcher.group(3)) {
                case "U":
                    field.movePeg(row, column, MoveDirection.UP);
                    break;
                case "D":
                    field.movePeg(row, column, MoveDirection.DOWN);
                    break;
                case "L":
                    field.movePeg(row, column, MoveDirection.LEFT);
                    break;
                case "R":
                    field.movePeg(row, column, MoveDirection.RIGHT);
                    break;
            }
        }
        else {
            System.out.println("Invalid input !!");
        }
    }

    private void printTopScores() {
        var scores = scoreService.getTopScores("pegsolitaire");
        System.out.println("---------------------------------------------------------------");
        for (int i = 0; i < scores.size(); i++) {
            var score = scores.get(i);
            System.out.printf("%d. %s %d\n", i + 1, score.getPlayer(), score.getPoints());
        }
        System.out.println("---------------------------------------------------------------");
    }

    private void printLatestComments() {
        var comments = commentService.getComments("pegsolitaire");
        System.out.println("-------- Latest comments -------------------------------------------------------");
        for (int i = 0; i < comments.size(); i++) {
            var comment = comments.get(i);
            System.out.printf("%d. %s: %s\n", i + 1, comment.getPlayer(), comment.getComment());
        }
        System.out.println("---------------------------------------------------------------");
    }

    private void rate()
    {
        System.out.println("Rate the game from 0 to 5 :");
        if(scanner.hasNextInt()) {
            int rating = scanner.nextInt();
            if(rating < 0 || rating > 5)
            {
                System.out.println("Rating must be a number between 0 and 5 !!");
                rate();
            }
            else {
                ratingService.setRating(new Rating("pegsolitaire", System.getProperty("user.name"), rating, new Date()));
            }
        }
        else {
            System.out.println("Rating must be a number between 0 and 5 !!");
            scanner.next();
            rate();
        }

    }
    private void comment()
    {
        System.out.println("Write your comment. If you dont want to add comment ,enter 'X'");
        String comment = scanner.nextLine();
        if ("x".equals(comment) || ("X".equals(comment)) ){
            return;
        }
        else {
            var date = new Date();
            commentService.addComment(new Comment("pegsolitaire", System.getProperty("user.name"), comment,date));
        }
    }

    private void rateAndComment()
    {
        System.out.println("Do you want to rate and comment ? Y/N");
        String input = scanner.nextLine().toUpperCase();
        if ("N".equals(input)) {
            return;
        }
        else if ("Y".equals(input)) {
            comment();
            rate();
        }
        else {
            System.out.println("Wrong input !");
            rateAndComment();
        }
    }

    private void printAvgRating()
    {
        System.out.printf("Average rating: %d\n", ratingService.getAverageRating("pegsolitaire"));
    }

}
