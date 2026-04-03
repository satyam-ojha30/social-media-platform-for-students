package socialapp;

import javax.swing.*;
import java.awt.*;

public class PostPanel extends JPanel {

    private JLabel likeLabel;
    private JButton likeBtn;

    public PostPanel(User postUser, Post post, Runnable updateFeedCallback) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        setBackground(new Color(245, 245, 245));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));

        JLabel authorLabel = new JLabel(postUser.username);
        authorLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(authorLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(post.content);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
        add(contentArea, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        likeBtn = new JButton();
        likeLabel = new JLabel();
        updateLikeButton(post);

        btnPanel.add(likeBtn);
        btnPanel.add(likeLabel);

        likeBtn.addActionListener(e -> {
            post.toggleLike(SocialMediaApp.currentUser.username);
            updateLikeButton(post);
        });

        if (postUser == SocialMediaApp.currentUser) {
            JButton editBtn = new JButton("Edit");
            JButton deleteBtn = new JButton("Delete");

            btnPanel.add(editBtn);
            btnPanel.add(deleteBtn);

            editBtn.addActionListener(e -> {
                String newContent = JOptionPane.showInputDialog(this, "Edit your post:", post.content);
                if (newContent != null && !newContent.isEmpty()) {
                    post.content = newContent;
                    updateFeedCallback.run();
                }
            });

            deleteBtn.addActionListener(e -> {
                postUser.getPosts().remove(post);
                updateFeedCallback.run();
            });
        }

        add(btnPanel, BorderLayout.SOUTH);
    }

    private void updateLikeButton(Post post) {
        String username = SocialMediaApp.currentUser.username;

        if (post.likedUsers.contains(username)) {
            likeBtn.setText("Unlike");
        } else {
            likeBtn.setText("Like");
        }

        likeLabel.setText("Likes: " + post.getLikeCount());
    }
}
