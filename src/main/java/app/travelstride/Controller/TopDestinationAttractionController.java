package app.travelstride.Controller;

import app.travelstride.Model.dto.TopAttractionDTO;
import app.travelstride.Model.dto.TopAttractionRequest;
import app.travelstride.Service.TopDestinationAttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/top-attractions")
public class TopDestinationAttractionController {

    @Autowired
    private TopDestinationAttractionService service;

    @GetMapping
    public Page<TopAttractionDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAll(pageable);
    }

    @GetMapping("/search-by-name")
    public Page<TopAttractionDTO> searchByDestinationName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.searchByDestinationName(name, pageable);
    }

    @PostMapping
    public TopAttractionDTO create(
           @RequestBody TopAttractionRequest topAttractionRequest) {
        return service.mapToDTO(service.create(topAttractionRequest.getDestinationId(), topAttractionRequest.getContent()));
    }

    @PutMapping("/{id}")
    public TopAttractionDTO updateAttraction(
            @PathVariable Long id,
            @RequestBody TopAttractionRequest topAttractionRequest) {
        return service.mapToDTO(service.update(id, topAttractionRequest.getContent(),topAttractionRequest.getDestinationId()));
    }

    @GetMapping("/by-destination")
    public TopAttractionDTO getByDestinationId(@RequestParam String destinationName) {
        return service.getByDestinationId(destinationName);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
