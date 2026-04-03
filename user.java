package socialapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String username;
    private List<Post> posts;

    public User(String username) {
        this.username = username;
        this.posts = new ArrayList<>();
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }
}
