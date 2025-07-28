package uz.dev.rentcar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "Audit Logs API", description = "APIs for retrieving audit log records")
@SecurityRequirement(name = "bearerAuth")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @Operation(summary = "Retrieve all audit logs", description = "Returns all audit logs in the system (Visible to Admin)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audit logs successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuditLogDTO.class),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": 1,
                                        "email": "admin@gmail.com",
                                        "action": "CREATE_APPOINTMENT",
                                        "entity": "Appointment",
                                        "entityId": 15,
                                        "timestamp": "2025-07-03T14:23:51"
                                      }
                                    ]
                                    """)))
    })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditLogDTO>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }

    @Operation(summary = "Retrieve audit logs by email", description = "Returns all audit logs for the given email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audit logs successfully retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuditLogDTO.class),
                            examples = @ExampleObject(value = """
                                    [
                                      {
                                        "id": 2,
                                        "email": "admin@gmail.com",
                                        "action": "CANCEL_APPOINTMENT",
                                        "entity": "Appointment",
                                        "entityId": 17,
                                        "timestamp": "2025-07-02T09:11:15"
                                      }
                                    ]
                                    """))),
            @ApiResponse(responseCode = "404", description = "email not found",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "No logs found for email: registrator@gmail.com")))
    })
    @GetMapping("/user/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditLogDTO>> getLogsByEmail(
            @Parameter(description = "email", example = "admin@gmail.com") @PathVariable String email) {

        return ResponseEntity.ok(auditLogService.getLogsByEmail(email));

    }
}