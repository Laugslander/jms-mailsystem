package nl.mailsystem.client.ui;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import nl.mailsystem.client.ClientController;
import nl.mailsystem.client.ui.listener.MailEventListener;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * @author Robin Laugs
 */
public class ClientFxmlController implements Initializable, MailEventListener {

    @FXML
    private TextField textFieldSubject, textFieldTo, textFieldMailSubject;

    @FXML
    private TextArea textAreaText, textAreaMailText;

    @FXML
    private Button buttonSend;

    @FXML
    private ListView<Mail> listViewMails;

    private final ClientController controller;

    ClientFxmlController(ClientController controller) {
        this.controller = controller;
        this.controller.setMailEventListener(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding inputCheck = textFieldSubject.textProperty().isEmpty()
                .or(textFieldTo.textProperty().isEmpty())
                .or(textAreaText.textProperty().isEmpty());

        buttonSend.disableProperty().bind(inputCheck);
        buttonSend.setOnMouseClicked(e -> buttonSendClicked());

        listViewMails.setOnMouseClicked(e -> listViewMailsClicked());
    }

    @Override
    public void onMailEvent(Mail mail) {
        listViewMails.getItems().add(mail);
    }

    private void buttonSendClicked() {
        Mail mail = assembleMail();

        controller.sendMailToServer(mail);

        clearInput();
    }

    private void listViewMailsClicked() {
        Mail mail = listViewMails.getSelectionModel().getSelectedItem();

        if (!isNull(mail)) {
            textFieldMailSubject.setText(mail.getSubject());
            textAreaMailText.setText(mail.getText());
        }
    }

    private Mail assembleMail() {
        return Mail.builder()
                .subject(textFieldSubject.getText())
                .text(textAreaText.getText())
                .receivers(assembleReceivers())
                .build();
    }

    private Collection<MailAddress> assembleReceivers() {
        return stream(textFieldTo.getText().split(","))
                .map(MailAddress::fromString)
                .collect(toList());
    }

    private void clearInput() {
        textFieldTo.clear();
        textFieldSubject.clear();
        textAreaText.clear();
    }

}
