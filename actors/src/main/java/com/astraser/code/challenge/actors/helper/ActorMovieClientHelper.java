package com.astraser.code.challenge.actors.helper;

import com.astraser.code.challenge.actors.dto.ActorDto;
import com.astraser.code.challenge.actors.dto.MovieDto;
import com.astraser.code.challenge.actors.service.client.ActorMovieClient;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@AllArgsConstructor
@Slf4j
public class ActorMovieClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(ActorMovieClientHelper.class);

    private ActorMovieClient actorMovieClient;

    public Collection<MovieDto> getActorMovies(ActorDto actorDto) {
        try {
            ResponseEntity<Collection<MovieDto>> actorMoviesDtoResponseEntity = actorMovieClient.getActorMovies(actorDto.getId());

            if (actorMoviesDtoResponseEntity.hasBody()) {
                return actorMoviesDtoResponseEntity.getBody();
            }
        } catch (FeignException fe) {
            logger.error("Failed to fetch movies for actor {} with exception: {}", actorDto.getId(), fe.getMessage());
        }

        return Collections.emptyList();
    }

    public void deleteActorMovies(Long id) {
        try {
            actorMovieClient.deleteActorMovieByActor(id);
        } catch (FeignException fe) {
            logger.error("Failed to delete movies for actor {} with exception: {}", id, fe.getMessage());
        }
    }
}
