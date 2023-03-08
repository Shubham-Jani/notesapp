package com.example.notesapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import  javafx.scene.control.Button;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        // user creation will be the first scene
        DataBaseHandler dbh = new DataBaseHandler();
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        Label userLabel = new Label("Username");
        GridPane.setConstraints(userLabel,0,0);

        TextField  username = new TextField();
        username.setPromptText("Enter Your Username");
        GridPane.setConstraints(username,1,0);
        username.setAlignment(Pos.CENTER);

        Label passowordLabel = new Label("Password");
        GridPane.setConstraints(passowordLabel,0,1);

        PasswordField password = new PasswordField();
        password.setPromptText("Enter your Password");
        password.setAlignment(Pos.CENTER);
        GridPane.setConstraints(password,1,1);


        Button submitButton = new Button("Create");
        GridPane.setConstraints(submitButton,0,2);

        Button loginInsteaedButton = new Button("Login Instead");
        GridPane.setConstraints(loginInsteaedButton,1,2);


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
                            int id = dbh.getLastId("user")+1;
                            dbh.addUser(id,user,pass);
                           System.out.println("User Created");
                           AlertBox.display("Success","User Created");
                       }
                   }catch (Exception error){
                       System.out.println(error.getMessage());
                       AlertBox.display("Error",error.getMessage());
                   }
                }
        );

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(10);
        layout.setHgap(10);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(userLabel,username,passowordLabel,password,submitButton,loginInsteaedButton);
        Scene userCreationScene = new Scene(layout, 320, 240);
        stage.setTitle("Notes App");
        stage.setScene(userCreationScene);

        loginInsteaedButton.setOnAction(e -> {
            try {
                stage.setScene(Login.display(stage, userCreationScene));
                stage.setTitle("Login");
                stage.setMinWidth(600);
                stage.setMinHeight(400);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        stage.show();
    }

    public static void main(String[] args)  throws Exception{

        launch();
    }
}