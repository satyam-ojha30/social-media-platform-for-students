package socialapp;

import javax.swing.*;
import java.awt.*;

public class PostPanel extends JPanel {

    public PostPanel(User user,
                     Post post,
                     Runnable refreshCallback) {

        setLayout(new BoxLayout(this,
                BoxLayout.Y_AXIS));

        setBackground(
                Color.decode(user.postColor));

        setBorder(
                BorderFactory.createLineBorder(
                        Color.GRAY, 2));

        // ================= TOP PANEL =================
        JPanel topPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT));

        topPanel.setBackground(
                Color.decode(user.postColor));

        // ================= PROFILE PICTURE =================
        if (!user.profilePic.isEmpty()) {

            ImageIcon profileIcon =
                    new ImageIcon(user.profilePic);

            Image img =
                    profileIcon.getImage()
                            .getScaledInstance(
                                    50,
                                    50,
                                    Image.SCALE_SMOOTH);

            JLabel picLabel =
                    new JLabel(
                            new ImageIcon(img));

            topPanel.add(picLabel);
        }

        // ================= USERNAME =================
        JLabel usernameLabel =
                new JLabel(user.username);

        usernameLabel.setForeground(
                Color.decode(user.textColor));

        usernameLabel.setFont(
                new Font(
                        user.fontStyle,
                        Font.BOLD,
                        18));

        topPanel.add(usernameLabel);

        add(topPanel);

        // ================= TIME =================
        JLabel timeLabel =
                new JLabel(post.timestamp);

        timeLabel.setForeground(Color.GRAY);

        add(timeLabel);

        // ================= CONTENT =================
        JLabel contentLabel =
                new JLabel(post.content);

        contentLabel.setForeground(
                Color.decode(user.textColor));

        contentLabel.setFont(
                new Font(
                        user.fontStyle,
                        Font.PLAIN,
                        16));

        add(contentLabel);

        // ================= POST IMAGE =================
        if (post.imagePath != null
                && !post.imagePath.isEmpty()) {

            ImageIcon postIcon =
                    new ImageIcon(post.imagePath);

            Image img =
                    postIcon.getImage()
                            .getScaledInstance(
                                    350,
                                    250,
                                    Image.SCALE_SMOOTH);

            JLabel imageLabel =
                    new JLabel(
                            new ImageIcon(img));

            add(imageLabel);
        }

        // ================= LIKE PANEL =================
        JPanel likePanel = new JPanel();

        likePanel.setBackground(
                Color.decode(user.postColor));

        JLabel likeLabel =
                new JLabel(
                        "Likes: "
                                + post.getLikeCount());

        JButton likeBtn =
                new JButton("Like");

        likeBtn.setBackground(
                Color.decode(user.buttonColor));

        likeBtn.setForeground(Color.WHITE);

        likeBtn.addActionListener(e -> {

            post.toggleLike(
                    SocialMediaApp.currentUser.username);

            likeLabel.setText(
                    "Likes: "
                            + post.getLikeCount());

            JOptionPane.showMessageDialog(
                    this,
                    "Post Liked!");
        });

        likePanel.add(likeLabel);

        likePanel.add(likeBtn);

        add(likePanel);

        // ================= COMMENTS =================
        JTextArea commentsArea =
                new JTextArea(5, 25);

        commentsArea.setEditable(false);

        commentsArea.setBackground(
                Color.decode(user.commentColor));

        commentsArea.setForeground(
                Color.decode(user.textColor));

        commentsArea.setFont(
                new Font(
                        user.fontStyle,
                        Font.PLAIN,
                        14));

        for (String c : post.comments) {

            commentsArea.append(c + "\n");
        }

        JScrollPane commentScroll =
                new JScrollPane(commentsArea);

        add(commentScroll);

        // ================= COMMENT BUTTON =================
        JButton commentBtn =
                new JButton("Add Comment");

        commentBtn.setBackground(
                Color.decode(user.buttonColor));

        commentBtn.setForeground(Color.WHITE);

        commentBtn.addActionListener(e -> {

            String comment =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Comment:");

            if (comment != null
                    && !comment.trim().isEmpty()) {

                post.addComment(
                        SocialMediaApp.currentUser.username
                                + ": " + comment);

                JOptionPane.showMessageDialog(
                        this,
                        "Comment Added!");

                refreshCallback.run();
            }
        });

        add(commentBtn);
    }
}
