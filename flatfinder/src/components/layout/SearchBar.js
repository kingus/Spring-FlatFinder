import React, { useState } from "react";
// import "./Apartaments.css";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faSearch,
  faPlus,
  faMinus,
  faTimes,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./SearchBar.css";
import Select from "react-select";

const SearchBar = (props) => {
  const [districtsList, setDistrictsList] = useState([
    { value: "bemowo", label: "Bemowo" },
    { value: "bialoleka", label: "Białołeka" },
    { value: "bielany", label: "Bielany" },
    { value: "ochota", label: "Ochota" },
    { value: "praga-poludnie", label: "Praga-południe" },
    { value: "praga-polnoc", label: "Praga-północ" },
    { value: "rembertow", label: "Rembertow" },
    { value: "targowek", label: "Targowek" },
    { value: "ursus", label: "Ursus" },
    { value: "ursynow", label: "Ursynow" },
    { value: "wawer", label: "Wawer" },
    { value: "wesola", label: "Wesola" },
    { value: "wilanow", label: "Wilanow" },
    { value: "wola", label: "Wola" },
    { value: "wlochy", label: "Włochy" },
    { value: "srodmiescie", label: "Środmieście" },
    { value: "zoliborz", label: "Żoliborz" },
  ]);
  const [selectedDistricts, setSelectedDistricts] = useState();

  const [rooms, setRooms] = useState(0);
  const [area, setArea] = useState({
    min: "",
    max: "",
  });
  const [price, setPrice] = useState({
    min: "",
    max: "",
  });
  const [pricePerM, setPricePerM] = useState({
    min: "",
    max: "",
  });
  const [description, setDesctiption] = useState("");
  const [district, setDistrict] = useState("");

  library.add(faSearch, faPlus, faMinus, faTimes);

  const handleChangeDescription = (enteredDescription) => {
    setDesctiption(enteredDescription);
  };
  const handleChangeDistrict = (enteredDescription) => {
    setDistrict(enteredDescription);
  };

  return (
    <div className="main-container">
      <div className="search-bar">
        <div className="search-description">
          <h5>Title</h5>
          <div className="row">
            <input
              className="description-input"
              value={description}
              onChange={(e) => {
                handleChangeDescription(e.target.value);
              }}
            ></input>
            <div
              className="close-icon"
              onClick={() => {
                setDesctiption("");
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>

        {/* <div className="search-description">
          <h5>Rooms</h5>
          <div className="row rooms">
            <div
              className="plus"
              onClick={() => {
                setRooms(rooms - 1);
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "minus"]}
                style={{ fontSize: "0.5em" }}
                color="#dddddd"
              />
            </div>
            <h5 value={rooms}>{rooms}</h5>

            <div
              className="plus"
              onClick={() => {
                setRooms(rooms + 1);
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "plus"]}
                color="#dddddd"
                style={{ fontSize: "0.5em" }}
              />
            </div>
            <div
              className="close-icon"
              onClick={() => {
                setRooms(0);
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div> */}

        <div className="search-description">
          <h5>Area</h5>

          <div className="row">
            <div className="input-container-area">
              <input
                value={area.min}
                className="area-input"
                onChange={(e) => {
                  setArea({ ...area, min: e.target.value });
                }}
              ></input>
              <h5>m²</h5>
            </div>

            <h5>-</h5>
            <div className="input-container-area">
              <input
                value={area.max}
                className="area-input"
                onChange={(e) => setArea({ ...area, max: e.target.value })}
              ></input>
              <h5>m²</h5>
            </div>
            <div
              className="close-icon"
              onClick={() => {
                setArea({ min: "", max: "" });
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>
        <div className="search-description">
          <h5>Price</h5>
          <div className="row">
            <div className="input-container-price">
              <input
                value={price.min}
                className="price-input"
                onChange={(e) => setPrice({ ...price, min: e.target.value })}
              ></input>
              <h5>PLN</h5>
            </div>

            <h5> - </h5>
            <div className="input-container-price">
              <input
                className="price-input"
                value={price.max}
                onChange={(e) => setPrice({ ...price, max: e.target.value })}
              ></input>
              <h5>PLN</h5>
            </div>
            <div
              className="close-icon"
              onClick={() => {
                setPrice({ min: "", max: "" });
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>
        <div className="search-description">
          <h5>Price per m</h5>
          <div className="row">
            <div className="input-container-price">
              <input
                value={pricePerM.min}
                className="price-input"
                onChange={(e) =>
                  setPricePerM({ ...pricePerM, min: e.target.value })
                }
              ></input>
              <h5>PLN</h5>
            </div>
            <h5> - </h5>
            <div className="input-container-price">
              <input
                className="price-input"
                value={pricePerM.max}
                onChange={(e) =>
                  setPricePerM({ ...pricePerM, max: e.target.value })
                }
              ></input>
              <h5>PLN</h5>
            </div>
            <div
              className="close-icon"
              onClick={() => {
                setPricePerM({ min: "", max: "" });
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>
        <div className="search-description">
          <h5>Disctrict</h5>
          <div className="row">
            <input
              className="description-input"
              value={district}
              onChange={(e) => {
                handleChangeDistrict(e.target.value);
              }}
            ></input>
            <div
              className="close-icon"
              onClick={() => {
                setDistrict("");
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>
        {/* <div className="search-description-select">
          <h5>Disctrict</h5>

          <div className="select-district"> */}
        {/* <Select
              // defaultValue={[colourOptions[2], colourOptions[3]]}
              isMulti
              value={selectedDistricts}
              options={districtsList}
              className="basic-multi-select district-select"
              classNamePrefix="select"
              onChange={handleChange}
            /> */}
        {/* <select
              onChange={(e) => handleAddrTypeChange(e)}
              className="browser-default custom-select"
            >
              {Add.map((address, key) => (
                <option key={key} value={key}>
                  {address}
                </option>
              ))}
            </select> */}

        {/* <select
              id="districts"
              name="districts"
              value={district}
              className="select-dist"
              onChange={(e) => {
                setDistrict(e.target.value);
              }}
            >
              {districtsList.map((district) => {
                return (
                  <option id={district} value={district}>
                    {district}
                  </option>
                );
              })}
            </select> */}
        {/* </div> */}
        {/* </div> */}
      </div>
      <div
        className="search-button"
        onClick={() => {
          console.log("description");
          console.log(description);
          console.log("area");
          console.log(area);
          console.log("price");
          console.log(price);
          console.log("district");
          console.log(district);
          console.log("pricePerM");
          console.log(pricePerM);
          props.handleClickSearch(
            description,
            area,
            price,
            district,
            pricePerM,
            rooms
          );
        }}
      >
        <FontAwesomeIcon icon={["fa", "search"]} size="xs" color="white" />
      </div>
    </div>
  );
};
export default SearchBar;
