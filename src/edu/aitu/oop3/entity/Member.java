package edu.aitu.oop3.entity;

public class Member{

    private int id;
    private String name;
    private String email;

    public Member(){}

    public Member(int id, String name, String email) {
        setId(id);
        setName(name);
        setEmail(email);
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
}
