package com.aalto.paycraft.controller;

import com.aalto.paycraft.dto.CompanyProfileDTO;
import com.aalto.paycraft.dto.DefaultApiResponse;
import com.aalto.paycraft.service.ICompanyProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyProfileController {
    private final ICompanyProfileService iCompanyProfileService;

    @PostMapping("/create/{employerProfileId}")
    public ResponseEntity<DefaultApiResponse<CompanyProfileDTO>> createCompanyProfile(@Valid @RequestBody CompanyProfileDTO companyProfileDTO, @PathVariable("employerProfileId") UUID employerProfileId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.createCompanyProfile(companyProfileDTO, employerProfileId));
    }

    @GetMapping("/{companyProfileId}")
    public ResponseEntity<DefaultApiResponse<CompanyProfileDTO>> getCompanyProfile(@Valid @PathVariable("companyProfileId") UUID companyProfileId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.getCompanyProfile(companyProfileId));
    }

    @GetMapping("/{employerProfileId}/employer")
    public ResponseEntity<DefaultApiResponse<List<CompanyProfileDTO>>> getCompaniesProfile(@Valid @PathVariable("employerProfileId") UUID employerProfileId,
                                                                                    @RequestParam(defaultValue = "0") Integer page,
                                                                                    @RequestParam(defaultValue = "10", name = "size") Integer pageSize){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.getCompanies(employerProfileId, page, pageSize));
    }

    @PutMapping("/{companyProfileId}")
    public ResponseEntity<DefaultApiResponse<CompanyProfileDTO>> updateCompanyProfile(@Valid @PathVariable("companyProfileId") UUID companyProfileId, @Valid @RequestBody CompanyProfileDTO companyProfileDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.updateCompanyProfile(companyProfileId, companyProfileDTO));
    }

    @DeleteMapping("/{companyProfileId}")
    public ResponseEntity<DefaultApiResponse<CompanyProfileDTO>> deleteCompanyProfile(@Valid @PathVariable("companyProfileId") UUID companyProfileId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(iCompanyProfileService.deleteCompanyProfile(companyProfileId));
    }
}

/*todo:
vTo start building the MVP payroll application, the following endpoints, functions, and features should be considered. The focus will be on clean error handling, frontend integration, and a smooth user experience.

### Endpoints

1. **Employer Management**
   - **POST /employers**: Create a new employer profile.
   - **GET /employers**: List all employers.
   - **GET /employers/{id}**: Fetch a specific employer's details.
   - **PUT /employers/{id}**: Update employer profile.
   - **DELETE /employers/{id}**: Remove an employer.

2. **Company Management**
   - **POST /companies**: Create a new company profile under an employer.
   - **GET /companies**: List all companies.
   - **GET /companies/{id}**: Fetch specific company profile.
   - **PUT /companies/{id}**: Update company profile.
   - **DELETE /companies/{id}**: Remove a company.

3. **Employee Management**
   - **POST /employees**: Add a new employee to a company.
   - **GET /employees**: List all employees.
   - **GET /employees/{id}**: Fetch employee details.
   - **PUT /employees/{id}**: Update employee information.
   - **DELETE /employees/{id}**: Remove an employee.

4. **Payroll Management**
   - **POST /payrolls**: Create payroll for an employee.
   - **GET /payrolls**: List all payrolls.
   - **GET /payrolls/{id}**: Get payroll details by ID.
   - **PUT /payrolls/{id}**: Update payroll information.
   - **DELETE /payrolls/{id}**: Delete payroll entry.

5. **Payment Management**
   - **POST /payments**: Process payroll payments.
   - **GET /payments**: List all payments.
   - **GET /payments/{id}**: Get details of a specific payment.
   - **PUT /payments/{id}**: Update payment details.
   - **DELETE /payments/{id}**: Cancel a payment.

6. **Notification Management**
   - **POST /notifications**: Send notification to employer.
   - **GET /notifications**: Get all notifications.
   - **GET /notifications/{id}**: Get notification details.
   - **DELETE /notifications/{id}**: Delete notification.

### Functions

1. **Authentication & Authorization**:
   - Secure API endpoints with JWT-based authentication.
   - Role-based access control (admin/employer/employee).

2. **Data Validation**:
   - Use validation libraries to ensure inputs are correct (e.g., email, phone number).
   - Verify BVN (Bank Verification Number) if required.

3. **Error Handling**:
   - Graceful error responses with HTTP status codes.
   - Custom error messages for invalid input, not found, and unauthorized actions.
   - Consistent error format to provide clear feedback to frontend.

4. **Pagination & Filtering**:
   - Implement pagination for listing endpoints (employees, employers, payrolls, payments).
   - Add filtering options (e.g., filter employees by department, payrolls by date).

5. **Batch Operations**:
   - Allow batch payroll processing for better efficiency.
   - Bulk employee upload via CSV or JSON format.

6. **Notifications**:
   - Automated notifications for payroll generation, payment processing, or errors.
   - Cron jobs for periodic tasks like sending payroll reminders.

### Features

1. **Frontend Integration**:
   - JSON-based API for easy integration with frontend frameworks (e.g., React).
   - CORS support for cross-origin requests.
   - Descriptive and human-readable error messages to improve user interaction.

2. **Search**:
   - Implement search functionality for employees, payrolls, and payments.

3. **User Experience**:
   - Meaningful and actionable response messages.
   - Provide instant feedback on long-running tasks (e.g., payroll generation).
   - Support for real-time notifications via WebSockets or polling.

By following these guidelines, you can begin implementing endpoints and backend logic while ensuring the system is efficient, maintainable, and user-friendly.
* */