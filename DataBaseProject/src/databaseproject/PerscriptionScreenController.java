/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author haxx
 */
public class PerscriptionScreenController implements Initializable {

    /**
     * Initializes the controller class.
     * 
     * 
     * 
     */
    
     @FXML
    private ChoiceBox<Integer> PatientChoice;

    @FXML
    private ChoiceBox<Integer> PerscriptionChoice;

    @FXML
    private TextField QTYText;

    @FXML
    private Button CancelButton;

    @FXML
    private Button AddNewButton;

    @FXML
    private Button DisplayButton;

    @FXML
    private TextField MedicineName;

    @FXML
    private Button ViewPerscriptionButton;
    private Connection conn = null;
    private Statement stmt = null;
    public static String Username;
    public static String Password;
    @FXML
    void AddPerscription(ActionEvent event) throws IOException {
        
        Stage st = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PerscriptionCreateScreenDocument.fxml"));
        PerscriptionCreateScreenController PerscriptionCreateScreenController;
        PerscriptionCreateScreenController = loader.<PerscriptionCreateScreenController>getController();
        Region root = (Region)loader.load();
        Scene scene = new Scene(root);
        //int patient = 3; //PatientChoice.getValue();
        //PerscriptionCreateScreenController.setPatientID(patient);
        st.setScene(scene);
        st.showAndWait();
        
        
    }

    @FXML
    void CloseWindow(ActionEvent event) {
                
        Stage thisstage = (Stage) CancelButton.getScene().getWindow();
        // do what you have to do
         thisstage.close();

    }

    @FXML
    void DisplayPerscriptions(ActionEvent event) throws SQLException {
        
               stmt = conn.createStatement();
               String sql = "Select PersciptionID from Perscription Where PatientID = "+PatientChoice.getValue();
               ResultSet rs =  stmt.executeQuery(sql);
               ArrayList MedList = new ArrayList();
               while(rs.next()){
                  MedList.add(rs.getInt("PersciptionID"));
                  System.out.println(rs.getInt("PersciptionID"));
               PerscriptionChoice.setItems(FXCollections.observableArrayList(MedList));
    

    }
    }

    @FXML
    void ViewPerscription(ActionEvent event) throws SQLException {
        
               stmt = conn.createStatement();
               String sql = "Select * from Perscription Where PersciptionID = "+PerscriptionChoice.getValue();
               ResultSet rs =  stmt.executeQuery(sql);
               rs.next();
               MedicineName.setText(rs.getString("MedicineID"));
               QTYText.setText(""+rs.getInt("QTY"));
               
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
          String JDBC_DRIVER = "com.mysql.jdbc.Driver";
         
        
       try {
           Class.forName("com.mysql.jdbc.Driver");
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(DoctorScreenController.class.getName()).log(Level.SEVERE, null, ex);
       }
       try {
           conn = DriverManager.getConnection(
                   "jdbc:mysql://localhost/hospitalDB","root","root");
           
           try{
               stmt = conn.createStatement();
               String sql;
               sql = "Select PatientID from Patient Order By PatientID";
               ResultSet rs =  stmt.executeQuery(sql);
               ArrayList PatientList = new ArrayList();
               while(rs.next()){
                  PatientList.add(rs.getInt("PatientID"));
                  System.out.println(rs.getInt("PatientID"));
               }
               PatientChoice.setItems(FXCollections.observableArrayList(PatientList));
               
               
           }catch (SQLException ex){
               
           }
       } catch (SQLException ex) {
           Logger.getLogger(DoctorScreenController.class.getName()).log(Level.SEVERE, null, ex);
       }

        // TODO
    }

}
    

