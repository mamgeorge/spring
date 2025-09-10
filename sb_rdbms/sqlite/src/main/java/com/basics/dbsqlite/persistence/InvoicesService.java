package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Invoices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoicesService {

	private final InvoicesRepository invoicesRepository;

	@Autowired
	public InvoicesService(InvoicesRepository invoicesRepository) {
		this.invoicesRepository = invoicesRepository;
	}

	public Invoices findById(Integer id) { return invoicesRepository.findById(id).get(); }

	public List<Invoices> findAll( ) { return invoicesRepository.findAll(); }

	public Invoices save(Invoices invoices) {

		Invoices invoicesNew = new Invoices();
		try { invoicesNew = invoicesRepository.save(invoices); }
		catch (InvalidDataAccessApiUsageException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return invoicesNew;
	}

	public long getMaxId( ) { return invoicesRepository.count(); }
}
