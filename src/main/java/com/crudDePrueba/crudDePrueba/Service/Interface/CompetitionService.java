package com.crudDePrueba.crudDePrueba.Service.Interface;

import com.crudDePrueba.crudDePrueba.Model.Entity.Competition;
import com.crudDePrueba.crudDePrueba.Model.Entity.TeamByCompetition;
import com.crudDePrueba.crudDePrueba.Model.Record.CompetitionRecord;
import com.crudDePrueba.crudDePrueba.Model.Record.TeamRecord;
import com.crudDePrueba.crudDePrueba.Projection.CompetitionProjection;
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

    List<TeamByCompetition> addTeam(Long idCompetition, Long idTeam, Integer matchesPlayed);
    Map<String, List<TeamProjection>> getTeamsByCompetitionId(Long compId);
}
