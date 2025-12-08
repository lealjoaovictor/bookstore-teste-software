import { API_BASE } from './config'

export async function getUsers(){
  const res = await fetch(`${API_BASE}/users`)
  if(!res.ok) throw new Error('Failed to fetch users')
  return res.json()
}

export async function createUser(data){
  const res = await fetch(`${API_BASE}/users`, {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify(data)
  })
  if(!res.ok) throw new Error('Failed to create user')
  return res.json()
}

export async function deleteUser(id){
  const res = await fetch(`${API_BASE}/users/${id}`, { method:'DELETE' })
  if(!res.ok) throw new Error('Failed to delete user')
  return true
}
