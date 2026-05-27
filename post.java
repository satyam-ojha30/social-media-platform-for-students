package socialapp;

import java.util.*;

public class Post {

    public String content;
    public String imagePath;

    public Set<String> likedUsers;
    public List<String> comments;

    public String timestamp;

    public Post(String content, String imagePath) {

        this.content = content;
        this.imagePath = imagePath;

        likedUsers = new HashSet<>();
        comments = new ArrayList<>();

        timestamp = new Date().toString();
    }

    // Like System
    public void toggleLike(String username) {

        if (likedUsers.contains(username)) {

            likedUsers.remove(username);

        } else {

            likedUsers.add(username);
        }
    }

    public int getLikeCount() {

        return likedUsers.size();
    }

    // Comment System
    public void addComment(String comment) {

        comments.add(comment);
    }
}
