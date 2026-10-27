package edu.tor.quarkusswing.ui;

import edu.tor.quarkusswing.service.PersonService;

import javax.swing.*;
import java.awt.*;

public class LoginDialog extends javax.swing.JDialog {
    private boolean authenficateed = false;
    private final PersonService personService;

    public LoginDialog(PersonService personService) {
        this.personService = personService;
        setTitle("Вхід до системи");
        setSize(350, 180);
        setLocationRelativeTo(null);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.add(new JLabel("Login"));
        JTextField loginTF = new JTextField();
        panel.add(loginTF);
        panel.add(new JLabel("Password"));
        JTextField passTF = new JTextField();
        panel.add(passTF);
        JButton btnLogin =new JButton("Увійти");
        btnLogin.addActionListener(e -> {
            String login=loginTF.getText();
            String pass=passTF.getText();
            if ("admin".equals(login)/*personService.isExistByName(login)*/&&"admin".equalsIgnoreCase(pass)){
                authenficateed=true;
                dispose();
            }else {
                JOptionPane.showMessageDialog(this,
                        "Невырний логын або пароль",
                        "Помилка входу",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(new JLabel(""));
        panel.add(btnLogin);
        add(panel, BorderLayout.CENTER);
    }

    public boolean isAuthenticated() {
        return authenficateed;
    }
}
