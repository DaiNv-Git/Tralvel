package app.travelstride.Controller;

import app.travelstride.Model.Jpa.PostRepository;
import app.travelstride.Model.Post;
import app.travelstride.Model.dto.PostRequest;
import app.travelstride.Model.dto.PostResponse;
import app.travelstride.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

  
    @PostMapping("/create")
    public ResponseEntity<?> createPost(
            @RequestPart("post")  PostRequest request,  
            @RequestPart("cover") MultipartFile file  
    ) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Cover image is required");
            }
            
            String uploadDir = "/home/user/Travel/BE/images/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            String imageUrl = "/images/" + fileName;

            // Lưu bài viết
            Post post = new Post();
            post.setTitle(request.getTitle());
            post.setContentHtml(request.getContent());
            post.setTypes(request.getTypes());
            post.setCoverImage(imageUrl);
            post.setIsShow(request.getShow());
            postRepository.save(post);

            return ResponseEntity.ok("Post created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create post");
        }
    }
    // ✅ Update
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestPart("post") PostRequest request,
            @RequestPart(value = "cover", required = false) MultipartFile file 
    ) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        Post existingPost = optionalPost.get();

        // Cập nhật thông tin từ JSON
        existingPost.setTitle(request.getTitle());
        existingPost.setContentHtml(request.getContent());
        existingPost.setTypes(request.getTypes());
        existingPost.setIsShow(request.getShow());
        // Nếu có file ảnh mới, cập nhật
        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "/home/user/Travel/BE/images/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.write(filePath, file.getBytes());

                existingPost.setCoverImage("/images/" + fileName);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update image");
            }
        }

        postRepository.save(existingPost);
        return ResponseEntity.ok(existingPost);
    }


    // ✅ Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postRepository.deleteById(id);
    }
    
    @GetMapping("/popular-posts")
    public Page<PostResponse> getPopularPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return postService.getPopularPosts(PageRequest.of(page, size));
    }


}
