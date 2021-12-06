package com.test.service;

import com.test.model.Telephone;
import com.test.repository.TelephoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TelephoneServiceIMPL implements TelephoneService{
    @Autowired
    private TelephoneRepository telephoneRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Telephone telephone) {
        telephoneRepository.save(telephone);
    }
}
