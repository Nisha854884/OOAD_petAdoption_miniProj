package com.petadoption.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AdminDTO {

    @NotBlank(message = "Department cannot be blank")
    @Size(min = 2, max = 100, message = "Department must be between 2 and 100 characters")
    private String department;

    @Size(max = 500, message = "Permissions description cannot exceed 500 characters")
    private String permissions;

    public AdminDTO() {}

    public AdminDTO(String department, String permissions) {
        this.department = department;
        this.permissions = permissions;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPermissions() { return permissions; }
    public void setPermissions(String permissions) { this.permissions = permissions; }
}
