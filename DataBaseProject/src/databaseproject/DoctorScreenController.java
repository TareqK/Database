/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author haxx
 */
public class DoctorScreenController implements Initializable {
    

   
    @FXML
    private ChoiceBox<Integer> CurrentPatients;

    @FXML
    private TextField PatientNameBox;

    @FXML
    private DatePicker Admission;

    @FXML
    private DatePicker DOB;

    @FXML
    private TextField PatientSexBox;

    @FXML
    private TextField PatientRoomBox;

    @FXML
    private TextField PhoneBox;
    
    @FXML
    private Button ExitButton;



    /**
     * Initializes the controller class.
     */
    
    private Connection conn = null;
    private Statement stmt = null;
    public static String Username;
    public static String Password;
    
    public static void setUsername(String username){
        DoctorScreenController.Username=username;
    }
    
    public static void setPassword(String password){
        
        DoctorScreenController.Password= password;
    }
    
    @FXML public void DisplayData(ActionEvent e) throws SQLException{
        
       int Patient = CurrentPatients.getValue();
       String sql = "Select * from Patient Where PatientID ="+Patient;
       stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery(sql);
       rs.next();
       PatientNameBox.setText(rs.getString("Name"));
       Admission.setValue(fromDate(rs.getDate("AdmissionDate")));
       DOB.setValue(fromDate(rs.getDate("DOB")));
       PatientSexBox.setText(rs.getString("Sex"));
       PatientRoomBox.setText(rs.getString("RoomNo"));
    }
    
    
    @FXML public void CreatePatient(ActionEvent e) throws SQLException, IOException{
        Stage st = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PatientCreateScreenDocument.fxml"));
        PatientCreateScreenController PatientCreateScreenController = loader.<PatientCreateScreenController>getController();
        Region root = (Region)loader.load();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.showAndWait();
            
        
        
        stmt = conn.createStatement();
         String sql2 = "Select PatientID from Patient Order By PatientID";
         ResultSet rs =  stmt.executeQuery(sql2);
         ArrayList PatientList = new ArrayList();
         while(rs.next()){
          PatientList.add(rs.getInt("PatientID"));
          System.out.println(rs.getInt("PatientID"));
            }
        CurrentPatients.setItems(FXCollections.observableArrayList(PatientList));

      }
    
    @FXML public void EditPatient(ActionEvent e) throws SQLException{
       stmt = conn.createStatement();
       StringBuilder sql1 = new StringBuilder() ;
       sql1.append("UPDATE Patient SET Name = ");
       sql1.append("'");
       sql1.append(PatientNameBox.getText());
       sql1.append("',DOB = '");
       sql1.append(DOB.getValue());
       sql1.append("',Sex = '");
       sql1.append(PatientSexBox.getText());
       sql1.append("',RoomNo = '");
       sql1.append(PatientRoomBox.getText());
       sql1.append("',AdmissionDate = '");
       sql1.append(Admission.getValue());
       sql1.append("'WHERE PatientID = '");
       sql1.append(CurrentPatients.getValue());
       sql1.append("'");
       stmt.execute(sql1.toString());
 
       
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
               CurrentPatients.setItems(FXCollections.observableArrayList(PatientList));
               
           }catch (SQLException ex){
               
           }
       } catch (SQLException ex) {
           Logger.getLogger(DoctorScreenController.class.getName()).log(Level.SEVERE, null, ex);
       }

    }    
    
    
  public static LocalDate fromDate(Date date) {
    Instant instant = Instant.ofEpochMilli(date.getTime());
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        .toLocalDate();
  }
  
  @FXML
  public void perscription(ActionEvent e) throws IOException
  {
      Stage st = new Stage();
      FXMLLoader loader = new FXMLLoader(getClass().getResource("PerscriptionScreenDocument.fxml"));
      PerscriptionScreenController PerscriptionScreenController = loader.<PerscriptionScreenController>getController();
        
      Region root = (Region)loader.load();
      Scene scene = new Scene(root);
      st.setScene(scene);
      st.showAndWait();
  }
  
  @FXML 
  public void Exit(ActionEvent e){
         Stage thisstage = (Stage) ExitButton.getScene().getWindow();
        // do what you have to do
         thisstage.close();
         System.exit(0);

  }
  
}

    

