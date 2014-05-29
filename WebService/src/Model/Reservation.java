/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author patrick
 */
public class Reservation {
    private int id;
    private int idRestaurant;
    private String name;
    private String email;
    private String dateTime;
    private String status;

    public Reservation(int id, int idRestaurant, String name, String email, String dateTime, String status) {
        this.id = id;
        this.idRestaurant = idRestaurant;
        this.name = name;
        this.email = email;
        this.dateTime = dateTime;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(int idRestaurant) {
        this.idRestaurant = idRestaurant;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", idRestaurant=" + idRestaurant + ", name=" + name + ", email=" + email + ", dateTime=" + dateTime + ", status=" + status + '}';
    }
    
    
}
