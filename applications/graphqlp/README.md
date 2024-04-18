query addr { getAddresses(count: 4) {

  	address_id 
  	address
	} 
}

query addrFilt { getAddresses(count: 4) {

  	address_id
  	address
		district  
 		city_id
  	phone
	} 
}
