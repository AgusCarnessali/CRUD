package com.crudDePrueba.crudDePrueba.Controller;

import com.crudDePrueba.crudDePrueba.Model.Entity.Team;
import com.crudDePrueba.crudDePrueba.Model.Entity.TeamByCompetition;
import com.crudDePrueba.crudDePrueba.Model.Record.TeamRecord;
import com.crudDePrueba.crudDePrueba.Projection.CompetitionProjection;
import com.crudDePrueba.crudDePrueba.Service.Interface.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamRecord>> getAll() {
        return new ResponseEntity<>(teamService.getTeams(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamRecord> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(teamService.getTeamById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Team> create(@Valid @RequestBody TeamRecord teamRecord) {
        return new ResponseEntity<>(teamService.create(teamRecord), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> update(@Valid @RequestBody TeamRecord teamRecord, @PathVariable("id") Long id) {
        return new ResponseEntity<>(teamService.update(teamRecord, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        teamService.delete(id);
    }

    @PutMapping("/competitions/{teamId}/{compId}/{matches}")
    public ResponseEntity<List<TeamByCompetition>> addCompetition(@PathVariable("teamId") Long idTeam,
                                                                  @PathVariable("compId") Long idComp,
                                                                  @PathVariable("matches") Integer matches) {
        return new ResponseEntity<>(teamService.addCompetition(idTeam, idComp, matches), HttpStatus.CREATED);
    }

    @GetMapping("/teambycompetition/{teamId}")
    public ResponseEntity<Map<String,List<CompetitionProjection>>>getTeamCompetitions(@PathVariable("teamId")Long id){
        return new ResponseEntity<>(teamService.getCompetitionsByTeamId(id),HttpStatus.OK);
    }
}
