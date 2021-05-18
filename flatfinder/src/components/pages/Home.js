import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import * as apartmentsActions from "../../store/actions/apartments";
import SearchBar from "../layout/SearchBar";
import { Oval } from "@agney/react-loading";
import "../layout/Apartaments.css";
import ApartmentTile from "../layout/ApartmentTile";
import clsx from 'clsx'
import Map from "../map/Map";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles({
  main_home_container: {
    backgroundColor: '#eff1f3',
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    maxWidth: 'none',

  },
  under_searchbar_container: {
    backgroundColor: '#eff1f3',
    width: '100%',
    display: 'flex',
    flexDirection: 'row',
  },
  apartments_list: {
    backgroundColor: '#eff1f3',
    width: '60%',
    height: '76.5vh',
    display: 'flex',
    flexDirection: 'column',
    overflow: 'scroll',
    overflowX: 'hidden',
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
  apartment_offer: {
    marginTop: '15px',
  },
  current_offer: {
    /* background-color: #b6b6b6; */
    borderRadius: '10px',
    boxShadow: '3px 3px 10px 1px #b6b6b6',
  },
  puff: {
    display: 'flex',
    width: '100%',
    height:'70vh',
    justifyContent: 'center',
    alignItems: 'flex-start',
    marginTop: '100px',
  },
  map_container: {
    display: 'flex',
    justifyContent: 'center',
    height: '77vh',
    width: '40%',
    paddingTop: '15px',
    borderRadius: '10px',
  }
});

const Home = () => {
  const classes = useStyles();
  const [currentOffer, setCurrentOffer] = useState({
    lat: 52.2297,
    lng: 21.0122,
  });
  const [isLoading, setIsLoading] = useState(false);
  const apartments = useSelector((state) => state.apartments.apartments);
  const fav_apartments = useSelector(
    (state) => state.apartments.fav_apartments
  );
  const [filtered_apartments, setFilteredApartments] = useState([]);

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
        setFilteredApartments(apartments);
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
    let area_min = area.min;
    let area_max = area.max;
    let price_min = price.min;
    let price_max = price.max;
    let price_per_m_min = pricePerM.min;
    let price_per_m_max = pricePerM.max;

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

    setFilteredApartments(
      apartments.filter(
        (apartment) =>
          apartment.title.toLowerCase().includes(title.toLowerCase()) &&
          area_min <= apartment.area &&
          apartment.area <= area_max &&
          price_min <= apartment.price &&
          apartment.price <= price_max &&
          price_per_m_min <= apartment.price / apartment.area &&
          apartment.price / apartment.area <= price_per_m_max &&
          // apartment.district.toLowerCase().includes(district.toLowerCase())
          district.toLowerCase().includes(apartment.district.toLowerCase())
      )
    );
  };

  const ifApartmentIsFav = (apartment, fav_apartments) => {
    let i;
    for (i = 0; i < fav_apartments.length; i++) {
      if (fav_apartments[i].id === apartment.id) {
        return true;
      }
    }
    return false;
  };
  return (
    <div className={classes.main_home_container}>
      <SearchBar handleClickSearch={handleClickSearch}></SearchBar>
      {isLoading ? (
        <div className={classes.puff}>
          <Oval width="100" />
        </div>
      ) : (
        <div className={classes.under_searchbar_container}>
          <div className={classes.apartments_list}>
            {filtered_apartments.map((apartment) => {
              return (
                <div
                  className={clsx({
                    [classes.apartment_offer] : true,
                    [classes.current_offer] : currentOffer.id === apartment.id,
                  })}
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
          <div className={classes.map_container}>
            <Map center={currentOffer} />
          </div>
        </div>
      )}
    </div>
  );
};

export default Home;
