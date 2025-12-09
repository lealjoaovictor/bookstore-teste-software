import { useEffect, useState } from "react";
import { getBooks, createBook } from "../api/books";
import BookForm from "../components/BookForm";
import "../styles/books.css";

export default function BooksPage() {
    const [books, setBooks] = useState([]);

    async function load() {
        try {
            const data = await getBooks();
            setBooks(data);
        } catch (err) {
            console.error("Failed to load books", err);
        }
    }

    async function handleCreate(book) {
        try {
            await createBook(book);
            load();
        } catch (err) {
            console.error("Failed to create book", err);
        }
    }

    useEffect(() => {
        load();
    }, []);

    return (
        <div className="books-container">
            <h1>Books</h1>

            <BookForm onSubmit={handleCreate} />

            <table className="books-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>ISBN</th>
                        <th>Price</th>
                        <th>Stock</th>
                    </tr>
                </thead>
                <tbody>
                    {books.map(b => (
                        <tr key={b.id}>
                            <td>{b.id}</td>
                            <td>{b.title}</td>
                            <td>{b.isbn}</td>
                            <td>{b.price}</td>
                            <td>{b.stock}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}
