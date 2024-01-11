package com.crudDePrueba.crudDePrueba.Controller;

import com.crudDePrueba.crudDePrueba.Model.Entity.Competition;
import com.crudDePrueba.crudDePrueba.Model.Entity.TeamByCompetition;
import com.crudDePrueba.crudDePrueba.Model.Record.CompetitionRecord;
import com.crudDePrueba.crudDePrueba.Projection.TeamProjection;
import com.crudDePrueba.crudDePrueba.Service.Interface.CompetitionService;
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
@RequestMapping(path = "competitions")
public class CompetitionController {

    @Autowired
    CompetitionService competitionService;

    @GetMapping
    public ResponseEntity<List<CompetitionRecord>> getAll() {
        return new ResponseEntity<>(competitionService.getCompetitions(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitionRecord> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(competitionService.getCompetitionById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Competition> create(@Valid @RequestBody CompetitionRecord competitionRecord) {
        return new ResponseEntity<>(competitionService.create(competitionRecord), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Competition> update(@Valid @RequestBody CompetitionRecord competitionRecord, @PathVariable("id") Long id) {
        return new ResponseEntity<>(competitionService.update(competitionRecord, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        competitionService.delete(id);
    }

    @PutMapping("/teams/{compId}/{teamId}/{matches}")
    public ResponseEntity<List<TeamByCompetition>> addTeam(
            @PathVariable("compId") Long compId,
            @PathVariable("teamId") Long teamId,
            @PathVariable("matches") Integer matches) {
        return new ResponseEntity<>(competitionService.addTeam(compId, teamId, matches), HttpStatus.CREATED);
    }

    @GetMapping("teambycompetition/{compId}")
    public ResponseEntity<Map<String,List<TeamProjection>>> getTeamsByCompetitionId(@PathVariable("compId")Long id){
        return new ResponseEntity<>(competitionService.getTeamsByCompetitionId(id),HttpStatus.OK);
    }
}
