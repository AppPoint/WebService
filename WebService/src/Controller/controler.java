/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.DAO;
import Model.Reservation;
import Model.Restaurant;
import java.util.ArrayList;

/**
 *
 * @author patrick
 */
public class controler {
    private DAO dao;

    public controler() {
        this.dao = new DAO();
    }
    
    public ArrayList<Restaurant> listRestaurants(double latitude, double longitude){
        return this.dao.ListRestaurants(latitude, longitude);
    }
    
    public Restaurant getRestaurantReference(String reference){
        return this.dao.getRestaurantReference(reference);
    }
    
    public String createRestaurant(String name, String adress, String email, String password){
        return this.dao.createRestaurant(name, adress, email, password);
    }
    
    public Restaurant getRestaurantEmail(String email){
        return this.dao.GetRestaurantEmail(email);
    }
    
    public String updateRestaurant(int id, String description, boolean featureReservation, boolean featureMenu){
        return this.dao.updateRestaurant(id, description, featureReservation, featureMenu);
    }
    
    public String createReservation(int idRestaurant, String name, String email, String date, String time){
        return this.dao.createReservation(idRestaurant, name, email, date, time);
    }
    
    public ArrayList<Reservation> listReservations(int idRestaurant){
        return this.dao.listReservations(idRestaurant);
    }
    
    public String updateReservation(int id, int idRestaurant, String status){
        return this.dao.updateReservation(id, idRestaurant, status);
    }
    
    public String deleteRestaurant(int id){
        return this.dao.deleteRestaurant(id);
    }
    
}
