import { Link } from 'react-router-dom';

const cards = [
  { title: 'Companies', description: 'Browse all companies in the system.', url: '/companies' },
  { title: 'Job Listings', description: 'View available job listings.', url: '/jobs' },
  { title: 'Users', description: 'Inspect registered users.', url: '/users' },
  { title: 'Technologies', description: 'See technology categories and items.', url: '/tech' }
];

export default function Home() {
  return (
    <section className="hero">
      <div className="hero-copy">
        <h1>Job Listing Dashboard</h1>
        <p>Explore companies, job listings, users, and technologies powered by the Spring Boot backend.</p>
      </div>
      <div className="card-grid">
        {cards.map((card) => (
          <Link key={card.title} to={card.url} className="card-link">
            <article className="card">
              <h2>{card.title}</h2>
              <p>{card.description}</p>
            </article>
          </Link>
        ))}
      </div>
    </section>
  );
}
