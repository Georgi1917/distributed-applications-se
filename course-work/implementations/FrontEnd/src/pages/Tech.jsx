import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getTechs } from '../api.js';
import SearchSortBar from '../components/SearchSortBar.jsx';
import Pagination from '../components/Pagination.jsx';

export default function Tech() {
  const [techs, setTechs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchBy, setSearchBy] = useState('');
  const [sortBy, setSortBy] = useState('id');
  const [asc, setAsc] = useState(true);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const navigate = useNavigate();

  const fetchTechs = (pageIndex = 0) => {
    setLoading(true);
    setError(null);
    getTechs({ page: pageIndex, searchBy, sortBy, asc, size: 8 })
      .then((data) => {
        setTechs(data.content || []);
        setTotalPages(data.page?.totalPages ?? 0);
        setPage(data.page?.number ?? pageIndex);
      })
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchTechs();
  }, []);

  const handleSubmit = (event) => {
    event.preventDefault();
    fetchTechs(0);
  };

  const handlePageChange = (newPage) => {
    fetchTechs(newPage);
  };

  return (
    <section className="page">
      <h1>Technologies</h1>
      <p className="page-intro">Browse tech items exposed by the backend API.</p>
      <SearchSortBar
        label="technologies"
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
          { value: 'techCategory', label: 'Category' }
        ]}
      />
      {loading && <div className="status">Loading technologies…</div>}
      {error && <div className="status status-error">{error}</div>}
      {!loading && !error && (
        <>
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Category</th>
                </tr>
              </thead>
              <tbody>
                {techs.map((tech) => (
                  <tr key={tech.id} style={{ cursor: 'pointer' }} onClick={() => navigate(`/techs/${tech.id}`)}>
                    <td>{tech.id}</td>
                    <td>{tech.name}</td>
                    <td>{tech.techCategory}</td>
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
