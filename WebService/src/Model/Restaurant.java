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
public class Restaurant {
    private int id;
    private String email;
    private String password;
    private String description;
    private String idPlacesAPI;

    public Restaurant(int id, String email, String password, String description, String idPlacesAPI) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.description = description;
        this.idPlacesAPI = idPlacesAPI;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdPlacesAPI() {
        return idPlacesAPI;
    }

    public void setIdPlacesAPI(String idPlacesAPI) {
        this.idPlacesAPI = idPlacesAPI;
    }

    @Override
    public String toString() {
        return "Restaurant{" + "id=" + id + ", email=" + email + ", password=" + password + ", description=" + description + ", idPlacesAPI=" + idPlacesAPI + '}';
    }
    
}
