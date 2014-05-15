
import Model.RestaurantDAO;

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
    public static void main(String[] args) {
        RestaurantDAO dao = new RestaurantDAO(); 
        System.out.println(dao.ListRestaurants());
    }
    
}
