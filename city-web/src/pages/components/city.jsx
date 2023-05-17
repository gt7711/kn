import React from 'react';
import './city.css';

function City({ name,image,onEdit }) {
  return (
    <div className="city" onClick={onEdit}>
      <img src={image} alt="city-logo" style={{ width: '100%', height: '90%' }} onError={({ currentTarget }) => {
        currentTarget.onerror = null; // prevents looping
        currentTarget.src = "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg";
      }}/>
      <label>{name}</label>
    </div>
  );
}

export default City;