package com.example.notesapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;

public class ShowNotes {
    public static Scene display(int user_id) throws Exception{
        Accordion accor = new Accordion();
        Button addNote = new Button("Add Note");
        VBox root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(accor,addNote);

        refreshScene(user_id, accor);

        addNote.setOnAction(e->{
            try {
                AddNote.display(user_id);
                refreshScene(user_id, accor); // refresh the scene after adding a new note
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return new Scene(root);
    }
    private static void refreshScene(int user_id, Accordion accor) throws Exception {
        DataBaseHandler dbh = new DataBaseHandler();
        ResultSet userNotes = dbh.getNotesOfUser(user_id);

        accor.getPanes().clear(); // clear existing notes from the Accordion

        while (userNotes.next()) {
            VBox note_node = new VBox();
            TextArea note_content = new TextArea(userNotes.getString(4));
            Button deleteButton = new Button("Delete");

            int note_id = userNotes.getInt(1); // get the ID of the note

            deleteButton.setOnAction(e -> {
                try {
                    dbh.deleteUniversal("note", note_id); // delete the note using a separate query
                    refreshScene(user_id, accor); // refresh the scene after deleting the note
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });

            note_content.setWrapText(true);
            note_content.setMinHeight(400);
            note_node.getChildren().addAll(note_content, deleteButton);
            TitledPane note_title = new TitledPane(userNotes.getString(3), note_node);
            accor.getPanes().add(note_title); // add new notes to the Accordion
        }

        userNotes.close(); // close the ResultSet after processing the data
    }


}
