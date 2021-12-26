package home.controller;

import home.entity.Vaccin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class controllerHome implements Initializable {
    private Connection conn;
    @FXML
    private ListView listView;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magasin","root","");
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery("SELECT * FROM vaccin");
            while(rs.next()){
                listView.getItems().add(new Vaccin(rs.getString("antigene"),rs.getInt("age"),rs.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Stage stge;
    private Scene scene;
    private AnchorPane root;

    public void switchAddPersonne(ActionEvent e) throws IOException{
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/addPerson.fxml"));
            //stge = (Stage)((Node)e.getSource()).getScene().getWindow();
            stge = new Stage();
            //scene = new Scene(root);
            stge.setScene(new Scene(root));
            stge.setResizable(false);
            stge.setTitle("Vaccination - Ajout de Personne");
            stge.show();
        }catch (Exception er){
            System.out.println(er.getCause());
        }
    }
    public void switchAddVaccin(ActionEvent e) throws IOException{
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/addVaccin.fxml"));
            //stge = (Stage)((Node)e.getSource()).getScene().getWindow();
            stge = new Stage();
            //scene = new Scene(root);
            stge.setScene(new Scene(root));
            stge.setResizable(false);
            stge.setTitle("Vaccination - Ajout de vaccin");
            stge.show();
        }catch (Exception er){
            System.out.println(er.getCause());
        }
    }

}
