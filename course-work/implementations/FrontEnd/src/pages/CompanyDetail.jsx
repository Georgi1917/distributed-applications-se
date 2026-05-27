import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getCompanyDetail, getJobListingsByCompany } from '../api.js';

export default function CompanyDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [company, setCompany] = useState(null);
  const [jobListings, setJobListings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);
        const companyData = await getCompanyDetail(id);
        setCompany(companyData);
        const jobsData = await getJobListingsByCompany(id);
        setJobListings(jobsData.content || []);
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
  if (!company) return <div className="page"><p>Company not found</p></div>;

  return (
    <div className="detail-page">
      <button onClick={() => navigate('/companies')} className="back-button">
        ← Back to Companies
      </button>

      <div className="detail-header">
        <div className="detail-content">
          <h1>{company.CompanyName}</h1>
          <div className="detail-meta">
            <div className="detail-meta-item">
              <span className="detail-meta-label">Type</span>
              <span className="detail-meta-value">{company.Type || 'N/A'}</span>
            </div>
            <div className="detail-meta-item">
              <span className="detail-meta-label">Remote Policy</span>
              <span className="detail-meta-value">{company.CompanyRemotePolicy || 'N/A'}</span>
            </div>
          </div>
          {company.description && (
            <div style={{ marginTop: '1rem', color: '#475569' }}>
              {company.description}
            </div>
          )}
        </div>
      </div>

      <div className="detail-section">
        <h2>Job Listings ({jobListings.length})</h2>
        {jobListings.length === 0 ? (
          <div className="empty-state">
            <p>No job listings posted by this company</p>
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
                    Experience: {job.ExperienceLevel}
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
