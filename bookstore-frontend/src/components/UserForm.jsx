import React, { useState } from 'react'

export default function UserForm({ onCreated }){
  const [name,setName]=useState('')
  const [email,setEmail]=useState('')

  async function submit(e){
    e.preventDefault()
    await onCreated({ username: name, email })
    setName(''); setEmail('')
  }

  return (
    <form className="card" onSubmit={submit}>
      <h3 className="text-lg font-semibold mb-2">New user</h3>
      <div className="mb-2">
        <input className="input" placeholder="Name" value={name} onChange={e=>setName(e.target.value)} required/>
      </div>
      <div className="mb-2">
        <input className="input" placeholder="Email" type="email" value={email} onChange={e=>setEmail(e.target.value)} required/>
      </div>
      <button className="button btn-primary" type="submit">Create</button>
    </form>
  )
}
