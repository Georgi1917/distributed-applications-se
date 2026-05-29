import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createCompany } from '../api.js';

const companyTypes = ['PRIVATE', 'PUBLIC'];
const remotePolicies = ['OFFICE', 'HYBRID', 'FULLY_REMOTE'];

export default function CompanyCreate() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    CompanyName: '',
    Description: '',
    EmployeeCount: '',
    Type: companyTypes[0],
    CompanyRemotePolicy: remotePolicies[0],
    IsHiring: true
  });
  const [error, setError] = useState(null);

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
      const created = await createCompany(payload);
      navigate(`/companies/${created.id ?? created.Id ?? ''}` || '/companies');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="page">
      <h1>Create Company</h1>
      {error && <div className="status status-error">{error}</div>}
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
          <button type="submit" className="btn btn-primary">Create</button>
          <button type="button" className="btn" onClick={() => navigate('/companies')}>Cancel</button>
        </div>
      </form>
    </div>
  );
}
