import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createUser } from '../api.js';

const roles = ['USER','ADMIN'];

export default function UserCreate() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: '', username: '', password: '', role: roles[0] });
  const [error, setError] = useState(null);

  const handleChange = (e) => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setError(null);
      const payload = { email: form.email, username: form.username, password: form.password, role: form.role };
      const created = await createUser(payload);
      navigate(`/users/${created.Id ?? created.id ?? ''}` || '/users');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="page">
      <h1>Create User</h1>
      {error && <div className="status status-error">{error}</div>}
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input id="email" name="email" value={form.email} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input id="username" name="username" value={form.username} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input id="password" type="password" name="password" value={form.password} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="role">Role</label>
          <select id="role" name="role" value={form.role} onChange={handleChange}>{roles.map(r => <option key={r} value={r}>{r}</option>)}</select>
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary">Create</button>
          <button type="button" className="btn" onClick={() => navigate('/users')}>Cancel</button>
        </div>
      </form>
    </div>
  );
}
