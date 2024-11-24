package com.test.task.controller;

import com.test.task.model.CriteriaModel;
import com.test.task.model.dto.CommentDto;
import com.test.task.model.dto.InvalidRequestDataDto;
import com.test.task.model.dto.TaskDto;
import com.test.task.model.enums.TaskStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Сервис управления задачами")
public interface TaskController {

    @GetMapping("/{taskId}")
    @Operation(summary = "Получение задачи по Id", description = """
            Достает из БД задачу по переданному id.
            В случае успеха в теле ответа возвращается встреча TaskDto и код 200,
            в противном случае в теле возвращается описание ошибки и код 400.
            Если задачу пытается получить неавторизованный пользователь возвращается код 403
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача получена"),
            @ApiResponse(responseCode = "400", description = "Ошибка получения задачи", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Попытка получения задачи неавторизованным пользователем",
            content = @Content)
    })
    ResponseEntity<TaskDto> getTask(@PathVariable("taskId") long taskId);

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создание задачи", description = """
            Задачу может создать только авторизованный пользователь с ролью ADMIN.
            В теле запроса передается задача TaskDto.
            Полученная задача проходит валидацию.
            В случае успешной проверки задаче присваивается статус в ожидании (PENDING),
            полю автора задачи присваивается текущий авторизованный пользователь.
            Задача сохраняется в БД.
            В случае успешного создания задачи возвращается код 200,
            в противном случае в теле ответа возвращается описание ошибки и код 400.
            Если задачу пытается создать неавторизованный пользователь возвращается код 403
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка создания задачи", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Попытка получения задачи неавторизованным пользователем",
                    content = @Content)
    })
    void createTask(@Valid @RequestBody TaskDto taskDto);

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создание задачи", description = """
            Задачу может удалить только авторизованный пользователь с ролью ADMIN.
            В переменной пути передается id задачи, если задача существует она удалятся из БД.
            В случае успешного создания задачи возвращается код 200,
            в противном случае в теле ответа возвращается описание ошибки и код 400.
            Если задачу пытается удалить неавторизованный пользователь возвращается код 403
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "400", description = "Ошибка удаления задачи", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Попытка удаления задачи неавторизованным пользователем",
                    content = @Content)
    })
    void deleteTask(@PathVariable("taskId") long taskId);

    @PatchMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Обновление задачи", description = """
            Задачу может обновить только авторизованный пользователь с ролью ADMIN.
            В теле запроса передается задача TaskDto.
            Полученная задача проходит валидацию.
            В случае успешной проверки задача обновляется в БД и возвращается код 200,
            в противном случае в теле ответа возвращается описание ошибки и код 400.
            Если задачу пытается обновить неавторизованный пользователь возвращается код 403
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка обновления задачи", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Попытка обновления задачи неавторизованным пользователем",
                    content = @Content)
    })
    void updateTask(@Valid @RequestBody TaskDto taskDto);

    @PatchMapping("/{taskId}/status")
    @Operation(summary = "Обновление статуса задачи", description = """
            Задачу может обновить только авторизованный пользователь с ролью ADMIN или пользователь назначенный
            исполнителем задачи.
            В переменной пути передается id задачи, в параметре запроса новый статус.
            После успешной проверки пользователя статус задачи обновляется в БД и возвращается код 200,
            в противном случае в теле ответа возвращается описание ошибки и код 400.
            Если статус задачи пытается обновить неавторизованный пользователь возвращается код 403
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус задачи успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка обновления статуса задачи", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Попытка обновления статуса задачи неавторизованным пользователем",
                    content = @Content)
    })
    void changeTaskStatus(@RequestParam("status") TaskStatus status, @PathVariable("taskId") long taskId);

    @PostMapping("/{taskId}/comment")
    @Operation(summary = "Добавление комментария к задаче", description = """
            Комментарий к задаче может добавить любой авторизованный пользователь.
            В переменной пути передается id задачи, в теле запроса передается комментарий CommentDto.
            Полученный комментарий проходит валидацию.
            После успешной проверки полю автор комментария присваивается текущий авторизованный пользователь,
            комментарий сохраняется в БД. Комментарий добавляется к задаче, задача обновляется в БД.
            В случае успешного добавления комментария возвращается код 200,
            в противном случае в теле ответа возвращается описание ошибки и код 400.
            Если комментарий пытается добавить неавторизованный пользователь возвращается код 403
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка добавления комментария", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Попытка добавления комментария неавторизованным пользователем",
                    content = @Content)
    })
    void addComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("taskId") long taskId);

    @GetMapping("/")
    @Operation(summary = "Получение всех задач", description = """
            Достает из БД все задачи. Поддерживается пагинация, для этого в параметрах запроса необходимо 
            передать параметры page и size (по умолчанию 0 и 20). Также поддерживается фильтрация запроса,
            для этого в случае необходимости в теле запроса передается критерий запроса CriteriaModel.
            В случае успеха в теле ответа возвращается список встреч и код 200,
            в противном случае в теле возвращается описание ошибки и код 400.
            Если задачи пытается получить неавторизованный пользователь возвращается код 403
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи получена"),
            @ApiResponse(responseCode = "400", description = "Ошибка получения задач", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Попытка получения задач неавторизованным пользователем",
                    content = @Content)
    })
    ResponseEntity<List<TaskDto>> getTasks(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size,
                                           @RequestBody(required = false) CriteriaModel criteriaModel);

    @PatchMapping("/{taskId}/performer")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Назначение исполнителя задаче", description = """
            Назначить исполнителя может только авторизованный пользователь с ролью ADMIN.
            В переменной пути передается id задачи, в параметрах запроса передается имя пользователя исполнителя.
            По переданному имени из БД достается пользователь и устанавливается в поле исполнитель задачи.
            Задаче присваивается статус в процессе(IN_PROGRESS).
            В случае успеха в теле ответа возвращается код 200,
            в противном случае в теле ответа возвращается описание ошибки и код 400.
            Если исполнителя пытается назначить неавторизованный пользователь возвращается код 403
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача получена"),
            @ApiResponse(responseCode = "400", description = "Ошибка получения задачи", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvalidRequestDataDto.class)) }),
            @ApiResponse(responseCode = "403", description = "Попытка получения задачи неавторизованным пользователем",
                    content = @Content)
    })
    void assignPerformer(@RequestParam("userName") String userName, @PathVariable("taskId") long taskId);
}
