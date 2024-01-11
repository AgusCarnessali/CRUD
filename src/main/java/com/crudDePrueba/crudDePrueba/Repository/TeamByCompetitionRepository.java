package com.crudDePrueba.crudDePrueba.Repository;

import com.crudDePrueba.crudDePrueba.Model.Entity.TeamByCompetition;
import com.crudDePrueba.crudDePrueba.Projection.CompetitionProjection;
import com.crudDePrueba.crudDePrueba.Projection.TeamProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TeamByCompetitionRepository extends JpaRepository<TeamByCompetition,Long> {
    @Query(value = "SELECT c.name,c.price,tc.matches_played FROM competitions c join team_by_competition tc on tc.competition_id = c.id WHERE tc.team_id =:id",nativeQuery = true)
    List<CompetitionProjection> getCompetitionsByTeamId(Long id);

    @Query(value = "SELECT t.name,t.city,t.fans, tc.matches_played FROM teams t join team_by_competition tc on tc.team_id = t.id WHERE tc.competition_id=:id",nativeQuery = true)
    List<TeamProjection>getTeamByCompetitionId(Long id);

}


