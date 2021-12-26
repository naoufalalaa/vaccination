package home.controller;

import home.entity.Enfant;
import home.entity.Vaccin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class controllerVaccin implements Initializable {

    @FXML
    private TextField addAntigene;
    @FXML
    private TextField addDescription;
    @FXML
    private TextField addAge;
    @FXML
    private ListView listeView;
    @FXML
    private TextField search1;
    @FXML
    private Connection conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magasin","root","");
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery("SELECT * FROM vaccin");
            while(rs.next()){
                listeView.getItems().add(new Vaccin(rs.getString("antigene"),rs.getInt("age"),rs.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addVaccin() {
        try {
            String nom = addAntigene.getText();
            String description = addDescription.getText().toUpperCase();
            String a = addAge.getText();
            int age = Integer.parseInt(a);

            try {
                Statement stm = conn.createStatement();
                stm.executeUpdate("insert into vaccin(antigene,age,unity,description) values('" + nom + "','" + age + "','months','" + description + "')");
                listeView.getItems().add(new Vaccin(nom, age, description));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(nom + " pour les " + a + " mois a été ajouté avec succès !");
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur de la base de données");
                alert.setContentText(nom+" pour les "+age+" mois existe déjà dans la base de données");
                alert.show();
            }
        }catch(Exception e){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Informations invalides!");
            alert.setContentText("Champs vides!");
            alert.show();
        }
    }



    public void search(){
        String element = search1.getText();
        listeView.getItems().clear();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs=stm.executeQuery("SELECT * FROM vaccin WHERE antigene LIKE '%"+element+"%' OR age LIKE '%"+element+"%' or description LIKE '%"+element+"%'");
            while(rs.next()) {
                listeView.getItems().add(new Vaccin(rs.getString("antigene"),rs.getInt("age"),rs.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerVaccin() {
        int indice=listeView.getSelectionModel().getSelectedIndex();
        Vaccin en=(Vaccin) listeView.getSelectionModel().getSelectedItem();
        if(indice>=0) {
            try {
                Statement stm=conn.createStatement();
                stm.executeUpdate("delete from vaccin where antigene ='"+en.getAntigene()+"' and age = '"+en.getAge()+"'");
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(en.getAntigene()+" pour les "+en.getAge()+" mois a été supprimé avec succès!");
                alert.show();
            } catch (Exception e) {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
            listeView.getItems().remove(indice);
        }else{
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un élément!");
            alert.show();
        }

    }



    private Stage stge;
    private Scene scene;
    private AnchorPane root;

    public void backHome(ActionEvent e) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/homevue.fxml"));
            stge = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stge.setScene(scene);
            stge.setTitle("Vaccination");
            stge.show();
        }catch (Exception er){
            System.out.println(er.getCause());
        }
    }
}
