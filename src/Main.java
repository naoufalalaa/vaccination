import home.entity.Enfant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magasin","root","");
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery("SELECT * FROM enfants");
            while(rs.next()){
                System.out.println(rs.getString("nom")+" , "+rs.getString("prenom")+" , "+rs.getInt("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
