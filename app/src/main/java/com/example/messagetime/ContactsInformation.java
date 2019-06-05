package com.example.messagetime;

public class ContactsInformation {
    private String id;
    private String name;
    private String number;

    ContactsInformation(String idIn, String nameIn, String numberIn){
        id = idIn;
        name = nameIn;
        number = numberIn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
