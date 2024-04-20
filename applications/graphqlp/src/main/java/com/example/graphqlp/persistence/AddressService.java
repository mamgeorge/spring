package com.example.graphqlp.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

	private final AddressRepository addressRepository;

	@Autowired
	public AddressService(AddressRepository addressRepository) { this.addressRepository = addressRepository; }

	public List<Address> findAll( ) { return addressRepository.findAll(); }

	public Address findById(Integer id) { return addressRepository.findById(id).get(); } // getReferenceById

	public long getMaxId( ) { return addressRepository.count(); }
}
