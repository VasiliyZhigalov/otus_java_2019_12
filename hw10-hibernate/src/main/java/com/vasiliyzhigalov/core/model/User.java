package com.vasiliyzhigalov.core.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private AddressDataSet address;

    @ElementCollection
    @CollectionTable(name = "phones")
    private Set<PhoneDataSet> phones;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public User(long id, AddressDataSet address, Set<PhoneDataSet> phones, String name, int age) {
        this.id = id;
        this.address = address;
        this.phones = phones;
        this.name = name;
        this.age = age;
    }

    public User(AddressDataSet address, Set<PhoneDataSet> phones, String name, int age) {

        this.address = address;
        this.phones = phones;
        this.name = name;
        this.age = age;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public Set<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(Set<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", address=" + address +
                ", phones=" + phones +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                age == user.age &&
                Objects.equals(address, user.address) &&
                Objects.equals(phones, user.phones) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, phones, name, age);
    }
}
