package com.crudDePrueba.crudDePrueba.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "team_by_competition")
public class TeamByCompetition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "matches_played")
    @NotNull
    private Integer matchesPlayed;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "team_id")
    Team team;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "competition_id")
    Competition competition;

    @Override
    public String toString() {
        return "TeamByCompetition{" +
                "id=" + id +
                ", matchesPlayed=" + matchesPlayed ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamByCompetition that = (TeamByCompetition) o;
        return Objects.equals(id, that.id) && Objects.equals(matchesPlayed, that.matchesPlayed) && Objects.equals(team, that.team) && Objects.equals(competition, that.competition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matchesPlayed, team, competition);
    }
}
