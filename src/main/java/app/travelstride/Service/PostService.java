package app.travelstride.Service;

import app.travelstride.Model.Jpa.PostRepository;
import app.travelstride.Model.Jpa.TypeRepository;
import app.travelstride.Model.Post;
import app.travelstride.Model.Type;
import app.travelstride.Model.dto.PostResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TypeRepository typeRepository;

    public PostResponse mapToResponse(Post post) {
        PostResponse response = new PostResponse();
        BeanUtils.copyProperties(post, response);
        response.setShow(post.getIsShow());

        if (post.getTypes() != null && !post.getTypes().isEmpty()) {
            List<Long> typeIds = Arrays.stream(post.getTypes().split(","))
                    .map(String::trim)
                    .filter(s -> s.matches("\\d+"))
                    .map(Long::parseLong)
                    .toList();

            if (!typeIds.isEmpty()) {
                List<String> typeNames = typeRepository.findAllById(typeIds)
                        .stream()
                        .map(Type::getName)
                        .toList();
                response.setTypeNames(typeNames);
            }
        }

        return response;
    }

    public Page<PostResponse> searchPosts(String keyword, String typeId, Pageable pageable) {
        List<Post> posts = postRepository.findAll();

        // Filter theo keyword và typeId
        List<Post> filtered = posts.stream()
                .filter(post -> (keyword == null || post.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                        && (typeId == null || Arrays.asList(post.getTypes().split(",")).contains(typeId)))
                .toList();

        // Manual pagination
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        List<PostResponse> content = filtered.subList(start, end).stream().map(this::mapToResponse).toList();

        return new PageImpl<>(content, pageable, filtered.size());
    }
    public Page<PostResponse> getPopularPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findByIsShowTrue(pageable);

        List<PostResponse> content = postPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();

        return new PageImpl<>(content, pageable, postPage.getTotalElements());
    }

}

