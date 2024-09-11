### **System Design for PayCraft**

**PayCraft** is a payroll SaaS that operates both offline (via USSD) and online (via Progressive Web App). It is designed to seamlessly handle automatic employee salary disbursements, either at set intervals or on demand, without overhead. PayCraft targets modern small and medium-sized businesses that value fast growth and automation.

### **Core Components**
- **Employee Management**: Allows companies to store employee profiles and create new company profiles.
- **Salary Calculation Engine**: Evaluates the company wallet balance, compares it with the total employee salary, and disburses payments if criteria are met.
- **Automation Engine**: Runs jobs at intervals and triggers disbursements based on company configurations.
- **Payment Gateway**: Integrates with Kora Pay for bulk transactions, virtual wallets, and single payment transactions.
- **USSD Integration**: Supports offline transactions.
- **Compliance and Reporting**: Handles tax compliance and generates payroll reports.

### **Backend Services**
- **Employee Service**: Manages employee data and CRUD operations.
- **Payroll Calculation Service**: Processes salaries based on employee data.
- **Payment Service**: Integrates with Kora for payment processing.
- **Notification Service**: Handles notifications.
- **Email Service**: Manages email communications.
- **Payslip Generation Service**: Generates payment slips in PDF and CSV formats, including a PRN (PayCraft Retrieval Number).

### **Backend Tools (MVP)**
- Java 17
- Spring Boot
- Spring JPA
- Spring Security (JWT for authentication)
- MySQL Database
- Messaging Broker (Kafka)
- Caching (Redis)

### **Future Releases**
- **Role-Based Access Control**: Introduce multiple access levels (e.g., Admin, HR). Currently, PayCraft supports only Admin (Employer) access.
- **Multi-Currency Support**: Currently supports Naira (NGN). Future releases will introduce support for other currencies, pending integration with Kora.

### **Entities**
- **CompanyProfile**: `companyName`, `companySize`, `officeAddress`, `industryType`, `emailAddress`, `companyPhoneNumber`
- **EmployerProfile**: `firstName`, `lastName`, `emailAddress`, `phoneNumber`, `personalAddress`, `jobTitle`, `BVN`, `password`
- **EmployeeProfile**: `employeeID`, `firstName`, `lastName`, `dateOfBirth`, `address`, `startDate`, `endDate`, `jobTitle`, `department`, `salaryType`, `salaryAmount`, `bankAccountDetails`, `employmentStatus`
- **Payroll**: 
   - `payrollId`: Unique identifier
   - `employeeId`: Foreign key linking to the Employee entity
   - `payPeriodStart`: Start date of the pay period
   - `payPeriodEnd`: End date of the pay period
   - `grossSalary`: Total salary before deductions
   - `netSalary`: Salary after deductions
   - `paymentDate`: Date of salary disbursement
   - `payStatus`: Status (Pending, Paid, Failed)
- **Payment**: 
   - `paymentId`: Unique identifier
   - `payrollId`: Foreign key linking to Payroll
   - `paymentMethod`: Bank transfer, cheque, etc.
   - `paymentDate`: Date of payment processing
   - `transactionId`: Reference ID for the bank transaction
   - `paymentStatus`: Status (Successful, Pending, Failed)
- **Notification**: `id`, `date`, `message`
