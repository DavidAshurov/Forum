package forum.test;

import forum.dao.Forum;
import forum.dao.ForumImpl;
import forum.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class ForumImplTest {
    private Forum forum;
    private Post[] posts;
    private Comparator<Post> comparator = (p1, p2) -> {
        int res = p1.getAuthor().compareTo(p2.getAuthor());
        return res != 0 ? res : Integer.compare(p1.getPostID(), p2.getPostID());
    };

    @BeforeEach
    void setUp() {
        forum = new ForumImpl();
        posts = new Post[6];
        posts[0] = new Post(1,"a","David","aaa");
        posts[1] = new Post(2,"b","Vanya","bbb");
        posts[2] = new Post(3,"c","Alex","ccc");
        posts[3] = new Post(4,"d","David","ddd");
        posts[4] = new Post(5,"e","Fedor","eee");
        posts[5] = new Post(6,"f","David","fff");
        for (int i = 0; i < posts.length - 1; i++) {
            forum.addPost(posts[i]);
        }
    }

    @Test
    void testAddPost() {
        assertFalse(forum.addPost(null));
        assertFalse(forum.addPost(posts[1]));
        assertTrue(forum.addPost(posts[5]));
    }

    @Test
    void testRemovePost() {
        assertFalse(forum.removePost(6));
        assertTrue(forum.removePost(4));
        assertFalse(forum.removePost(4));
    }

    @Test
    void testUpdatePost() {
        assertFalse(forum.updatePost(6,""));
        assertTrue(forum.updatePost(4,"dddd"));
        assertEquals("dddd",forum.getPostByID(4).getContent());
    }

    @Test
    void testGetPostByID() {
        assertNull(forum.getPostByID(6));
        assertEquals(posts[2],forum.getPostByID(3));
    }

    @Test
    void testGetPostsByAuthor() {
        assertNull(forum.getPostsByAuthor("Dovid"));
        Post[] expected = {posts[0],posts[3]};
        Post[] actual = forum.getPostsByAuthor("David");
        Arrays.sort(actual,comparator);
        assertArrayEquals(expected,actual);
        posts[5].setDate(LocalDateTime.of(2024, Month.OCTOBER,6,0,0));
        forum.addPost(posts[5]);
        actual = forum.getPostsByAuthor("David", LocalDate.of(2024,Month.OCTOBER,7),LocalDate.now());
        Arrays.sort(actual,comparator);
        assertArrayEquals(expected,actual);
    }

    @Test
    void testSize() {
        assertEquals(5,forum.size());
    }
}