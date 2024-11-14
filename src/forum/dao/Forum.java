package forum.dao;

import forum.model.Post;

import java.time.LocalDate;

public interface Forum {
    boolean addPost(Post post);
    boolean removePost(int postID);
    boolean updatePost(int postID, String content);
    Post getPostByID(int postID);
    Post[] getPostsByAuthor(String author);
    Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo);
    int size();
}
