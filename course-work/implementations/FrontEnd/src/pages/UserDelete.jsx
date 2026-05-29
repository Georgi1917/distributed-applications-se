import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getUserDetail, deleteUser } from '../api.js';

export default function UserDelete() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await getUserDetail(id);
        setUser(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [id]);

  const handleDelete = async () => {
    try {
      await deleteUser(id);
      navigate('/users');
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!user) return <div className="page"><p>User not found</p></div>;

  return (
    <div className="page">
      <h1>Delete User</h1>
      <p>Are you sure you want to delete <strong>{user.Username}</strong>?</p>
      <div className="form-actions">
        <button className="btn btn-danger" onClick={handleDelete}>Yes, delete</button>
        <button className="btn" onClick={() => navigate(`/users/${id}`)}>Cancel</button>
      </div>
    </div>
  );
}
