import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getCompanies } from '../api.js';
import { useAuth } from '../contexts/AuthContext.jsx';
import SearchSortBar from '../components/SearchSortBar.jsx';
import Pagination from '../components/Pagination.jsx';

export default function Companies() {
  const [companies, setCompanies] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchBy, setSearchBy] = useState('');
  const [sortBy, setSortBy] = useState('id');
  const [asc, setAsc] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const { isAdmin } = useAuth();

  const fetchCompanies = (pageIndex = 0) => {
    setLoading(true);
    setError(null);
    getCompanies({ page: pageIndex, searchBy, sortBy, asc, size: 6 })
      .then((data) => {
        setCompanies(data.content || []);
        setTotalPages(data.page?.totalPages ?? 0);
        setPage(data.page?.number ?? pageIndex);
      })
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchCompanies();
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    fetchCompanies(0);
  };

  const handlePageChange = (newPage) => {
    fetchCompanies(newPage);
  };

  return (
    <section className="page">
      <div className="page-header">
        <h1>All Companies</h1>
        <div className="page-actions">
          {isAdmin && <Link to="/companies/create" className="btn btn-primary">Create Company</Link>}
        </div>
      </div>
      <p className="page-intro">This page loads company data from the backend.</p>
      <SearchSortBar
        label="companies"
        searchValue={searchBy}
        onSearchChange={setSearchBy}
        sortValue={sortBy}
        onSortChange={setSortBy}
        ascValue={asc}
        onAscChange={setAsc}
        onSubmit={handleSubmit}
        sortOptions={[
          { value: 'id', label: 'ID' },
          { value: 'companyName', label: 'Name' },
          { value: 'employeeCount', label: 'Employees' },
          { value: 'type', label: 'Type' },
          { value: 'companyRemotePolicy', label: 'Remote policy' },
          { value: 'isHiring', label: 'Hiring' }
        ]}
      />
      {loading && <div className="status">Loading companies…</div>}
      {error && <div className="status status-error">{error}</div>}
      {!loading && !error && (
        <>
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Employees</th>
                  <th>Type</th>
                  <th>Remote</th>
                  <th>Hiring</th>
                  {isAdmin && <th>Actions</th>}
                </tr>
              </thead>
              <tbody>
                {companies.map((company) => (
                  <tr key={company.Id} style={{ cursor: 'pointer' }} onClick={() => window.location.href = `/companies/${company.Id}`}>
                    <td>{company.Id}</td>
                    <td>{company.CompanyName}</td>
                    <td>{company.EmployeeCount}</td>
                    <td>{company.Type}</td>
                    <td>{company.CompanyRemotePolicy}</td>
                    <td>{company.IsHiring ? 'Yes' : 'No'}</td>
                    {isAdmin && (
                      <td style={{ display: 'flex', gap: '0.5rem' }}>
                        <Link to={`/companies/${company.Id}/edit`} className="btn btn-small" onClick={(e) => e.stopPropagation()} style={{ background: '#3b82f6', color: 'white' }}>Edit</Link>
                        <Link to={`/companies/${company.Id}/delete`} className="btn btn-danger btn-small" onClick={(e) => e.stopPropagation()}>Delete</Link>
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
