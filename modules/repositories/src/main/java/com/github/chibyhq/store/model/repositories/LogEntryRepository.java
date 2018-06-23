package com.github.chibyhq.store.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.chibyhq.playar.model.LogEntry;

public interface LogEntryRepository extends PagingAndSortingRepository<LogEntry, String>{

}
