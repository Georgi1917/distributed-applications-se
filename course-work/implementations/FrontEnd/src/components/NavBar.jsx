import { NavLink } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext.jsx';

const activeClass = ({ isActive }) => isActive ? 'nav-link active' : 'nav-link';

export default function NavBar() {
  const { token, logout } = useAuth();

  const handleLogout = () => {
    logout();
  };

  return (
    <header className="site-header">
      <div className="brand">Job Listing Portal</div>
      <nav>
        <NavLink to="/" className={activeClass}>Home</NavLink>
        <NavLink to="/companies" className={activeClass}>Companies</NavLink>
        <NavLink to="/jobs" className={activeClass}>Job Listings</NavLink>
        <NavLink to="/users" className={activeClass}>Users</NavLink>
        <NavLink to="/tech" className={activeClass}>Technologies</NavLink>
        {token ? (
          <button onClick={handleLogout} className="nav-link logout-btn">
            Logout
          </button>
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
