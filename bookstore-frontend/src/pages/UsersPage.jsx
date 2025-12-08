import React, { useEffect, useState } from 'react'
import UserForm from '../components/UserForm'
import UserList from '../components/UserList'
import * as api from '../api/users'

export default function UsersPage(){
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  async function load(){
    try{
      setLoading(true)
      const data = await api.getUsers()
      console.log("USERS FROM USERS PAGE:", data)
      setUsers(data)
    }catch(e){
      setError(String(e))
    }finally{
      setLoading(false)
    }
  }

  useEffect(()=>{ load() }, [])

  async function handleCreate(payload){
    await api.createUser(payload)
    await load()
  }

  async function handleDelete(id){
    if(!confirm('Delete user?')) return
    await api.deleteUser(id)
    await load()
  }

  return (
    <div>
      <UserForm onCreated={handleCreate}/>
      {error && <div className="card text-red-600">{error}</div>}
      {loading ? <div className="card small">Loading...</div> : <UserList users={users} onDelete={handleDelete}/>}
    </div>
  )
}
