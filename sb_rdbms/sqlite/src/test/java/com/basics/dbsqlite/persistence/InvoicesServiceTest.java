package com.basics.dbsqlite.persistence;

import com.basics.dbsqlite.model.Customer;
import com.basics.dbsqlite.model.Invoices;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.basics.dbsqlite.ReflectionHelper.exposeObject;
import static org.aspectj.util.LangUtil.EOL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @Disabled( "avoids loading instance" )
class InvoicesServiceTest {

	@Autowired private InvoicesService invoicesService;

	@Test
	void findById( ) {

		Invoices invoices = invoicesService.findById(10);
		System.out.println(exposeObject(invoices));
		assertNotNull(invoices);
	}

	@Test void findAll( ) {

		StringBuilder sb = new StringBuilder();
		Iterable<Invoices> iterable = invoicesService.findAll();
		for ( Invoices invoices : iterable ) {
			sb.append(invoices.getInvoiceid())
				.append(" / ")
				.append(invoices.getCustomerid())
				.append(" / ")
				.append(invoices.getBillingaddress())
				.append(EOL);
		}
		System.out.println(sb);
		assertNotNull(iterable);
	}

	@Test
	void save( ) {

		Invoices invoices = invoicesService.save(null);
		System.out.println("getInvoiceid: " + invoices.getInvoiceid());

		long maxId = invoicesService.getMaxId();
		System.out.println("maxId: " + maxId);
		assertNotNull(invoices);
	}

	@Test
	void getMaxId( ) {

		long maxId = invoicesService.getMaxId();
		System.out.println("maxId: " + maxId);
		assertNotNull(maxId);
	}
}