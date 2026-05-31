import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getCompanyDetail, getJobListingsByCompany } from '../api.js';
import Pagination from '../components/Pagination.jsx';

export default function CompanyDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [company, setCompany] = useState(null);
  const [jobListings, setJobListings] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const pageSize = 6;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);
        const companyData = await getCompanyDetail(id);
        setCompany(companyData);
        const jobsData = await getJobListingsByCompany(id, { page, size: pageSize });
        setJobListings(jobsData?.content || []);
        setTotalPages(jobsData?.page?.totalPages ?? 0);
        setPage(jobsData?.page?.number ?? page);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, [id, page]);

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
          {company.Description && (
            <div style={{ marginTop: '1.5rem', color: '#475569' }}>
              <h2 style={{ margin: '0 0 0.5rem', fontSize: '1.1rem' }}>Description</h2>
              <p style={{ margin: 0, lineHeight: 1.6 }}>{company.Description}</p>
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
        {totalPages > 1 && (
          <Pagination page={page} totalPages={totalPages} onPageChange={(p) => setPage(p)} />
        )}
      </div>
    </div>
  );
}
