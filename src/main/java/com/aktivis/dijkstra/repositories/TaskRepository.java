package com.aktivis.dijkstra.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aktivis.dijkstra.entities.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    
   
}
