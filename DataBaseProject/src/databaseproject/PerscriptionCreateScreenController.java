/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author haxx
 */
public class PerscriptionCreateScreenController implements Initializable {


    @FXML
    private TextField QTYText;

    @FXML
    private Button CancelButton;

    @FXML
    private Button CreateButton;


    @FXML
    private ChoiceBox<String> MedicineName;
    
    @FXML 
    private ChoiceBox<Integer> CurrentPatients;

    private Connection conn = null;
    private Statement stmt = null;
    
    private static int PatientID;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
               sql = "Select MedicineName from Medicine";
               ResultSet rs =  stmt.executeQuery(sql);
               ArrayList<String> MedList = new ArrayList<>();
               while(rs.next()){
                  MedList.add(rs.getString("MedicineName"));
                  System.out.println(rs.getString("MedicineName"));
               }
               Statement stmt1 = conn.createStatement();
               String sql1;
               sql1 = "Select PatientID from Patient Order By PatientID";
               ResultSet rs1 =  stmt.executeQuery(sql1);
               ArrayList PatientList = new ArrayList();
               while(rs1.next()){
                  PatientList.add(rs1.getInt("PatientID"));
                  System.out.println(rs1.getInt("PatientID"));
               }
               CurrentPatients.setItems(FXCollections.observableArrayList(PatientList));
               
               MedicineName.setItems(FXCollections.observableArrayList(MedList));
               
               
           }catch (SQLException ex){
               
           }
       } catch (SQLException ex) {
           Logger.getLogger(DoctorScreenController.class.getName()).log(Level.SEVERE, null, ex);
       }

        // TODO
    }

    
    
    @FXML
    public void CreatePerscription(ActionEvent e) throws SQLException{
        
         String medicine = MedicineName.getValue();
         int patient = CurrentPatients.getValue();
         stmt = conn.createStatement();
         int qty;
        qty = Integer.parseInt(QTYText.getText());
         String sql1 = "Select MedicineID from Medicine WHERE MedicineName = '"+medicine+"'";
        ResultSet rs1;
        rs1 = stmt.executeQuery(sql1);
        rs1.next();
        int medicineId= rs1.getInt("MedicineID");
        Statement stmt2 = conn.createStatement();
        String sql2 = ("INSERT INTO Perscription (MedicineID,PatientID,QTY) Values("
         +"'"+medicineId+"','"+patient+"','"+qty+"')");
        stmt2.execute(sql2);
         Stage thisstage = (Stage) CancelButton.getScene().getWindow();
        // do what you have to do
         thisstage.close();
    }
    
    @FXML
    public void CloseWindow(ActionEvent e)
    {
                Stage thisstage = (Stage) CancelButton.getScene().getWindow();
        // do what you have to do
         thisstage.close();
    }
    
    
}
