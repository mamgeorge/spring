					SELECT * FROM pg_database WHERE datistemplate = false;

					SELECT DISTINCT (table_schema) FROM information_schema.tables;

					SELECT
																																		table_schema,
																																																			table_name
							FROM information_schema.tables
							WHERE table_schema =  'public'
								ORDER BY table_name ASC;

					SELECT
																																		actor_id,
																																																			first_name,
																																																			last_name,
																																																			last_update FROM actor;

					SELECT * FROM customer;
					SELECT * FROM film;
					SELECT * FROM rental;
					SELECT * FROM staff;
					SELECT * FROM store;

			INSERT INTO staff VALUES (
																						                         3,
																																						                          'John',
																																						                          'Jonzz',
																																								                         5,
																																						                          'Joe.Smith@yahoo.com',
																																								                         1,
																																						                         true,
																																						                          'JJ',
																																						                         random (),
																																						                         NOW (),
																																						                          'scan.png');

							--// actor, address, category, city, country, customer, film_category, film, film_actor,
--// inventory, language, payment, rental, staff , store
