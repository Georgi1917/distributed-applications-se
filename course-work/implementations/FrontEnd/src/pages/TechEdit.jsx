import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getTechDetail, updateTech } from '../api.js';

const categories = ['FRONT_END','BACK_END','EMBEDDED','DEV_OPS','INFRASTRUCTURE','QUALITY_ASSURANCE','MOBILE','DATA_SCIENCE'];

export default function TechEdit() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [form, setForm] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await getTechDetail(id);
        setForm({ name: data.name || '', techCategory: data.techCategory || categories[0] });
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
      await updateTech(id, { name: form.name, techCategory: form.techCategory });
      navigate(`/techs/${id}`);
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!form) return <div className="page"><p>Not found</p></div>;

  return (
    <div className="page">
      <h1>Edit Technology</h1>
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
          <button type="submit" className="btn btn-primary">Save</button>
          <button type="button" className="btn" onClick={() => navigate(`/techs/${id}`)}>Cancel</button>
        </div>
      </form>
    </div>
  );
}
