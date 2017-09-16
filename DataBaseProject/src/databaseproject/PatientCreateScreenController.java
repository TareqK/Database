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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PatientCreateScreenController implements Initializable  {

    @FXML
    private TextField PatientNameBox;

    @FXML
    private TextField PatientSexBox;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField PhoneBox;

    @FXML
    private DatePicker DOB;

    @FXML
    private DatePicker Admission;

    @FXML
    private ChoiceBox<Integer> RoomBox;
    
    private Connection conn = null;
    private Statement stmt = null;
    public static String Username;
    public static String Password;

    @FXML
    void CloseWindow(ActionEvent event) {
        
        Stage thisstage = (Stage) CancelButton.getScene().getWindow();
        // do what you have to do
         thisstage.close();

    }

    @FXML
    void CreatePatient(ActionEvent event) throws SQLException {
         stmt = conn.createStatement();
       StringBuilder sql1 = new StringBuilder() ;
       sql1.append("INSERT INTO Patient (Name,DOB,Sex,RoomNo,AdmissionDate) Values(");
       sql1.append("'");
       sql1.append(PatientNameBox.getText());
       sql1.append("','");
       sql1.append(DOB.getValue());
       sql1.append("','");
       sql1.append(PatientSexBox.getText());
       sql1.append("','");
       sql1.append(RoomBox.getValue());
       sql1.append("','");
       sql1.append(Admission.getValue());
       sql1.append("')");
       stmt.execute(sql1.toString());
       
       Stage thisstage = (Stage) CancelButton.getScene().getWindow();
        // do what you have to do
       thisstage.close();

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
               sql = "Select RoomNo from Room";
               ResultSet rs =  stmt.executeQuery(sql);
               ArrayList RoomList = new ArrayList();
               while(rs.next()){
                  RoomList.add(rs.getInt("RoomNo"));
                  System.out.println(rs.getInt("RoomNo"));
               }
               RoomBox.setItems(FXCollections.observableArrayList(RoomList));
               
           }catch (SQLException ex){
               
           }
       } catch (SQLException ex) {
           Logger.getLogger(DoctorScreenController.class.getName()).log(Level.SEVERE, null, ex);
       }

    }    

    void setPassword(String Pswd) {
        PatientCreateScreenController.Password = Pswd;
                
    }

    void setUsername(String Usr) {
        PatientCreateScreenController.Username= Usr;
    }
    

}
