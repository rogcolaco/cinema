package controller;

import dao.GenreDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Genre;
import util.SwitcherDisplay;

import java.io.IOException;
import java.sql.SQLException;

public class ManageGenre extends MenuPrincipal{

    @FXML private Button btnUpdateGenre;
    @FXML private Button btnConfirmGenre;
    //@FXML private Button btnCancelOp;
    @FXML private Label lbGenreFieldTitle;
    @FXML private Button btnRemoveGenre;
    @FXML private TextField txtGenreName;

    /*Confirmar Classes*/
    @FXML private TableView<Genre> tableGenre;
    @FXML private TableColumn<Genre, String> cGenre;

    @FXML public void initialize(){ fill(); }

    public void fill(){
        GenreDAO dao = new GenreDAO();
        cGenre.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableGenre.setItems(dao.readAll());
    }

    public void updateGenre(ActionEvent actionEvent) {
        if (tableGenre.getSelectionModel().getSelectedItem() != null){
            lbGenreFieldTitle.setText("Alterar Gênero");
            btnConfirmGenre.setText("Alterar");

            Genre genre = tableGenre.getSelectionModel().getSelectedItem();
            txtGenreName.setText((genre.getName()));
        }
    }

    public void removeGenre(ActionEvent actionEvent) throws SQLException {
        GenreDAO dao = new GenreDAO();
        if (tableGenre.getSelectionModel().getSelectedItem() != null) {
            dao.delete(tableGenre.getSelectionModel().getSelectedItem());
            tableGenre.setItems(dao.readAll());
        } else { return;}
    }

    public void addGenre(ActionEvent actionEvent) throws SQLException {
        Genre genre = new Genre();
        GenreDAO dao = new GenreDAO();
        genre.setName(txtGenreName.getText());

        if (genre.getName().length() > 0) {
            /*Caso o botão de confirmação seja utilizado para alterar um gênero*/
            if (btnConfirmGenre.getText().equals("Alterar")) {
                genre.setId(tableGenre.getSelectionModel().getSelectedItem().getId());
                dao.update(genre);
                tableGenre.setItems(dao.readAll());
                lbGenreFieldTitle.setText("Cadastrar Novo Gênero");
                btnConfirmGenre.setText("Confirmar");

                /*Caso o botão de confirmação seja utilizado para salvar um novo gênero*/
            } else {
                int max = dao.MaxId();
                genre.setId(max);
                dao.save(genre);
                tableGenre.setItems(dao.readAll());
            }
            txtGenreName.clear();
        } else {
            MsgErro msg = new MsgErro();
            msg.show();
        }
    }
}
