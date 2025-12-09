const BASE_URL = "http://localhost:8080/api/orders";

export async function getOrders() {
  const res = await fetch(BASE_URL);
  if (!res.ok) throw new Error("Failed to fetch orders");
  return res.json();
}

export async function getOrderById(id) {
  const res = await fetch(`${BASE_URL}/${id}`);
  if (!res.ok) throw new Error("Failed to fetch order " + id);
  return res.json();
}

export async function createOrder(order) {
  const res = await fetch(BASE_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(order),
  });

  if (!res.ok) throw new Error("Failed to create order");
  return res.json();
}
