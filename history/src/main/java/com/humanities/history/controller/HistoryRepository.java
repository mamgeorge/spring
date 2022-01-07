package com.humanities.history.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository public interface HistoryRepository extends CrudRepository<History, Long> { }
