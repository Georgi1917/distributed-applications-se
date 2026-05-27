import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getJobListingDetail, getTechsByListing, getUsersByListing } from '../api.js';

export default function JobListingDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [listing, setListing] = useState(null);
  const [techs, setTechs] = useState([]);
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);
        const listingData = await getJobListingDetail(id);
        setListing(listingData);
        const techsData = await getTechsByListing(id);
        setTechs(techsData.content || []);
        const usersData = await getUsersByListing(id);
        setUsers(usersData.content || []);
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
  if (!listing) return <div className="page"><p>Job listing not found</p></div>;

  return (
    <div className="detail-page">
      <button onClick={() => navigate('/job-listings')} className="back-button">
        ← Back to Job Listings
      </button>

      <div className="detail-header">
        <div className="detail-content">
          <h1>{listing.Name}</h1>
          <div className="detail-meta">
            <div className="detail-meta-item">
              <span className="detail-meta-label">Experience Level</span>
              <span className="detail-meta-value">{listing.ExperienceLevel || 'N/A'}</span>
            </div>
          </div>
          {listing.description && (
            <div style={{ marginTop: '1rem', color: '#475569' }}>
              {listing.description}
            </div>
          )}
        </div>
      </div>

      <div className="detail-section">
        <h2>Required Technologies ({techs.length})</h2>
        {techs.length === 0 ? (
          <div className="empty-state">
            <p>No specific technologies required</p>
          </div>
        ) : (
          <div className="detail-list">
            {techs.map((tech) => (
              <Link
                key={tech.id}
                to={`/techs/${tech.id}`}
                style={{ textDecoration: 'none', color: 'inherit' }}
              >
                <div className="detail-item">
                  <p className="detail-item-title">{tech.name}</p>
                  <p className="detail-item-text">Category: {tech.techCategory}</p>
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>

      <div className="detail-section">
        <h2>Applicants ({users.length})</h2>
        {users.length === 0 ? (
          <div className="empty-state">
            <p>No users have applied yet</p>
          </div>
        ) : (
          <div className="detail-list">
            {users.map((user) => (
              <Link
                key={user.Id}
                to={`/users/${user.Id}`}
                style={{ textDecoration: 'none', color: 'inherit' }}
              >
                <div className="detail-item">
                  <p className="detail-item-title">{user.Username}</p>
                  <p className="detail-item-text">Email: {user.Email}</p>
                </div>
              </Link>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
