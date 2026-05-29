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
import CompanyCreate from './pages/CompanyCreate.jsx';
import CompanyEdit from './pages/CompanyEdit.jsx';
import CompanyDelete from './pages/CompanyDelete.jsx';
import JobListingCreate from './pages/JobListingCreate.jsx';
import JobListingEdit from './pages/JobListingEdit.jsx';
import JobListingDelete from './pages/JobListingDelete.jsx';
import TechCreate from './pages/TechCreate.jsx';
import TechEdit from './pages/TechEdit.jsx';
import TechDelete from './pages/TechDelete.jsx';
import UserCreate from './pages/UserCreate.jsx';
import UserEdit from './pages/UserEdit.jsx';
import UserDelete from './pages/UserDelete.jsx';

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
              <Route path="/companies/create" element={<CompanyCreate />} />
              <Route path="/companies/:id" element={<CompanyDetail />} />
              <Route path="/companies/:id/edit" element={<CompanyEdit />} />
              <Route path="/companies/:id/delete" element={<CompanyDelete />} />
              <Route path="/job-listings" element={<JobListings />} />
              <Route path="/job-listings/create" element={<JobListingCreate />} />
              <Route path="/job-listings/:id" element={<JobListingDetail />} />
              <Route path="/job-listings/:id/edit" element={<JobListingEdit />} />
              <Route path="/job-listings/:id/delete" element={<JobListingDelete />} />
              <Route path="/jobs" element={<JobListings />} />
              <Route path="/users" element={<Users />} />
              <Route path="/users/create" element={<UserCreate />} />
              <Route path="/users/:id" element={<UserDetail />} />
              <Route path="/users/:id/edit" element={<UserEdit />} />
              <Route path="/users/:id/delete" element={<UserDelete />} />
              <Route path="/tech" element={<Tech />} />
              <Route path="/tech/create" element={<TechCreate />} />
              <Route path="/techs/:id" element={<TechDetail />} />
              <Route path="/techs/:id/edit" element={<TechEdit />} />
              <Route path="/techs/:id/delete" element={<TechDelete />} />
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

