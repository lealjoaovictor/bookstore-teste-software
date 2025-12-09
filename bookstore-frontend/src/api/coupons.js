const API = "http://localhost:8080/api/coupons";

export async function getCoupons() {
  const res = await fetch(API);
  if (!res.ok) throw new Error("Failed to fetch coupons");
  return res.json();
}
