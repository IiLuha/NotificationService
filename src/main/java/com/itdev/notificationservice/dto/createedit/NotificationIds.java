package com.itdev.notificationservice.dto.createedit;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record NotificationIds(

        @NotNull
        @NotEmpty
        List<@Positive Long> ids
) {
}
