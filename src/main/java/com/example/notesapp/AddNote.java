package com.example.notesapp;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AddNote {
    public static void display(int user_id) throws Exception{
        DataBaseHandler dbh = new DataBaseHandler();
        Stage window = new Stage();
        window.setTitle("Add Note");
        window.setMinWidth(600);
        window.setMinHeight(400);

        // note title
        Label noteTitleLabel = new Label("Title");
        GridPane.setConstraints(noteTitleLabel,0,0);
        TextField noteTitle = new TextField();
        noteTitle.setPromptText("Note Title");
        GridPane.setConstraints(noteTitle,1,0);
        // note content
        Label noteContentLabel = new Label("Title");
        GridPane.setConstraints(noteContentLabel,0,1);
        TextArea noteContent = new TextArea();
        noteContent.setPromptText("Note Title");
        GridPane.setConstraints(noteContent,1,1);

        // submit button

        Button addButton = new Button("Add");
        GridPane.setConstraints(addButton,0,2);
        addButton.setOnAction(e->{
            try {
                String title = noteTitle.getText().trim();
                String content = noteContent.getText().trim();
                if(title.isEmpty() || content.isEmpty()){
                    AlertBox.display("error","You forgot to write something");
                }
                else{
                    int id = dbh.getLastId("note") + 1;
                    dbh.addNote(id,user_id,title,content);
                    AlertBox.display("Success","Added the note");
                    window.close();
                }
            } catch (Exception ex) {
                AlertBox.display("Error",ex.getMessage());
            }
        });
        GridPane root = new GridPane();
        root.setPadding(new Insets(10,10,10,10));
        root.setVgap(10);
        root.setHgap(10);
        root.getChildren().addAll(noteTitleLabel,noteTitle,noteContentLabel,noteContent,addButton);
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();

    }
}
