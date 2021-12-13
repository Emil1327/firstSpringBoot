package com.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "reset_password_token")
    private String token;

    @Column(name = "reset_password_token_create_data")
    private long milliseconds;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn()
    private Telephone telephone;


    @NotNull
    @Column(name = "status")
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authority;

    public User(int id, String name, LocalDate data, String email, String password, Gender gender, String token, long milliseconds, Address address, Telephone telephone, Status status, List<Authority> authority) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.token = token;
        this.milliseconds = milliseconds;
        this.address = address;
        this.telephone = telephone;
        this.status = status;
        this.authority = authority;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Authority> getAuthor() {
        return authority;
    }

    public void setAuthor(List<Authority> author) {
        this.authority = author;
    }

    private void UserStatus(User user) {
        user.status = Status.UNVERIFIED;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Telephone getTelephone() {
        return telephone;
    }

    public void setTelephone(Telephone telephone) {
        this.telephone = telephone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User(User user) {
    }

    public List<Authority> getAuthority() {
        return authority;
    }

    public void setAuthority(List<Authority> authority) {
        this.authority = authority;
    }

    public User(int id, String name, String email, String password, Address address, Gender gender, Telephone telephone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.address = address;
        this.telephone = telephone;
    }

    public User() {
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && gender == user.gender && Objects.equals(address, user.address) && Objects.equals(telephone, user.telephone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, gender, address, telephone);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", address=" + address +
                ", telephone=" + telephone +
                '}';
    }
}
