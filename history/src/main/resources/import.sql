--// note: persistence is case sensitive with objects; multilines need MultipleLinesSqlCommandExtractor

INSERT INTO history( datebegpre, datebeg, dateendpre, dateend,
	eramain, locations, personname, eventmain, references, grouping, mediaicopath )
	VALUES
	( --// Hebrews
		'-' , '0004-00-00-00.00.00' ,
		'+' , '0029-04-01-00.00.00' ,
		'Chazal era Zugot period' , 'Israel, Jerusalem' ,
		'Jesus Christ' ,
		'birth , ministry , death , resurrection' ,
		'Josephus, MaraBarSerapion, Phlegon, Thallus' , 'h0000' ,
		'_0000_H_Nazareth_JesusCross' ),
	( --// Africa
		'+' , '0200' ,
		'+' , '0960' ,
		'Kushite Empire' , 'Ethiopia, Tigray Region, Axum' ,
		'Axum_Kingdom' ,
		'Construction of Stela, emerged from Damot' ,
		'Book of Axum' , 'a0000' ,
		'_0000_A_Axum' ),
	( --// Mesopotamia
		'-' , '0002' ,
		'+' , '0004' ,
		'Parthian Empire' , 'Iraq, BaghdadGovernorate, SalmanPak / old: Ctesiphon' ,
		'Parthia dynasty Arsacid king #26 Phraates 5' ,
		'Negotiated with Augustus, ruled with regent Musa, Zoroastrian' ,
		'Josephus, Cassius Dio' , 'm0000' ,
		'_0000_M_Parthia_KingPhraates5' ),
	( --// India
		'+' , '0020' ,
		'+' , '0024' ,
		'Satavahana dynasty' , 'Maharashtra, Paithan' ,
		'Satavahana dynasty king #17 Hala' ,
		'compiled Maharashtri Prakrit poems called GahaSattasai' ,
		'Matsya Purana' , 'i0000' ,
		'_0000_I_Satavahana_AndhraPradesh_Earings' ),
	( --// China
		'+' , '0009' ,
		'+' , '0023' ,
		'Han Dynasty 漢朝 han4chao2' , 'China, Shaanxi, Xian / 陕西, 西安, old: 长安 [chang2 an1] ChangAn' ,
		'Xin emperor Wang Mang / 新王莽 Xin1 dynasty emperor #01 Wang2 Mang3' ,
		'漢朝 Han Dynasty interregnum 新 Xin dynasty, and only emperor' ,
		'Book of Han 《漢書》 & Book of Later Han 《後漢書》' , 'c0000' ,
		'_0000_C_Xin_EmperorWangMangCoin' ),
	( --// Europe
		'-' , '0027' ,
		'+' , '0014' ,
		'Roman Empire' , 'Italy, Rome' ,
		'Roman emperor #1 Caesar Octavias Augustus' ,
		'founder of Roman Principate; contemporary with Cunobelinus' ,
		'Suetonius' , 'e0000' ,
		'_0000_E_Rome_EmperorAugustus1' ),
	( --// America2
		'-' , '0400' ,
		'+' , '0400' ,
		'Late Preclassic Mayan' , 'Guatemala, PetenDepartment, Flores' ,
		'Maya Tikal, aka Yax Mutal' ,
		'earliest known ruler: YaxEhbXok / includes: Teotihuacan, Zapotec, Haustec' ,
		'Popol Vuh' , 'a0000' ,
		'_0000_A2_Maya_Tikal' );
