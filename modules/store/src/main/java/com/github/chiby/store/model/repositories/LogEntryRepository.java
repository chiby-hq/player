package com.github.chiby.store.model.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.github.chiby.player.model.LogEntry;

public interface LogEntryRepository extends PagingAndSortingRepository<LogEntry, String>{

}
