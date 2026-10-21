import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class User {

    private String username;
    private String password;
    private String role;

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // TODO: add getters
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}

class WelcomePage extends Frame {

    WelcomePage(User user) {
        // TODO: build the page that says "Hello, username" and the role of the user
        super("Hola mes amigas");
        setLayout(new FlowLayout());
        Label welcomeLabel = new Label("¡Buenos días " + user.getUsername());
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(welcomeLabel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

public class LoginFormTask extends Frame {

    //TODO: add list of users to seed
    private List<User> users = new ArrayList<>();
    private int failedAttemps = 0;
    private Button loginButton;

    public LoginFormTask() {
        super("Login");
        loadUsers();

        setLayout(new GridBagLayout());
        Panel content = new Panel(new BorderLayout(10, 15));
        content.setBackground(new Color(245, 239, 230));

        Label title = new Label("Holaa! ", Label.CENTER);
        title.setFont(new Font("Segoe Script", Font.BOLD, 25));
        title.setForeground(new Color(92, 64, 51));
        content.add(title, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(3, 2, 8, 8));
        form.add(new Label("Username:", Label.RIGHT));
        final TextField username = new TextField(18);
        form.add(username);

        form.add(new Label("Password:", Label.RIGHT));
        final TextField password = new TextField(18);
        password.setEchoChar('*');
        form.add(password);

        form.add(new Label("Role:", Label.RIGHT));
        final Choice role = new Choice();
        role.add("Student");
        role.add("TA");
        role.add("Professor");
        role.add("DS");
        form.add(role);

        content.add(form, BorderLayout.CENTER);

        Panel buttons = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        Button cancel = new Button("Cancel");
        cancel.setBackground(new Color(210, 180, 140));

        loginButton = new Button("Login");
        loginButton.setBackground(new Color(139, 69, 19));
        loginButton.setForeground(Color.WHITE);
        buttons.add(cancel);
        buttons.add(loginButton);
        content.add(buttons, BorderLayout.SOUTH);

        add(content);

        loginButton.addActionListener(e -> {
            // TODO: the login logic
            String enteredUser = username.getText().trim();
            String enteredPass = password.getText().trim();
            String selectedRole = role.getSelectedItem();

            if (enteredUser.isEmpty() || enteredPass.isEmpty()) {
                title.setText("Please fill all fields!");
                title.setForeground(Color.RED);
                return;
            }

            User found = findUser(enteredUser);

            if (found != null && found.getPassword().equals(enteredPass) && found.getRole().equals(selectedRole)) {
                new WelcomePage(found);
                dispose();
            } else {
                failedAttemps++;
                if (failedAttemps >= 3) {
                    loginButton.setEnabled(false);
                    title.setText("Account Locked (3 fails)!");
                } else {
                    title.setText("Wrong info! (" + failedAttemps + "/3)");
                }
                title.setForeground(Color.RED);
            }
        });

        cancel.addActionListener(e -> {
            // TODO: close the window
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { dispose(); }
        });
        setSize(420, 340);
        setLocationRelativeTo(null);
    }

    private void loadUsers() {
        // TODO: add at least 3 users to the list one for each role
        users.add(new User("Menna", "2204", "Student"));
        users.add(new User("Shrouk", "2908", "DS"));
        users.add(new User("Hania", "1234", "TA"));
    }

    private User findUser(String name) {
        // TODO: search the list for the user
        for (User u : users) {
            if (u.getUsername().equals(name)) {
                return u;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        new LoginFormTask().setVisible(true);
    }
}