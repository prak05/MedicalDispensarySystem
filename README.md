<div align="center">

<img src="https://www.google.com/search?q=https://komarev.com/ghpvc/%3Fusername%3Dyour-github-username%26color%3D4CAF50%26style%3Dflat-square%26label%3DProject%2BViews" alt="Project Views" />

🏥 Enterprise-Grade Desktop App for Prescription Management
Streamlining healthcare workflows with Java Swing, JDBC, and a robust MySQL backend

</div>

🎯 Project Overview
class MedicalDispensarySystem {
    public MedicalDispensarySystem() {
        this.name = "Medical Dispensary System";
        this.version = "1.0.0";
        this.database = "MySQL";
        this.guiFramework = "Java Swing";
        this.userRoles = new String[]{"Doctor", "Patient", "Dispensary"};
    }

    public Map<String, String> getKeyFeatures() {
        return Map.of(
            "🩺", "Role-Based Access Control",
            "✍️", "Seamless Prescription Creation",
            "📋", "Patient Medical History Viewer",
            "📦", "Real-Time Medicine Dispensing",
            "📊", "Inventory-Aware System"
        );
    }

    public String getImpact() {
        return "Optimizing prescription and dispensing workflows for medical professionals.";
    }
}

🚀 System Architecture
<div align="center">

graph TD
    A[👨‍⚕️ User] --> B{Login UI};
    B --> C[🩺 Doctor Dashboard];
    B --> D[🧍 Patient Dashboard];
    B --> E[📦 Dispensary Dashboard];

    subgraph "Core Functionality"
        C --> F[Create Prescription];
        D --> G[View History];
        E --> H[Dispense Medicine];
    end

    subgraph "Database Layer"
        F --> I[MySQL Database];
        G --> I;
        H --> I;
    end

    style A fill:#9C27B0,stroke:#7B1FA2,color:#fff
    style B fill:#FF9800,stroke:#F57C00,color:#fff
    style C fill:#2196F3,stroke:#1976D2,color:#fff
    style D fill:#2196F3,stroke:#1976D2,color:#fff
    style E fill:#2196F3,stroke:#1976D2,color:#fff
    style I fill:#4CAF50,stroke:#2E7D32,color:#fff

</div>

🌟 Features Showcase by Role
<div align="center">
<table>
<tr>
<td width="33%">

🩺 Doctor's Portal
➕ Create Prescriptions: Write new prescriptions for patients.

👥 Patient Selection: Easily search and select from a list of all patients.

💊 Medicine Inventory: View available medicines and current stock levels.

📝 Add Notes: Include specific dosage, instructions, and overall notes.

🔒 Secure Login: Dedicated and authenticated access.

</td>
<td width="33%">

🧍 Patient's Dashboard
📋 View History: Access a complete list of all past prescriptions.

🔍 Detailed View: Select a prescription to see all prescribed medicines, dosages, and notes.

status: Check Status: See if a prescription is PENDING or DISPENSED.

📅 Chronological Order: Prescriptions are sorted by date for easy tracking.

🔒 Secure Login: Private access to personal medical data.

</td>
<td width="33%">

📦 Dispensary Interface
⏳ Pending Queue: View all prescriptions waiting to be dispensed.

✅ Dispense Medicine: Mark prescriptions as DISPENSED with a single click.

🔄 Real-Time Updates: The system instantly reflects the new status for all roles.

🔎 Quick Look-Up: See patient and doctor details for each prescription.

🔒 Secure Login: Authorized access for pharmacy staff.

</td>
</tr>
</table>
</div>

🛠️ Technology Stack
<div align="center">

⚙️ Backend & Database
🎨 Frontend
🔧 Tools & Environment
</div>

🚀 Getting Started on Windows 11
Follow these instructions to get the project running on your local machine.

📋 Step-by-Step Installation
<details>
<summary><b>🔧 Click here for Detailed Setup Instructions</b></summary>

1. Prerequisites
Make sure you have the following software installed:

Java Development Kit (JDK): Version 11 or higher.

MySQL Server & MySQL Workbench: The community edition is free. You can download it from the official MySQL website.

Git Bash: To run the .sh scripts on Windows. Download it from git-scm.com.

2. Database Setup (Windows 11)
You need to create the database and import the schema with the sample data.

Method 1: Using MySQL Workbench (Recommended)

Open MySQL Workbench and connect to your local database server.

Click the "Create a new schema" icon in the top toolbar.

Name the schema medical_dispensary and click Apply.

In the top menu, go to File -> Open SQL Script...

Navigate to the project directory and select the db/schema.sql file.

Click the lightning bolt icon (Execute) in the toolbar to run the entire script. This will create all the tables and insert the sample data.

Method 2: Using the MySQL Command Line

Open the Command Prompt or PowerShell.

Log in to MySQL using the root user (you will be prompted for your password):

mysql -u root -p

Run the db/schema.sql script. You must provide the full path to the file.

SOURCE C:/path/to/your/project/MedicalDispensarySystem/db/schema.sql;

(Remember to replace the path with the actual path on your computer).

Type exit; to quit the MySQL client.

3. Application Configuration
Open the file src/com/mds/service/DatabaseManager.java.

Update the DB_USER and DB_PASSWORD variables with the username and password for your MySQL database.

private static final String DB_USER = "root";  // Change this if needed
private static final String DB_PASSWORD = "your_mysql_password"; // IMPORTANT: SET YOUR PASSWORD HERE

4. Compile and Run
Open Git Bash in the root directory of the project (MedicalDispensarySystem).

Run the compilation script:

./compile.sh

Once compilation is successful, run the application:

./run.sh

The application's welcome screen should now appear.

</details>

🔑 Login Credentials
Use the following sample credentials to log in. All users share the same password for simplicity.

Password for all users: password123

Role

Username

🩺 Doctor

dr_smith 
 dr_jones

🧍 Patient

patient_john 
 patient_mary 
 patient_robert

📦 Dispensary

pharmacy_admin

🗂️ Project Structure
MedicalDispensarySystem/
├── 📂 bin/                      # Compiled .class files (auto-generated)
├── 🗄️ db/
│   └── schema.sql            # Database schema and sample data
├── 📚 lib/
│   └── mysql-connector-j...  # MySQL JDBC driver
├── 📝 src/
│   └── com/mds/
│       ├── MainApp.java
│       ├── 📦 model/
│       ├── ⚙️ service/
│       └── 🖼️ view/
├── ▶️ compile.sh                # Compilation script
└── ▶️ run.sh                    # Execution script

🤝 Contributing
<div align="center">

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

def contribute():
    options = [
        "🐛 Reporting a bug",
        "💡 Suggesting an enhancement",
        "📝 Updating documentation",
        "🔧 Fixing an issue"
    ]
    return "Please fork the repo and create a pull request!"

