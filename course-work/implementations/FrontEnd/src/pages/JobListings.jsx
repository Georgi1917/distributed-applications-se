import { useEffect, useState } from 'react';
import { getJobListings } from '../api.js';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext.jsx';
import SearchSortBar from '../components/SearchSortBar.jsx';
import Pagination from '../components/Pagination.jsx';

export default function JobListings() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchBy, setSearchBy] = useState('');
  const [sortBy, setSortBy] = useState('id');
  const [asc, setAsc] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const { isAdmin } = useAuth();

  const fetchJobListings = (pageIndex = 0) => {
    setLoading(true);
    setError(null);
    getJobListings({ page: pageIndex, searchBy, sortBy, asc, size: 8 })
      .then((data) => {
        setJobs(data.content || []);
        setTotalPages(data.page?.totalPages ?? 0);
        setPage(data.page?.number ?? pageIndex);
      })
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchJobListings();
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    fetchJobListings(0);
  };

  const handlePageChange = (newPage) => {
    fetchJobListings(newPage);
  };

  return (
    <section className="page">
      <div className="page-header">
        <h1>Job Listings</h1>
        <div className="page-actions">
          {isAdmin && <Link to="/job-listings/create" className="btn btn-primary">Create Listing</Link>}
        </div>
      </div>
      <p className="page-intro">Review job listings returned by the backend API.</p>
      <SearchSortBar
        label="job listings"
        searchValue={searchBy}
        onSearchChange={setSearchBy}
        sortValue={sortBy}
        onSortChange={setSortBy}
        ascValue={asc}
        onAscChange={setAsc}
        onSubmit={handleSubmit}
        sortOptions={[
          { value: 'id', label: 'ID' },
          { value: 'name', label: 'Name' },
          { value: 'experienceLevel', label: 'Experience' }
        ]}
      />
      {loading && <div className="status">Loading job listings…</div>}
      {error && <div className="status status-error">{error}</div>}
      {!loading && !error && (
        <>
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Experience</th>
                  <th>Company ID</th>
                  {isAdmin && <th>Actions</th>}
                </tr>
              </thead>
              <tbody>
                {jobs.map((job) => (
                  <tr key={job.Id} style={{ cursor: 'pointer' }} onClick={() => window.location.href = `/job-listings/${job.Id}`}>
                    <td>{job.Id}</td>
                    <td>{job.Name}</td>
                    <td>{job.ExperienceLevel}</td>
                    <td>{job.company_id}</td>
                    {isAdmin && (
                      <td style={{ display: 'flex', gap: '0.5rem' }}>
                        <Link to={`/job-listings/${job.Id}/edit`} className="btn btn-small" onClick={(e) => e.stopPropagation()} style={{ background: '#3b82f6', color: 'white' }}>Edit</Link>
                        <Link to={`/job-listings/${job.Id}/delete`} className="btn btn-danger btn-small" onClick={(e) => e.stopPropagation()}>Delete</Link>
                      </td>
                    )}
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          <Pagination page={page} totalPages={totalPages} onPageChange={handlePageChange} />
        </>
      )}
    </section>
  );
}
