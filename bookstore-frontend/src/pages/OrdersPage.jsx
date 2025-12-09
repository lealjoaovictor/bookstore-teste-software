import React, { useEffect, useState } from 'react'
import { getUsers } from '../api/users'
import * as ordersApi from '../api/orders'
import * as couponsApi from '../api/coupons'
import { getBooks } from '../api/books'

import CreateOrder from '../components/CreateOrder'
import OrderDetails from "../components/OrderDetails"

export default function OrdersPage({ onNavigate, onSelectOrder }) {
  const [users, setUsers] = useState([])
  const [orders, setOrders] = useState([])
  const [ordersWithUser, setOrdersWithUser] = useState([])
  const [coupons, setCoupons] = useState([])
  const [books, setBooks] = useState([])
  const [loading, setLoading] = useState(true)

  const [selectedUser, setSelectedUser] = useState('')
  const [selectedOrder, setSelectedOrder] = useState(null)

  async function load() {
  setLoading(true)
  try {
    const [u, o, c, b] = await Promise.all([
      getUsers(),
      ordersApi.getOrders(),
      couponsApi.getCoupons(),
      getBooks()
    ])

    console.log("📌 USERS:", u)
    console.log("📌 ORDERS:", o)  // <<< aqui
    console.log("📌 COUPONS:", c)
    console.log("📌 BOOKS:", b)

    setUsers(u || [])
    setOrders(o || [])
    setCoupons(c || [])
    setBooks(b || [])
  } catch (e) {
    console.error("❌ ERRO NO LOAD()", e)
  } finally {
    setLoading(false)
  }
}


  useEffect(() => { load() }, [])

const filteredOrders = selectedUser
  ? orders.filter(o => o?.user && String(o.user.id) === String(selectedUser))
  : orders


  console.log("selectedUser", selectedUser)
console.log("orders", orders.map(o => o.user))
console.log("filteredOrders", filteredOrders)


  return (
    <div>
      <CreateOrder
        users={users}
        coupons={coupons}
        books={books}
        onCreated={load}
      />

      <div className="card mt-4 mb-3">
        <label className="small font-medium">Filtrar por usuário:</label>
        <select
          className="input mt-1"
          value={selectedUser}
          onChange={e => setSelectedUser(e.target.value)}
        >
          <option value="">-- Todos --</option>
          {users.map(u => (
            <option key={u.id} value={String(u.id)}>
              {u.username || u.name}
            </option>
          ))}
        </select>
      </div>

      <div className="card">
        <h3 className="text-lg font-semibold mb-2">Orders</h3>

        {loading ? (
          <div className="small">Loading...</div>
        ) : filteredOrders.length === 0 ? (
          <div className="small text-gray-500">
            Nenhuma order encontrada para este usuário.
          </div>
        ) : (
          <ul>
            {filteredOrders.map(ord => (
              <li
                key={ord.id}
                className="border-b py-2 cursor-pointer hover:bg-gray-100"
                onClick={() => setSelectedOrder(ord.id)}
              >
                <div className="flex justify-between">
                  <div>
                    <div className="font-medium">
                      Order #{ord.id} — {ord.user?.username || '—'}
                    </div>

                    <div className="small text-gray-500">
                      Status: {ord.status} • Total: R$ {ord.total}
                      {ord.couponCode && (
                        <> • Cupom: <strong>{ord.couponCode}</strong></>
                      )}
                    </div>
                  </div>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>

      {selectedOrder && (
        <OrderDetails
          orderId={selectedOrder}
          onClose={() => setSelectedOrder(null)}
        />
      )}
    </div>
  )
}
