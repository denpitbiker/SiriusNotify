package ru.notificator.sirius.siriusnotificator;

public class Info {
    String token;
    String name;
    String surname;
    String mac;

    public void setToken(String token) {
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getToken() {

        return token;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMac() {
        return mac;
    }

    public Info(String token, String name, String surname, String mac) {

        this.token = token;
        this.name = name;
        this.surname = surname;
        this.mac = mac;
    }
}
