SQL_CUSTOMERS=SELECT \
	ROW_NUMBER() OVER (ORDER BY LastName) AS row_num, \
	FirstName, LastName, Company, Address \
FROM customers ORDER BY LastName ASC LIMIT 5;

SQL_EMPLOYEES=SELECT * FROM employees LIMIT 5;

SQL_INVOICES=SELECT * FROM invoices LIMIT 5;

SQL_PLAYLISTS=SELECT * FROM playlists LIMIT 5;