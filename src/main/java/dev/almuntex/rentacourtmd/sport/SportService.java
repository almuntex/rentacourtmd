package dev.almuntex.rentacourtmd.sport;

import dev.almuntex.rentacourtmd.exception.ResourceAlreadyExistsException;
import dev.almuntex.rentacourtmd.exception.ResourceNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportService {

    private static final String SPORT_NAME_UNIQUE_CONSTRAINT = "sport_name_unique_idx";

    private final SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public SportDto create(CreateUpdateSportRequest req) {
        Sport newSport = new Sport(req.name());
        try {
            Sport saved = sportRepository.save(newSport);
            return toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw mapDataIntegrityViolationException(ex, req.name());
        }
    }

    public SportDto get(int id) {
        Sport sport = sportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No sport found with id=" + id)
        );

        return toDto(sport);
    }

    public List<SportDto> getAll() {
        return sportRepository.findAll().stream().map(this::toDto).toList();
    }

    public SportDto update(int id, CreateUpdateSportRequest req) {
        Sport sport = sportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No sport found with id=" + id)
        );

        sport.setName(req.name());

        try {
            Sport saved = sportRepository.save(sport);
            return toDto(saved);
        } catch (DataIntegrityViolationException ex) {
            throw mapDataIntegrityViolationException(ex, req.name());
        }
    }

    public void delete(int id) {
        Sport sport = sportRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No sport found with id=" + id)
        );

        sportRepository.deleteById(sport.getId());
    }

    private SportDto toDto(Sport sport) {
        return new SportDto(sport.getId(), sport.getName());
    }

    private RuntimeException mapDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                String sportName) {
        Throwable cause = ex.getCause();

        if (cause instanceof ConstraintViolationException cve) {
            if (SPORT_NAME_UNIQUE_CONSTRAINT.equals(cve.getConstraintName())) {
                return new ResourceAlreadyExistsException("A sport named \"" + sportName + "\" already exists. " +
                        "Sport names must be unique, ignoring case.");
            }
        }

        return new RuntimeException("Couldn't save \"" + sportName + "\" to the database.", ex);
    }
}
