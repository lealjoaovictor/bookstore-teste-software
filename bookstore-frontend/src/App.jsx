import React, { useState } from 'react'
import Navbar from './components/Navbar'
import HomePage from './pages/HomePage'
import UsersPage from './pages/UsersPage'
import OrdersPage from './pages/OrdersPage'
import BooksPage from "./pages/BooksPage";

export default function App(){
  const [route, setRoute] = useState('home')
  return (
    <div>
      <Navbar onNavigate={setRoute} active={route}/>
      <main className="container">
        {route === 'home' && <HomePage />}
        {route === 'users' && <UsersPage />}
        {route === 'orders' && <OrdersPage />}
        {route === 'books' && <BooksPage />}
      </main>
    </div>
  )
}
