package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.SessionDAO;
import dao.TicketDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Seats;
import model.Session;

import javax.swing.*;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewSaleController extends MenuPrincipal{

    //Toolbar buttons
    @FXML private Button btnSale;
    @FXML private Button btnMovie;
    @FXML private Button btnTheater;
    @FXML private Button btnGenre;
    @FXML private Button btnPrice;
    @FXML private Button btnMovieSession;
    @FXML private Button btnCancelSale;
    @FXML private Button btnReport;

    @FXML private ChoiceBox<Session> cbSessionSale;
    @FXML private ChoiceBox<Integer> cbPromoTickets;
    @FXML private Button btnSetSale;
    @FXML private Button btnCancelOp;

    /*Verificar Classes*/
    @FXML private TableView<Seats> tableSeats;
    @FXML private TableColumn<Seats, Integer> cSeat;
    @FXML private TableColumn<Seats, Boolean> cStatus;

    @FXML
    public void initialize() throws SQLException {
        fill();
    }

    private ObservableList<Session> listSession = FXCollections.observableArrayList();


    public void fill() throws SQLException {

        SessionDAO daoSession = new SessionDAO();
        listSession= daoSession.readAll(0, true);;
        cbSessionSale.setItems(listSession);

    }

    public void updateSeats(ActionEvent actionEvent) throws SQLException {
        Gson gson = new Gson();
        SessionDAO daoSession = new SessionDAO();
        Session s = new Session();
        ObservableList<Seats> seats = FXCollections.observableArrayList();
        s = daoSession.readOne(cbSessionSale.getSelectionModel().getSelectedItem().getId());
        HashMap<Integer, Boolean> seatMap = new Gson().fromJson(s.getSeats(), new TypeToken<HashMap<Integer, Boolean>>() {}.getType());
        tableSeats.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for(Map.Entry<Integer, Boolean> map : seatMap.entrySet()){
            Seats seat = new Seats();
            seat.setNumber(map.getKey());
            seat.setAvailability(map.getValue());
            seats.add(seat);
        }

        cSeat.setCellValueFactory(new PropertyValueFactory<>("number"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("availability"));
        tableSeats.setItems(seats);
    }

    public void updatePromotionalTickets(MouseEvent mouseEvent) throws SQLException {
        ArrayList<Seats> p = new ArrayList<>(tableSeats.getSelectionModel().getSelectedItems());
        ObservableList<Integer> amount = FXCollections.observableArrayList();
        for (int i = 0; i <= p.size(); i++) {
            amount.add(i);
        }
        cbPromoTickets.setItems(amount);
    }

    public ArrayList<Integer> selectedSeats() {
        ArrayList<Seats> seats = new ArrayList<>(tableSeats.getSelectionModel().getSelectedItems());
        ArrayList<Integer> selected = new ArrayList<>();
        for (Seats seat : seats) {
            selected.add(seat.getNumber());
        }
        return selected;
    }

    public String updateSeats(ArrayList<Integer> selectedSeats) throws SQLException {
        Gson gson = new Gson();
        SessionDAO daoSession = new SessionDAO();
        Session s = new Session();
        s = daoSession.readOne(cbSessionSale.getSelectionModel().getSelectedItem().getId());
        HashMap<Integer, Boolean> seatMap = new Gson().fromJson(s.getSeats(), new TypeToken<HashMap<Integer, Boolean>>() {}.getType());
        for (int seat : selectedSeats) {
            seatMap.put(seat, true);
        }
        return gson.toJson(seatMap);
    }

    public void printTicket(int id, Session session, double price, ArrayList selected, int qtdSeats, int qtdPromotional, double totalSale) throws PrinterException {
        String intro = "### CISTEMAX ###\n\n";
        String strId = "Número da venda: " + id + "\n";
        String strSession = "Sessão: " + session.toString() + "\n";
        String strPrice = "Valor unitário dos ingressos: R$" + String.valueOf(price).replace(".",",") + "\n";
        String strQtdSeats = "Quantidade de assentos totais: " + qtdSeats + "\n";
        String strQtdPromocionais = "Quantidade de assentos promocionais: " + qtdPromotional + "\n";
        String strSelected = "Assentos escolhidos: " + selected.toString().replace("[","").replace("]","") + "\n\n";
        String strTotal = "Total da compra: R$" + String.valueOf(totalSale).replace(".",",") + "\n";
        String strThanks = "\n\nObrigado pela preferência!" + "\n";
        JTextArea myTicket = new JTextArea();
        myTicket.setLineWrap(true);
        myTicket.append(intro + strSession + strId + strPrice + strQtdSeats + strQtdPromocionais +strSelected + strTotal + strThanks);
        myTicket.print();
    }

    public void executeSale(ActionEvent actionEvent) throws SQLException, PrinterException {
        TicketDAO ticketDAO = new TicketDAO();
        SessionDAO daoSession = new SessionDAO();
        Session s = new Session();
        Double price = ticketDAO.ticketPrice(cbSessionSale.getSelectionModel().getSelectedItem().getTicket());
        ArrayList<Integer> selected = selectedSeats();
        int qtdSeats = selected.size();
        int qtdPromotional = cbPromoTickets.getSelectionModel().getSelectedItem();
        double totalSale = ((qtdSeats - qtdPromotional) * price) + (qtdPromotional*(price/2));
        s = daoSession.readOne(cbSessionSale.getSelectionModel().getSelectedItem().getId());
        System.out.println(s);
        System.out.println("TICKET Price: " + price);
        System.out.println("SELECTED SEATS: " + selectedSeats());
        System.out.println("QTD SEATS: " + qtdSeats);
        System.out.println("Promotional Tickets: " + qtdPromotional);
        System.out.println("TOTAL: " + totalSale);
        System.out.println("NEW SEATS: " + updateSeats(selected));

        printTicket(1, s, price, selected, qtdSeats, qtdPromotional,totalSale);
    }
}
