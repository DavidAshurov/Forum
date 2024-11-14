package forum.dao;

import forum.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

public class ForumImpl implements Forum {
    private Post[] posts;
    private int size;
    private Comparator<Post> comparator = (p1,p2) -> {
        int res = p1.getAuthor().compareTo(p2.getAuthor());
        return res != 0 ? res : p1.getDate().compareTo(p2.getDate());
    };

    public ForumImpl() {
        this.posts = new Post[5];
    }

    @Override
    public boolean addPost(Post post) {
        if (post == null || getPostByID(post.getPostID()) != null) {
            return false;
        }
        if (size == posts.length) {
            posts = Arrays.copyOf(posts,size * 2);
        }
        int idx = Arrays.binarySearch(posts,0,size,post,comparator);
        idx = idx >= 0 ? idx : -idx - 1;
        System.arraycopy(posts,idx,posts,idx + 1,size++ - idx);
        posts[idx] = post;
        return true;
    }

    @Override
    public boolean removePost(int postID) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getPostID() == postID) {
                System.arraycopy(posts,i+1,posts,i,size - i - 1);
                posts[--size] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updatePost(int postID, String content) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getPostID() == postID) {
                posts[i].setContent(content);
                return true;
            }
        }
        return false;
    }

    @Override
    public Post getPostByID(int postID) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getPostID() == postID) {
                return posts[i];
            }
        }
        return null;
    }

    @Override
    public Post[] getPostsByAuthor(String author) {
        Post pattern = new Post(0,null,author,null);
        pattern.setDate(LocalDateTime.MIN);
        int idxOfFirstPost = Arrays.binarySearch(posts,0,size,pattern,comparator);
        if (idxOfFirstPost < 0 && !posts[-idxOfFirstPost - 1].getAuthor().equals(author)) {
            return null;
        }
        pattern = new Post(0,null,author,null);
        pattern.setDate(LocalDateTime.MAX);
        int idxOfLastPost = Arrays.binarySearch(posts,0,size,pattern,comparator);
        idxOfFirstPost = -idxOfFirstPost - 1;
        idxOfLastPost = -idxOfLastPost - 1;
        return Arrays.copyOfRange(posts,idxOfFirstPost,idxOfLastPost);
    }

    @Override
    public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {
        Post pattern = new Post(0,null,author,null);
        pattern.setDate(dateFrom.atStartOfDay());
        int idxOfFirstPost = Arrays.binarySearch(posts,0,size,pattern,comparator);
        if (idxOfFirstPost < 0 && !posts[-idxOfFirstPost - 1].getAuthor().equals(author)) {
            return null;
        }
        pattern = new Post(0,null,author,null);
        pattern.setDate(dateTo.plusDays(1).atStartOfDay());
        int idxOfLastPost = Arrays.binarySearch(posts,0,size,pattern,comparator);
        idxOfFirstPost = -idxOfFirstPost - 1;
        idxOfLastPost = -idxOfLastPost - 1;
        return Arrays.copyOfRange(posts,idxOfFirstPost,idxOfLastPost);
    }

    @Override
    public int size() {
        return size;
    }
}
