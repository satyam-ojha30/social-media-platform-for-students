package socialapp;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SocialMediaApp extends JFrame {

    public static User currentUser;

    private static Map<String, String> users =
            new HashMap<>();

    private static Map<String, User> userObjects =
            new HashMap<>();

    private JPanel feedPanel;

    private JLabel profileLabel;

    private JButton addPostBtn;
    private JButton refreshBtn;

    public SocialMediaApp() {

        showLoginScreen();
    }

    // ================= LOGIN SCREEN =================
    private void showLoginScreen() {

        JFrame loginFrame =
                new JFrame("Social Media Login");

        loginFrame.setSize(450, 350);

        loginFrame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        loginFrame.setLocationRelativeTo(null);

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                new Color(36, 37, 42));

        JLabel title =
                new JLabel(
                        "Mini Social Media",
                        SwingConstants.CENTER);

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font("Arial",
                        Font.BOLD,
                        28));

        mainPanel.add(title,
                BorderLayout.NORTH);

        JPanel centerPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                1,
                                10,
                                15));

        centerPanel.setBackground(
                new Color(36, 37, 42));

        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        50,
                        20,
                        50));

        JTextField usernameField =
                new JTextField();

        JPasswordField passwordField =
                new JPasswordField();

        usernameField.setBorder(
                BorderFactory.createTitledBorder(
                        "Username"));

        passwordField.setBorder(
                BorderFactory.createTitledBorder(
                        "Password"));

        centerPanel.add(usernameField);

        centerPanel.add(passwordField);

        JPanel buttonPanel =
                new JPanel();

        buttonPanel.setBackground(
                new Color(36, 37, 42));

        JButton loginBtn =
                new JButton("Login");

        JButton signupBtn =
                new JButton("Signup");

        buttonPanel.add(loginBtn);

        buttonPanel.add(signupBtn);

        centerPanel.add(buttonPanel);

        mainPanel.add(centerPanel,
                BorderLayout.CENTER);

        loginFrame.add(mainPanel);

        // LOGIN
        loginBtn.addActionListener(e -> {

            String username =
                    usernameField.getText();

            String password =
                    new String(
                            passwordField.getPassword());

            if (users.containsKey(username)
                    && users.get(username)
                    .equals(password)) {

                currentUser =
                        userObjects.get(username);

                loginFrame.dispose();

                initMainApp();

            } else {

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "Invalid Username or Password!");
            }
        });

        // SIGNUP
        signupBtn.addActionListener(e -> {

            String username =
                    usernameField.getText();

            String password =
                    new String(
                            passwordField.getPassword());

            if (username.isEmpty()
                    || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "Fields Cannot Be Empty!");

                return;
            }

            if (users.containsKey(username)) {

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "User Already Exists!");

            } else {

                users.put(username, password);

                userObjects.put(username,
                        new User(username));

                JOptionPane.showMessageDialog(
                        loginFrame,
                        "Signup Successful!");
            }
        });

        loginFrame.setVisible(true);
    }

    // ================= MAIN APP =================
    private void initMainApp() {

        setTitle(
                "Social Media - "
                        + currentUser.username);

        setSize(750, 850);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // ================= TOP PANEL =================
        JPanel topPanel =
                new JPanel(new BorderLayout());

        topPanel.setBackground(
                new Color(52, 58, 64));

        profileLabel =
                new JLabel(
                        "👤 "
                                + currentUser.username
                                + " | Bio: "
                                + currentUser.bio);

        profileLabel.setForeground(Color.WHITE);

        profileLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10));

        JButton editProfileBtn =
                new JButton("Customize Profile");

        JButton logoutBtn =
                new JButton("Logout");

        JPanel topBtnPanel =
                new JPanel();

        topBtnPanel.setBackground(
                new Color(52, 58, 64));

        topBtnPanel.add(editProfileBtn);

        topBtnPanel.add(logoutBtn);

        topPanel.add(profileLabel,
                BorderLayout.CENTER);

        topPanel.add(topBtnPanel,
                BorderLayout.EAST);

        add(topPanel,
                BorderLayout.NORTH);

        // ================= FEED PANEL =================
        feedPanel =
                new JPanel();

        feedPanel.setLayout(
                new BoxLayout(
                        feedPanel,
                        BoxLayout.Y_AXIS));

        feedPanel.setBackground(
                Color.decode(
                        currentUser.feedColor));

        JScrollPane scrollPane =
                new JScrollPane(feedPanel);

        add(scrollPane,
                BorderLayout.CENTER);

        // ================= BOTTOM PANEL =================
        JPanel bottomPanel =
                new JPanel();

        addPostBtn =
                new JButton("Add Post");

        refreshBtn =
                new JButton("Refresh Feed");

        bottomPanel.add(addPostBtn);

        bottomPanel.add(refreshBtn);

        add(bottomPanel,
                BorderLayout.SOUTH);

        // ================= ADD POST =================
        addPostBtn.addActionListener(e -> {

            String content =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Post Content:");

            JFileChooser chooser =
                    new JFileChooser();

            chooser.setDialogTitle(
                    "Choose Post Image");

            int result =
                    chooser.showOpenDialog(this);

            String imagePath = "";

            if (result ==
                    JFileChooser.APPROVE_OPTION) {

                imagePath =
                        chooser.getSelectedFile()
                                .getAbsolutePath();
            }

            if (content != null
                    && !content.trim().isEmpty()) {

                currentUser.addPost(
                        new Post(content,
                                imagePath));

                JOptionPane.showMessageDialog(
                        this,
                        "New Post Uploaded!");

                refreshFeed();
            }
        });

        // ================= REFRESH =================
        refreshBtn.addActionListener(
                e -> refreshFeed());

        // ================= CUSTOMIZE PROFILE =================
        editProfileBtn.addActionListener(e -> {

            String newBio =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter New Bio:",
                            currentUser.bio);

            if (newBio != null) {

                currentUser.setBio(newBio);
            }

            // Profile Picture
            JFileChooser chooser =
                    new JFileChooser();

            chooser.setDialogTitle(
                    "Choose Profile Picture");

            int result =
                    chooser.showOpenDialog(this);

            if (result ==
                    JFileChooser.APPROVE_OPTION) {

                currentUser.setProfilePic(
                        chooser.getSelectedFile()
                                .getAbsolutePath());
            }

            // Background Color
            Color bgColor =
                    JColorChooser.showDialog(
                            this,
                            "Choose App Background",
                            Color.WHITE);

            if (bgColor != null) {

                currentUser.bgColor =
                        String.format(
                                "#%02x%02x%02x",
                                bgColor.getRed(),
                                bgColor.getGreen(),
                                bgColor.getBlue());
            }

            // Feed Color
            Color feedColor =
                    JColorChooser.showDialog(
                            this,
                            "Choose Feed Color",
                            Color.LIGHT_GRAY);

            if (feedColor != null) {

                currentUser.feedColor =
                        String.format(
                                "#%02x%02x%02x",
                                feedColor.getRed(),
                                feedColor.getGreen(),
                                feedColor.getBlue());
            }

            // Post Color
            Color postColor =
                    JColorChooser.showDialog(
                            this,
                            "Choose Post Card Color",
                            Color.WHITE);

            if (postColor != null) {

                currentUser.postColor =
                        String.format(
                                "#%02x%02x%02x",
                                postColor.getRed(),
                                postColor.getGreen(),
                                postColor.getBlue());
            }

            // Comment Color
            Color commentColor =
                    JColorChooser.showDialog(
                            this,
                            "Choose Comment Box Color",
                            Color.LIGHT_GRAY);

            if (commentColor != null) {

                currentUser.commentColor =
                        String.format(
                                "#%02x%02x%02x",
                                commentColor.getRed(),
                                commentColor.getGreen(),
                                commentColor.getBlue());
            }

            // Text Color
            Color txtColor =
                    JColorChooser.showDialog(
                            this,
                            "Choose Text Color",
                            Color.BLACK);

            if (txtColor != null) {

                currentUser.textColor =
                        String.format(
                                "#%02x%02x%02x",
                                txtColor.getRed(),
                                txtColor.getGreen(),
                                txtColor.getBlue());
            }

            // Button Color
            Color btnColor =
                    JColorChooser.showDialog(
                            this,
                            "Choose Button Color",
                            Color.BLUE);

            if (btnColor != null) {

                currentUser.buttonColor =
                        String.format(
                                "#%02x%02x%02x",
                                btnColor.getRed(),
                                btnColor.getGreen(),
                                btnColor.getBlue());
            }

            // Font Style
            String[] fonts =
                    GraphicsEnvironment
                            .getLocalGraphicsEnvironment()
                            .getAvailableFontFamilyNames();

            String font =
                    (String)
                            JOptionPane.showInputDialog(
                                    this,
                                    "Choose Font",
                                    "Font Style",
                                    JOptionPane.PLAIN_MESSAGE,
                                    null,
                                    fonts,
                                    currentUser.fontStyle);

            if (font != null) {

                currentUser.fontStyle = font;
            }

            applyTheme();

            refreshFeed();

            JOptionPane.showMessageDialog(
                    this,
                    "Profile Customized!");
        });

        // ================= LOGOUT =================
        logoutBtn.addActionListener(e -> {

            dispose();

            currentUser = null;

            new SocialMediaApp();
        });

        applyTheme();

        refreshFeed();

        setLocationRelativeTo(null);

        setVisible(true);
    }

    // ================= APPLY THEME =================
    private void applyTheme() {

        getContentPane().setBackground(
                Color.decode(
                        currentUser.bgColor));

        feedPanel.setBackground(
                Color.decode(
                        currentUser.feedColor));

        profileLabel.setForeground(
                Color.decode(
                        currentUser.textColor));

        profileLabel.setFont(
                new Font(
                        currentUser.fontStyle,
                        Font.BOLD,
                        16));

        addPostBtn.setBackground(
                Color.decode(
                        currentUser.buttonColor));

        refreshBtn.setBackground(
                Color.decode(
                        currentUser.buttonColor));

        addPostBtn.setForeground(Color.WHITE);

        refreshBtn.setForeground(Color.WHITE);
    }

    // ================= REFRESH FEED =================
    private void refreshFeed() {

        feedPanel.removeAll();

        for (User user :
                userObjects.values()) {

            for (Post post :
                    user.getPosts()) {

                feedPanel.add(
                        new PostPanel(
                                user,
                                post,
                                this::refreshFeed));

                feedPanel.add(
                        Box.createVerticalStrut(10));
            }
        }

        feedPanel.revalidate();

        feedPanel.repaint();
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                SocialMediaApp::new);
    }
}
