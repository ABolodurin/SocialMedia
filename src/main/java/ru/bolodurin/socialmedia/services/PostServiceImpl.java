package ru.bolodurin.socialmedia.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bolodurin.socialmedia.model.dto.PostRequest;
import ru.bolodurin.socialmedia.model.dto.PostResponse;
import ru.bolodurin.socialmedia.model.entities.Code;
import ru.bolodurin.socialmedia.model.entities.CommonException;
import ru.bolodurin.socialmedia.model.entities.Post;
import ru.bolodurin.socialmedia.model.entities.User;
import ru.bolodurin.socialmedia.model.mappers.PostResponseMapper;
import ru.bolodurin.socialmedia.repositories.PostRepository;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    public static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10, Sort.by("timestamp").descending());
    private final PostRepository postRepository;
    private final PostResponseMapper postResponseMapper;

    @Override
    public Page<PostResponse> create(User user, PostRequest post) {
        postRepository.save(new Post(post.getHeader(), post.getContent(), user));

        return findByUser(user);
    }

    @Override
    public Page<PostResponse> update(User user, PostResponse updatedPost) {
        Post post = findById(updatedPost.getId());

        if (!isOfACurrentUser(user, post)) throw CommonException
                .builder()
                .code(Code.UPDATE_NON_OWN_ENTITY_ERROR)
                .message("You can't update post which is not yours. PostID: " + post.getId())
                .build();

        post.setHeader(updatedPost.getHeader());
        post.setContent(updatedPost.getContent());
        postRepository.save(post);

        return findByUser(user);
    }

    @Override
    public Page<PostResponse> delete(User user, PostResponse postResponse) {
        Post post = findById(postResponse.getId());

        if (!isOfACurrentUser(user, post)) throw CommonException
                .builder()
                .code(Code.UPDATE_NON_OWN_ENTITY_ERROR)
                .message("You can't delete post which is not yours. PostID: " + post.getId())
                .build();

        postRepository.deleteById(post.getId());

        return findByUser(user);
    }

    private Post findById(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> CommonException
                        .builder()
                        .code(Code.NOT_FOUND)
                        .message("Post not found. PostID: " + id)
                        .build());
    }

    @Override
    public Page<PostResponse> findByUser(User user) {
        return postRepository
                .findAllByUserOrderByTimestampDesc(user, DEFAULT_PAGEABLE)
                .orElseThrow(() -> CommonException
                        .builder()
                        .code(Code.NOT_FOUND)
                        .message("No posts found for the user " + user.getUsername())
                        .build())
                .map(postResponseMapper);
    }

    @Override
    public PostResponse show(Long id) {
        return postResponseMapper.apply(findById(id));
    }


    private boolean isOfACurrentUser(User user, Post post) {
        return post.getUser().getUsername().equals(user.getUsername());
    }

}
