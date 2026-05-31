import { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { getUsers } from '../api.js';
import { useAuth } from '../contexts/AuthContext.jsx';
import SearchSortBar from '../components/SearchSortBar.jsx';
import Pagination from '../components/Pagination.jsx';

export default function Users() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchBy, setSearchBy] = useState('');
  const [sortBy, setSortBy] = useState('id');
  const [asc, setAsc] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();
  const { isAdmin, user: currentUser } = useAuth();

  const fetchUsers = (pageIndex = 0) => {
    setLoading(true);
    setError(null);
    getUsers({ page: pageIndex, searchBy, sortBy, asc, size: 6 })
      .then((data) => {
        setUsers(data.content || []);
        setTotalPages(data.page?.totalPages ?? 0);
        setPage(data.page?.number ?? pageIndex);
      })
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    fetchUsers(0);
  };

  const handlePageChange = (newPage) => {
    fetchUsers(newPage);
  };

  return (
    <section className="page">
      <div className="page-header">
        <h1>Users</h1>
        <div className="page-actions">
          {isAdmin && <Link to="/users/create" className="btn btn-primary">Create User</Link>}
        </div>
      </div>
      <p className="page-intro">See user accounts and roles from the backend.</p>
      <SearchSortBar
        label="users"
        searchValue={searchBy}
        onSearchChange={setSearchBy}
        sortValue={sortBy}
        onSortChange={setSortBy}
        ascValue={asc}
        onAscChange={setAsc}
        onSubmit={handleSubmit}
        sortOptions={[
          { value: 'id', label: 'ID' },
          { value: 'email', label: 'Email' },
          { value: 'username', label: 'Username' },
          { value: 'role', label: 'Role' }
        ]}
      />
      {loading && <div className="status">Loading users…</div>}
      {error && <div className="status status-error">{error}</div>}
      {!loading && !error && (
        <>
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Email</th>
                  <th>Username</th>
                  <th>Role</th>
                  {isAdmin && <th>Actions</th>}
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.Id} style={{ cursor: 'pointer' }} onClick={() => navigate(`/users/${user.Id}`)}>
                    <td>{user.Id}</td>
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
          <Pagination page={page} totalPages={totalPages} onPageChange={handlePageChange} />
        </>
      )}
    </section>
  );
}
