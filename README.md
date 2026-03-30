# MiniMart POS System

A web‑based point‑of‑sale system built with **Java Spring Boot**, **PostgreSQL**, and **Alpine.js**.  
Designed for small retail stores to manage sales, inventory, and invoices.

## Features

- **User Authentication** – Predefined login credentials (admin/cashier).
- **Dashboard** – Real‑time metrics: total products, sales, low‑stock items, revenue.
- **Point of Sale (POS)** – Add products by barcode or name, cart management with quantity controls, and checkout with invoice generation.
- **Save Invoice as Image** – Capture and download invoices as PNG.
- **Product Management** – Add, edit, delete, and view products with stock status.
- **Invoice History** – View all invoices with detailed item breakdown.
- **Reports** – Revenue analysis for today, last week, and last month.

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.1, Spring Data JPA, Hibernate
- **Database**: PostgreSQL
- **Frontend**: HTML5, Tailwind CSS, Alpine.js
- **Tools**: Maven, IntelliJ IDEA, Git, Postman

## Getting Started

### Prerequisites

- Java 17 or later
- PostgreSQL 12 or later
- Maven (optional, can use Maven wrapper)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/rohansoni786/minimart-pos.git
   cd minimart-pos