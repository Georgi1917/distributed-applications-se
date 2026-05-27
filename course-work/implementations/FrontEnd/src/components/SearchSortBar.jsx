export default function SearchSortBar({
  searchValue,
  onSearchChange,
  sortValue,
  onSortChange,
  ascValue,
  onAscChange,
  onSubmit,
  sortOptions,
  label
}) {
  return (
    <form className="search-sort-form" onSubmit={onSubmit}>
      <div className="control-group">
        <label>
          Search {label}
          <input
            className="search-input"
            type="search"
            value={searchValue}
            onChange={(event) => onSearchChange(event.target.value)}
            placeholder="Search by keyword"
          />
        </label>
      </div>
      <div className="control-group">
        <label>
          Sort by
          <select className="sort-select" value={sortValue} onChange={(event) => onSortChange(event.target.value)}>
            {sortOptions.map((option) => (
              <option key={option.value} value={option.value}>{option.label}</option>
            ))}
          </select>
        </label>
      </div>
      <div className="control-group control-group-inline">
        <label className="toggle-label">
          Order
          <select value={ascValue ? 'asc' : 'desc'} onChange={(event) => onAscChange(event.target.value === 'asc')}>
            <option value="asc">Ascending</option>
            <option value="desc">Descending</option>
          </select>
        </label>
        <button type="submit" className="button-primary">Apply</button>
      </div>
    </form>
  );
}
