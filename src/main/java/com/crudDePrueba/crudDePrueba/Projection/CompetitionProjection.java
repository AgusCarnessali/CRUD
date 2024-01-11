package com.crudDePrueba.crudDePrueba.Projection;

import org.springframework.beans.factory.annotation.Value;

public interface CompetitionProjection {
    String getName();

    @Value("#{target.matches_played}")
    Integer getMatchesPlayed();
}
