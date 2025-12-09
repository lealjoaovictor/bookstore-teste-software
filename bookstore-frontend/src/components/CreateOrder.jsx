import React, { useEffect, useState } from 'react'
import * as ordersApi from '../api/orders'

export default function CreateOrder({ users, coupons, books, onCreated }) {
  const [userId, setUserId] = useState('')
  const [items, setItems] = useState([])
  const [bookId, setBookId] = useState('')
  const [qty, setQty] = useState(1)
  const [total, setTotal] = useState(0)
  const [coupon, setCoupon] = useState("")

  useEffect(() => {
    const t = items.reduce((sum, it) => sum + (it.price || 0) * it.quantity, 0)
    setTotal(t)
  }, [items])

  function addItem() {
    if (!bookId || qty <= 0) return

    const book = books.find(b => b.id === Number(bookId))
    if (!book) return

    const newItem = {
      book: { id: book.id },
      title: book.title,
      quantity: Number(qty),
      price: book.price
    }

    setItems(prev => [...prev, newItem])
    setBookId('')
    setQty(1)
  }

  async function submit(e) {
    e.preventDefault()

    if (!userId) {
      alert('Select a user')
      return
    }

    if (items.length === 0) {
      alert('Add at least one item')
      return
    }

    const payload = {
      user: { id: Number(userId) },
      items: items.map(it => ({
        book: it.book,
        quantity: it.quantity
      })),
      total,
      status: 'NEW',
      couponCode: coupon || null
    }

    try {
      await ordersApi.createOrder(payload)

      setItems([])
      setTotal(0)
      setUserId('')
      setCoupon("")

      onCreated && onCreated()

    } catch (err) {
      console.error("Failed to create order", err)
      alert("Failed to create order")
    }
  }

  return (
    <form className="card" onSubmit={submit}>
      <h3 className="text-lg font-semibold mb-2">Create Order</h3>

      {/* Usuário */}
      <div className="mb-2">
        <select className="input" value={userId} onChange={e => setUserId(e.target.value)}>
          <option value="">-- Select user --</option>
          {users.map(u => (
            <option key={u.id} value={u.id}>
              {u.username || u.name}
            </option>
          ))}
        </select>
      </div>

      {/* Cupom */}
      <div className="mb-2">
        <label className="small font-medium">Cupom de desconto:</label>
        <select className="input mt-1" value={coupon} onChange={e => setCoupon(e.target.value)}>
          <option value="">-- Nenhum --</option>
          {coupons.map(c => (
            <option key={c.id} value={c.code}>
              {c.code} ({c.type})
            </option>
          ))}
        </select>
      </div>

      <div className="mb-2">
        <div className="flex gap-2">
          <select className="input" value={bookId} onChange={e => setBookId(e.target.value)}>
            <option value="">-- Select book --</option>
            {books.map(b => (
              <option key={b.id} value={b.id}>
                {b.title} — R$ {b.price}
              </option>
            ))}
          </select>

          <input
            className="input"
            type="number"
            min="1"
            style={{ width: 80 }}
            value={qty}
            onChange={e => setQty(e.target.value)}
          />

          <button type="button" className="button btn-primary" onClick={addItem}>
            Add
          </button>
        </div>
      </div>

      <div className="mb-2">
        <div className="small font-medium">Items</div>
        {items.length === 0 ? (
          <div className="small text-gray-500">No items</div>
        ) : (
          <ul className="mt-2">
            {items.map((it, i) => (
              <li key={i} className="flex justify-between py-1">
                <div>{it.title} × {it.quantity}</div>
                <div className="small text-gray-500">
                  R$ {(it.price * it.quantity).toFixed(2)}
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>

      <div className="mb-2 flex items-center justify-between">
        <div>
          Total: <span className="font-semibold">R$ {total.toFixed(2)}</span>
        </div>

        <button className="button btn-success" type="submit">
          Create Order
        </button>
      </div>
    </form>
  )
}
