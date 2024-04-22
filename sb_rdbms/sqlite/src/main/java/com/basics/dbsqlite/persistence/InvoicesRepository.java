package com.basics.dbsqlite.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository public interface InvoicesRepository extends JpaRepository<Invoices, Integer> { }