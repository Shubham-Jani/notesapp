package com.example.notesapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class Login {
    public  static  Scene display(Stage stage, Scene scene) throws Exception{
        DataBaseHandler dbh = new DataBaseHandler();

        Label userLabel = new Label("Username");
        GridPane.setConstraints(userLabel,0,0);

        TextField username = new TextField();
        username.setPromptText("Enter Your Username");
        GridPane.setConstraints(username,1,0);
        username.setAlignment(Pos.CENTER);

        Label passowordLabel = new Label("Password");
        GridPane.setConstraints(passowordLabel,0,1);

        PasswordField password = new PasswordField();
        password.setPromptText("Enter your Password");
        password.setAlignment(Pos.CENTER);
        GridPane.setConstraints(password,1,1);


        Button submitButton = new Button("Login");
        GridPane.setConstraints(submitButton,0,2);
        submitButton.setOnAction(e -> {
                    String user = username.getText();
                    String pass = password.getText();
                    user = user.trim();
                    pass = pass.trim();
                    try{
                        if(user.isEmpty()){
                            System.out.println("Invalid Username");
                            AlertBox.display("Error","Invalid Username");
                        }else if(pass.isEmpty()){
                            System.out.println("Invalid Password");
                            AlertBox.display("Error","Invalid Password");
                        }
                        else{
                           ResultSet userSet =  dbh.authenticate(user,pass);
                           if(!userSet.isBeforeFirst()){
                               System.out.println("User not found");
                               AlertBox.display("Error","User Not Found");
                           }else{
                               if(userSet.next()){
                                   stage.setScene(ShowNotes.display(userSet.getInt(1)));
                                   stage.setMinHeight(800);
                                   stage.setMinWidth(1200);
                               }
                           }


                        }
                    }catch (Exception error){
                        System.out.println(error.getMessage());
                        AlertBox.display("Error",error.getMessage());
                    }
                }
        );

        Button registerInstead = new Button("Register Instead");
        GridPane.setConstraints(registerInstead,1,2);

        registerInstead.setOnAction(e -> {
                stage.setScene(scene);
            });


        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(10);
        layout.setHgap(10);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(userLabel,username,passowordLabel,password,submitButton,registerInstead);
        return new Scene(layout);

    }
}
