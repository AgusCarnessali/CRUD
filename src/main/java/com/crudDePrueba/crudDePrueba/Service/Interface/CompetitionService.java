package com.crudDePrueba.crudDePrueba.Service.Interface;

import com.crudDePrueba.crudDePrueba.Model.Entity.Competition;
import com.crudDePrueba.crudDePrueba.Model.Entity.TeamCompetition;
import com.crudDePrueba.crudDePrueba.Model.Record.CompetitionRecord;
import com.crudDePrueba.crudDePrueba.Model.Record.TeamCompetitionRecord;
import com.crudDePrueba.crudDePrueba.Projection.TeamProjection;

import java.util.List;
import java.util.Map;

public interface CompetitionService {
    List<CompetitionRecord> getCompetitions();

    CompetitionRecord getCompetitionById(Long id);

    Competition create(CompetitionRecord competitionRecord);

    Competition update(CompetitionRecord competitionRecord, Long id);

    void delete(Long id);

    CompetitionRecord competitionRecordBuilder(Competition competition);

    Competition competitionBuilder(CompetitionRecord competitionRecord,Long id);

    List<TeamCompetition> addTeam(TeamCompetitionRecord teamCompetitionRecord);
    Map<String, List<TeamProjection>> getTeamsByCompetitionId(Long compId);
}
