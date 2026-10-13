package dev.almuntex.rentacourtmd.sport;

import jakarta.validation.constraints.NotBlank;

public record CreateUpdateSportRequest(@NotBlank String name) {

    public CreateUpdateSportRequest {
        if (name != null) {
            name = name.trim();
        }
    }
}
