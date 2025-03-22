package app.travelstride.Controller;

import app.travelstride.Model.TripRequest;
import app.travelstride.Service.TripRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/request")
public class TripRequestController {
    @Autowired
    private TripRequestService service;

    // Thêm mới
    @PostMapping
    public ResponseEntity<TripRequest> create(@RequestBody TripRequest trip) {
        return ResponseEntity.ok(service.create(trip));
    }

    // Sửa
    @PutMapping("/{id}")
    public ResponseEntity<TripRequest> update(@PathVariable Long id, @RequestBody TripRequest trip) {
        return ResponseEntity.ok(service.update(id, trip));
    }

    // Xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Lấy chi tiết 1 bản ghi
    @GetMapping("/{id}")
    public ResponseEntity<TripRequest> get(@PathVariable Long id) {
        return service.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    
    @GetMapping
    public ResponseEntity<Page<TripRequest>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<TripRequest> result = service.search(name, phone, from, to, pageable);
        return ResponseEntity.ok(result);
    }
}
