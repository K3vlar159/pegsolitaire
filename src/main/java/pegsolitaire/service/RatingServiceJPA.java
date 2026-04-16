package pegsolitaire.service;

import pegsolitaire.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) throws RatingException {
        int updatedRows = entityManager.createNamedQuery("Rating.setRating")
                .setParameter("rating", rating.getRating())
                .setParameter("ratedOn", rating.getRatedOn())
                .setParameter("game", rating.getGame())
                .setParameter("player", rating.getPlayer())
                .executeUpdate();

        if (updatedRows == 0) {
            entityManager.persist(rating);
        }
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        Double averageRating = (Double) entityManager.createNamedQuery("Rating.getAvgRating")
                .setParameter("game", game).getSingleResult();
        return averageRating == null ? 0 : Math.round(averageRating.floatValue());
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        try {
            return ((Rating) entityManager.createNamedQuery("Rating.getRating")
                    .setParameter("game", game)
                    .setParameter("player", player)
                    .getSingleResult()).getRating();
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public void reset() throws RatingException {
        entityManager.createNamedQuery("Rating.resetRatings").executeUpdate();
    }
}

