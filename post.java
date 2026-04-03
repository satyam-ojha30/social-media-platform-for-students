package socialapp;

import java.util.HashSet;
import java.util.Set;

public class Post {
    public String content;
    public Set<String> likedUsers;

    public Post(String content) {
        this.content = content;
        this.likedUsers = new HashSet<>();
    }

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
}
