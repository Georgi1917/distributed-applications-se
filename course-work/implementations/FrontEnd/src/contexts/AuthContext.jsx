import { createContext, useState, useContext, useEffect } from 'react';
import { getUserByUsername } from '../api.js';

const AuthContext = createContext(null);

const parseJwt = (token) => {
  if (!token) return null;
  try {
    const [, payload] = token.split('.');
    if (!payload) return null;
    const padded = payload.padEnd(payload.length + ((4 - (payload.length % 4)) % 4), '=');
    const base64 = padded.replace(/-/g, '+').replace(/_/g, '/');
    const json = decodeURIComponent(
      Array.from(atob(base64), (c) => `%${c.charCodeAt(0).toString(16).padStart(2, '0')}`).join('')
    );
    return JSON.parse(json);
  } catch {
    return null;
  }
};

const normalizeRoles = (claims) => {
  if (!claims) return [];
  if (typeof claims.Role === 'string') return [claims.Role];
  if (typeof claims.role === 'string') return [claims.role];
  if (Array.isArray(claims.Role)) return claims.Role;
  if (Array.isArray(claims.role)) return claims.role;
  if (Array.isArray(claims.roles)) return claims.roles;
  if (Array.isArray(claims.authorities)) return claims.authorities;
  return [];
};

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('auth_token'));
  const [user, setUser] = useState(null);
  const [loadingUser, setLoadingUser] = useState(false);

  useEffect(() => {
    const loadUserFromToken = async () => {
      if (!token) {
        localStorage.removeItem('auth_token');
        setUser(null);
        setLoadingUser(false);
        return;
      }

      localStorage.setItem('auth_token', token);
      setLoadingUser(true);
      setUser(null);

      const claims = parseJwt(token);
      const username = claims?.sub || claims?.username;
      if (!username) {
        setLoadingUser(false);
        return;
      }

      try {
        const found = await getUserByUsername(username);
        setUser(found || null);
      } catch {
        setUser(null);
      } finally {
        setLoadingUser(false);
      }
    };

    loadUserFromToken();
  }, [token]);

  const isAdmin = Boolean(
    user && normalizeRoles(user).some((role) => /admin/i.test(String(role)))
  );

  const login = (authToken) => {
    setToken(authToken);
  };

  const logout = () => {
    setToken(null);
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ token, user, setUser, login, logout, isAdmin, loadingUser }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
}
