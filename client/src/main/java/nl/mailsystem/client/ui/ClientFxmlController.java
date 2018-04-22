package nl.mailsystem.client.ui;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Setter;
import nl.mailsystem.client.ClientController;
import nl.mailsystem.common.domain.Mail;
import nl.mailsystem.common.domain.MailAddress;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Robin Laugs
 */
public class ClientFxmlController implements Initializable {

    @FXML
    private TextField textFieldSubject, textFieldTo, textFieldMailSubject;

    @FXML
    private TextArea textAreaText, textAreaMailText;

    @FXML
    private Button buttonSend;

    @FXML
    private ListView listViewMails;

    @Setter
    private ClientController controller;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding binding = textFieldSubject.textProperty().isEmpty()
                .or(textFieldTo.textProperty().isEmpty())
                .or(textAreaText.textProperty().isEmpty());

        buttonSend.disableProperty().bind(binding);
        buttonSend.setOnMouseClicked(e -> buttonSendClicked());
    }

    private void buttonSendClicked() {
        Mail mail = assembleMail();

        controller.sendMailToServer(mail);
    }

    private Mail assembleMail() {
        return Mail.builder()
                .subject(textFieldSubject.getText())
                .text(textAreaText.getText())
                .receiver(new MailAddress(textFieldTo.getText()))
                .build();
    }

}
