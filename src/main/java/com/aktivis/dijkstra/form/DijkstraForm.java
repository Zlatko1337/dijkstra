package com.aktivis.dijkstra.form;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DijkstraForm {
	private String incidenceMatrix;
	private String weights;
	private int startVertex;
	private int endVertex;
}
