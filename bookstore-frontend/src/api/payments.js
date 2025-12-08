import { API_BASE } from './config'

export async function createPayment(orderId, data){
  const res = await fetch(`${API_BASE}/orders/${orderId}/payment`, {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify(data)
  })
  if(!res.ok) throw new Error('Failed to create payment')
  return res.json()
}
