/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import Model.DAO;
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
    
}
