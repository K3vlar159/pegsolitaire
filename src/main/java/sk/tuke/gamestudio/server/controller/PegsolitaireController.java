package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.pegsolitaire.core.*;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;

@Controller
@RequestMapping("/pegsolitaire")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class PegsolitaireController {

    private GameField field = new GameField(Pattern.STANDARD.getPattern());
    private Integer currentRow;
    private Integer currentCol;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserController userController;

    @RequestMapping
    public String pegs(@RequestParam(required = false) Integer row, @RequestParam(required = false) Integer col, Model model) {

        if(field.getState() == FieldState.PLAYING) {
            if (currentRow != null && currentCol != null && row != null && col != null) {
                processMove(currentRow, currentCol, row, col);
                currentRow = null;
                currentCol = null;
                if (field.getState() == FieldState.SOLVED && userController.isLogged()) {
                    scoreService.addScore(new Score("pegsolitaire", userController.getLoggedUser().getLogin(), field.getScore(), new Date()));
                }
            }
            else if (row != null && col != null) {
                if(field.getTile(row,col).getState() == TileState.PEG) {
                    currentRow = row;
                    currentCol = col;
                }
            }
        }
        model.addAttribute("topscores",scoreService.getTopScores("pegsolitaire"));

        return "pegs";
    }

    private void processMove(int row, int col, int destRow, int destCol) {
        MoveDirection dir = findDirection(row,col,destRow,destCol);
        if(dir != null){
            field.movePeg(row,col,dir);
        }
        else return;
    }

    private MoveDirection findDirection(int row, int col, int destRow, int destCol){
        if(row - destRow == 2 && col == destCol) return MoveDirection.UP;
        if(row - destRow == -2 && col == destCol) return MoveDirection.DOWN;
        if(col - destCol == 2 && row == destRow) return MoveDirection.LEFT;
        if(col - destCol == -2 && row == destRow) return MoveDirection.RIGHT;
        else return null;
    }

    @RequestMapping("/standard")
    public String standard(){
        field = new GameField(Pattern.STANDARD.getPattern());
        return "pegs";
    }
    @RequestMapping("/european")
    public String european(){
        field = new GameField(Pattern.EUROPEAN.getPattern());
        return "pegs";
    }
    @RequestMapping("/diamond")
    public String diamond(){
        field = new GameField(Pattern.DIAMOND.getPattern());
        return "pegs";
    }

    @RequestMapping("/test")
    public String test(){
        field = new GameField(Pattern.TEST.getPattern());
        return "pegs";
    }

    public String getHtmlField() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>\n");
        for (int row = 0; row < field.getRowCount(); row++) {
            sb.append("<tr>\n");
            for (int col = 0; col < field.getColumnCount(); col++) {
                Tile tile = field.getTile(row, col);
                sb.append("<td>\n");
                sb.append("<a href='/pegsolitaire?row=" + row + "&col=" + col + "'>\n");
                if(currentCol != null && currentRow != null && col == currentCol && row == currentRow && tile.getState() == TileState.PEG){
                    sb.append("<img src='/images/selected.png'>");
                }
                else{
                    sb.append("<img src='/images/" + getImageName(tile) + ".png'>");
                }

                sb.append("</a>\n");
                sb.append("</td>\n");
            }
            sb.append("</tr>\n");
        }
        sb.append("<table>\n");
        return sb.toString();
    }

    private String getImageName(Tile tile) {
        switch (tile.getState()) {
            case CLOSED:
                return ("closed");
            case PEG:
                return ("peg");
            case EMPTY:
                return ("empty");
            default:
                throw new RuntimeException("unexpected tile state");
        }
    }


}
