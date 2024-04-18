--// pgs
SELECT * FROM information_schema.schemata;
SELECT * FROM pg_catalog.pg_namespace;
SELECT * FROM pg_catalog.pg_tables;
SELECT * FROM "public".actor;
SELECT * FROM "public".address;
SELECT * FROM "public".city;
SELECT * FROM "public".country;
SELECT * FROM "public".customer;
SELECT * FROM "public".inventory;
SELECT * FROM "public".store;
SELECT * FROM "public".film;
SELECT * FROM "public".rental;
SELECT * FROM "public".customer c, "public".rental r
	WHERE c.customer_id = r.customer_id
	ORDER BY c.customer_id ASC;

--// derby
SELECT * FROM airlines;
SELECT * FROM cities;
SELECT * FROM countries;
SELECT * FROM flightavailability;
SELECT * FROM flights;
SELECT * FROM flights_history;
SELECT * FROM maps;
