import { Navigate, useLocation } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext.jsx';

export default function RequireAuth({ children, adminOnly = false }) {
  const { token, loadingUser, isAdmin } = useAuth();
  const location = useLocation();

  if (loadingUser) {
    return <div className="status">Checking authentication…</div>;
  }

  if (!token) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  if (adminOnly && !isAdmin) {
    return (
      <section className="page">
        <div className="page-header">
          <h1>Not authorized</h1>
        </div>
        <div className="status status-error">
          You are not authorized to access this page.
        </div>
      </section>
    );
  }

  return children;
}
