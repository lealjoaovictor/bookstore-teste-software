import { useState, useEffect } from "react";
import { getAuthors } from "../api/authors";
import { getCategories } from "../api/categories";

export default function BookForm({ onSubmit }) {

    const [title, setTitle] = useState("");
    const [isbn, setIsbn] = useState("");
    const [price, setPrice] = useState("");
    const [stock, setStock] = useState("");

    const [authorId, setAuthorId] = useState("");
    const [categoryId, setCategoryId] = useState("");

    const [authors, setAuthors] = useState([]);
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        getAuthors().then(setAuthors);
        getCategories().then(setCategories);
    }, []);

    const submit = (e) => {
        e.preventDefault();

        onSubmit({
            title,
            isbn,
            price: Number(price),
            stock: Number(stock),
            author: authorId ? { id: Number(authorId) } : null,
            category: categoryId ? { id: Number(categoryId) } : null
        });

        setTitle("");
        setIsbn("");
        setPrice("");
        setStock("");
        setAuthorId("");
        setCategoryId("");
    };

    return (
        <form onSubmit={submit} className="book-form">
            <input placeholder="Title" value={title} onChange={e => setTitle(e.target.value)} />
            <input placeholder="ISBN" value={isbn} onChange={e => setIsbn(e.target.value)} />
            <input placeholder="Price" value={price} onChange={e => setPrice(e.target.value)} type="number" />
            <input placeholder="Stock" value={stock} onChange={e => setStock(e.target.value)} type="number" />

            <select value={authorId} onChange={e => setAuthorId(e.target.value)}>
                <option value="">Select author</option>
                {authors.map(a => (
                    <option key={a.id} value={a.id}>{a.name}</option>
                ))}
            </select>

            <select value={categoryId} onChange={e => setCategoryId(e.target.value)}>
                <option value="">Select category</option>
                {categories.map(c => (
                    <option key={c.id} value={c.id}>{c.name}</option>
                ))}
            </select>

            <button type="submit">Add Book</button>
        </form>
    );
}