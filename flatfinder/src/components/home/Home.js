import React, { useState, useEffect, useCallback, useRef } from "react";
import Form from "./Form";
import { useDispatch, useSelector } from "react-redux";
import * as apartmentsActions from "../../store/actions/apartments";
import SearchBar from "../layout/SearchBar";
import { Circles, Puff } from "@agney/react-loading";
import "../layout/Apartaments.css";
import Apartament from "../layout/Apartament";
import { GOOGLE_MAP_API_KEY } from "../../constants/constants";
import { GoogleMap, useLoadScript } from "@react-google-maps/api";
import Map from "../map/Map";

const Home = () => {
  const [currentOffer, setCurrentOffer] = useState({
    lat: 52.2297,
    lng: 21.0122,
  });
  const [isLoading, setIsLoading] = useState(false);
  const apartments = useSelector((state) => state.apartments.apartments);
  const [filtered_apartaments, setFilteredApartaments] = useState([]);

  const [error, setError] = useState();
  const dispatch = useDispatch();

  const loadApartments = useCallback(async () => {
    setError(null);
    try {
      await dispatch(apartmentsActions.getApartments());
    } catch (err) {
      setError(err.message);
    }
  }, [dispatch, setIsLoading, setError]);

  useEffect(() => {
    setIsLoading(true);
    loadApartments().then(() => {
      setIsLoading(false);
    });
  }, [dispatch, loadApartments]);

  const handleClickSearch = (
    searchDescription,
    area,
    price,
    district,
    pricePerM
  ) => {
    var area_min = area.min;
    var area_max = area.max;
    var price_min = price.min;
    var price_max = price.max;
    var price_per_m_min = pricePerM.min;
    var price_per_m_max = pricePerM.max;
    console.log(price_per_m_min);
    console.log(price_per_m_max);

    if (!(area_min || area_max)) {
      area_min = 0;
      area_max = 99999999;
    }
    if (!(price_min || price_max)) {
      price_min = 0;
      price_max = 99999999;
    }
    if (!(price_per_m_min || price_per_m_max)) {
      price_per_m_min = 0;
      price_per_m_max = 99999999;
    }

    setFilteredApartaments(
      apartments.filter((apartment) => {
        console.log("TYYYY", apartment.price_per_m);
        if (
          apartment.description
            .toLowerCase()
            .includes(searchDescription.toLowerCase()) &&
          area_min <= apartment.area &&
          apartment.area <= area_max &&
          price_min <= apartment.price &&
          apartment.price <= price_max &&
          price_per_m_min <= apartment.price_per_m &&
          apartment.price_per_m <= price_per_m_max &&
          apartment.place.toLowerCase().includes(district.toLowerCase())
        ) {
          return apartment;
        }
      })
    );
  };

  return (
    <div className="container">
      <div id="map"></div>
      {/* <Map /> */}
      {/* <GoogleMap
        id="map"
        mapContainerStyle={mapContainerStyle}
        zoom={8}
        center={center}
        options={options}
        onLoad={onMapLoad}
      /> */}
      <SearchBar
        handleClickSearch={() => {
          handleClickSearch(handleClickSearch);
        }}
      ></SearchBar>
      {/* <button onClick={refreshData}>REFRESH DATA</button> */}
      {/* <div className="refresh" onClick={() => refreshData()}>
        <FontAwesomeIcon icon={["fa", "sync"]} size="2x" />
      </div> */}
      {isLoading ? (
        <div className="puff">
          <Puff width="100" />
        </div>
      ) : (
        <div className="mainContainer">
          <div className="apartaments_list">
            {apartments.map((apartment) => {
              // console.log(apartment.is_favourite);
              return (
                <div
                  className={
                    currentOffer.id === apartment.id
                      ? "apartments_container current_offer"
                      : "apartments_container"
                  }
                  onClick={() => {
                    setCurrentOffer({
                      id: apartment.id,
                      lat: apartment.latitude,
                      lng: apartment.longitude,
                    });
                  }}
                >
                  <Apartament
                    key={apartment.id}
                    id={apartment.id}
                    description={apartment.description}
                    place={apartment.district}
                    area={apartment.area}
                    price_per_m={apartment.price_per_m}
                    price={apartment.price}
                    source_id={apartment.source_id}
                    offer_url={apartment.offer_url}
                    rooms={apartment.rooms}
                    is_favourite={apartment.is_favourite}
                    img_url={apartment.img_url}
                    show_details={false}
                    lat={apartment.latitude}
                    lng={apartment.longitude}
                    //   notify={notify}
                    //   key={apartment.apartment_id}
                    //   description={apartment.description}
                    //   place={apartment.place}
                    //   area={apartment.area}
                    //   price_per_m={apartment.price_per_m}
                    //   price={apartment.price}
                    //   source={apartment.source}
                    //   offer_url={apartment.offer_url}
                    //   rooms={apartment.rooms}
                    //   is_favourite={apartment.is_favourite}
                    //   notify={notify}
                  />
                </div>
              );
            })}
          </div>
          <div className="mapContainer">
            <Map center={currentOffer} />
          </div>
        </div>
      )}
    </div>

    //

    // <div className="container">
    //   <Puff width="100" />
    //   <div className="container">
    //     <div className="row center-align">
    //       <div className="col s7">
    //         <Form />
    //       </div>
    //       <div className="col s5">notelist</div>
    //     </div>
    //   </div>
    // </div>
  );
};

export default Home;
