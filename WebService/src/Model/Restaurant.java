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
    private String name;
    private String adress;
    private Double latitude;
    private Double longitude;
    private String placesID;
    private String email;
    private String password;
    private String description;
    private String referencePlaces;
    private boolean isPoint;

    public Restaurant(String name, String adress, Double latitude, Double longitude, String placesID, String referencePlaces, boolean isPoint) {
        this.name = name;
        this.adress = adress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placesID = placesID;
        this.referencePlaces = referencePlaces;
        this.isPoint = isPoint;
    }

    public Restaurant(int id, String name, String adress, Double latitude, Double longitude, String placesID, String email, String password, String description, String referencePlaces, boolean isPoint) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.placesID = placesID;
        this.email = email;
        this.password = password;
        this.description = description;
        this.referencePlaces = referencePlaces;
        this.isPoint = isPoint;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPlacesID() {
        return placesID;
    }

    public void setPlacesID(String placesID) {
        this.placesID = placesID;
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

    public String getReferencePlaces() {
        return referencePlaces;
    }

    public void setReferencePlaces(String referencePlaces) {
        this.referencePlaces = referencePlaces;
    }

    public boolean isIsPoint() {
        return isPoint;
    }

    public void setIsPoint(boolean isPoint) {
        this.isPoint = isPoint;
    }

    @Override
    public String toString() {
        return "Restaurant{" + "id=" + id + ", name=" + name + ", adress=" + adress + ", latitude=" + latitude + ", longitude=" + longitude + ", placesID=" + placesID + ", email=" + email + ", password=" + password + ", description=" + description + ", referencePlaces=" + referencePlaces + ", isPoint=" + isPoint + '}';
    }
    
}
