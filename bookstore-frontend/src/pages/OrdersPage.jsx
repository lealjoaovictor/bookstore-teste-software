import React, { useEffect, useState } from 'react'
import { getUsers } from '../api/users'
import * as ordersApi from '../api/orders'

export default function OrdersPage() {
  const [users, setUsers] = useState([])
  const [orders, setOrders] = useState([])
  const [loading, setLoading] = useState(true)

  async function load() {
    setLoading(true)
    try {
      const [u, o] = await Promise.all([getUsers(), ordersApi.getOrders()])
      console.log("USERS LOADED:", u)
      setUsers(u)
      setOrders(o)
    } catch (e) {
      console.error(e)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => { load() }, [])

  return (
    <div>
      <CreateOrder users={users} onCreated={load} />
      <div className="card">
        <h3 className="text-lg font-semibold mb-2">Orders</h3>
        {loading ? <div className="small">Loading...</div> : (
          <ul>
            {orders.map(ord => (
              <li key={ord.id} className="border-b py-2">
                <div className="flex justify-between">
                  <div>
                    <div className="font-medium">Order #{ord.id} — {ord.user?.username || ord.user?.name || '—'}</div>
                    <div className="small text-gray-500">Status: {ord.status} • Total: {ord.total}</div>
                  </div>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  )
}


function CreateOrder({ users, onCreated }) {
  const [userId, setUserId] = useState('')
  const [items, setItems] = useState([])
  const [product, setProduct] = useState('')
  const [qty, setQty] = useState(1)
  const [total, setTotal] = useState(0)

  useEffect(() => {
    const t = items.reduce((s, it) => s + (it.price || 0) * it.quantity, 0)
    setTotal(t)
  }, [items])

  function addItem() {
    if (!product || qty <= 0) return
    const newItem = { product, quantity: Number(qty), price: 10 } // placeholder price
    setItems(prev => [...prev, newItem])
    setProduct(''); setQty(1)
  }

  async function submit(e) {
    e.preventDefault()
    if (!userId) { alert('Select a user'); return }
    const payload = {
      user: { id: Number(userId) },
      items,
      total,
      status: 'NEW'
    }
    await ordersApi.createOrder(payload)
    setItems([]); setTotal(0); setUserId('')
    onCreated && onCreated()
  }

  return (
    <form className="card" onSubmit={submit}>
      <h3 className="text-lg font-semibold mb-2">Create Order</h3>
      <div className="mb-2">
        <select className="input" value={userId} onChange={e => setUserId(e.target.value)}>
          <option value="">-- Select user --</option>
          {users.map(u => <option key={u.id} value={u.id}>{u.username || u.name}</option>)}
        </select>
      </div>

      <div className="mb-2">
        <div className="flex gap-2">
          <input className="input" placeholder="Product name" value={product} onChange={e => setProduct(e.target.value)} />
          <input className="input" type="number" min="1" style={{ width: 80 }} value={qty} onChange={e => setQty(e.target.value)} />
          <button type="button" className="button btn-primary" onClick={addItem}>Add</button>
        </div>
      </div>

      <div className="mb-2">
        <div className="small font-medium">Items</div>
        {items.length === 0 ? <div className="small text-gray-500">No items</div> : (
          <ul className="mt-2">
            {items.map((it, i) => (
              <li key={i} className="flex justify-between py-1">
                <div>{it.product} × {it.quantity}</div>
                <div className="small text-gray-500">R$ {(it.price * it.quantity).toFixed(2)}</div>
              </li>
            ))}
          </ul>
        )}
      </div>

      <div className="mb-2 flex items-center justify-between">
        <div>Total: <span className="font-semibold">R$ {total.toFixed(2)}</span></div>
        <button className="button btn-success" type="submit">Create Order</button>
      </div>
    </form>
  )
}
