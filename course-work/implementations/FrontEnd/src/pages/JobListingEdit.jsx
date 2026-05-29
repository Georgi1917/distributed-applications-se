import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getJobListingDetail, updateJobListing, getCompanies } from '../api.js';

const experienceLevels = ['JUNIOR', 'MID', 'SENIOR'];

export default function JobListingEdit() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [form, setForm] = useState(null);
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await getJobListingDetail(id);
        setForm({ Name: data.Name || '', Description: data.Description || '', ExperienceLevel: data.ExperienceLevel || experienceLevels[0], company_id: data.company_id ?? '' });
        const comps = await getCompanies({ page: 0, size: 100 });
        setCompanies(comps?.content || []);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((f) => ({ ...f, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setError(null);
      const payload = { Name: form.Name, Description: form.Description, ExperienceLevel: form.ExperienceLevel };
      await updateJobListing(id, payload);
      navigate(`/job-listings/${id}`);
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!form) return <div className="page"><p>Not found</p></div>;

  return (
    <div className="page">
      <h1>Edit Job Listing</h1>
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
          <label htmlFor="ExperienceLevel">Experience</label>
          <select id="ExperienceLevel" name="ExperienceLevel" value={form.ExperienceLevel} onChange={handleChange}>{experienceLevels.map(e => <option key={e} value={e}>{e}</option>)}</select>
        </div>
        <div className="form-group">
          <label htmlFor="company_id">Company</label>
          <select id="company_id" name="company_id" value={form.company_id} onChange={handleChange} disabled>
            <option value="">(company fixed)</option>
            {companies.map(c => <option key={c.Id ?? c.id} value={c.Id ?? c.id}>{c.CompanyName || c.name}</option>)}
          </select>
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary">Save</button>
          <button type="button" className="btn" onClick={() => navigate(`/job-listings/${id}`)}>Cancel</button>
        </div>
      </form>
    </div>
  );
}
