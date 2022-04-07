package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void setRating(Rating rating) throws RatingException {

        try {
            getRating(rating.getGame(), rating.getPlayer());
        }
        catch (NoResultException e) {
            entityManager.persist(rating);
            return;
        }
        entityManager.createNamedQuery("Rating.setRating")
                .setParameter("rating", rating.getRating())
                .setParameter("ratedOn", rating.getRatedOn())
                .setParameter("game", rating.getGame())
                .setParameter("player", rating.getPlayer()).executeUpdate();
    }


    @Override
    public int getAverageRating(String game) throws RatingException {
        Double averageRating = (double) entityManager.createNamedQuery("Rating.getAvgRating")
                .setParameter("game", game).getSingleResult();

        return Math.round(averageRating.floatValue());
    }

    @Override
    public int getRating(String game, String player) throws RatingException {

        return ((Rating) entityManager.createNamedQuery("Rating.getRating")
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult()).getRating();
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();

    }
}
