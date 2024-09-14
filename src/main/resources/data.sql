CREATE TABLE EmployerProfile (
    employerId INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(100) NOT NULL,
    lastName VARCHAR(100) NOT NULL,
    emailAddress VARCHAR(100) NOT NULL,
    phoneNumber VARCHAR(20) NOT NULL,
    personalAddress VARCHAR(255) NOT NULL,
    jobTitle VARCHAR(100) NOT NULL,
    BVN VARCHAR(11), -- optional
    password VARCHAR(255) NOT NULL
);

CREATE TABLE CompanyProfile (
    companyProfileId INT AUTO_INCREMENT PRIMARY KEY,
    companyName VARCHAR(255) NOT NULL,
    companySize ENUM('SMALL', 'MEDIUM', 'LARGE', 'ENTERPRISE'),
    officeAddress VARCHAR(255) NOT NULL,
    industryType VARCHAR(100) NOT NULL,
    emailAddress VARCHAR(100) NOT NULL,
    companyPhoneNumber VARCHAR(20) NOT NULL,
    employerId INT,
    FOREIGN KEY (employerId) REFERENCES EmployerProfile(employerId)
);

CREATE TABLE EmployeeProfile (
    employeeID INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    dateOfBirth DATE NOT NULL,
    address VARCHAR(255) NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE,
    jobTitle VARCHAR(255) NOT NULL,
    department VARCHAR(255),
    salaryType ENUM('HOURLY', 'MONTHLY', 'ANNUALLY'),
    salaryAmount DECIMAL(15, 2) NOT NULL,
    bankAccountDetails VARCHAR(255) NOT NULL,
    employmentStatus ENUM('ACTIVE', 'ON LEAVE', 'TERMINATED', 'RESIGNED', 'RETIRED', 'CONTRACT'),
    companyProfileId INT,
    FOREIGN KEY (companyProfileId) REFERENCES CompanyProfile(companyProfileId)
);

CREATE TABLE Payroll (
    payrollId INT AUTO_INCREMENT PRIMARY KEY,
    employeeId INT,
    payPeriodStart DATE,
    payPeriodEnd DATE,
    grossSalary DECIMAL(15, 2),
    netSalary DECIMAL(15, 2),
    paymentDate DATE,
    payStatus VARCHAR(50),
    FOREIGN KEY (employeeId) REFERENCES EmployeeProfile(employeeID)
);

CREATE TABLE Payment (
    paymentId INT AUTO_INCREMENT PRIMARY KEY,
    payrollId INT,
    paymentMethod VARCHAR(50),
    paymentDate DATE,
    transactionId VARCHAR(100),
    paymentStatus VARCHAR(50),
    FOREIGN KEY (payrollId) REFERENCES Payroll(payrollId)
);

CREATE TABLE Notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
    message TEXT,
    employerId INT,
    FOREIGN KEY (employerId) REFERENCES EmployerProfile(employerId)
);
