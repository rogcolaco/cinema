package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.SwitcherDisplay;

import java.io.IOException;

public class ManageTheater {
    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private Button btnUpdateTheater;
    @FXML private Button btnConfirmTheater;
    @FXML private Label lbTheaterFieldTitle;
    @FXML private Button btnRemoveTheater;
    @FXML private TextField txtTheaterName;
    @FXML private TextField txtQtdSeats;

    /*Verificar Classes*/
    @FXML private TableView<String> tableTheater;
    @FXML private TableColumn<String, String> cTheaterName;
    @FXML private TableColumn<String,String> cQtdSeats;

    public void newSale(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/NewSale.fxml",stage,"Cistemax - Gerenciar Vendas", btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngMovie(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManageMovie.fxml", stage, "Cistemax - Gerenciar Filmes",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngTheater(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManageTheater.fxml",stage,"Cistemax - Gerenciar Salas",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngGenre(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManageGenre.fxml",stage,"Cistemax - Gerenciar Gêneros",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngPrice(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManagePrice.fxml",stage,"Cistemax - Gerenciar Preço",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngMovieSession(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/ManageMovieSession.fxml",stage,"Cistemax - Gerenciar Sessões",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngCancelSale(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/CancelSale.fxml",stage,"Cistemax - Cancelar Venda ",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void mngReport(ActionEvent actionEvent) {
        Stage stage = (Stage)btnSale.getScene().getWindow();
        SwitcherDisplay display = new SwitcherDisplay();
        display.show("/view/Report.fxml",stage,"Cistemax - Emitir Relatório",btnSale.getScene().getWidth(), btnSale.getScene().getHeight());
    }

    public void CancelOp(ActionEvent actionEvent) {
        this.newSale(actionEvent);
    }

    public void updateTheater(ActionEvent actionEvent) {
        lbTheaterFieldTitle.setText("Alterar Sala");
        btnConfirmTheater.setText("Confirma Alteração");
    }
}