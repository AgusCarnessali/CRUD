package com.crudDePrueba.crudDePrueba.Service.Implementation;

import com.crudDePrueba.crudDePrueba.Model.Entity.Competition;
import com.crudDePrueba.crudDePrueba.Model.Entity.Team;
import com.crudDePrueba.crudDePrueba.Model.Entity.TeamCompetition;
import com.crudDePrueba.crudDePrueba.Model.Record.CompetitionRecord;
import com.crudDePrueba.crudDePrueba.Model.Record.TeamCompetitionRecord;
import com.crudDePrueba.crudDePrueba.Projection.TeamProjection;
import com.crudDePrueba.crudDePrueba.Repository.CompetitionRepository;
import com.crudDePrueba.crudDePrueba.Repository.TeamByCompetitionRepository;
import com.crudDePrueba.crudDePrueba.Repository.TeamRepository;
import com.crudDePrueba.crudDePrueba.Service.Interface.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamByCompetitionRepository teamByCompetitionRepository;

    @Override
    public List<CompetitionRecord> getCompetitions() {
        return competitionRepository.findAll().stream().map(this::competitionRecordBuilder).toList();
    }

    @Override
    public CompetitionRecord getCompetitionById(Long id) {
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("competition not found"));
        return competitionRecordBuilder(competition);
    }

    @Override
    public Competition create(CompetitionRecord competitionRecord) {
        return competitionRepository.save(competitionBuilder(competitionRecord, null));
    }

    @Override
    public Competition update(CompetitionRecord competitionRecord, Long id) {
        return competitionRepository.save(competitionBuilder(competitionRecord, id));
    }

    @Override
    public void delete(Long id) {
        competitionRepository.deleteById(id);
    }

    @Override
    public CompetitionRecord competitionRecordBuilder(Competition competition) {
        return new CompetitionRecord(competition.getName(), competition.getPrize());
    }

    @Override
    public Competition competitionBuilder(CompetitionRecord competitionRecord, Long id) {

        Competition competition = new Competition();
        competition.setId(id);
        competition.setName(competitionRecord.name());
        competition.setPrize(competitionRecord.prize());

        return competition;
    }

    @Override
    public List<TeamCompetition> addTeam(TeamCompetitionRecord teamCompetitionRecord) {
        Competition competition = competitionRepository.findById(teamCompetitionRecord.idComp())
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        Team team = teamRepository.findById(teamCompetitionRecord.idTeam())
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Optional<TeamCompetition> existingTeamByCompetition = competition.getTeamList().stream()
                .filter(tc -> tc.getTeam().equals(team))
                .findFirst();

        if (existingTeamByCompetition.isPresent()) {
            existingTeamByCompetition.get().setMatchesPlayed(existingTeamByCompetition.get().getMatchesPlayed() + teamCompetitionRecord.matches());
        } else {
            TeamCompetition newTeamCompetition = new TeamCompetition();
            newTeamCompetition.setTeam(team);
            newTeamCompetition.setCompetition(competition);
            newTeamCompetition.setMatchesPlayed(teamCompetitionRecord.matches());
            competition.getTeamList().add(newTeamCompetition);
        }

        competitionRepository.saveAndFlush(competition);
        return competition.getTeamList();
    }


    @Override
    public Map<String, List<TeamProjection>> getTeamsByCompetitionId(Long compId) {
        CompetitionRecord competition = getCompetitionById(compId);
        List<TeamProjection> teams = new ArrayList<>(teamByCompetitionRepository.getTeamByCompetitionId(compId));
        Map<String, List<TeamProjection>> map = new HashMap<>();
        map.put(competition.name(), teams);
        return map;
    }
}
