package home.controller;

import home.entity.Enfant;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class controllerEnfant implements Initializable {
    @FXML
    private TextField addNom;
    @FXML
    private TextField addPrenom;
    @FXML
    private TextField addAge;
    @FXML
    private ListView listeView;
    @FXML
    private TextField search1;
    @FXML
    private Connection conn;

    public String capitalize(String str) {
        String st = "";
        String[] a = str.split(" ");
        if(a.length>1) {
            for (int i = 0; i < a.length; i++) {
                a[i] = (a[i].toLowerCase()).replace((a[i].charAt(0)), (a[i].toUpperCase().charAt(0)));
                st += a[i] + " ";
            }
            return st.substring(0,st.length()-1);
        }
        else{
            a=str.split("");
            a[0]=a[0].toUpperCase();
            for (int i=0;i < a.length; i++){
                st+=a[i];
            }
        }

        return st;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/magasin","root","");
            Statement stm=conn.createStatement();
            ResultSet rs=stm.executeQuery("SELECT * FROM enfants");
            while(rs.next()){
                listeView.getItems().add(new Enfant(rs.getString("nom"),rs.getString("prenom"),rs.getInt("age")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addEnfant() {
        try {
            String nom = addNom.getText().toUpperCase();
            String prnom = capitalize(addPrenom.getText());
            String a = addAge.getText();
            int age = Integer.parseInt(a);

            try {
                Statement stm = conn.createStatement();
                stm.executeUpdate("insert into enfants(nom,prenom,age) values('" + nom + "','" + prnom + "','" + age + "')");
                listeView.getItems().add(new Enfant(nom, prnom, age));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(nom + " " + prnom + " a été ajouté avec succès !");
                alert.show();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur de la base de données");
                alert.setContentText(nom+" "+prnom+" existe déjà dans la base de donnée");
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
            ResultSet rs=stm.executeQuery("SELECT * FROM enfants WHERE nom LIKE '%"+element+"%' OR prenom LIKE '%"+element+"%' or age LIKE '%"+element+"%'");
            while(rs.next()) {
                listeView.getItems().add(new Enfant(rs.getString("nom"),rs.getString("prenom"),rs.getInt("age")));
            }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void supprimerEnfant() {
        int indice=listeView.getSelectionModel().getSelectedIndex();
        Enfant en=(Enfant) listeView.getSelectionModel().getSelectedItem();
        if(indice>=0) {
            try {
                Statement stm=conn.createStatement();
                stm.executeUpdate("delete from enfants where nom ='"+en.getNom()+"' and prenom = '"+en.getPrenom()+"'");
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(en.getNom()+" "+en.getPrenom()+" a été supprimé avec succès!");
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

    public TextField getNomEnfant() {
        return addNom;
    }

    public void setNomEnfant(TextField nomEnfant) {
        this.addNom = nomEnfant;
    }

    public TextField getPrenomEnfant() {
        return addPrenom;
    }

    public void setPrenomEnfant(TextField prenomEnfant) {
        this.addPrenom = prenomEnfant;
    }

    public TextField getAddAge() {
        return addAge;
    }

    public void setAddAge(TextField addAge) {
        this.addAge = addAge;
    }

    public ListView getListeView() {
        return listeView;
    }

    private Stage stge;
    private Scene scene;
    private AnchorPane root;
    @FXML
    private Button back;
    public void backHome(ActionEvent e) throws IOException {
        try {
            Stage curent = (Stage) back.getScene().getWindow();
            curent.close();
        }catch (Exception er){
            System.out.println(er.getCause());
        }
    }

    public void setListeView(ListView listeView) {
        this.listeView = listeView;
    }
}

