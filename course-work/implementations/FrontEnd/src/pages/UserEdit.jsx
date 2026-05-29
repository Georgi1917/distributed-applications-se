import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getUserDetail, updateUser } from '../api.js';

const roles = ['USER','ADMIN'];

export default function UserEdit() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [form, setForm] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await getUserDetail(id);
        setForm({ email: data.Email || '', username: data.Username || '', role: data.Role || roles[0] });
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [id]);

  const handleChange = (e) => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setError(null);
      await updateUser(id, { email: form.email, username: form.username, role: form.role });
      navigate(`/users/${id}`);
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!form) return <div className="page"><p>Not found</p></div>;

  return (
    <div className="page">
      <h1>Edit User</h1>
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
          <label htmlFor="role">Role</label>
          <select id="role" name="role" value={form.role} onChange={handleChange}>{roles.map(r => <option key={r} value={r}>{r}</option>)}</select>
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary">Save</button>
          <button type="button" className="btn" onClick={() => navigate(`/users/${id}`)}>Cancel</button>
        </div>
      </form>
    </div>
  );
}
