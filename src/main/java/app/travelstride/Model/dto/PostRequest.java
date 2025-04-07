package app.travelstride.Model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PostRequest {
    @NotBlank
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    
    private String content;

    @NotBlank
    @Size(max = 100, message = "Types cannot exceed 100 characters")
    private String types;

    private Boolean isShow;

    public Boolean isShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public PostRequest() {
    }

    public PostRequest(String title, String content, String types) {
        this.title = title;
        this.content = content;
        this.types = types;
    }
}
