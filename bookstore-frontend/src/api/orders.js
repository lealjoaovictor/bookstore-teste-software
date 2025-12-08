import { API_BASE } from './config'

export async function getOrders(){
  const res = await fetch(`${API_BASE}/orders`)
  if(!res.ok) throw new Error('Failed to fetch orders')
  return res.json()
}

export async function createOrder(data){
  const res = await fetch(`${API_BASE}/orders`, {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify(data)
  })
  if(!res.ok) throw new Error('Failed to create order')
  return res.json()
}
