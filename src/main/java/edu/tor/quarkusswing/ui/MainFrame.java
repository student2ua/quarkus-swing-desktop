package edu.tor.quarkusswing.ui;

import edu.tor.quarkusswing.entity.Person;
import edu.tor.quarkusswing.service.PersonService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final PersonService personService;
    private DefaultTableModel tableModel;

    public MainFrame(PersonService personService1) {
        this.personService = personService1;
        setTitle("Quarkus Swing Desktop — Главное окно");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        JMenu menuDb = new JMenu("База данных");
        JMenuItem showItem = new JMenuItem("Показать пользователей");
        showItem.addActionListener(e -> showUsers());
        JMenuItem addItem = new JMenuItem("Добавить пользователя");
        addItem.addActionListener(e -> addUser());
        menuDb.add(showItem);
        menuDb.add(addItem);

        JMenu menuHelp = new JMenu("Справка");
        JMenuItem aboutItem = new JMenuItem("О программе");
        aboutItem.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "Swing + Quarkus Desktop (Hibernate Panache + Agroal pool)",
                        "О программе",
                        JOptionPane.INFORMATION_MESSAGE));
        menuHelp.add(aboutItem);

        menuBar.add(menuFile);
        menuBar.add(menuDb);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        tableModel = new DefaultTableModel(new String[]{"ID", "Имя", "Email"}, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        showUsers();
    }

    public void loadUsers() {
        showUsers();
    }

    private void showUsers() {
        List<Person> users = personService.listAll();
        tableModel.setRowCount(0);
        for (Person user : users) {
            tableModel.addRow(new Object[]{user.id, user.name, user.email});
        }
    }

    private void addUser() {
        String name = JOptionPane.showInputDialog(this,
                "Введите имя:",
                "Добавить пользователя",
                JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.isBlank()) return;

        String email = JOptionPane.showInputDialog(this, "Введите email:", "Добавить пользователя", JOptionPane.QUESTION_MESSAGE);
        if (email == null || email.isBlank()) return;
        personService.create(name, email);
        showUsers();
        JOptionPane.showMessageDialog(this, "Пользователь сохранён в БД");
    }
}
