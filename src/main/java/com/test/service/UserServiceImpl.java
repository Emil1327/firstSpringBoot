package com.test.service;

import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.model.*;
import com.test.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.util.resources.LocaleData;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
        user.setToken("123141");

        Address address = user.getAddress();
        addressService.save(address);

        Telephone telephone = user.getTelephone();
        telephoneService.save(telephone);

        user.getData();

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        sendEmail(user.getEmail());
    }

    @Override
    public void sendEmail(String email) {
        String text = "Uzumes ancnel verefikaciya sxmi ays hxumin http://localhost:8080/user/verify?email=" + email;
        mailSender.sendSimpleMessage(email, "Verify", text);

    }

    @Transactional
    @Override
    public void verify(String email) throws NotFoundException {
        User user = userRepository.getByEmail(email);
        if (user == null) {
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
    public User login(String email, String password) throws BadRequestException {
        if (userRepository.login(email, password).getStatus() != Status.VERIFIED) {
            throw new BadRequestException();
        }
        return userRepository.login(email, password);
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        if (userRepository.getByEmail(email) != null) {
            User user = userRepository.getByEmail(email);
            return user;
        }
        throw new NotFoundException();
    }

    @Override
    public List<User> getAllByName(String name) {
        return userRepository.getAllByName(name);
    }

    @Override
    public void resetPassword(String email) throws NotFoundException {
        String randomString = RandomStringUtils.random(10, true, false);

        if (userRepository.getByEmail(email) == null) {
            throw new NotFoundException();
        }
        User user = userRepository.getByEmail(email);
        user.setToken(randomString);

        long milliseconds = System.currentTimeMillis();
        user.setMilliseconds(milliseconds);
        userRepository.save(user);

        String text = "Dzet tokene : " + user.getToken();
        mailSender.sendSimpleMessage(email, "Token", text);
    }

    @Override
    public void saveNewPassword(String token, String password) throws NotFoundException, BadRequestException {
        if (userRepository.getUserByToken(token) == null) {
            throw new NotFoundException();
        }

        User user = userRepository.getUserByToken(token);

        int a = 120000;
        long b = user.getMilliseconds();
        long milliseconds = System.currentTimeMillis();
        long c = milliseconds - b;

        if (a <= c) {
            throw new BadRequestException();
        }

        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        user.setToken(null);
        user.setMilliseconds(0);

        user.setToken("0");
        userRepository.save(user);


    }
}
