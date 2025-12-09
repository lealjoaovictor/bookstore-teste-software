const API = "http://localhost:8080/api/books";

export async function getBooks() {
    const res = await fetch(API);
    if (!res.ok) throw new Error("Failed to fetch books");
    return res.json();
}

export async function createBook(book) {
    const res = await fetch(API, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(book),
    });
    if (!res.ok) throw new Error("Failed to create book");
    return res.json();
}