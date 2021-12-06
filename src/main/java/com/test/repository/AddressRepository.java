package com.test.repository;

import com.test.model.Address;

import com.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "SELECT a from Address a where a.id=:id")
    List<Address> findGetIdAddress(int id);

    @Query(value = "SELECT a from Address a where a.number=:number and a.street=:street")
    Address getAllByAddress (String number ,String street);

}
