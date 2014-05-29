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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
                            "", "", "", rest.getString("reference"), false, false, false));
                }else{
                    JSONObject location = rest.getJSONObject("geometry").getJSONObject("location");
                    int id = rs.getInt("idRestaurant");
                    listRestaurants.add(new Restaurant(rs.getInt("idRestaurant"), rest.getString("name"), rest.getString("vicinity"),
                            location.getDouble("lat"), location.getDouble("lng"), rest.getString("id"),
                            rs.getString("Email"), rs.getString("Password"), rs.getString("Description"), 
                            rs.getString("PlacesReference"), true, hasFeatureReservation(id), hasFeatureMenu(id)));
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
        JSONObject location = rest.getJSONObject("geometry").getJSONObject("location");
        try {
            PreparedStatement stmp = this.conn.prepareStatement("SELECT * FROM Restaurant WHERE PlacesReference = ?");
            stmp.setString(1, reference);
            ResultSet rs = stmp.executeQuery();
            if(!rs.next()){
                restaurant = new Restaurant(0, rest.getString("name"), rest.getString("vicinity"),
                            location.getDouble("lat"), location.getDouble("lng"), rest.getString("id"),
                            "", "", "", rest.getString("reference"), false, false, false);
            } else{
                int id  = rs.getInt("idRestaurant");
                restaurant = new Restaurant(rs.getInt("idRestaurant"), rest.getString("name"), rest.getString("vicinity"),
                            location.getDouble("lat"), location.getDouble("lng"), rest.getString("id"),
                            rs.getString("Email"), rs.getString("Password"), rs.getString("Description"), 
                            rs.getString("PlacesReference"), true, hasFeatureReservation(id), hasFeatureMenu(id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return restaurant;
    }
    
    public String createRestaurant(String name, String adress, String email, String password){
        Restaurant restaurant = null;
        String urlStr = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + name.replace(" ", "+") + "&sensor=true&key=AIzaSyCJvZbxZgko6iW6AuFVUCMwqJDFBw-Nah8";
        String placesRestaurants = GetURLContent(urlStr);
        JSONObject jsonPlaces = new JSONObject(placesRestaurants);
        JSONArray arrayPlaces = jsonPlaces.getJSONArray("results");
        for (int i = 0; i < arrayPlaces.length(); i++){
            JSONObject rest = (JSONObject) arrayPlaces.get(i);
            if (rest.getString("formatted_address").toLowerCase().contains(adress.toLowerCase())){
                try {
                    PreparedStatement pstmt = this.conn.prepareStatement("INSERT INTO Restaurant (PlacesID, Email, Description, Password, PlacesReference) VALUES (?, ?, ?, ?, ?);");
                    pstmt.setString(1, rest.getString("id"));
                    pstmt.setString(2, email);
                    pstmt.setString(3, "");
                    pstmt.setString(4, password);
                    pstmt.setString(5, rest.getString("reference"));
                    pstmt.executeUpdate();
                    return "Restaurante encontrado com sucesso";
                } catch (SQLException ex) {
                    Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "Restaurante não encontrado";
        
    }
    
    public Restaurant GetRestaurantEmail(String email){
        Restaurant restaurant = null;
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Restaurant WHERE email = ?;");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String urlStr = "https://maps.googleapis.com/maps/api/place/details/json?reference=" + rs.getString("PlacesReference") + "&sensor=false&key=AIzaSyCJvZbxZgko6iW6AuFVUCMwqJDFBw-Nah8";
                String placesRestaurants = GetURLContent(urlStr);
                JSONObject rest = new JSONObject(placesRestaurants).getJSONObject("result");
                JSONObject location = rest.getJSONObject("geometry").getJSONObject("location");
                int id = rs.getInt("idRestaurant");
                restaurant = new Restaurant(rs.getInt("idRestaurant"), rest.getString("name"), rest.getString("vicinity"),
                            location.getDouble("lat"), location.getDouble("lng"), rest.getString("id"),
                            rs.getString("Email"), rs.getString("Password"), rs.getString("Description"), 
                            rs.getString("PlacesReference"), true, hasFeatureReservation(id), hasFeatureMenu(id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return restaurant;
    }
    
    public String updateRestaurant(int id, String description, boolean featureReservation, boolean featureMenu){
        String result = null;
        try {
            PreparedStatement stmt = this.conn.prepareStatement("UPDATE Restaurant SET Description=? WHERE idRestaurant = ?;");
            stmt.setString(1, description);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            if (featureReservation){
                stmt = this.conn.prepareStatement("INSERT IGNORE INTO Restaurant_Features (idRestaurant, idSystemFeatures) VALUES (?,1);");
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } else{
                stmt = this.conn.prepareStatement("DELETE FROM Restaurant_Features WHERE idRestaurant = ? AND idSystemFeatures = 1;");
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            if (featureMenu){
                stmt = this.conn.prepareStatement("INSERT IGNORE INTO Restaurant_Features (idRestaurant, idSystemFeatures) VALUES (?,2);");
                stmt.setInt(1, id);
                stmt.executeUpdate();
            } else{
                stmt = this.conn.prepareStatement("DELETE FROM Restaurant_Features WHERE idRestaurant = ? AND idSystemFeatures = 2;");
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }
            result = "Restaurant atualizado com sucesso";
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean hasFeatureReservation(int id){
        boolean result = false;
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Restaurant_Features WHERE idRestaurant = ? AND idSystemFeatures = 1;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean hasFeatureMenu(int id){
        boolean result = false;
        try {
            PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Restaurant_Features WHERE idRestaurant = ? AND idSystemFeatures = 2;");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public String createReservation(int idRestaurant, String name, String email, String date, String time){
        String result = "Não foi possivel criar a reserva";
        String dateTime = date + "-" + time;
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        Date dateObj = null;
        try {
            dateObj = formatDateTime.parse(dateTime);
            PreparedStatement stmt = this.conn.prepareStatement("INSERT INTO Reservation(idRestaurant,Name,DateTime,Email,StatusFK)VALUES(?,?,?,?,1);");
            stmt.setInt(1, idRestaurant);
            stmt.setString(2, name);
            stmt.setTimestamp(3, new Timestamp(dateObj.getTime()));
            stmt.setString(4, email);
            stmt.executeUpdate();
            result = "Reserva criada com sucesso";
        } catch (ParseException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public ArrayList<Reservation> listReservations (int idRestaurant){
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
        PreparedStatement stmt;
        SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        try {
            stmt = this.conn.prepareStatement("SELECT * FROM Reservation, ReservationStatus WHERE idRestaurant = ? and StatusFK = idReservationStatus;");
            stmt.setInt(1, idRestaurant);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String date =  formatDateTime.format(rs.getTimestamp("dateTime"));
                reservationList.add(new Reservation(rs.getInt("idReservas"), rs.getInt("idRestaurant"), 
                        rs.getString("name"), rs.getString("email"), date, rs.getString("Status")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (reservationList.isEmpty()){
            return null;
        }
        return reservationList;
    }
    
    public String updateReservation(int id, int idRestaurant, String status){
        String result = "Não foi possível atualizar a reserva";
        try {
            PreparedStatement stmt = this.conn.prepareStatement("UPDATE Reservation SET StatusFK = (SELECT idReservationStatus FROM ReservationStatus WHERE Status = ?) WHERE idReservas = ? AND idRestaurant = ?;");
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.setInt(3, idRestaurant);
            stmt.executeUpdate();
            sendEmail(id, status);
            result = "Reserva atualizada com sucesso";
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
        
    }
    
    public String deleteRestaurant(int id){
        String result = "Não foi possível deletar o restaurante";
        try {
            PreparedStatement stmt = this.conn.prepareStatement("delete from Reservation where idRestaurant = ?;");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt = this.conn.prepareStatement("delete from Restaurant_Features where idRestaurant = ?;");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt = this.conn.prepareStatement("delete from Restaurant where idRestaurant = ?;");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            result =     "Restaurante deletado com sucesso";
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    public void sendEmail(int id, String status){
            SimpleDateFormat formatDateTime = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
            Properties props = new Properties();
            /** Parâmetros de conexão com servidor Gmail */
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                             protected PasswordAuthentication getPasswordAuthentication() 
                             {
                                   return new PasswordAuthentication("seuemail@gmail.com", "suasenha");
                             }
                        });

            /** Ativa Debug para sessão */

            try {
                  PreparedStatement stmt = this.conn.prepareStatement("SELECT * FROM Reservation re WHERE idReservas = ?");
                  stmt.setInt(1, id);
                  ResultSet rs = stmt.executeQuery();
                  rs.next();
                  String date = formatDateTime.format(rs.getTimestamp("dateTime")).split("-")[0];
                  String time = formatDateTime.format(rs.getTimestamp("dateTime")).split("-")[1];
                  Message message = new MimeMessage(session);
                  message.setFrom(new InternetAddress("pck1993@gmail.com")); //Remetente

                  Address[] toUser = InternetAddress //Destinatário(s)
                             .parse(rs.getString("email"));  

                  message.setRecipients(Message.RecipientType.TO, toUser);
                  message.setSubject("Status Reserva");//Assunto
                  message.setText("Email de alteração do status da resreva no dia " + date + " às " + time + " horas para " + status);
                  /**Método para enviar a mensagem criada*/
                  Transport.send(message);


             } catch (MessagingException e) {
                  throw new RuntimeException(e);
            } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
}
