package com.aktivis.dijkstra.service;

import org.springframework.stereotype.Component;

@Component
public interface DijkstraService {

	public String calculate(String incidenceMatrix, String weights, int startVertex, int endVertex);
	
}
