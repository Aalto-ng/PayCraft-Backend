## Entity Relationship

1. **EmployerProfile to CompanyProfile**:
   - An `EmployerProfile` can be associated with multiple `CompanyProfile` entries.
   - **Relationship**: One-to-Many (One `EmployerProfile` → Many `CompanyProfiles`)

2. **CompanyProfile to EmployeeProfile**:
   - A `CompanyProfile` can be associated with multiple `EmployeeProfile` entries.
   - **Relationship**: One-to-Many (One `CompanyProfile` → Many `EmployeeProfiles`)

3. **EmployeeProfile to Payroll**:
   - An `EmployeeProfile` can have only one `Payroll` entry.
   - **Relationship**: One-to-One (One `EmployeeProfile` → One `Payroll`)

4. **Payroll to Payment**:
   - A `Payroll` entry can be associated with multiple `Payment` entries.
   - **Relationship**: One-to-Many (One `Payroll` → Many `Payments`)

5. **EmployerProfile to Notification**:
   - A `Notification` is associated with one `EmployerProfile`.
   - **Relationship**: Many-to-One (Many `Notifications` → One `EmployerProfile`)

### Summary of Relationships:
- **EmployerProfile** → **CompanyProfile**: One-to-Many
- **CompanyProfile** → **EmployeeProfile**: One-to-Many
- **EmployeeProfile** → **Payroll**: One-to-One
- **Payroll** → **Payment**: One-to-Many
- **Notification** → **EmployerProfile**: Many-to-One

### View the Data Schema [here](https://github.com/Aalto-ng/PayCraft-Backend/blob/database-schema-and-models/src/main/resources/data.sql)
