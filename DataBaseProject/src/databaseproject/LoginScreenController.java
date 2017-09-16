/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseproject;

import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 *
 * @author haxx
 */
public class LoginScreenController implements Initializable {
    
    @FXML
    private Label ErrorLabel;
    @FXML
    private ChoiceBox Accountbox;
    @FXML
    private TextField UsernameText;
    @FXML
    private PasswordField PasswordText;
    @FXML
    private Button CancelButton;
    @FXML
    private Button LoginButton;
    
    
    
    @FXML
    private void onLoginButtonPressed(ActionEvent event) throws SQLException, ClassNotFoundException, IOException
    {
         Connection conn = null;
         String Username;
         String Password;
         Statement stmt = null;
         String JDBC_DRIVER = "com.mysql.jdbc.Driver";
         
        
        Class.forName("com.mysql.jdbc.Driver");
        try {
        Username = UsernameText.getText();
        Password = PasswordText.getText();
        conn = DriverManager.getConnection(
        "jdbc:mysql://localhost/hospitalDB","root","root");
        
        Stage st = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoctorScreenDocument.fxml"));
        DoctorScreenController DoctorScreenController = loader.<DoctorScreenController>getController();
        DoctorScreenController.setUsername(Username);
        DoctorScreenController.setPassword(Password);
        Region root = (Region)loader.load();
        Stage thisstage = (Stage) CancelButton.getScene().getWindow();
        // do what you have to do
         thisstage.close();
        Scene scene = new Scene(root);
        st.setScene(scene);
        st.show();

        }catch (SQLException e)
        
        {
        
        ErrorLabel.setText(e.getLocalizedMessage());
        UsernameText.setText("");
        PasswordText.setText("");
        
        }
        
        finally{
    //It's important to close the connection when you are done with it
    try { conn.close(); } catch (SQLException e) { /* Propagate the original exception
instead of this one that you want just logged */
        }
    }
        
        
        
    }
    
    @FXML 
    private void onCancelButtonClick(ActionEvent e){
        
        System.exit(0);
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    
    
}
