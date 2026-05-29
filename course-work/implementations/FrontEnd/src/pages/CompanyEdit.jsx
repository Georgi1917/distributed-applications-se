import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getCompanyDetail, updateCompany } from '../api.js';

const companyTypes = ['PRIVATE', 'PUBLIC'];
const remotePolicies = ['OFFICE', 'HYBRID', 'FULLY_REMOTE'];

export default function CompanyEdit() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [form, setForm] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await getCompanyDetail(id);
        setForm({
          CompanyName: data.CompanyName || '',
          Description: data.Description || '',
          EmployeeCount: data.EmployeeCount ?? '',
          Type: data.Type || companyTypes[0],
          CompanyRemotePolicy: data.CompanyRemotePolicy || remotePolicies[0],
          IsHiring: data.IsHiring ?? true
        });
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [id]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setForm((f) => ({ ...f, [name]: type === 'checkbox' ? checked : value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setError(null);
      const payload = {
        CompanyName: form.CompanyName,
        Description: form.Description,
        EmployeeCount: form.EmployeeCount ? Number(form.EmployeeCount) : null,
        Type: form.Type,
        CompanyRemotePolicy: form.CompanyRemotePolicy,
        IsHiring: Boolean(form.IsHiring)
      };
      await updateCompany(id, payload);
      navigate(`/companies/${id}`);
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!form) return <div className="page"><p>Not found</p></div>;

  return (
    <div className="page">
      <h1>Edit Company</h1>
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="CompanyName">Company Name</label>
          <input id="CompanyName" name="CompanyName" value={form.CompanyName} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="Description">Description</label>
          <textarea id="Description" name="Description" value={form.Description} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="EmployeeCount">Employee Count</label>
          <input id="EmployeeCount" name="EmployeeCount" value={form.EmployeeCount} onChange={handleChange} type="number" />
        </div>
        <div className="form-group">
          <label htmlFor="Type">Type</label>
          <select id="Type" name="Type" value={form.Type} onChange={handleChange}>{companyTypes.map(t => <option key={t} value={t}>{t}</option>)}</select>
        </div>
        <div className="form-group">
          <label htmlFor="CompanyRemotePolicy">Remote Policy</label>
          <select id="CompanyRemotePolicy" name="CompanyRemotePolicy" value={form.CompanyRemotePolicy} onChange={handleChange}>{remotePolicies.map(r => <option key={r} value={r}>{r}</option>)}</select>
        </div>
        <div className="form-group">
          <label htmlFor="IsHiring">Hiring Status</label>
          <div className="checkbox-row">
            <input id="IsHiring" type="checkbox" name="IsHiring" checked={form.IsHiring} onChange={handleChange} />
            <span>Currently hiring</span>
          </div>
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary">Save</button>
          <button type="button" className="btn" onClick={() => navigate(`/companies/${id}`)}>Cancel</button>
        </div>
      </form>
    </div>
  );
}
