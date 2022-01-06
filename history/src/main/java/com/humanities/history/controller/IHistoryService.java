package com.humanities.history.controller;

import java.util.List;

public interface IHistoryService {

	History findById(Long id);

	History save(History city);

	List<History> findAll();
}
