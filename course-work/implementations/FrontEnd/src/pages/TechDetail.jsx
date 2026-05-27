import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getTechDetail, getJobListingsByTech } from '../api.js';

export default function TechDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [tech, setTech] = useState(null);
  const [jobListings, setJobListings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);
        const techData = await getTechDetail(id);
        setTech(techData);
        const jobsData = await getJobListingsByTech(id);
        setJobListings(jobsData?.content || []);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, [id]);

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!tech) return <div className="page"><p>Technology not found</p></div>;

  return (
    <div className="detail-page">
      <button onClick={() => navigate('/tech')} className="back-button">
        ← Back to Technologies
      </button>

      <div className="detail-header">
        <div className="detail-content">
          <h1>{tech.name}</h1>
          <div className="detail-meta">
            <div className="detail-meta-item">
              <span className="detail-meta-label">Category</span>
              <span className="detail-meta-value">{tech.techCategory || 'N/A'}</span>
            </div>
          </div>
        </div>
      </div>

      <div className="detail-section">
        <h2>Job Listings using this Tech ({jobListings.length})</h2>
        {jobListings.length === 0 ? (
          <div className="empty-state">
            <p>No job listings currently reference this technology.</p>
          </div>
        ) : (
          <div className="detail-list">
            {jobListings.map((job) => (
              <Link
                key={job.Id}
                to={`/job-listings/${job.Id}`}
                style={{ textDecoration: 'none', color: 'inherit' }}
              >
                <div className="detail-item">
                  <p className="detail-item-title">{job.Name}</p>
                  <p className="detail-item-text">
                    Experience: {job.ExperienceLevel} • Company ID: {job.company_id}
                  </p>
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
