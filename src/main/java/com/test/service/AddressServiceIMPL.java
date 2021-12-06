package com.test.service;

import com.test.model.Address;
import com.test.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AddressServiceIMPL implements AddressService{
    @Autowired
    private AddressRepository addressRepository;

    @Override
    @Transactional()
    public void save(Address address) {
        addressRepository.save(address);
    }

    @Override
    public List<Address> findGetIdAddress(int id) {
        return addressRepository.findGetIdAddress(id);
    }

    @Override
    public Address getAllByAddress(String number, String street) {
        return addressRepository.getAllByAddress(number,street);
    }


}
