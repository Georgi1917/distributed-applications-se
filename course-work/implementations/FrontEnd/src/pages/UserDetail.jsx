import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getUserDetail, getJobListingsByUser } from '../api.js';
import Pagination from '../components/Pagination.jsx';

export default function UserDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [jobListings, setJobListings] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const pageSize = 2;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);
        const userData = await getUserDetail(id);
        setUser(userData);
        const jobsData = await getJobListingsByUser(id, { page, size: pageSize });
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
  if (!user) return <div className="page"><p>User not found</p></div>;

  return (
    <div className="detail-page">
      <button onClick={() => navigate('/users')} className="back-button">
        ← Back to Users
      </button>

      <div className="detail-header">
        <div className="detail-content">
          <h1>{user.Username}</h1>
          <div className="detail-meta">
            <div className="detail-meta-item">
              <span className="detail-meta-label">Email</span>
              <span className="detail-meta-value">{user.Email}</span>
            </div>
            <div className="detail-meta-item">
              <span className="detail-meta-label">Role</span>
              <span className="detail-meta-value">{user.Role || 'USER'}</span>
            </div>
          </div>
        </div>
      </div>

      <div className="detail-section">
        <h2>Applied Job Listings ({jobListings.length})</h2>
        {jobListings.length === 0 ? (
          <div className="empty-state">
            <p>This user hasn't applied to any job listings yet</p>
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
