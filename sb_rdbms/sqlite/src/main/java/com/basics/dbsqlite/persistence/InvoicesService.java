package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Invoices;

import lombok.NonNull;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service public class InvoicesService {

	private final InvoicesRepository invoicesRepository;

	public InvoicesService(InvoicesRepository invoicesRepository) {
		this.invoicesRepository = invoicesRepository;
	}

	public Invoices findById(Integer id) { 
		@NonNull Integer idSafe = (id != null) ? id : 0; 
		return invoicesRepository.findById(idSafe).get(); }

	public List<Invoices> findAll( ) { return invoicesRepository.findAll(); }

	public Invoices save(Invoices invoices) {

		Invoices invoicesNew = new Invoices();
		Invoices invoicesSafe = (invoices != null) ? invoices : invoicesNew;
		try { invoicesNew = invoicesRepository.save(invoicesSafe); }
		catch (InvalidDataAccessApiUsageException ex) { System.out.println("ERROR: " + ex.getMessage()); }
		return invoicesNew;
	}

	public long getMaxId( ) { return invoicesRepository.count(); }
}
