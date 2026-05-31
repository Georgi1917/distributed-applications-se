import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { createJobListing, getCompanies } from '../api.js';

const experienceLevels = ['JUNIOR', 'MID', 'SENIOR'];

export default function JobListingCreate() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ Name: '', Description: '', Salary: '', ExperienceLevel: experienceLevels[0], company_id: '' });
  const [companies, setCompanies] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const data = await getCompanies({ page: 0, size: 100 });
        setCompanies(data?.content || []);
      } catch (e) {
        // ignore
      }
    })();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setError(null);
      const payload = {
        Name: form.Name,
        Description: form.Description,
        salary: Number(form.Salary),
        ExperienceLevel: form.ExperienceLevel,
        company_id: Number(form.company_id)
      };
      const created = await createJobListing(payload);
      navigate(`/job-listings/${created.Id ?? created.id ?? ''}` || '/job-listings');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="page">
      <h1>Create Job Listing</h1>
      {error && <div className="status status-error">{error}</div>}
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="Name">Title</label>
          <input id="Name" name="Name" value={form.Name} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="Description">Description</label>
          <textarea id="Description" name="Description" value={form.Description} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="Salary">Salary</label>
          <input id="Salary" name="Salary" type="number" min="0" step="0.01" value={form.Salary} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="ExperienceLevel">Experience</label>
          <select id="ExperienceLevel" name="ExperienceLevel" value={form.ExperienceLevel} onChange={handleChange}>{experienceLevels.map(e => <option key={e} value={e}>{e}</option>)}</select>
        </div>
        <div className="form-group">
          <label htmlFor="company_id">Company</label>
          <select id="company_id" name="company_id" value={form.company_id} onChange={handleChange} required>
            <option value="">Select a company</option>
            {companies.map(c => <option key={c.Id ?? c.id} value={c.Id ?? c.id}>{c.CompanyName || c.name}</option>)}
          </select>
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary">Create</button>
          <button type="button" className="btn" onClick={() => navigate('/job-listings')}>Cancel</button>
        </div>
      </form>
    </div>
  );
}
