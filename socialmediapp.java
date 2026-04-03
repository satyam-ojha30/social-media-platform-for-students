package socialapp;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SocialMediaApp extends JFrame {

    public static User currentUser;

    // 🧠 store users + passwords
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, User> userObjects = new HashMap<>();

    private JPanel feedPanel;

    public SocialMediaApp() {
        showLoginScreen();
    }

    // 🔐 LOGIN + SIGNUP SCREEN
    private void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login / Signup");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new GridLayout(3, 2));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");

        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passwordField);
        loginFrame.add(loginBtn);
        loginFrame.add(signupBtn);

        // ✅ LOGIN
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (users.containsKey(username) && users.get(username).equals(password)) {
                currentUser = userObjects.get(username);
                loginFrame.dispose();
                initMainApp();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid login!");
            }
        });

        // ✅ SIGNUP
        signupBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (users.containsKey(username)) {
                JOptionPane.showMessageDialog(loginFrame, "User already exists!");
            } else {
                users.put(username, password);
                userObjects.put(username, new User(username));
                JOptionPane.showMessageDialog(loginFrame, "Signup successful!");
            }
        });

        loginFrame.setVisible(true);
    }

    // 🏠 MAIN APP UI
    private void initMainApp() {
        setTitle("Social Media - " + currentUser.username);
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(feedPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton addPostBtn = new JButton("Add Post");
        add(addPostBtn, BorderLayout.SOUTH);

        addPostBtn.addActionListener(e -> {
            String content = JOptionPane.showInputDialog(this, "Enter post:");
            if (content != null && !content.isEmpty()) {
                currentUser.addPost(new Post(content));
                refreshFeed();
            }
        });

        setVisible(true);
    }

    private void refreshFeed() {
        feedPanel.removeAll();

        for (Post post : currentUser.getPosts()) {
            feedPanel.add(new PostPanel(currentUser, post, this::refreshFeed));
        }

        feedPanel.revalidate();
        feedPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SocialMediaApp::new);
    }
}
