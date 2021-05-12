import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import * as apartmentsActions from "../../store/actions/apartments";
import SearchBar from "../layout/SearchBar";
import { Oval } from "@agney/react-loading";
import "../layout/Apartaments.css";
import ApartmentTile from "../layout/ApartmentTile";

import Map from "../map/Map";

const Home = () => {
  const [currentOffer, setCurrentOffer] = useState({
    lat: 52.2297,
    lng: 21.0122,
  });
  const [isLoading, setIsLoading] = useState(false);
  const apartments = useSelector((state) => state.apartments.apartments);
  const fav_apartments = useSelector(
    (state) => state.apartments.fav_apartments
  );
  const [filtered_apartaments, setFilteredApartaments] = useState([]);

  const dispatch = useDispatch();

  const loadApartments = useCallback(async () => {
    try {
      await dispatch(apartmentsActions.getFavApartments());
      await dispatch(apartmentsActions.getApartments());
    } catch (err) {}
  }, [dispatch]);

  useEffect(() => {
    let mounted = true;
    setIsLoading(true);
    loadApartments().then(() => {
      if (mounted) {
        setFilteredApartaments(apartments);
        setIsLoading(false);
      }
    });
    return () => (mounted = false);
  }, [dispatch, loadApartments]);

  const handleClickSearch = (
    title,
    area,
    price,
    district,
    pricePerM,
    rooms
  ) => {
    var area_min = area.min;
    var area_max = area.max;
    var price_min = price.min;
    var price_max = price.max;
    var price_per_m_min = pricePerM.min;
    var price_per_m_max = pricePerM.max;

    if (!area_min) {
      area_min = 0;
    }
    if (!area_max) {
      area_max = 99999999;
    }
    if (!price_min) {
      price_min = 0;
    }
    if (!price_max) {
      price_max = 99999999;
    }
    if (!price_per_m_min) {
      price_per_m_min = 0;
    }
    if (!price_per_m_max) {
      price_per_m_max = 99999999;
    }

    setFilteredApartaments(
      apartments.filter(
        (apartment) =>
          apartment.title.toLowerCase().includes(title.toLowerCase()) &&
          area_min <= apartment.area &&
          apartment.area <= area_max &&
          price_min <= apartment.price &&
          apartment.price <= price_max &&
          price_per_m_min <= apartment.price / apartment.area &&
          apartment.price / apartment.area <= price_per_m_max &&
          apartment.district.toLowerCase().includes(district.toLowerCase())
      )
    );
  };

  const ifApartmentIsFav = (obj, list) => {
    var i;
    for (i = 0; i < list.length; i++) {
      if (list[i].id === obj.id) {
        return true;
      }
    }
    return false;
  };
  return (
    <div className="container">
      {/* <div id="map"></div> */}
      <SearchBar handleClickSearch={handleClickSearch}></SearchBar>
      {isLoading ? (
        <div className="puff">
          <Oval width="100" />
        </div>
      ) : (
        <div className="mainContainer">
          <div className="apartaments_list">
            {filtered_apartaments.map((apartment) => {
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
                  <ApartmentTile
                    key={apartment.id}
                    id={apartment.id}
                    description={apartment.description}
                    title={apartment.title}
                    district={apartment.district}
                    area={apartment.area}
                    price_per_m={apartment.price_per_m}
                    price={apartment.price}
                    source_id={apartment.source_id}
                    source={apartment.source}
                    offer_url={apartment.offer_url}
                    rooms={apartment.rooms}
                    is_favourite={ifApartmentIsFav(apartment, fav_apartments)}
                    img_url={apartment.img_url}
                    show_details={false}
                    lat={apartment.latitude}
                    lng={apartment.longitude}
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
  );
};

export default Home;
