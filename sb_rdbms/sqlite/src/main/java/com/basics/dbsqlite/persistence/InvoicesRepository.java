package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Invoices;

import lombok.NonNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoices, Integer> { 
	
    @SuppressWarnings("null")
	Page<Invoices> findAll(@NonNull Pageable pageable);
}