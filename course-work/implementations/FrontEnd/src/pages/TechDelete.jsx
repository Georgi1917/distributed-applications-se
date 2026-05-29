import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getTechDetail, deleteTech } from '../api.js';

export default function TechDelete() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [tech, setTech] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await getTechDetail(id);
        setTech(data);
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
      await deleteTech(id);
      navigate('/tech');
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!tech) return <div className="page"><p>Tech not found</p></div>;

  return (
    <div className="page">
      <h1>Delete Technology</h1>
      <p>Are you sure you want to delete <strong>{tech.name}</strong>?</p>
      <div className="form-actions">
        <button className="btn btn-danger" onClick={handleDelete}>Yes, delete</button>
        <button className="btn" onClick={() => navigate(`/techs/${id}`)}>Cancel</button>
      </div>
    </div>
  );
}
