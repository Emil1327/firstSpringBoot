package com.test.controller;

import com.test.exception.BadRequestException;
import com.test.exception.NotFoundException;
import com.test.model.Address;
import com.test.model.User;
import com.test.service.AddressService;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    @GetMapping
    List<User> get_all() {
        return userService.getAll();
    }

    @GetMapping("{id}")
    @RolesAllowed(value = "ROLE_ADMIN")
    User get_by_id(@PathVariable int id) {
        return userService.getById(id);
    }

    @RolesAllowed(value = "ROLE_ADMIN")

    @GetMapping("/GetByEmail")
    User get_by_email(@RequestParam("email") String email) throws NotFoundException {
        return userService.getByEmail(email);
    }

    @PutMapping("/update")
    public void update_by_name(@RequestParam("name") String name, @RequestParam("id") int id) {
        userService.updateByName(name, id);
    }

    @PostMapping("/register")
    public void create(@RequestBody() User user) throws NotFoundException {
        userService.register(user);
    }

    @GetMapping("/login")
    User login(@RequestParam("email") String email, @RequestParam("password") String password) throws BadRequestException {
        return userService.login(email, password);
    }

    @GetMapping("/getByAddress")
    public List<Address> get_by_address(int id) {
        return addressService.findGetIdAddress(id);
    }

    @GetMapping("/verify")
    public void verify(@RequestParam("email") String email) throws NotFoundException {
        userService.verify(email);
    }

    @GetMapping("/sendEmail")
    public void send_email(@RequestParam("email") String email){
        userService.sendEmail(email);
    }

    @GetMapping("/resetPass")
    public  void reset_password_token (@RequestParam("email") String email) throws NotFoundException {
        userService.resetPassword(email);
    }


    @PostMapping("/newPassword")
    public void sava_new_password(@RequestParam("token") long token ,@RequestParam("password") String password) throws NotFoundException {
        userService.saveNewPassword(token,password);
    }
}
