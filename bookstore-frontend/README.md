# Bookstore Frontend (React + Vite + Tailwind)

Minimal frontend for the Spring Boot backend.

## Features
- Create / list / delete users
- Create / list orders (simple items)
- Create payments (basic)

## Quick start

1. Install dependencies
```bash
cd bookstore-frontend
npm install
```

2. Start dev server
```bash
npm run dev
```

The app will run at http://localhost:3000

## API base URL
By default the frontend expects the backend API at `http://localhost:8080/api`.
If your backend uses a different base, create a `.env` file with:
```
VITE_API_BASE=http://localhost:8080/api
```

## Notes
- The frontend is intentionally simple and pragmatic. It assumes the backend exposes:
  - `GET /api/users` and `POST /api/users` and `DELETE /api/users/{id}`
  - `GET /api/orders`, `POST /api/orders`
  - `POST /api/orders/{orderId}/payment`

Adjust endpoints if your backend differs.

