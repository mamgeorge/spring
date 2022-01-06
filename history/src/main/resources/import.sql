--// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor

INSERT INTO history( datebegpre, datebeg, dateendpre, dateend,
	eramain, locations, personname, eventmain, references, grouping, mediaicopath )
	VALUES (
	'-' , '0004-00-00-00.00.00' ,
	'+' , '0029-04-01-00.00.00' ,
	'Roman Empire' , 'Israel, Jerusalem' ,
	'Jesus Christ' ,
	'birth , ministry , death , resurrection' ,
	'Josephus, MaraBarSerapion, Phlegon, Thallus' , 'h0000' ,
	'_0000_H_Nazareth_JesusCross' );
