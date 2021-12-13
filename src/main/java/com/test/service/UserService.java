package com.test.service;



import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.model.Address;
import com.test.model.User;

import javax.management.relation.Role;
import java.util.List;


public interface UserService {

    public List<User> getAll();

    public void removeById(int id);

    public void register(User user) throws NotFoundException;

    public User getById(int id);

    User getByEmail(String email) throws NotFoundException;

    List<User> getAllByName (String name);

    void updateByName(String name , int id);

    User login(String email,String password) throws BadRequestException;

    void verify(String email) throws NotFoundException;

    public void sendEmail(String toEmail);

    void resetPassword(String email) throws NotFoundException;

    void saveNewPassword(String token, String password) throws NotFoundException, BadRequestException;
}
