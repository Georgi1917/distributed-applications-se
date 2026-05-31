import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getCompanies, getUsers } from '../api.js';
import { useAuth } from '../contexts/AuthContext.jsx';
import Pagination from '../components/Pagination.jsx';

const companySortOptions = [
  { value: 'id', label: 'Default' },
  { value: 'companyName', label: 'Name' },
  { value: 'employeeCount', label: 'Employees' },
  { value: 'type', label: 'Type' },
  { value: 'companyRemotePolicy', label: 'Remote policy' },
  { value: 'isHiring', label: 'Hiring' }
];

const userSortOptions = [
  { value: 'id', label: 'Default' },
  { value: 'email', label: 'Email' },
  { value: 'username', label: 'Username' },
  { value: 'role', label: 'Role' }
];

const searchModes = [
  { value: 'all', label: 'All' },
  { value: 'companies', label: 'Companies only' },
  { value: 'users', label: 'Users only' }
];

export default function Companies() {
  const [companies, setCompanies] = useState([]);
  const [users, setUsers] = useState([]);
  const [loadingCompanies, setLoadingCompanies] = useState(true);
  const [loadingUsers, setLoadingUsers] = useState(true);
  const [error, setError] = useState(null);
  const [searchBy, setSearchBy] = useState('');
  const [searchMode, setSearchMode] = useState('all');
  const [companySortBy, setCompanySortBy] = useState('id');
  const [companyAsc, setCompanyAsc] = useState(true);
  const [userSortBy, setUserSortBy] = useState('id');
  const [userAsc, setUserAsc] = useState(true);
  const [companyPage, setCompanyPage] = useState(0);
  const [companyTotalPages, setCompanyTotalPages] = useState(0);
  const [userPage, setUserPage] = useState(0);
  const [userTotalPages, setUserTotalPages] = useState(0);
  const { isAdmin, user: currentUser } = useAuth();

  const fetchCompanies = async (pageIndex = 0, query = '') => {
    setLoadingCompanies(true);
    setError(null);
    try {
      const data = await getCompanies({ page: pageIndex, searchBy: query, sortBy: companySortBy, asc: companyAsc, size: 6 });
      setCompanies(data.content || []);
      setCompanyTotalPages(data.page?.totalPages ?? 0);
      setCompanyPage(data.page?.number ?? pageIndex);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoadingCompanies(false);
    }
  };

  const fetchUsers = async (pageIndex = 0, query = '') => {
    setLoadingUsers(true);
    setError(null);
    try {
      const data = await getUsers({ page: pageIndex, searchBy: query, sortBy: userSortBy, asc: userAsc, size: 6 });
      setUsers(data.content || []);
      setUserTotalPages(data.page?.totalPages ?? 0);
      setUserPage(data.page?.number ?? pageIndex);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoadingUsers(false);
    }
  };

  const runFetch = (companyQuery, userQuery) => {
    fetchCompanies(0, companyQuery);
    fetchUsers(0, userQuery);
  };

  useEffect(() => {
    runFetch('', '');
  }, []);

  const handleSearchSubmit = (event) => {
    event.preventDefault();
    const companyQuery = searchMode !== 'users' ? searchBy : '';
    const userQuery = searchMode !== 'companies' ? searchBy : '';
    setCompanyPage(0);
    setUserPage(0);
    runFetch(companyQuery, userQuery);
  };

  const handleCompanySortChange = (value) => {
    setCompanySortBy(value);
    setCompanyPage(0);
    const query = searchMode !== 'users' ? searchBy : '';
    fetchCompanies(0, query);
  };

  const handleUserSortChange = (value) => {
    setUserSortBy(value);
    setUserPage(0);
    const query = searchMode !== 'companies' ? searchBy : '';
    fetchUsers(0, query);
  };

  const handleCompanyOrderChange = (value) => {
    const nextAsc = value === 'asc';
    setCompanyAsc(nextAsc);
    setCompanyPage(0);
    const query = searchMode !== 'users' ? searchBy : '';
    fetchCompanies(0, query);
  };

  const handleUserOrderChange = (value) => {
    const nextAsc = value === 'asc';
    setUserAsc(nextAsc);
    setUserPage(0);
    const query = searchMode !== 'companies' ? searchBy : '';
    fetchUsers(0, query);
  };

  const handleCompanyPageChange = (newPage) => {
    const query = searchMode !== 'users' ? searchBy : '';
    fetchCompanies(newPage, query);
  };

  const handleUserPageChange = (newPage) => {
    const query = searchMode !== 'companies' ? searchBy : '';
    fetchUsers(newPage, query);
  };

  return (
    <section className="page">
      <div className="page-header">
        <div>
          <h1>Companies & Users</h1>
          <p className="page-intro">Search and sort both companies and users from a single page.</p>
        </div>
        <div className="page-actions" style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
          {isAdmin && <Link to="/companies/create" className="btn btn-primary">Create Company</Link>}
          {isAdmin && <Link to="/users/create" className="btn btn-primary">Create User</Link>}
        </div>
      </div>

      <form className="form" onSubmit={handleSearchSubmit} style={{ marginBottom: '1rem', display: 'flex', flexWrap: 'wrap', gap: '1rem', alignItems: 'flex-end' }}>
        <div className="form-group" style={{ flex: '1 1 320px' }}>
          <label htmlFor="list-search">Search companies and users</label>
          <input
            id="list-search"
            type="search"
            value={searchBy}
            onChange={(event) => setSearchBy(event.target.value)}
            placeholder="Search by keyword"
          />
        </div>
        <div className="form-group" style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
          <span style={{ fontSize: '0.9rem', color: '#475569' }}>Search target</span>
          <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
            {searchModes.map((mode) => (
              <button
                key={mode.value}
                type="button"
                onClick={() => setSearchMode(mode.value)}
                className={searchMode === mode.value ? 'btn btn-secondary' : 'btn'}
                style={searchMode === mode.value ? { backgroundColor: '#1d4ed8', color: 'white' } : {}}
              >
                {mode.label}
              </button>
            ))}
          </div>
        </div>
        <div className="form-actions" style={{ alignSelf: 'flex-end' }}>
          <button type="submit" className="btn btn-primary">Apply Search</button>
        </div>
      </form>

      {error && <div className="status status-error">{error}</div>}

      <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem', alignItems: 'start' }}>
        <div className="panel" style={{ minWidth: 0 }}>
          <div style={{ marginBottom: '1rem' }}>
            <h2>Companies</h2>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '0.75rem', marginTop: '0.75rem', alignItems: 'center' }}>
              <label style={{ display: 'flex', flexDirection: 'column', gap: '0.35rem', fontSize: '0.9rem', color: '#334155' }}>
                Sort by
                <select value={companySortBy} onChange={(event) => handleCompanySortChange(event.target.value)} style={{ minWidth: '10rem', padding: '0.5rem', borderRadius: '0.35rem', border: '1px solid #cbd5e1' }}>
                  {companySortOptions.map((option) => (
                    <option key={option.value} value={option.value}>{option.label}</option>
                  ))}
                </select>
              </label>
              <label style={{ display: 'flex', flexDirection: 'column', gap: '0.35rem', fontSize: '0.9rem', color: '#334155' }}>
                Order
                <select value={companyAsc ? 'asc' : 'desc'} onChange={(event) => handleCompanyOrderChange(event.target.value)} style={{ minWidth: '10rem', padding: '0.5rem', borderRadius: '0.35rem', border: '1px solid #cbd5e1' }}>
                  <option value="asc">Ascending</option>
                  <option value="desc">Descending</option>
                </select>
              </label>
            </div>
          </div>

          {loadingCompanies ? (
            <div className="status">Loading companies…</div>
          ) : companies.length === 0 ? (
            <div className="empty-state"><p>No companies found</p></div>
          ) : (
            <>
              <div className="table-wrapper">
                <table>
                  <thead>
                    <tr>
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
              <Pagination page={companyPage} totalPages={companyTotalPages} onPageChange={handleCompanyPageChange} />
            </>
          )}
        </div>

        <div className="panel" style={{ minWidth: 0 }}>
          <div style={{ marginBottom: '1rem' }}>
            <h2>Users</h2>
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '0.75rem', marginTop: '0.75rem', alignItems: 'center' }}>
              <label style={{ display: 'flex', flexDirection: 'column', gap: '0.35rem', fontSize: '0.9rem', color: '#334155' }}>
                Sort by
                <select value={userSortBy} onChange={(event) => handleUserSortChange(event.target.value)} style={{ minWidth: '10rem', padding: '0.5rem', borderRadius: '0.35rem', border: '1px solid #cbd5e1' }}>
                  {userSortOptions.map((option) => (
                    <option key={option.value} value={option.value}>{option.label}</option>
                  ))}
                </select>
              </label>
              <label style={{ display: 'flex', flexDirection: 'column', gap: '0.35rem', fontSize: '0.9rem', color: '#334155' }}>
                Order
                <select value={userAsc ? 'asc' : 'desc'} onChange={(event) => handleUserOrderChange(event.target.value)} style={{ minWidth: '10rem', padding: '0.5rem', borderRadius: '0.35rem', border: '1px solid #cbd5e1' }}>
                  <option value="asc">Ascending</option>
                  <option value="desc">Descending</option>
                </select>
              </label>
            </div>
          </div>

          {loadingUsers ? (
            <div className="status">Loading users…</div>
          ) : users.length === 0 ? (
            <div className="empty-state"><p>No users found</p></div>
          ) : (
            <>
              <div className="table-wrapper">
                <table>
                  <thead>
                    <tr>
                      <th>Email</th>
                      <th>Username</th>
                      <th>Role</th>
                      {isAdmin && <th>Actions</th>}
                    </tr>
                  </thead>
                  <tbody>
                    {users.map((user) => (
                      <tr key={user.Id} style={{ cursor: 'pointer' }} onClick={() => window.location.href = `/users/${user.Id}`}>
                        <td>{user.Email}</td>
                        <td>{user.Username}</td>
                        <td>{user.Role}</td>
                        {isAdmin && (
                          <td style={{ display: 'flex', gap: '0.5rem' }}>
                            {user.Id !== currentUser?.Id && (
                              <>
                                <Link to={`/users/${user.Id}/edit`} className="btn btn-small" onClick={(e) => e.stopPropagation()} style={{ background: '#3b82f6', color: 'white' }}>Edit</Link>
                                <Link to={`/users/${user.Id}/delete`} className="btn btn-danger btn-small" onClick={(e) => e.stopPropagation()}>Delete</Link>
                              </>
                            )}
                          </td>
                        )}
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
              <Pagination page={userPage} totalPages={userTotalPages} onPageChange={handleUserPageChange} />
            </>
          )}
        </div>
      </div>
    </section>
  );
}
