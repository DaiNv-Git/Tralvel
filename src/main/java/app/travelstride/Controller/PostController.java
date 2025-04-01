package app.travelstride.Controller;

import app.travelstride.Model.Jpa.PostRepository;
import app.travelstride.Model.Jpa.TypeRepository;
import app.travelstride.Model.Post;
import app.travelstride.Model.Type;
import app.travelstride.Model.dto.PostCreateRequest;
import app.travelstride.Model.dto.PostResponse;
import app.travelstride.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private  PostService postService;
    @Autowired
    private  PostRepository postRepository;

    @GetMapping("/{id}")
    public PostResponse getPostDetail(@PathVariable Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        return postService.mapToResponse(post);
    }


    @GetMapping
    public Page<PostResponse> searchPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String typeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return postService.searchPosts(keyword, typeId, PageRequest.of(page, size));
    }

    // ✅ Create
    @PostMapping("/create")
    public ResponseEntity<?> createPost(
            @RequestPart("data") PostCreateRequest postRequest,
            @RequestPart("cover") MultipartFile file) {

        try {
            // Tạo thư mục nếu chưa có
            String uploadDir = "/home/user/Travel/BE/images/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Lưu file ảnh
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            // Tạo URL cho ảnh
            String imageUrl = "/images/" + fileName;

            // Lưu Post vào DB
            Post post = new Post();
            post.setTitle(postRequest.getTitle());
            post.setContentHtml(postRequest.getContent());
            post.setTypes(postRequest.getTypes());
            post.setCoverImage(imageUrl);
            postRepository.save(post);

            return ResponseEntity.ok("Post created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create post");
        }
    }

    // ✅ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            // Cập nhật từng trường nếu có
            existingPost.setTitle(post.getTitle());
            existingPost.setContentHtml(post.getContentHtml());
            existingPost.setTypes(post.getTypes());
            if (post.getCoverImage() != null) {
                existingPost.setCoverImage(post.getCoverImage());
            }

            postRepository.save(existingPost);
            return ResponseEntity.ok(existingPost);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }
    }


    // ✅ Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postRepository.deleteById(id);
    }


}
