package pegsolitaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import pegsolitaire.entity.Rating;

public class RatingServiceRestClient implements RatingService {

    private String url = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) throws RatingException {
        restTemplate.postForEntity(url, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) throws RatingException {
        return restTemplate.getForEntity(url + "/" + game, Integer.class).getBody();
    }

    @Override
    public int getRating(String game, String player) throws RatingException {
        return restTemplate.getForEntity(url + "/" + game + "/" + player, Integer.class).getBody();
    }

    @Override
    public void reset() throws RatingException {
        throw new UnsupportedOperationException("Reset is not supported on web interface");
    }
}

