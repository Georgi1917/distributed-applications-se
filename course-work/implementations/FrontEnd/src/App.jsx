import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext.jsx';
import NavBar from './components/NavBar.jsx';
import Home from './pages/Home.jsx';
import Companies from './pages/Companies.jsx';
import JobListings from './pages/JobListings.jsx';
import Users from './pages/Users.jsx';
import Tech from './pages/Tech.jsx';
import Login from './pages/Login.jsx';
import Register from './pages/Register.jsx';
import CompanyDetail from './pages/CompanyDetail.jsx';
import JobListingDetail from './pages/JobListingDetail.jsx';
import TechDetail from './pages/TechDetail.jsx';
import UserDetail from './pages/UserDetail.jsx';

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <div className="app-shell">
          <NavBar />
          <main>
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/companies" element={<Companies />} />
              <Route path="/companies/:id" element={<CompanyDetail />} />
              <Route path="/job-listings" element={<JobListings />} />
              <Route path="/job-listings/:id" element={<JobListingDetail />} />
              <Route path="/jobs" element={<JobListings />} />
              <Route path="/users" element={<Users />} />
              <Route path="/users/:id" element={<UserDetail />} />
              <Route path="/tech" element={<Tech />} />
              <Route path="/techs/:id" element={<TechDetail />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="*" element={<Home />} />
            </Routes>
          </main>
        </div>
      </BrowserRouter>
    </AuthProvider>
  );
}

