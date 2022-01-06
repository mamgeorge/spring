package com.humanities.history.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public class HistoryService implements IHistoryService {

	@Autowired
	private HistoryRepository historyRepository;

	@Override
	public History findById(Long id) {return historyRepository.findById(id).get();}

	@Override
	public History save(History history) {return historyRepository.save(history);}

	@Override
	public List<History> findAll() {return (List<History>) historyRepository.findAll();}
}