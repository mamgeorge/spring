package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Invoices;

import lombok.NonNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoicesRepository extends JpaRepository<Invoices, Integer> { 
	
	Page<Invoices> findAll(@NonNull Pageable pageable);
}