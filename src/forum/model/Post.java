package forum.model;

import java.time.LocalDateTime;

public class Post {
    private int postID;
    private String title;
    private String author;
    private String content;
    private LocalDateTime date;
    private int likes;

    public Post(int postID, String title, String author, String content) {
        this.postID = postID;
        this.title = title;
        this.author = author;
        this.content = content;
        this.date = LocalDateTime.now();
    }

    public int getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getLikes() {
        return likes;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void addLike() {
        likes++;
    }

    @Override
    public String toString() {
        return "postID=" + postID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", likes=" + likes;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;

        return postID == post.postID;
    }

    @Override
    public int hashCode() {
        return postID;
    }
}
