SELECT name FROM sqlite_schema WHERE 
	type ='table' AND 
	name NOT LIKE 'sqlite_%'
	ORDER BY name;

SELECT 
	ROW_NUMBER() OVER (ORDER BY LastName) AS row_num, 
	FirstName, LastName, Company, Address --//, 
		--// City, State, Country, PostalCode, 
		--// Phone, Fax, Email, SupportRepId
	FROM customers ORDER BY LastName ASC LIMIT 5;

	