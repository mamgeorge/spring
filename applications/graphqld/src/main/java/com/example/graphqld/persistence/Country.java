package com.example.graphqld.persistence;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter @NoArgsConstructor
@Entity @Table( name = "COUNTRIES", schema = "app")
public class Country {

	public static final String[] ISO_CODES = new String("AF AL DZ AS AO AR AM AU AT AZ BS BD BB BE BZ BM BO BW BR BG KH "
		+ "CM CA CV CL CN CO CG CR CI CU CZ DK DO EC EG SV ET FK FJ FI FR GE DE GH GR GP GT HN HU IS IN ID IR "
		+ "IQ IE IL IT JM JP JO KE KR LB LT MG MY ML MX MA MZ NP NL NZ NI NG NO PK PY PE PH PL PT RU WS SN SL "
		+ "SG SK ZA ES LK SD SE CH SY TJ TZ TH TT TN TR UA GB US UY UZ VE VN VG VI YU ZR ZW").split(" ");

	@Id @GeneratedValue( strategy = GenerationType.AUTO )
	@Column( name = "country_iso_code" )
	private String country_iso_code;

	private String country;
	private String region;
}
