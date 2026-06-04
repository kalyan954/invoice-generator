# Invoice Generator Application

A full-stack web application for creating, managing, and generating PDF invoices. Built with Spring Boot backend, React frontend, and PostgreSQL database.

## 🎯 Features

- **Create Invoices**: Add customer information and line items with automatic calculations
- **View Invoices**: Display all invoices in a clean, organized table format
- **Invoice Details**: View complete invoice information including subtotal, tax, and total amount
- **PDF Download**: Generate and download invoices as PDF files
- **Real-time Calculations**: Automatic tax calculation (18%) and total amount computation
- **Responsive Design**: Works seamlessly on desktop and mobile devices
- **Data Validation**: Input validation on both frontend and backend

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                     Frontend (React + Vite)             │
│                     Port: 5173                          │
└────────────────────┬────────────────────────────────────┘
                     │ HTTP/REST API
┌────────────────────▼────────────────────────────────────┐
│                Backend (Spring Boot)                    │
│                Port: 8080                               │
│    ┌────────────────────────────────────────────────┐   │
│    │  Controllers → Services → Repository → Entity  │   │
│    └────────────────┬───────────────────────────────┘   │
└────────────────────┬────────────────────────────────────┘
                     │ JDBC
┌────────────────────▼────────────────────────────────────┐
│              PostgreSQL Database                        │
│              Port: 5432                                 │
└─────────────────────────────────────────────────────────┘
```

## 🛠️ Tech Stack

### Backend
- **Java 17** - Programming language
- **Spring Boot 4.0.6** - Web framework
- **Spring Data JPA** - ORM and database abstraction
- **PostgreSQL** - Relational database
- **Lombok** - Boilerplate code generation
- **Jakarta Validation** - Input validation
- **OpenPDF** - PDF generation
- **Maven** - Dependency management & build tool

### Frontend
- **React 18** - UI framework
- **Vite** - Build tool & dev server
- **Axios** - HTTP client
- **Tailwind CSS** - Styling

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration

## 📋 Prerequisites

### For Local Development
- Java 17 JDK
- Node.js 20+
- PostgreSQL 13+ (or use Docker)
- Maven 3.8+

### For Docker Setup
- Docker Desktop or Docker Engine
- Docker Compose

## 🚀 Quick Start

### Option 1: Docker Compose (Recommended)

```bash
# Clone the repository
git clone <repository-url>
cd invoice-generator

# Build and start all services
docker-compose up --build

# Access the application
# Frontend: http://localhost:5173
# Backend API: http://localhost:8080/api
# Database: localhost:5432
```

### Option 2: Local Development

#### Backend Setup

```bash
# Navigate to backend directory
cd invoice-generator

# Set environment variables
export DB_URL=jdbc:postgresql://localhost:5432/invoice_generator
export DB_USERNAME=postgres
export DB_PASSWORD=your_password

# Build the application
./mvnw clean package -DskipTests

# Run the application
./mvnw spring-boot:run
```

#### Frontend Setup

```bash
# Navigate to frontend directory
cd invoice-generator-ui

# Install dependencies
npm install

# Start development server
npm run dev

# Application will be available at http://localhost:5173
```

## 📚 API Endpoints

### Create Invoice
```http
POST /api/invoices
Content-Type: application/json

{
  "customerName": "John Doe",
  "items": [
    {
      "productName": "Web Design",
      "quantity": 1,
      "price": 5000.00
    },
    {
      "productName": "Development",
      "quantity": 40,
      "price": 150.00
    }
  ]
}

Response: 201 Created
{
  "id": 1,
  "invoiceNumber": "INV-1001",
  "customerName": "John Doe",
  "subtotal": 11000.00,
  "tax": 1980.00,
  "total": 12980.00,
  "createdAt": "2026-06-04T12:30:00",
  "items": [...]
}
```

### Get All Invoices
```http
GET /api/invoices

Response: 200 OK
[
  {
    "id": 1,
    "invoiceNumber": "INV-1001",
    "customerName": "John Doe",
    "subtotal": 11000.00,
    "tax": 1980.00,
    "total": 12980.00,
    "createdAt": "2026-06-04T12:30:00",
    "items": [...]
  }
]
```

### Get Invoice by ID
```http
GET /api/invoices/{id}

Response: 200 OK
{
  "id": 1,
  "invoiceNumber": "INV-1001",
  "customerName": "John Doe",
  "subtotal": 11000.00,
  "tax": 1980.00,
  "total": 12980.00,
  "createdAt": "2026-06-04T12:30:00",
  "items": [...]
}
```

### Download Invoice PDF
```http
GET /api/invoices/{id}/pdf

