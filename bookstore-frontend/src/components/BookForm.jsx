import { useState } from "react";

export default function BookForm({ onSubmit }) {
    const [title, setTitle] = useState("");
    const [isbn, setIsbn] = useState("");
    const [price, setPrice] = useState("");
    const [stock, setStock] = useState("");

    const submit = (e) => {
        e.preventDefault();
        onSubmit({ title, isbn, price: Number(price), stock: Number(stock) });
        setTitle("");
        setIsbn("");
        setPrice("");
        setStock("");
    };

    return (
        <form onSubmit={submit} className="book-form">
            <input placeholder="Title" value={title} onChange={e => setTitle(e.target.value)} />
            <input placeholder="ISBN" value={isbn} onChange={e => setIsbn(e.target.value)} />
            <input placeholder="Price" value={price} onChange={e => setPrice(e.target.value)} type="number" />
            <input placeholder="Stock" value={stock} onChange={e => setStock(e.target.value)} type="number" />
            <button type="submit">Add Book</button>
        </form>
    );
}
