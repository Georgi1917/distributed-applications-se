import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getJobListingDetail, deleteJobListing } from '../api.js';

export default function JobListingDelete() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [listing, setListing] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const data = await getJobListingDetail(id);
        setListing(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [id]);

  const handleDelete = async () => {
    try {
      await deleteJobListing(id);
      navigate('/job-listings');
    } catch (err) {
      setError(err.message);
    }
  };

  if (loading) return <div className="page"><p>Loading...</p></div>;
  if (error) return <div className="page"><div className="status status-error">{error}</div></div>;
  if (!listing) return <div className="page"><p>Job listing not found</p></div>;

  return (
    <div className="page">
      <h1>Delete Job Listing</h1>
      <p>Are you sure you want to delete <strong>{listing.Name}</strong>?</p>
      <div className="form-actions">
        <button className="btn btn-danger" onClick={handleDelete}>Yes, delete</button>
        <button className="btn" onClick={() => navigate(`/job-listings/${id}`)}>Cancel</button>
      </div>
    </div>
  );
}
