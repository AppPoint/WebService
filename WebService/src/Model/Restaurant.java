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
    private String name;
    private String adress;
    private double latitude;
    private double longitude;
    private double rating;
    private boolean isPoint;

    public Restaurant(int id, String email, String password, String description, String idPlacesAPI, String name, String adress, double latitude, double longitude, boolean isPoint) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.description = description;
        this.idPlacesAPI = idPlacesAPI;
        this.name = name;
        this.adress = adress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isPoint = isPoint;
    }

    public Restaurant(int id, String email, String password, String description, String idPlacesAPI) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.description = description;
        this.idPlacesAPI = idPlacesAPI;
    }

    public Restaurant(String idPlacesAPI, String name, String adress, double latitude, double longitude, boolean isPoint) {
        this.idPlacesAPI = idPlacesAPI;
        this.name = name;
        this.adress = adress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isPoint = isPoint;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public boolean isIsPoint() {
        return isPoint;
    }

    public void setIsPoint(boolean isPoint) {
        this.isPoint = isPoint;
    }

    @Override
    public String toString() {
        return "Restaurant{" + "id=" + id + ", email=" + email + ", password=" + password + ", description=" + description + ", idPlacesAPI=" + idPlacesAPI + ", name=" + name + ", adress=" + adress + ", latitude=" + latitude + ", longitude=" + longitude + ", rating=" + rating + ", isPoint=" + isPoint + '}';
    }
    
    
    
}
