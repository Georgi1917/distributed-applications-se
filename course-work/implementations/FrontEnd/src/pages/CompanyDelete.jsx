import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getCompanyDetail, deleteCompany } from '../api.js';

export default function CompanyDelete() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [company, setCompany] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await getCompanyDetail(id);
        setCompany(data);
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
      await deleteCompany(id);
      navigate('/companies');
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!company) return <div className="page"><p>Company not found</p></div>;

  return (
    <div className="page">
      <h1>Delete Company</h1>
      <p>Are you sure you want to delete <strong>{company.CompanyName}</strong>?</p>
      <div className="form-actions">
        <button className="btn btn-danger" onClick={handleDelete}>Yes, delete</button>
        <button className="btn" onClick={() => navigate(`/companies/${id}`)}>Cancel</button>
      </div>
    </div>
  );
}
