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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author patrick
 */
public class DAO {
    private Connection conn;

    public DAO() {
        this.conn = DBConnect.getConnection();
    }
    
    public ArrayList<Restaurant> ListRestaurants (double latitude, double longitude){
        String urlStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + latitude + "," + longitude + "&radius=500&types=restaurant&sensor=false&key=AIzaSyCJvZbxZgko6iW6AuFVUCMwqJDFBw-Nah8";
        ArrayList<Restaurant> listRestaurants = new ArrayList<>();
        String placesRestaurants = GetURLContent(urlStr);
        JSONObject jsonPlaces = new JSONObject(placesRestaurants);
        JSONArray arrayPlaces = jsonPlaces.getJSONArray("results");
        try {
            for (int i = 0; i < arrayPlaces.length(); i ++){
                JSONObject rest = (JSONObject) arrayPlaces.get(i);
                PreparedStatement stmp = this.conn.prepareStatement("SELECT * FROM Restaurant WHERE PlacesID = ?");
                stmp.setString(1, rest.getString("id"));
                ResultSet rs = stmp.executeQuery();
                if (!rs.next()){
                    JSONObject location = rest.getJSONObject("geometry").getJSONObject("location");
                    listRestaurants.add(new Restaurant(0, rest.getString("name"), rest.getString("vicinity"),
                            location.getDouble("lat"), location.getDouble("lng"), rest.getString("id"),
                            "", "", "", rest.getString("reference"), false));
                }else{
                    JSONObject location = rest.getJSONObject("geometry").getJSONObject("location");
                    listRestaurants.add(new Restaurant(rs.getInt("idRestaurant"), rest.getString("name"), rest.getString("vicinity"),
                            location.getDouble("lat"), location.getDouble("lng"), rest.getString("id"),
                            rs.getString("Email"), rs.getString("Password"), rs.getString("Description"), 
                            rs.getString("PlacesReference"), true));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listRestaurants;
    }
    
    public Restaurant getRestaurantReference(String reference){
        Restaurant restaurant = null;
        String urlStr = "https://maps.googleapis.com/maps/api/place/details/json?reference=" + reference + "&sensor=false&key=AIzaSyCJvZbxZgko6iW6AuFVUCMwqJDFBw-Nah8";
        String placesRestaurants = GetURLContent(urlStr);
        JSONObject rest = new JSONObject(placesRestaurants).getJSONObject("result");
        System.out.println(rest);
        JSONObject location = rest.getJSONObject("geometry").getJSONObject("location");
        try {
            PreparedStatement stmp = this.conn.prepareStatement("SELECT * FROM Restaurant WHERE PlacesReference = ?");
            stmp.setString(1, reference);
            ResultSet rs = stmp.executeQuery();
            if(!rs.next()){
                restaurant = new Restaurant(rs.getInt("idRestaurant"), rest.getString("name"), rest.getString("vicinity"),
                            location.getDouble("lat"), location.getDouble("lng"), rest.getString("id"),
                            "", "", "", rest.getString("PlacesReference"), false);
            } else{
                restaurant = new Restaurant(rs.getInt("idRestaurant"), rest.getString("name"), rest.getString("vicinity"),
                            location.getDouble("lat"), location.getDouble("lng"), rest.getString("id"),
                            rs.getString("Email"), rs.getString("Password"), rs.getString("Description"), 
                            rs.getString("PlacesReference"), true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao acessar URL";
        } catch (IOException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
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
