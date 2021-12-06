package com.test.service;

import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.model.Address;
import com.test.model.Status;
import com.test.model.Telephone;
import com.test.model.User;
import com.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AddressService addressService;
    @Autowired
    private TelephoneService telephoneService;
    @Autowired
    private MailSender mailSender;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void removeById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void register(User user) throws NotFoundException {
        user.setStatus(Status.UNVERIFIED);

        Address address = user.getAddress();
        addressService.save(address);

        Telephone telephone = user.getTelephone();
        telephoneService.save(telephone);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        sendEmail(user.getEmail());
    }

    @Override
    public void sendEmail(String email){
        String text = "Uzumes ancnel verefikaciya sxmi ays hxumin http://localhost:8080/user/verify?email=" + email;
        mailSender.sendSimpleMessage(email,"Verify",text);

    }

    @Transactional
    @Override
    public void verify(String email) throws NotFoundException {
        User user = userRepository.getByEmail(email);
        if(user==null){
            throw new NotFoundException();
        }
        user.setStatus(Status.VERIFIED);
        userRepository.save(user);
    }

    @Override
    public User getById(int id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            try {
                throw new NotFoundException();
            } catch (NotFoundException e) {
                System.out.println("Barev");
            }
        }
        return optionalUser.get();
    }

    @Override
    public void updateByName(String name, int id) {
        User user1 = getById(id);
        user1.setName(name);
        userRepository.save(user1);
    }

    @Override
    public User login(String email,String password) throws BadRequestException {
        if(userRepository.login(email,password).getStatus() != Status.VERIFIED){
            throw new BadRequestException();
        }
        return userRepository.login(email,password);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        if(userRepository.getByEmail(email) != null){
            User user = userRepository.getByEmail(email);
            return user;
        }
        throw new NotFoundException();
    }

    @Override
    public List<User> getAllByName(String name) {
        return userRepository.getAllByName(name);
    }

}
