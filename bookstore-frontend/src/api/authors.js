export async function getAuthors() {
    const res = await fetch("http://localhost:8080/api/authors");
    return res.json();
}