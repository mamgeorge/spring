--// \src\main\resources\import.sql table cities
--// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor
--// note: H2 Console JDBC URL must match spring.datasource.url

INSERT INTO cities( name , population ) VALUES( 'JP Tokyo'			, 37400068 ); -- 01
INSERT INTO cities( name , population ) VALUES( 'IN Delhi'			, 28514000 ); -- 02
INSERT INTO cities( name , population ) VALUES( 'CN Shanghai'		, 25582000 ); -- 03
INSERT INTO cities( name , population ) VALUES( 'BR SaoPaulo'		, 21650000 ); -- 04
INSERT INTO cities( name , population ) VALUES( 'MX MexicoCity'		, 21581000 ); -- 05
INSERT INTO cities( name , population ) VALUES( 'EG Cairo'			, 20076000 ); -- 06
INSERT INTO cities( name , population ) VALUES( 'IN Mumbai'			, 19980000 ); -- 07
INSERT INTO cities( name , population ) VALUES( 'CN Beijing'		, 19618000 ); -- 08
INSERT INTO cities( name , population ) VALUES( 'BA Dhaka'			, 19578000 ); -- 09
INSERT INTO cities( name , population ) VALUES( 'JP Osaka'			, 19281000 ); -- 10
INSERT INTO cities( name , population ) VALUES( 'US NewYork'		, 18819000 ); -- 11
INSERT INTO cities( name , population ) VALUES( 'PK Karachi'		, 15400000 ); -- 12
INSERT INTO cities( name , population ) VALUES( 'AR BuenosAires'	, 14967000 ); -- 13
INSERT INTO cities( name , population ) VALUES( 'CN Chongqing'		, 14838000 ); -- 14
INSERT INTO cities( name , population ) VALUES( 'TR Istanbul'		, 14751000 ); -- 15
INSERT INTO cities( name , population ) VALUES( 'IN Kolkata'		, 14681000 ); -- 16
INSERT INTO cities( name , population ) VALUES( 'PH Manila'			, 13482000 ); -- 17
INSERT INTO cities( name , population ) VALUES( 'NG Lagos'			, 13463000 ); -- 18
INSERT INTO cities( name , population ) VALUES( 'BR RioDeJaneiro'	, 13293000 ); -- 19
INSERT INTO cities( name , population ) VALUES( 'CN Tianjin'		, 13215000 ); -- 20
INSERT INTO cities( name , population ) VALUES( 'CD Kinshasa'		, 13171000 ); -- 21
INSERT INTO cities( name , population ) VALUES( 'CN Guangzhou'		, 12638000 ); -- 22
INSERT INTO cities( name , population ) VALUES( 'US LosAngeles'		, 12458000 ); -- 23
INSERT INTO cities( name , population ) VALUES( 'RU Moscow'			, 12410000 ); -- 24

