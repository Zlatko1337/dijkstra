package com.aktivis.dijkstra.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;   
   
    private String incidenceMatrix;
    private String weights;
    private int startVertex;
    private int endVertex;
    private String result;
  
    public Task(String incidenceMatrix, String weights, int startVertex, int endVertex, String result) {
        this.incidenceMatrix = incidenceMatrix;
        this.weights = weights;
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

}
