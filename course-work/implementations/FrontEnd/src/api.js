const parseJson = async (response) => {
  const text = await response.text();
  if (!text) return null;
  try {
    return JSON.parse(text);
  } catch {
    return null;
  }
};

const fetchJson = async (path) => {
  const response = await fetch(path);
  if (!response.ok) {
    const error = await parseJson(response);
    throw new Error(error?.message ?? `Request failed: ${response.status} ${response.statusText}`);
  }
  return parseJson(response);
};

const buildQuery = ({ path, searchBy = '', sortBy = 'id', asc = true, size = 8, page = 0 }) => {
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

export const getJobListingsByCompany = (companyId) =>
  fetchJson(`/job_listing/?company_id=${companyId}&size=8`);

export const getTechsByListing = (listingId) =>
  fetchJson(`/tech/?listing_id=${listingId}&size=8`);

export const getUsersByListing = (listingId) =>
  fetchJson(`/user/?listing_id=${listingId}&size=8`);

export const getJobListingsByUser = (userId) =>
  fetchJson(`/job_listing/?user_id=${userId}&size=8`);

export const getJobListingsByTech = (techId) =>
  fetchJson(`/job_listing/?tech_id=${techId}&size=100`);

