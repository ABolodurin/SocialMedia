package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.bolodurin.socialmedia.entities.CommonException;
import ru.bolodurin.socialmedia.entities.Post;
import ru.bolodurin.socialmedia.entities.PostRequest;
import ru.bolodurin.socialmedia.entities.PostResponse;
import ru.bolodurin.socialmedia.entities.PostResponseMapper;
import ru.bolodurin.socialmedia.entities.User;
import ru.bolodurin.socialmedia.repositories.PostRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    private static Post post;
    private static User user;
    @Mock
    private PostRepository postRepository;
    private PostService postService;

    @BeforeEach
    void init() {
        postService = new PostServiceImpl(postRepository, new PostResponseMapper());

        user = User
                .builder()
                .username("username")
                .build();
        post = new Post("header", "content", null);
    }

    @Test
    void shouldCreatePost() {
        User current = user;
        Post expected = post;
        PostRequest postRequest = PostRequest
                .builder()
                .header(expected.getHeader())
                .content(expected.getContent())
                .build();
        expected.setUser(current);

        when(postRepository.findAllByUserOrderByTimestampDesc(any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(expected))));

        postService.create(current, postRequest);

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(postRepository).save(postArgumentCaptor.capture());

        Post actual = postArgumentCaptor.getValue();

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
        assertThat(actual.getUser()).isEqualTo(expected.getUser());
    }

    @Test
    void shouldUpdatePost() {
        Post expected = post;
        post.setUser(user);

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(new Post("", "", user)));
        when(postRepository.findAllByUserOrderByTimestampDesc(any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(expected))));

        postService.update(
                user, PostResponse
                        .builder()
                        .id(1L)
                        .header(expected.getHeader())
                        .content(expected.getContent())
                        .build());

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);

        verify(postRepository).save(postArgumentCaptor.capture());

        Post actual = postArgumentCaptor.getValue();

        assertThat(actual.getHeader()).isEqualTo(expected.getHeader());
        assertThat(actual.getContent()).isEqualTo(expected.getContent());
        assertThat(actual.getUser()).isEqualTo(expected.getUser());
    }

    @Test
    void shouldDeletePost() {
        Post expected = post;
        post.setUser(user);

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(new Post("", "", user)));
        when(postRepository.findAllByUserOrderByTimestampDesc(any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(expected))));

        postService.delete(
                user, PostResponse
                        .builder()
                        .id(1L)
                        .header(expected.getHeader())
                        .content(expected.getContent())
                        .build());

        verify(postRepository).deleteById(expected.getId());
    }

    @Test
    void shouldFindByUser() {
        User expected = user;
        when(postRepository.findAllByUserOrderByTimestampDesc(any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(new Post()))));

        postService.findByUser(expected);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(postRepository).findAllByUserOrderByTimestampDesc(
                userArgumentCaptor.capture(), any());
        assertThat(expected).usingRecursiveComparison().isEqualTo(userArgumentCaptor.getValue());
    }

    @Test
    void shouldShowPost() {
        Long expected = 1L;
        post.setUser(user);

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(post));

        postService.show(expected);

        verify(postRepository).findById(expected);
    }

    @Test
    void shouldThrowWhenPostNotFound() {
        Long expected = 1L;

        assertThatThrownBy(() -> postService.show(expected))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(String.valueOf(expected));
    }

    @Test
    void shouldThrowWhenUserHasNoPosts() {
        String expected = user.getUsername();

        assertThatThrownBy(() -> postService.findByUser(user))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(expected);
    }

    @Test
    void shouldThrowWhenUserIsNotOwner() {
        Post expected = post;
        post.setUser(user);

        when(postRepository.findById(1L))
                .thenReturn(Optional.of(new Post("", "", user)));

        assertThatThrownBy(() -> postService.update(
                new User(), PostResponse
                        .builder()
                        .id(1L)
                        .header(expected.getHeader())
                        .content(expected.getContent())
                        .build()))
                .isInstanceOf(CommonException.class);

        assertThatThrownBy(() -> postService.delete(
                new User(), PostResponse
                        .builder()
                        .id(1L)
                        .header(expected.getHeader())
                        .content(expected.getContent())
                        .build()))
                .isInstanceOf(CommonException.class);
    }

}
