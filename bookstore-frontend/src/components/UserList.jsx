import React from 'react'

export default function UserList({ users, onDelete }){
  return (
    <div className="card">
      <h3 className="text-lg font-semibold mb-2">Users</h3>
      {users.length === 0 ? <div className="small">No users yet</div> : (
        <ul>
          {users.map(u=>(
            <li key={u.id} className="flex items-center justify-between py-2 border-b last:border-b-0">
              <div>
                <div className="font-medium">{u.username || u.name || '—'}</div>
                <div className="small text-gray-500">{u.email}</div>
              </div>
              <div className="flex items-center space-x-2">
                <button className="button btn-danger small" onClick={()=>onDelete(u.id)}>Delete</button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  )
}
