https://www.baeldung.com/spring-graphql
https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/
https://www.bezkoder.com/spring-boot-graphql-example/
https://www.youtube.com/playlist?list=PLiwhu8iLxKwL1TU0RMM6z7TtkyW-3-5Wi

query addrSome { getAddresses(count: 4) {

	address_id 
	address
} }

query addrFull { getAddresses(count: 4) {

	address_id
	address
	district
	city_id
	phone
	last_update
} }

query addr {getAddress(address_id: 3) {

	address_id
	address
	district
	city_id
	phone
	last_update
} }

query addrRng { getAddressRng(beg: 2, end: 4) {

	address_id
	address
	district
	city_id
	phone
	last_update
} }
