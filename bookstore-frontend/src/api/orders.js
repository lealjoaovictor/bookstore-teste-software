import { API_BASE } from './config'

export async function getOrders(){
  const res = await fetch(`${API_BASE}/orders`)
  if(!res.ok) throw new Error('Failed to fetch orders')
  return res.json()
}

export async function createOrder(order) {
  const res = await fetch("http://localhost:8080/api/orders", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(order),
  });

  if (!res.ok) throw new Error("Failed to create order");
  return res.json();
}
