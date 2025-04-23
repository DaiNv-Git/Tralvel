package app.travelstride.Service;

import app.travelstride.Model.Destination;
import app.travelstride.Model.Jpa.DestinationRepository;
import app.travelstride.Model.Jpa.TopDestinationAttractionRepository;
import app.travelstride.Model.TopDestinationAttraction;
import app.travelstride.Model.dto.TopAttractionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopDestinationAttractionService {

    @Autowired
    private TopDestinationAttractionRepository repository;

    @Autowired
    private DestinationRepository destinationRepository;

    public Page<TopAttractionDTO> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToDTO);
    }

    public Page<TopAttractionDTO> searchByDestinationName(String name, Pageable pageable) {
        return repository.findByDestinationNameContaining(name, pageable).map(this::mapToDTO);
    }

    public TopDestinationAttraction create(Long destinationId, String content) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        TopDestinationAttraction tda = new TopDestinationAttraction();
        tda.setContent(content);
        tda.setDestination(destination);
        return repository.save(tda);
    }

    public TopDestinationAttraction update(Long id, String content) {
        TopDestinationAttraction tda = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Top attraction not found"));
        tda.setContent(content);
        return repository.save(tda);
    }

    public TopAttractionDTO getByDestinationId(String destinationId) {
        return repository.findByDestination_Destination(destinationId)
                .stream()
                .findFirst()
                .map(this::mapToDTO)
                .orElse(null); // Trả về null nếu không tìm thấy phần tử nào
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }

    public TopAttractionDTO mapToDTO(TopDestinationAttraction attraction) {
        TopAttractionDTO dto = new TopAttractionDTO();
        dto.setId(attraction.getId());
        dto.setContent(attraction.getContent());
        dto.setDestinationId(attraction.getDestination().getId());
        dto.setDestinationName(attraction.getDestination().getDestination());
        return dto;
    }
}
