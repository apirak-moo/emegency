package com.emegency.backend.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emegency.backend.dto.request.UserRequest;
import com.emegency.backend.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RequestMapping("/api/users")
@RestController
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
        summary = "ดึงข้อมูลผู้ใช้ทั้งหมด",
        description = "Endpoint นี้ใช้เพื่อดึงข้อมูลผู้ใช้ทั้งหมดในระบบ"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "สำเร็จ: คืนรายการผู้ใช้ (อาจเป็น [] ถ้าไม่มีข้อมูล)"),
        @ApiResponse(responseCode = "500", description = "เกิดข้อผิดพลาดภายในระบบ")
    })
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
        summary = "ดึงข้อมูลผู้ใช้ตาม ID",
        description = "Endpoint นี้ใช้เพื่อดึงข้อมูลผู้ใช้ตามรหัส UUID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "สำเร็จ: พบข้อมูลผู้ใช้"),
        @ApiResponse(responseCode = "404", description = "ไม่พบผู้ใช้"),
        @ApiResponse(responseCode = "500", description = "เกิดข้อผิดพลาดภายในระบบ")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(
        @Parameter(description = "UUID ของผู้ใช้", required = true)
        @PathVariable UUID id
    ) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(
        summary = "ลงทะเบียนผู้ใช้ใหม่",
        description = "Endpoint สำหรับสร้างผู้ใช้ใหม่โดยรับข้อมูลผ่าน request body"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "สร้างผู้ใช้สำเร็จ"),
        @ApiResponse(responseCode = "400", description = "ข้อมูลไม่ถูกต้อง"),
        @ApiResponse(responseCode = "500", description = "เกิดข้อผิดพลาดภายในระบบ")
    })
    @PostMapping
    public ResponseEntity<?> registerUser(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "ข้อมูลของผู้ใช้ที่ต้องการลงทะเบียน",
            required = true,
            content = @Content(schema = @Schema(implementation = UserRequest.class))
        )
        @Valid @RequestBody UserRequest request
    ) {
        userService.registerUser(request);
        return ResponseEntity.status(201).build();
    }

    @Operation(
        summary = "อัปเดตข้อมูลผู้ใช้",
        description = "Endpoint นี้ใช้สำหรับอัปเดตข้อมูลผู้ใช้ที่มีอยู่ตาม ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "อัปเดตข้อมูลผู้ใช้สำเร็จ"),
        @ApiResponse(responseCode = "400", description = "ข้อมูลไม่ถูกต้อง"),
        @ApiResponse(responseCode = "404", description = "ไม่พบผู้ใช้"),
        @ApiResponse(responseCode = "500", description = "เกิดข้อผิดพลาดภายในระบบ")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
        @Parameter(description = "UUID ของผู้ใช้", required = true) @PathVariable UUID id,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "ข้อมูลผู้ใช้ที่ต้องการอัปเดต",
            required = true,
            content = @Content(schema = @Schema(implementation = UserRequest.class))
        )
        @Valid @RequestBody UserRequest request
    ) {
        userService.updateUser(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "ลบผู้ใช้",
        description = "Endpoint สำหรับลบผู้ใช้งานออกจากระบบด้วย UUID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "ลบสำเร็จ"),
        @ApiResponse(responseCode = "404", description = "ไม่พบผู้ใช้"),
        @ApiResponse(responseCode = "500", description = "เกิดข้อผิดพลาดภายในระบบ")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
        @Parameter(description = "UUID ของผู้ใช้ที่ต้องการลบ", required = true)
        @PathVariable UUID id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
