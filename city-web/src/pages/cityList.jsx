import React, { useState, useEffect } from "react";
import axios from "axios";
import "./cityList.css";
import City from "./components/city";
import CityModal from "./components/cityModal";

const CityList = () => {
  const [cities, setCities] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(0);
  const [citiesPerPage, setCitiesPerPage] = useState(25);
  const [selectedCity, setSelectedCity] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [totalPages, setTotalPages] = useState(1000);

  useEffect(() => {
    const fetchCities = async () => {
      var config = {
        headers: { 'Access-Control-Allow-Origin': '*' }
      };
      const response = await axios.get(
        `/api/v1/cities?searchTerm=${searchTerm}&page=${currentPage}&size=${citiesPerPage}`,config
      );
      setCities(response.data.content);
      setTotalPages(response.data.totalPages);
    };
    fetchCities();
  }, [searchTerm, currentPage, citiesPerPage,isModalOpen]);

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
    setCurrentPage(0);
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleCitiesPerPageChange = (event) => {
    setCitiesPerPage(event.target.value);
    setCurrentPage(0);
  };

  const handleCityEdit = (city) => {
    setSelectedCity(city);
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setSelectedCity(null);
    setIsModalOpen(false);
  };

  const handleCityUpdate = async (updatedCity) => {
    try {
      await axios.put(`/api/v1/cities/${updatedCity.id}`, updatedCity,{
        withCredentials: false
      });
      setSelectedCity(null);
      setIsModalOpen(false);
      alert("City updated successfully!");
    } catch (error) {
      console.error(error);
      alert(error.response.data.message);
    }
  };
 
  return (
    <div className="city-list-container">
        <div className="controls">
          <div className="search-container">
            <input
              type="text"
              placeholder="Search by city name"
              value={searchTerm}
              onChange={handleSearch}
              className="search-box"
            />
            <button className="search-button">Search</button>
          </div>
          <div className="pagination-container">
            <label className="cities-per-page-label">Cities per page:</label>
            <select
              value={citiesPerPage}
              onChange={handleCitiesPerPageChange}
              className="cities-per-page-select"
            >
              <option value={25}>25</option>
              <option value={50}>50</option>
              <option value={100}>100</option>
              <option value={200}>200</option>
            </select>
            <ul className="pagination">
              <li className={currentPage === 0 ? "inactive" : ""}>
                <button disabled={currentPage === 0} onClick={() => handlePageChange(currentPage - 1)}>Previous</button>
              </li>
              <li className="active">
                <button onClick={() => handlePageChange(currentPage)}>{currentPage + 1}</button>
              </li>
              <li className={currentPage === totalPages - 1 ? "inactive" : ""}>
                <button disabled={currentPage === totalPages - 1} onClick={() => handlePageChange(currentPage + 1)}>Next</button>
              </li>
            </ul>
          </div>
        </div>
      <div className="city-list">
        {cities !=null && cities.map((city) => (
          <City name={city.name} image={city.image} onEdit={()=>handleCityEdit(city)}></City>
    ))}
  </div>
      
  {isModalOpen && 
    <CityModal 
      selectedCity={selectedCity} 
      setSelectedCity={setSelectedCity} 
      handleModalClose={handleModalClose} 
      handleCityUpdate={handleCityUpdate}>
    </CityModal>}
</div>
);
};
export default CityList;