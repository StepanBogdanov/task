package com.test.task.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Описание ошибки запроса")
public class InvalidRequestDataDto {

    @Schema(example = "Пользователь не найден")
    private String info;
}
