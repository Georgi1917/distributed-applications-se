import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { register } from '../api.js';
import { useAuth } from '../contexts/AuthContext.jsx';

export default function Register() {
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { setUser } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setLoading(true);

    try {
      const response = await register(email, username, password);
      setUser(response);
      navigate('/login');
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <div className="form-card">
        <h1>Register</h1>
        <p className="form-subtitle">Create a new account</p>
        {error && <div className="form-error">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              id="username"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Enter your username"
              required
              minLength="6"
            />
          </div>
          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              required
              minLength="6"
            />
          </div>
          <button type="submit" className="form-submit" disabled={loading}>
            {loading ? 'Registering...' : 'Register'}
          </button>
        </form>
        <div className="form-link">
          Already have an account? <Link to="/login">Login here</Link>
        </div>
      </div>
    </div>
  );
}
