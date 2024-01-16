package com.crudDePrueba.crudDePrueba.Service.Interface;

import com.crudDePrueba.crudDePrueba.Model.Entity.Team;
import com.crudDePrueba.crudDePrueba.Model.Entity.TeamCompetition;
import com.crudDePrueba.crudDePrueba.Model.Record.TeamCompetitionRecord;
import com.crudDePrueba.crudDePrueba.Model.Record.TeamRecord;
import com.crudDePrueba.crudDePrueba.Projection.CompetitionProjection;

import java.util.List;
import java.util.Map;

public interface TeamService {

    List<TeamRecord> getTeams();

    TeamRecord getTeamById(Long id);

    Team create(TeamRecord teamRecord);

    Team update(TeamRecord teamRecord, Long id);

    void delete(Long id);

    TeamRecord teamRecordBuilder(Team team);

    Team teamBuilder(TeamRecord teamRecord, Long id);

    List<TeamCompetition> addCompetition(TeamCompetitionRecord teamCompetitionRecord);

    Map<String, List<CompetitionProjection>> getCompetitionsByTeamId(Long teamId);
}
