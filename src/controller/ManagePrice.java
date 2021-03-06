package controller;

import dao.TicketDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Movie;
import model.Ticket;
import util.Regex;
import util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;

import static util.Utils.mostrarAlerta;

public class ManagePrice extends MenuPrincipal {

    Regex regex = new Regex();
    @FXML
    private Button btnConfirmPrice;
    @FXML
    private Button btnUpdatePrice;
    //@FXML private Button btnCancelOp;
    @FXML
    private Label lbPriceFieldTitle;
    @FXML
    private Button btnRemoveSession;
    @FXML
    private TextField txtSessionType;
    @FXML
    private TextField txtSessionPrice;
    @FXML
    private TableView<Ticket> tableSession;
    @FXML
    private TableColumn<Ticket, String> cSessionType;
    @FXML
    private TableColumn<Ticket, Double> cSessionPrice;

    @FXML
    public void initialize() {
        fill();
    }

    public void fill() {
        TicketDAO dao = new TicketDAO();
        cSessionType.setCellValueFactory(new PropertyValueFactory<>("type"));
        cSessionPrice.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableSession.setItems(dao.readAll());
        cSessionPrice.setCellFactory(col -> new TableCell<Ticket, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    setText(String.format("%.2f", item));
                }
            }
        });
    }


    public void updatePrice(ActionEvent actionEvent) {
        lbPriceFieldTitle.setText("Alterar Preço da Sessão");
        btnConfirmPrice.setText("Alterar");

        Ticket ticket = tableSession.getSelectionModel().getSelectedItem();
        txtSessionType.setText(ticket.getType());
        txtSessionPrice.setText(String.valueOf(ticket.getValue()));
    }

    public void confirm(ActionEvent actionEvent) throws SQLException {
        Ticket ticket = new Ticket();
        TicketDAO dao = new TicketDAO();
        ticket.setType(txtSessionType.getText());

        if (fillError(ticket)) {
            ticket.setValue(Double.parseDouble(txtSessionPrice.getText().replace(",", ".")));

            /*Caso o botão de confirmação seja utilizado para alterar um ticket*/
            if (btnConfirmPrice.getText().equals("Alterar")) {
                ticket.setId(tableSession.getSelectionModel().getSelectedItem().getId());
                dao.update(ticket);
                tableSession.setItems(dao.readAll());
                lbPriceFieldTitle.setText("Cadastrar Novo Tipo de Sessão");
                btnConfirmPrice.setText("Confirmar");

                /*Caso o botão de confirmação seja utilizado para salvar um ticket novo*/
            } else {
                int max = dao.maxId();
                ticket.setId(max);
                dao.save(ticket);
                tableSession.setItems(dao.readAll());
            }
            txtSessionType.clear();
            txtSessionPrice.clear();
        }
    }

    public void remove(ActionEvent actionEvent) throws SQLException {
        TicketDAO dao = new TicketDAO();
        dao.delete(tableSession.getSelectionModel().getSelectedItem());
        tableSession.refresh();
        tableSession.setItems(dao.readAll());
    }

    public boolean fillError(Ticket ticket) {
        ArrayList<String> erros = new ArrayList<>();
        String s = txtSessionPrice.getText().replace(",", ".");

        if (ticket.getType().trim().equals("")) {
        }
        if(txtSessionType.getText().trim().equals("")){
            erros.add("Campo 'Tipo da sessão' não pode ser vazio. \n");
        }
        if (!regex.isDouble(s)) {
            erros.add("Campo 'Preço' deve ser preenchido em reais. \n");
        }
        if (s.trim().equals("")) {
            erros.add("Campo 'Preço' não pode ser vazio. \n");
        }

        if (erros.isEmpty()) {
            return true;
        } else {
            mostrarAlerta("Sessão", "Erro ao cadastrar Sessão.", Utils.trataErros(erros), Alert.AlertType.ERROR);
            return false;
        }
    }

}
