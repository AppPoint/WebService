/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author patrick
 */
public class RestaurantDAO {
    private Statement stmp;
    private String urlStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-22.9531158,-43.3445191&radius=500&types=restaurant&sensor=false&key=AIzaSyCJvZbxZgko6iW6AuFVUCMwqJDFBw-Nah8";

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
        ArrayList<Restaurant> listRestaurants = new ArrayList<>();
        String placesRestaurants = GetURLContent(this.urlStr);
        JSONObject jsonPlaces = new JSONObject(placesRestaurants);
        JSONArray arrayPlaces = jsonPlaces.getJSONArray("results");
        try {
            for (int i = 0; i < arrayPlaces.length(); i ++){
                JSONObject rest = (JSONObject) arrayPlaces.get(i);
                ResultSet rs = this.stmp.executeQuery("SELECT * FROM Restaurant WHERE RestaurantPlaces = '" + rest.getString("id") + "'");
                if (!rs.next()){
                    JSONObject location = rest.getJSONObject("geometry").getJSONObject("location");
                    listRestaurants.add(new Restaurant(rest.getString("id"), rest.getString("name"), rest.getString("vicinity"),
                        location.getDouble("lat"), location.getDouble("lng"), false));
                }else{
                    JSONObject location = rest.getJSONObject("geometry").getJSONObject("location");
                    listRestaurants.add(new Restaurant(rs.getInt("idRestaurant"),rs.getString("Email"), rs.getString("Password"),
                            rs.getString("Description"), rest.getString("id"), rest.getString("name"), rest.getString("vicinity"),
                        location.getDouble("lat"), location.getDouble("lng"), true));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRestaurants;
    }
    
    public Restaurant FindRestaurant (int id){
        Restaurant restaurant = null;
        try {
            ResultSet rs = this.stmp.executeQuery("SELECT * FROM Restaurant WHERE idRestaurant = " + id);
            while(rs.next()){
                restaurant = new Restaurant(rs.getInt("idRestaurant"), rs.getString("Email"), rs.getString("Password"), 
                        rs.getString("Description"), rs.getString("RestaurantPlaces"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RestaurantDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return restaurant;
    }
    
    public String GetURLContent(String urlStr){
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(
                                    new InputStreamReader(
                                        connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) 
                response.append(inputLine);

            in.close();
            return response.toString();
        } catch (MalformedURLException ex) {
            Logger.getLogger(RestaurantDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao acessar URL";
        } catch (IOException ex) {
            Logger.getLogger(RestaurantDAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao acessar URL";
        }
    }
    
    public JSONObject JSONMerge(JSONObject obj1, JSONObject obj2){
        for(String s: JSONObject.getNames(obj2)){
                obj1.put(s, obj2.get(s));
        }
        return obj1;
    }
    
    
}
