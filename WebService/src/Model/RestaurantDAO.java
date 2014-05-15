/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patrick
 */
public class RestaurantDAO {
    private Statement stmp;

    public RestaurantDAO() {
        this.stmp = DBConnect.getStatement();
    }
    
    public String CreateRestaurant (String email, String password, String description, String placesId){
        String result;
        try {
            if(!(this.stmp.execute("INSERT INTO Restaurant(Email, RestaurantPlaces, Password, Description)"
                    + " VALUES ('" + email + "','" + placesId + "','" + password + "','" + description + "')"))){
                result = "Restaurante Criado com sucesso";
            }else{
                result = "Não foi possíve criar o restaurante";
            }
        } catch (SQLException ex) {
            result = "Erro com o MySQL";
            Logger.getLogger(RestaurantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ArrayList<Restaurant> ListRestaurants (){
        ArrayList<Restaurant> listRestaurants = new ArrayList<Restaurant>();
        try {
            ResultSet rs = this.stmp.executeQuery("SELECT * FROM Restaurant;");
            while(rs.next()){
                listRestaurants.add(new Restaurant(rs.getInt("idRestaurant"), rs.getString("Email"), rs.getString("Password"), 
                        rs.getString("Description"), rs.getString("RestaurantPlaces")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRestaurants;
    }
}
