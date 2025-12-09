import React from 'react'

export default function Navbar({ onNavigate, active }){
  return (
    <nav className="bg-white shadow">
      <div className="container flex items-center justify-between py-3">
        <div className="flex items-center space-x-3">
          <button className="text-xl font-bold" onClick={()=>onNavigate('home')}>Bookstore</button>
          <button className={'px-3 py-1 rounded ' + (active==='home'?'bg-gray-100':'')} onClick={()=>onNavigate('home')}>Home</button>
          <button className={'px-3 py-1 rounded ' + (active==='users'?'bg-gray-100':'')} onClick={()=>onNavigate('users')}>Users</button>
          <button className={'px-3 py-1 rounded ' + (active==='orders'?'bg-gray-100':'')} onClick={()=>onNavigate('orders')}>Orders</button>
          <button className={'px-3 py-1 rounded ' + (active==='books'?'bg-gray-100':'')} onClick={()=>onNavigate('books')}>Books</button>
        </div>
        <div className="text-sm text-gray-500">Frontend · React + Vite</div>
      </div>
    </nav>
  )
}
