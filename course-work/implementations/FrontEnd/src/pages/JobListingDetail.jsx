import { useState, useEffect } from 'react';
import { useParams, useNavigate, Link, useLocation } from 'react-router-dom';
import { getJobListingDetail, getTechsByListing, getUsersByListing, createJobApplication, getJobApplicationsByUser, getJobListingTechsByListing, deleteJobListingTech, getTechs, createJobListingTech } from '../api.js';
import { useAuth } from '../contexts/AuthContext.jsx';
import Pagination from '../components/Pagination.jsx';

export default function JobListingDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [listing, setListing] = useState(null);
  const [techs, setTechs] = useState([]);
  const [users, setUsers] = useState([]);
  const [listingTechs, setListingTechs] = useState([]);
  const [availableTechs, setAvailableTechs] = useState([]);
  const [showAddTechs, setShowAddTechs] = useState(false);
  const [availablePage, setAvailablePage] = useState(0);
  const [availableTotalPages, setAvailableTotalPages] = useState(0);
  const [addingTechId, setAddingTechId] = useState(null);
  const [techsPage, setTechsPage] = useState(0);

  const [techsTotalPages, setTechsTotalPages] = useState(0);
  const [usersPage, setUsersPage] = useState(0);
  const [usersTotalPages, setUsersTotalPages] = useState(0);
  const [applied, setApplied] = useState(false);
  const [applying, setApplying] = useState(false);
  const [removingTechId, setRemovingTechId] = useState(null);
  const pageSize = 6;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { token, user: currentUser, isAdmin } = useAuth();

  const refreshAvailableTechs = async (currentListingTechs) => {
    if (!isAdmin) return;
    try {
      const allTechs = await getTechs({ page: 0, size: 1000 });
      const assignedIds = new Set((currentListingTechs || listingTechs || []).map((item) => item.tech_id));
      const available = (allTechs?.content || []).filter((tech) => !assignedIds.has(tech.id));
      setAvailableTechs(available);
      setAvailablePage(0);
      setAvailableTotalPages(Math.ceil(available.length / pageSize));
    } catch (e) {
      setAvailableTechs([]);
      setAvailablePage(0);
      setAvailableTotalPages(0);
    }
  };
  const location = useLocation();

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        setError(null);
        const listingData = await getJobListingDetail(id);
        setListing(listingData);
        const techsData = await getTechsByListing(id, { page: techsPage, size: pageSize });
        setTechs(techsData?.content || []);
        setTechsTotalPages(techsData?.page?.totalPages ?? 0);
        setTechsPage(techsData?.page?.number ?? techsPage);
        const usersData = await getUsersByListing(id, { page: usersPage, size: pageSize });
        setUsers(usersData?.content || []);
        setUsersTotalPages(usersData?.page?.totalPages ?? 0);
        setUsersPage(usersData?.page?.number ?? usersPage);
        const listingTechsData = await getJobListingTechsByListing(id);
        setListingTechs(listingTechsData || []);

        // Check if current user has applied by fetching applications for the user
        if (currentUser) {
          try {
            const apps = await getJobApplicationsByUser(currentUser.Id);
            const found = (apps || []).some(a => (a.listing_id == id) || (a.ListingId == id) || (a.listingId == id) || (a.listing_id == Number(id)));
            setApplied(Boolean(found));
          } catch (e) {
            // ignore application check errors
            setApplied(false);
          }
        } else {
          setApplied(false);
        }
        await refreshAvailableTechs(listingTechsData);
      } catch (err) {

        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, [id, techsPage, usersPage, currentUser?.Id, isAdmin]);

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
          <div style={{ marginTop: '0.75rem' }}>
            {applied ? (
              <button className="btn btn" disabled>Already applied</button>
            ) : (
              <button
                className="btn btn-primary"
                onClick={async () => {
                  if (!token) {
                    navigate('/login', { state: { from: location } });
                    return;
                  }
                  setApplying(true);
                  try {
                    await createJobApplication({ user_id: currentUser.Id, listing_id: Number(id) });
                    setApplied(true);
                    try {
                      const usersData = await getUsersByListing(id, { page: usersPage, size: pageSize });
                      setUsers(usersData?.content || []);
                      setUsersTotalPages(usersData?.page?.totalPages ?? 0);
                      setUsersPage(usersData?.page?.number ?? usersPage);
                    } catch (e) {
                      // ignore refresh errors
                    }
                    try {
                      const apps = await getJobApplicationsByUser(currentUser.Id);
                      const found = (apps || []).some(a => (a.listing_id == id) || (a.ListingId == id) || (a.listingId == id));
                      setApplied(Boolean(found));
                    } catch (e) {
                      // ignore
                    }
                  } catch (err) {
                    setError(err.message);
                  } finally {
                    setApplying(false);
                  }
                }}
                disabled={applying}
              >
                {applying ? 'Applying…' : 'Apply'}
              </button>
            )}
          </div>
          <div className="detail-meta">
            <div className="detail-meta-item">
              <span className="detail-meta-label">Experience Level</span>
              <span className="detail-meta-value">{listing.ExperienceLevel || 'N/A'}</span>
            </div>
            <div className="detail-meta-item">
              <span className="detail-meta-label">Salary</span>
              <span className="detail-meta-value">{listing.salary != null ? listing.salary : 'N/A'}</span>
            </div>
          </div>
          {listing.Description && (
            <div style={{ marginTop: '1.5rem', color: '#475569' }}>
              <h2 style={{ margin: '0 0 0.5rem', fontSize: '1.1rem' }}>Description</h2>
              <p style={{ margin: 0, lineHeight: 1.6 }}>{listing.Description}</p>
            </div>
          )}
        </div>
      </div>

      <div className="detail-section">
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <h2>Required Technologies ({techs.length})</h2>
          {isAdmin && (
            <button
              className="btn btn-primary"
              onClick={() => setShowAddTechs(!showAddTechs)}
            >
              {showAddTechs ? 'Hide Available' : 'Add Tech'}
            </button>
          )}
        </div>
        {showAddTechs && (
          <div className="detail-list" style={{ marginBottom: '1.5rem' }}>
            <h3>Available technologies</h3>
            {availableTechs.length === 0 ? (
              <div className="empty-state">
                <p>All technologies are already assigned to this listing.</p>
              </div>
            ) : (
              availableTechs
                .slice(availablePage * pageSize, availablePage * pageSize + pageSize)
                .map((tech) => (
                  <div key={tech.id} className="detail-item" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div>
                      <p className="detail-item-title">{tech.name}</p>
                      <p className="detail-item-text">Category: {tech.techCategory}</p>
                    </div>
                    <button
                      className="btn btn-primary btn-small"
                      onClick={async () => {
                        setAddingTechId(tech.id);
                        try {
                          await createJobListingTech({ listing_id: Number(id), tech_id: tech.id, IsRequired: false });
                          const techsData = await getTechsByListing(id, { page: 0, size: pageSize });
                          setTechs(techsData?.content || []);
                          setTechsTotalPages(techsData?.page?.totalPages ?? 0);
                          setTechsPage(0);
                          const listingTechsData = await getJobListingTechsByListing(id);
                          setListingTechs(listingTechsData || []);
                          await refreshAvailableTechs(listingTechsData);
                        } catch (err) {
                          setError(err.message);
                        } finally {
                          setAddingTechId(null);
                        }
                      }}
                      disabled={addingTechId === tech.id}
                      style={{ marginLeft: '1rem' }}
                    >
                      {addingTechId === tech.id ? 'Adding…' : 'Add'}
                    </button>
                  </div>
                ))
            )}
            {availableTotalPages > 1 && (
              <Pagination page={availablePage} totalPages={availableTotalPages} onPageChange={(p) => setAvailablePage(p)} />
            )}
          </div>
        )}
        {techs.length === 0 ? (
          <div className="empty-state">
            <p>No specific technologies required</p>
          </div>
        ) : (
          <div className="detail-list">
            {techs.map((tech) => {
              const association = listingTechs.find((item) => item.tech_id === tech.id);
              return (
                <div key={tech.id} className="detail-item" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <Link
                    to={`/techs/${tech.id}`}
                    style={{ textDecoration: 'none', color: 'inherit', flex: 1 }}
                  >
                    <div>
                      <p className="detail-item-title">{tech.name}</p>
                      <p className="detail-item-text">Category: {tech.techCategory}</p>
                    </div>
                  </Link>
                  {isAdmin && association && (
                    <button
                      className="btn btn-danger btn-small"
                      onClick={async () => {
                        setRemovingTechId(association.id);
                        try {
                          await deleteJobListingTech(association.id);
                          const updatedListingTechs = listingTechs.filter(item => item.id !== association.id);
                          setListingTechs(updatedListingTechs);
                          setTechs(prev => prev.filter(t => t.id !== tech.id));
                          await refreshAvailableTechs(updatedListingTechs);
                        } catch (err) {
                          setError(err.message);
                        } finally {
                          setRemovingTechId(null);
                        }
                      }}
                      disabled={removingTechId === association.id}
                      style={{ marginLeft: '1rem' }}
                    >
                      {removingTechId === association.id ? 'Removing…' : 'Remove'}
                    </button>
                  )}
                </div>
              );
            })}
          </div>
        )}
        {techsTotalPages > 1 && (
          <Pagination page={techsPage} totalPages={techsTotalPages} onPageChange={(p) => setTechsPage(p)} />
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
        {usersTotalPages > 1 && (
          <Pagination page={usersPage} totalPages={usersTotalPages} onPageChange={(p) => setUsersPage(p)} />
        )}
      </div>
    </div>
  );
}
