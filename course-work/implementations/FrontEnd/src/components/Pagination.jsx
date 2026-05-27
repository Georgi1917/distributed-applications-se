export default function Pagination({ page, totalPages, onPageChange }) {
  if (totalPages === 0) {
    return null;
  }

  const previousDisabled = page <= 0;
  const nextDisabled = page >= totalPages - 1;

  return (
    <div className="pagination">
      <button type="button" className="pagination-button" onClick={() => onPageChange(0)} disabled={previousDisabled}>
        First
      </button>
      <button type="button" className="pagination-button" onClick={() => onPageChange(page - 1)} disabled={previousDisabled}>
        Previous
      </button>
      <span className="pagination-label">
        Page {page + 1} of {totalPages}
      </span>
      <button type="button" className="pagination-button" onClick={() => onPageChange(page + 1)} disabled={nextDisabled}>
        Next
      </button>
      <button type="button" className="pagination-button" onClick={() => onPageChange(totalPages - 1)} disabled={nextDisabled}>
        Last
      </button>
    </div>
  );
}