Response: 200 OK (binary PDF file)
Content-Disposition: attachment; filename=INV-1001.pdf
```

## 📁 Project Structure

```
invoice-generator/
├── src/
│   ├── main/
│   │   ├── java/com/kalyan/invoice_generator/
│   │   │   ├── controller/           # REST API endpoints
│   │   │   ├── service/              # Business logic
│   │   │   ├── repository/           # Data access layer
│   │   │   ├── entity/               # JPA entities
│   │   │   ├── dto/                  # Data transfer objects
│   │   │   ├── exception/            # Custom exceptions
│   │   │   └── pdf/                  # PDF generation
│   │   └── resources/
│   │       └── application.properties # Configuration
│   └── test/
│       └── java/                     # Unit tests
├── Dockerfile                        # Backend container image
├── docker-compose.yml               # Multi-container setup
├── pom.xml                          # Maven configuration
│
├── invoice-generator-ui/            # React Frontend
│   ├── src/
│   │   ├── components/              # React components
│   │   ├── pages/                   # Page components
│   │   ├── services/                # API service calls
│   │   └── App.jsx                  # Main app component
│   ├── Dockerfile                   # Frontend container image
│   ├── package.json                 # Dependencies
│   └── vite.config.js              # Vite configuration
│
└── README.md                        # This file
```

## 🗄️ Database Schema

### Invoices Table
```sql
CREATE TABLE invoices (
  id BIGSERIAL PRIMARY KEY,
  invoice_number VARCHAR(50) UNIQUE NOT NULL,
  customer_name VARCHAR(255) NOT NULL,
  subtotal DECIMAL(19,2),
  tax DECIMAL(19,2),
  total DECIMAL(19,2),
  created_at TIMESTAMP
);
```

### Invoice Items Table
```sql
CREATE TABLE invoice_items (
  id BIGSERIAL PRIMARY KEY,
  product_name VARCHAR(255) NOT NULL,
  quantity INTEGER,
  price DECIMAL(19,2),
  amount DECIMAL(19,2),
  invoice_id BIGINT NOT NULL REFERENCES invoices(id) ON DELETE CASCADE
);
```

## 🧪 Testing

### Run Backend Tests
```bash
# All tests
./mvnw test

# Specific test class
./mvnw test -Dtest=InvoiceGeneratorApplicationTests

# With coverage
./mvnw clean test jacoco:report
```

### Run Frontend Tests
```bash
cd invoice-generator-ui
npm run test
```

## 🐛 Troubleshooting

### Port Already in Use
```bash
# Kill process using port 8080
lsof -ti:8080 | xargs kill -9

# Or change port in application.properties
server.port=8081
```

### Database Connection Issues
- Ensure PostgreSQL is running on `localhost:5432`
- Verify credentials in `application.properties`
- Check that database `invoice_generator` exists

### Frontend Cannot Reach Backend
- Verify backend is running on port 8080
- Check browser console for CORS errors
- Ensure API URLs are correctly configured in `src/services/`

### Docker Build Failures
```bash
# Clean Docker cache and rebuild
docker-compose down -v
docker system prune -a
docker-compose up --build
```

## 📖 User Guide

### Creating an Invoice

1. **Navigate to Home**: Open http://localhost:5173
2. **Click "Create Invoice"**: Fill in customer name
3. **Add Line Items**: 
   - Enter product name
   - Enter quantity
   - Enter price
   - Click "Add Item"
4. **Review Summary**: Tax (18%) and total are calculated automatically
5. **Submit**: Click "Create Invoice" to save

### Viewing Invoices

1. **Open Invoices List**: Navigate to "My Invoices"
2. **View Details**: Click on any invoice to see full details
3. **Download PDF**: Click "Download PDF" button to save invoice as PDF file

## 🔒 Security Considerations

- Input validation on both frontend and backend
- SQL injection prevention through JPA parameterized queries
- CORS enabled for frontend origin
- No sensitive data exposed in logs
- Database passwords stored as environment variables

## 🚀 Deployment

### Production Deployment with Docker

```bash
# Build images
docker-compose -f docker-compose.yml build

# Run containers
docker-compose up -d

# View logs
docker-compose logs -f backend
docker-compose logs -f frontend
```

### Environment Variables

```env
# Backend
DB_URL=jdbc:postgresql://postgres:5432/invoice_generator
DB_USERNAME=postgres
DB_PASSWORD=secure_password

# Frontend (handled via Docker hostname resolution)
# No configuration needed - uses dynamic hostname detection
```

## 📝 License

This project is licensed under the MIT License.

## 👥 Support

For issues, questions, or suggestions, please contact the development team or create an issue in the repository.

## 🔄 Application Flow

```
User Opens Frontend
       ↓
   React App (Vite)
       ↓
Create/View Invoices
       ↓
       ├─→ POST /api/invoices → Backend
       ├─→ GET /api/invoices → Backend
       ├─→ GET /api/invoices/{id} → Backend
       └─→ GET /api/invoices/{id}/pdf → Backend
       ↓
   Spring Boot Service
       ↓
   Business Logic & Validation
       ↓
   JPA Repository
       ↓
   PostgreSQL Database
       ↓
   Response back to Frontend
       ↓
   Display to User
```

## 📊 Key Calculations

- **Subtotal**: Sum of all (quantity × price) for each line item
- **Tax**: Subtotal × 18%
- **Total**: Subtotal + Tax

## 🎓 Learning Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [React Documentation](https://react.dev)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Docker Documentation](https://docs.docker.com/)

---

**Last Updated**: June 4, 2026  
**Version**: 1.0.0
