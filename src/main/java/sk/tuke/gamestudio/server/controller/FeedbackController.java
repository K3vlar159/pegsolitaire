package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;

import java.util.Date;

@Controller
@RequestMapping("/pegsolitaire")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class FeedbackController {

    @Autowired
    private UserController userController;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;

    @RequestMapping("/playerfeedback")
    public String commentsAndRating(Model model){
        model.addAttribute("latestcomments",commentService.getComments("pegsolitaire"));
        model.addAttribute("averagerating",ratingService.getAverageRating("pegsolitaire"));
        return "playerfeedback";
    }

    @RequestMapping("/addcomment")
    public String addComment(String text) {
        commentService.addComment(new Comment("pegsolitaire", userController.getLoggedUser().getLogin(), text, new Date()));
        return "redirect:/pegsolitaire/playerfeedback";
    }

    @RequestMapping("/rate")
    public String setRating(int rating, Model model) {
        ratingService.setRating(new Rating("pegsolitaire", userController.getLoggedUser().getLogin(), rating, new Date()));
        model.addAttribute("playerrating",ratingService.getRating("pegsolitaire", userController.getLoggedUser().getLogin()));
        return "redirect:/pegsolitaire/playerfeedback";
    }

}
