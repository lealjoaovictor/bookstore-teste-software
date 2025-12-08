import React, { useState } from 'react'
import * as paymentsApi from '../api/payments'

export default function PaymentForm({ orderId, onPaid }){
  const [method, setMethod] = useState('CARD')
  const [amount, setAmount] = useState('')

  async function submit(e){
    e.preventDefault()
    await paymentsApi.createPayment(orderId, { method, amount: Number(amount) })
    onPaid && onPaid()
  }

  return (
    <form className="card" onSubmit={submit}>
      <h3 className="text-lg font-semibold mb-2">Create Payment for Order #{orderId}</h3>
      <div className="mb-2">
        <select className="input" value={method} onChange={e=>setMethod(e.target.value)}>
          <option value="CARD">Card</option>
          <option value="BOLETO">Boleto</option>
          <option value="PIX">PIX</option>
        </select>
      </div>
      <div className="mb-2">
        <input className="input" placeholder="Amount" type="number" step="0.01" value={amount} onChange={e=>setAmount(e.target.value)} />
      </div>
      <button className="button btn-primary" type="submit">Pay</button>
    </form>
  )
}
