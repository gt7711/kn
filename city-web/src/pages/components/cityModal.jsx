import React from 'react';
import './cityModal.css';

function CityModal({ selectedCity, setSelectedCity, handleModalClose,handleCityUpdate }) {
  return (
    <div className="modal-container">
      <div className="modal">
        <h2>Edit City</h2>
        <form onSubmit={(e) => e.preventDefault()}>
          <label>
            Name:
            <input
              type="text"
              value={selectedCity.name}
              onChange={(e) => setSelectedCity({ ...selectedCity, name: e.target.value })}
            />
          </label>
          <label>
            Image:
            <input
              type="text"
              value={selectedCity.image}
              onChange={(e) =>
                setSelectedCity({ ...selectedCity, image: e.target.value })
              }
            />
          </label>
          <div className="modal-buttons">
            <button onClick={handleModalClose}>Cancel</button>
            <button onClick={() => handleCityUpdate(selectedCity)}>Save</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default CityModal;