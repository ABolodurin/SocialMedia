package ru.bolodurin.socialmedia.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.bolodurin.socialmedia.TestEntityFactory;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.mappers.PostResponseMapper;
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
    private final TestEntityFactory entityFactory = TestEntityFactory.get();
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void init() {
        postService = new PostServiceImpl(postRepository, new PostResponseMapper());
    }

    @Test
    void shouldCreatePost() {
        User current = entityFactory.getUser();
        Post expected = entityFactory.getPost();
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
        User user = entityFactory.getUser();
        Post expected = entityFactory.getPost();
        expected.setUser(user);

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
        User user = entityFactory.getUser();
        Post expected = entityFactory.getPost();
        expected.setUser(user);

        when(postRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));
        when(postRepository.findAllByUserOrderByTimestampDesc(any(), any()))
                .thenReturn(Optional.of(new PageImpl<>(List.of(expected))));

        postService.delete(
                user, PostResponse
                        .builder()
                        .id(expected.getId())
                        .header(expected.getHeader())
                        .content(expected.getContent())
                        .build());

        verify(postRepository).deleteById(expected.getId());
    }

    @Test
    void shouldFindByUser() {
        User expected = entityFactory.getUser();
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
        User user = entityFactory.getUser();
        Post post = entityFactory.getPost();
        post.setUser(user);
        Long expected = post.getId();

        when(postRepository.findById(expected)).thenReturn(Optional.of(post));

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
        User expected = entityFactory.getUser();

        assertThatThrownBy(() -> postService.findByUser(expected))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(expected.getUsername());
    }

    @Test
    void shouldThrowWhenUserIsNotOwner() {
        Post expected = entityFactory.getPost();
        User user = entityFactory.getUser();
        expected.setUser(user);

        when(postRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        assertThatThrownBy(() -> postService.update(
                new User(), PostResponse
                        .builder()
                        .id(expected.getId())
                        .header(expected.getHeader())
                        .content(expected.getContent())
                        .build()))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(String.valueOf(expected.getId()));

        assertThatThrownBy(() -> postService.delete(
                new User(), PostResponse
                        .builder()
                        .id(expected.getId())
                        .header(expected.getHeader())
                        .content(expected.getContent())
                        .build()))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(String.valueOf(expected.getId()));
    }

}
