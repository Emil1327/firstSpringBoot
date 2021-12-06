package com.test.service;

import com.test.model.Address;
import java.util.List;


public interface AddressService {
    void save(Address address);

    List<Address> findGetIdAddress(int id);

    Address getAllByAddress(String number ,String street);
}
