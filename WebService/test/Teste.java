
import Model.RestaurantDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
        String urlStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-22.9531158,-43.3445191&radius=500&types=restaurant&sensor=false&key=AIzaSyCJvZbxZgko6iW6AuFVUCMwqJDFBw-Nah8";
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

            JSONObject json = new JSONObject(response.toString());
            JSONArray array = json.getJSONArray("results");
            JSONObject obj = new JSONObject().put("chave", "valor");
            JSONObject obj2 = (JSONObject) array.get(1);
            for(String s: JSONObject.getNames(obj2)){
                obj.put(s, obj2.get(s));
            }
            System.out.println(obj);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
}
