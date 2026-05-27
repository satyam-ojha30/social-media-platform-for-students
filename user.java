package socialapp;

import java.util.ArrayList;
import java.util.List;

public class User {

    public String username;
    public String bio;
    public String profilePic;

    // Theme Customization
    public String bgColor = "#f5f5f5";
    public String textColor = "#000000";
    public String buttonColor = "#008cff";
    public String feedColor = "#e4e6eb";
    public String postColor = "#ffffff";
    public String commentColor = "#f0f2f5";
    public String fontStyle = "Arial";

    private List<Post> posts;

    public User(String username) {

        this.username = username;
        this.bio = "No bio yet";
        this.profilePic = "";

        posts = new ArrayList<>();
    }

    public List<Post> getPosts() {

        return posts;
    }

    public void addPost(Post post) {

        posts.add(post);
    }

    public void setBio(String bio) {

        this.bio = bio;
    }

    public void setProfilePic(String path) {

        this.profilePic = path;
    }
}
