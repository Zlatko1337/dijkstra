package com.aktivis.dijkstra.service;

import java.util.HashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aktivis.dijkstra.entities.Task;
import com.aktivis.dijkstra.repositories.TaskRepository;

@Service
public class DijkstraServiceImpl implements DijkstraService{

	@Autowired
        private TaskRepository taskRepository;
	
	private static final int NO_PARENT = -1;
	private String path = "";
		
	@Override
	public String calculate(String incidenceMatrixString, String weights, int startVertex, int endVertex) {	
		int[][] incidenceMatrix = convertIncidenceMatrixStringToMatrix(incidenceMatrixString.split("\r\n"));
		int[][] incidenceMatrixTransposed = transposeMatrix(incidenceMatrix);	
		HashMap<Integer, Integer> edgeWeightMap = convertEdgeWeightStringToMap(weights); //<index,weight>
		int[][] adjacencyMatrix = convertIncidenceMatrixToAdjancencyMatrix(incidenceMatrixTransposed,edgeWeightMap);
		
		String result = dijkstra(adjacencyMatrix, startVertex, endVertex);
		path = "";
		
		taskRepository.save(new Task(incidenceMatrixString, weights, startVertex, endVertex, result));
		
		return result;
	}
	
	
	private String dijkstra(int[][] adjacencyMatrix, int startVertex, int endVertex) {
		startVertex -= 1;
		int vertices = adjacencyMatrix[0].length;
		int[] pathCosts = new int[vertices];
		boolean[] added = new boolean[vertices];

		for (int vertexIndex = 0; vertexIndex < vertices; vertexIndex++) {
			pathCosts[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}

		pathCosts[startVertex] = 0;
		int[] parents = new int[vertices];
		parents[startVertex] = NO_PARENT;

		for (int i = 1; i < vertices; i++) {
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0; vertexIndex < vertices; vertexIndex++) {
				if (!added[vertexIndex] && pathCosts[vertexIndex] < shortestDistance) {
					nearestVertex = vertexIndex;
					shortestDistance = pathCosts[vertexIndex];
				}
			}

			added[nearestVertex] = true;

			for (int vertexIndex = 0; vertexIndex < vertices; vertexIndex++) {
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

				if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < pathCosts[vertexIndex])) {
					parents[vertexIndex] = nearestVertex;
					pathCosts[vertexIndex] = shortestDistance + edgeDistance;
				}
			}
		}

		return print(startVertex, endVertex, pathCosts, parents);
	}
	
	
	private int[][] convertIncidenceMatrixStringToMatrix(String[] lines){
	    int rows = lines.length; 
	    int columns = lines[0].split(",").length;            
	    int[][] matrix = new int[rows][columns];
	    for (int i = 1;  i <= rows; i++) {
	        String[] currentLine = lines[i-1].split(",");
	        for (int j = 0; j < currentLine.length; j++) {
	            matrix[i-1][j] = Integer.parseInt(currentLine[j]);
	        }
	    }
	    
	    return matrix;
	}
	
	
	private int[][] convertIncidenceMatrixToAdjancencyMatrix(int[][] incidenceMatrixTransposed, HashMap<Integer, Integer> edgeWeightMap) {
		int edges = incidenceMatrixTransposed.length;
		int vertices = incidenceMatrixTransposed[0].length;
		var adjancencyMatrix = new int[vertices][vertices];
	
		for (int m = 0; m < edges; m++) {
			int i = -1, j = -1, n = 0;
			for (; n < vertices && i == -1; n++) {
			    if (incidenceMatrixTransposed[m][n] == 1) 
			    	i = n;
			}		        
			for (; n < vertices && j == -1; n++) {
			    if (incidenceMatrixTransposed[m][n] == 1) 
			    	j = n;
			}    
			if (j == -1) 
				j = i;
	
			adjancencyMatrix[i][j] = adjancencyMatrix[j][i] = edgeWeightMap.get(m+1);    
		}
 
		return adjancencyMatrix;
	}

	
	private int[] getColumn(int[][] matrix, int column) {
	    return IntStream.range(0, matrix.length)
	        .map(i -> matrix[i][column]).toArray();
	}	
	
	
	private int[][] transposeMatrix(final int[][] matrix) {
	    return IntStream.range(0, matrix[0].length)
	        .mapToObj(i -> Stream.of(matrix).mapToInt(row -> row[i]).toArray())
	        .toArray(int[][]::new);
	}
	
	
	private HashMap<Integer, Integer> convertEdgeWeightStringToMap(String edgeWeightString) {
		String[] edgeWeightStringArray = edgeWeightString.split("\r\n");	
		HashMap<Integer, Integer> edgeWeightMap = new HashMap<>(); //<index,weight>
		
		for (int k = 0; k < edgeWeightStringArray.length; k++) {
			String[] edge = edgeWeightStringArray[k].split(": ");
			edgeWeightMap.put(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
		}
		
		return edgeWeightMap;
	}
	
	
	private String print(int startVertex, int endVertex, int[] costs, int[] parents) {	
		constructPath(endVertex-1, endVertex-1, parents);
		
		return 
//			"v".concat(Integer.toString(startVertex+1))
//			.concat("->")
//			.concat("v")
//			.concat(Integer.toString(endVertex+1))
//			.concat(" \t ")
			"Total cost is ".concat(Integer.toString(costs[endVertex-1]))
			.concat(". Shortest path is ")
			.concat(path);	
	}

	
	private void constructPath(int currentVertex, int endVertex, int[] parents) {
		if (currentVertex == NO_PARENT) {
			return;
		}	
		constructPath(parents[currentVertex], endVertex, parents);	
		path = path.concat("v").concat(Integer.toString(currentVertex+1)).concat(currentVertex == endVertex? "." : "->");	
	}
	
	
}
