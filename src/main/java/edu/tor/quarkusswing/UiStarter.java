package edu.tor.quarkusswing;

import edu.tor.quarkusswing.service.PersonService;
import edu.tor.quarkusswing.ui.LoginDialog;
import edu.tor.quarkusswing.ui.MainFrame;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@ApplicationScoped
public class UiStarter {
    @Inject
    PersonService personService;

    void onStart(@Observes StartupEvent ev) {
        personService.initTestData();
        // Quarkus Desktop Swing гарантирует корректную работу с EDT,
        // но SwingUtilities.invokeLater всё ещё рекомендуется для создания окон.
        SwingUtilities.invokeLater(() -> {
            LoginDialog loginDialog = new LoginDialog(personService);
            loginDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if (loginDialog.isAuthenticated()) {
                        MainFrame mainFrame = new MainFrame(personService);
                        mainFrame.setVisible(true);
                    } else {
                        System.exit(0);
                    }
                }
            });
            loginDialog.setVisible(true);
        });
    }
}
