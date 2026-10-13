package dev.almuntex.rentacourtmd.sport;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sport")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @PostMapping
    public ResponseEntity<SportDto> create(@Valid @RequestBody CreateUpdateSportRequest request,
                                           UriComponentsBuilder uriBuilder) {
        SportDto created = sportService.create(request);

        URI location = uriBuilder
                .path("/api/v1/sport/{id}")
                .buildAndExpand(created.id())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SportDto> get(@PathVariable int id) {
        SportDto dto = sportService.get(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public List<SportDto> getAll() {
        return sportService.getAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SportDto> update(@PathVariable int id,
                                           @Valid @RequestBody CreateUpdateSportRequest request) {
        SportDto dto = sportService.update(id, request);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        sportService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
