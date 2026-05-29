import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createTech } from '../api.js';

const categories = ['FRONT_END','BACK_END','EMBEDDED','DEV_OPS','INFRASTRUCTURE','QUALITY_ASSURANCE','MOBILE','DATA_SCIENCE'];

export default function TechCreate() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ name: '', techCategory: categories[0] });
  const [error, setError] = useState(null);

  const handleChange = (e) => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setError(null);
      const payload = { name: form.name, techCategory: form.techCategory };
      const created = await createTech(payload);
      navigate(`/techs/${created.id ?? created.Id ?? ''}` || '/tech');
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="page">
      <h1>Create Technology</h1>
      {error && <div className="status status-error">{error}</div>}
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input id="name" name="name" value={form.name} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="techCategory">Category</label>
          <select id="techCategory" name="techCategory" value={form.techCategory} onChange={handleChange}>{categories.map(c => <option key={c} value={c}>{c}</option>)}</select>
        </div>
        <div className="form-actions">
          <button type="submit" className="btn btn-primary">Create</button>
          <button type="button" className="btn" onClick={() => navigate('/tech')}>Cancel</button>
        </div>
      </form>
    </div>
  );
}
