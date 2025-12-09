import { useEffect, useState } from "react";
import { getCoupons } from "../services/coupons";

export default function OrderForm({ onSubmit }) {

  const [userId, setUserId] = useState("");
  const [items, setItems] = useState([]);
  const [coupon, setCoupon] = useState("");
  const [coupons, setCoupons] = useState([]);

  useEffect(() => {
    load();
  }, []);

  async function load() {
    try {
      const data = await getCoupons();
      setCoupons(data);
    } catch (err) {
      console.error("Erro carregando cupons", err);
    }
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      user: { id: Number(userId) },
      items,
      couponCode: coupon || null,
    });
  };

  return (
    <form onSubmit={handleSubmit} className="form">

      {/* Campo usuário */}
      <label>Usuário ID:</label>
      <input
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
      />

      {/* Campo de Cupom */}
      <label>Cupom de desconto:</label>
      <select value={coupon} onChange={(e) => setCoupon(e.target.value)}>
        <option value="">Nenhum</option>
        {coupons.map(c => (
          <option key={c.id} value={c.code}>
            {c.code}
          </option>
        ))}
      </select>

      {/* Botão submit */}
      <button type="submit">Criar Pedido</button>
    </form>
  );
}
