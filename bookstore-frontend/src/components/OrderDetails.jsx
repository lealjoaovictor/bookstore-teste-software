import React, { useEffect, useState } from "react";
import * as ordersApi from "../api/orders";

export default function OrderDetails({ orderId, onClose }) {
  const [order, setOrder] = useState(null);

  useEffect(() => {
    async function load() {
      const data = await ordersApi.getOrderById(orderId);
      setOrder(data);
    }
    load();
  }, [orderId]);

  if (!order) return <div className="card">Loading...</div>;

  return (
    <div className="card mt-3">
      <h3 className="text-lg font-semibold mb-2">
        Order #{order.id} — {order.user?.username}
      </h3>

      <div className="mb-2">
        <strong>Status:</strong> {order.status}
      </div>

      <h4 className="mt-3 font-semibold">Items</h4>
      <ul className="mt-2">
        {order.items.map(item => (
          <li key={item.id} className="border-b py-2">
            {item.book?.title} — {item.quantity}×  
            <span className="text-gray-600 ml-2">
              R$ {(item.price * item.quantity).toFixed(2)}
            </span>
          </li>
        ))}
      </ul>

      <div className="mt-4 font-semibold">
        Total: R$ {order.total.toFixed(2)}
      </div>

      <button className="button btn-secondary mt-3" onClick={onClose}>
        Close
      </button>
    </div>
  );
}
