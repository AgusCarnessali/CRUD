package com.crudDePrueba.crudDePrueba.Service.Implementation;

import com.crudDePrueba.crudDePrueba.Model.Entity.Competition;
import com.crudDePrueba.crudDePrueba.Model.Entity.Team;
import com.crudDePrueba.crudDePrueba.Model.Entity.TeamByCompetition;
import com.crudDePrueba.crudDePrueba.Model.Record.TeamRecord;
import com.crudDePrueba.crudDePrueba.Projection.CompetitionProjection;
import com.crudDePrueba.crudDePrueba.Repository.CompetitionRepository;
import com.crudDePrueba.crudDePrueba.Repository.TeamByCompetitionRepository;
import com.crudDePrueba.crudDePrueba.Repository.TeamRepository;
import com.crudDePrueba.crudDePrueba.Service.Interface.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private TeamByCompetitionRepository teamByCompetitionRepository;

    @Override
    public List<TeamRecord> getTeams() {
        return teamRepository.findAll().stream().map(this::teamRecordBuilder).toList();
    }

    @Override
    public TeamRecord getTeamById(Long id) throws RuntimeException {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isEmpty()) {
            throw new RuntimeException("Team not found");       //HACER UNA EXCEPTION CUSTOM
        }
        return teamRecordBuilder(team.get());
    }

    @Override
    public Team create(TeamRecord teamRecord) {
        return teamRepository.save(teamBuilder(teamRecord, null));
    }

    @Override
    public Team update(TeamRecord teamRecord, Long id) {
        return teamRepository.save(teamBuilder(teamRecord, id));
    }

    @Override
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public TeamRecord teamRecordBuilder(Team team) {
        return new TeamRecord(team.getName(), team.getCity(), team.getFans());
    }

    @Override
    public Team teamBuilder(TeamRecord teamRecord, Long id) {
        Team team = new Team();
        team.setId(id);
        team.setCity(teamRecord.city());
        team.setName(teamRecord.name());
        team.setFans(teamRecord.fans());
        return team;
    }

    @Override
    public List<TeamByCompetition> addCompetition(Long idTeam, Long idCompetition, Integer matchesPlayed) {
        Team team = teamRepository.findById(idTeam)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        Competition competition = competitionRepository.findById(idCompetition)
                .orElseThrow(() -> new RuntimeException("Competition not found"));

        Optional<TeamByCompetition> existingTeamByCompetition = team.getCompetitionList().stream()
                .filter(tc -> tc.getCompetition().equals(competition))
                .findFirst();

        if (existingTeamByCompetition.isPresent()) {

            existingTeamByCompetition.get().setMatchesPlayed(existingTeamByCompetition.get().getMatchesPlayed() + matchesPlayed);
        } else {
            TeamByCompetition newTeamByCompetition = new TeamByCompetition();
            newTeamByCompetition.setTeam(team);
            newTeamByCompetition.setCompetition(competition);
            newTeamByCompetition.setMatchesPlayed(matchesPlayed);
            team.getCompetitionList().add(newTeamByCompetition);
        }

        teamRepository.saveAndFlush(team);
        return team.getCompetitionList();
    }


    @Override
    public Map<String, List<CompetitionProjection>> getCompetitionsByTeamId(Long teamId) {
        TeamRecord team = getTeamById(teamId);
        List<CompetitionProjection> competitions = new ArrayList<>(teamByCompetitionRepository.getCompetitionsByTeamId(teamId));
        Map<String, List<CompetitionProjection>> map = new HashMap<>();
        map.put(team.name(), competitions);
        return map;
    }
}
