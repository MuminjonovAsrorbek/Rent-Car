package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.rentcar.payload.AuditLogDTO;
import uz.dev.rentcar.service.template.AuditLogService;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Logs API", description = "API for managing audit logs")
@SecurityRequirement(name = "bearerAuth")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get all audit logs",
            description = "Retrieve a list of all audit logs in the system.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved audit logs",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuditLogDTO.class),
                                    examples = @ExampleObject(
                                            name = "AuditLogExample",
                                            value = "[{\"id\": 1, \"action\": \"CREATE\", \"entity\": \"User\", \"timestamp\": \"2023-10-01T12:00:00Z\"}]"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Access denied")
            }
    )
    public List<AuditLogDTO> getAllLogs() {

        return auditLogService.getAllLogs();

    }

    @GetMapping("/user/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Get audit logs by user email",
            description = "Retrieve a list of audit logs associated with a specific user's email.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved audit logs for the user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuditLogDTO.class),
                                    examples = @ExampleObject(
                                            name = "AuditLogByEmailExample",
                                            value = "[{\"id\": 1, \"action\": \"UPDATE\", \"entity\": \"Car\", \"timestamp\": \"2023-10-01T12:00:00Z\"}]"
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "403", description = "Forbidden - Access denied"),
                    @ApiResponse(responseCode = "404", description = "Not Found - No logs found for the given email")
            }
    )
    public List<AuditLogDTO> getLogsByEmail(
            @Parameter(description = "email", example = "admin@gmail.com") @PathVariable String email) {

        return auditLogService.getLogsByEmail(email);

    }
}