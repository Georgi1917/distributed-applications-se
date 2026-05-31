const parseJson = async (response) => {
  const text = await response.text();
  if (!text) return null;
  try {
    return JSON.parse(text);
  } catch {
    return null;
  }
};

const getAuthHeaders = () => {
  const token = localStorage.getItem('auth_token');
  return token ? { Authorization: `Bearer ${token}` } : {};
};

const fetchJson = async (path, options = {}) => {
  const response = await fetch(path, {
    ...options,
    headers: {
      ...getAuthHeaders(),
      ...options.headers
    }
  });
  if (!response.ok) {
    const error = await parseJson(response);
    throw new Error(error?.message ?? `Request failed: ${response.status} ${response.statusText}`);
  }
  return parseJson(response);
};

const buildQuery = ({ path, searchBy = '', sortBy = 'id', asc = true, size = 6, page = 0 }) => {
  const params = new URLSearchParams();
  params.set('page', String(page));
  params.set('size', String(size));
  params.set('sortBy', sortBy);
  params.set('asc', String(asc));
  if (searchBy.trim()) {
    params.set('searchBy', searchBy.trim());
  }
  return `${path}?${params.toString()}`;
};

export const getCompanies = (options = {}) => fetchJson(buildQuery({ path: '/company/', ...options }));
export const getJobListings = (options = {}) => fetchJson(buildQuery({ path: '/job_listing/', ...options }));
export const getUsers = (options = {}) => fetchJson(buildQuery({ path: '/user/', ...options }));
export const getTechs = (options = {}) => fetchJson(buildQuery({ path: '/tech/', ...options }));

export const login = async (username, password) => {
  const response = await fetch('/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  });
  if (!response.ok) {
    const error = await parseJson(response);
    throw new Error(error?.message || 'Login failed');
  }
  return parseJson(response);
};

export const register = async (email, username, password) => {
  const response = await fetch('/auth/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, username, password })
  });
  if (!response.ok) {
    const error = await parseJson(response);
    throw new Error(error?.message || 'Registration failed');
  }
  return parseJson(response);
};

// Detail endpoints
export const getCompanyDetail = (id) => fetchJson(`/company/${id}`);

export const getJobListingDetail = (id) => fetchJson(`/job_listing/${id}`);

export const getTechDetail = (id) => fetchJson(`/tech/${id}`);

export const getUserDetail = (id) => fetchJson(`/user/${id}`);

export const getUserByUsername = (username) => fetchJson(`/user/username/${username}`);

// Create / Update / Delete helpers
const sendJson = async (path, method, body) => {
  const response = await fetch(path, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...getAuthHeaders()
    },
    body: JSON.stringify(body)
  });
  if (!response.ok) {
    const error = await parseJson(response);
    throw new Error(error?.message ?? `Request failed: ${response.status}`);
  }
  return parseJson(response);
};

export const createCompany = (data) => sendJson('/company/', 'POST', data);
export const updateCompany = (id, data) => sendJson(`/company/update/${id}`, 'PUT', data);
export const deleteCompany = (id) => sendJson(`/company/delete/${id}`, 'DELETE');

export const createJobListing = (data) => sendJson('/job_listing/', 'POST', data);
export const updateJobListing = (id, data) => sendJson(`/job_listing/update/${id}`, 'PUT', data);
export const deleteJobListing = (id) => sendJson(`/job_listing/delete/${id}`, 'DELETE');

export const createTech = (data) => sendJson('/tech/', 'POST', data);
export const updateTech = (id, data) => sendJson(`/tech/update/${id}`, 'PUT', data);
export const deleteTech = (id) => sendJson(`/tech/delete/${id}`, 'DELETE');

export const createUser = (data) => sendJson('/user/', 'POST', data);
export const updateUser = (id, data) => sendJson(`/user/update/${id}`, 'PUT', data);
export const deleteUser = (id) => sendJson(`/user/delete/${id}`, 'DELETE');

export const getJobListingsByCompany = (companyId, { page = 0, size = 6 } = {}) =>
  fetchJson(`/job_listing/?company_id=${companyId}&page=${page}&size=${size}`);

export const getTechsByListing = (listingId, { page = 0, size = 6 } = {}) =>
  fetchJson(`/tech/?listing_id=${listingId}&page=${page}&size=${size}`);

export const getUsersByListing = (listingId, { page = 0, size = 6 } = {}) =>
  fetchJson(`/user/?listing_id=${listingId}&page=${page}&size=${size}`);

export const getJobListingsByUser = (userId, { page = 0, size = 6 } = {}) =>
  fetchJson(`/job_listing/?user_id=${userId}&page=${page}&size=${size}`);

export const getJobListingsByTech = (techId, { page = 0, size = 6 } = {}) =>
  fetchJson(`/job_listing/?tech_id=${techId}&page=${page}&size=${size}`);

export const createJobApplication = (data) => sendJson('/job_application/', 'POST', data);

export const getJobApplicationsByListing = (listingId) => fetchJson(`/job_application/by_listing/${listingId}`);

export const getJobApplicationsByUser = (userId) => fetchJson(`/job_application/by_user/${userId}`);

export const deleteJobApplication = (id) => sendJson(`/job_application/delete/${id}`, 'DELETE');

export const getJobListingTechsByListing = (listingId) => fetchJson(`/job_listing_tech/by_listing/${listingId}`);


export const deleteJobListingTech = (id) => sendJson(`/job_listing_tech/delete/${id}`, 'DELETE');

export const createJobListingTech = (data) => sendJson('/job_listing_tech/', 'POST', data);

