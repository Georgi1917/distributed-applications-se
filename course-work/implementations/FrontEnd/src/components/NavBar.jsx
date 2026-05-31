import { NavLink, Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext.jsx';

const activeClass = ({ isActive }) => isActive ? 'nav-link active' : 'nav-link';

export default function NavBar() {
  const { token, logout, user } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  return (
    <header className="site-header">
      <div className="brand">Job Listing Portal</div>
      <nav>
        <NavLink to="/" className={activeClass}>Home</NavLink>
        <NavLink to="/companies" className={activeClass}>Companies And Users</NavLink>
        <NavLink to="/jobs" className={activeClass}>Job Listings</NavLink>
        <NavLink to="/tech" className={activeClass}>Technologies</NavLink>
        {token ? (
          <div style={{ display: 'flex', gap: '0.75rem', alignItems: 'center' }}>
            <Link to={`/users/${user?.Id}`} className="nav-link" style={{ textDecoration: 'none' }}>Hello {user?.Username}</Link>
            <button onClick={handleLogout} className="logout-btn">
              Logout
            </button>
          </div>
        ) : (
          <>
            <NavLink to="/login" className={activeClass}>Login</NavLink>
            <NavLink to="/register" className={activeClass}>Register</NavLink>
          </>
        )}
      </nav>
    </header>
  );
}
