
import Controller.controler;
import Model.DBConnect;
import Model.Restaurant;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.IOUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author patrick
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        Connection conn = DBConnect.getConnection();
//        try {
//            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Restaurant (PlacesID, Email, Description, Password, PlacesReference) VALUES (?, ?, ?, ?, ?);");
//            pstmt.setString(1, "be76cc351a4c7818ea8f88ee67352f0d556deeb5");
//            pstmt.setString(2, "nordestino@gmail.com");
//            pstmt.setString(3, "Teste do Nordestino Carioca");
//            pstmt.setString(4, "nordestino");
//            pstmt.setString(5, "CoQBdAAAAIDDkaAlyLAEk4TQR8yMSA_zTPRiBsN6hcgFa0qLK8WTSdavh0XQomSmcxoO9U1i_fU9viybfiR2oG_jX0WgRQfUvb0wqDaq7fchaSaZ31isMptjUg2cZCAQwXXAd01BeFWXPtDZ5iSC9S4yOPJrXW2g4TKk5jk19UOImctP_LBhEhDwO0SdBgwoECwGwyyDR6WQGhRN1SGg3tFDLJmq9o2d5GD9tmdSjg");
//            pstmt.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
          controler controler = new controler();
          System.out.println(controler.listRestaurants(-22.9531158, -43.3445191));
    }
    
}
