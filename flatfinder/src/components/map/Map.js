/*global google*/
import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";

import { GoogleMap } from "@react-google-maps/api";
import "../layout/Apartaments.css";
import * as markersAction from "../../store/actions/markers";
import {makeStyles} from "@material-ui/core/styles";
import clsx from "clsx";

let service;

const mapContainerStyle = {
  height: "100%",
  width: "100%",
  borderRadius: '25px 25px 0px 0px',
  border: '2px solid white',
  borderBottom: 'none'
};
const useStyles = makeStyles({
  nestedMapContainer: {
    display: 'flex',
    justifyContent: 'space-between',
    flexDirection: 'column',
    width: '90%',
    height: '95%',
    boxShadow: '3px 3px 10px 1px #b6b6b6',
    borderRadius: '25px'


  },
  place_icon_container: {
    display: 'flex',
    justifyContent: 'space-evenly',
    alignItems: 'center',
    flexDirection: 'row',
    backgroundColor: 'white',
    paddingRight: '10px',
    paddingBottom: '10px',
    paddingTop: '10px',
    borderRadius: '0px 0px 25px 25px',
  },
  map_icon: {
    display: 'flex',
    width: '100%',
    height: '100%',
    paddingTop: '5px',
    backgroundColor: 'white',
    marginBottom: '15px',
    marginRight: '15px',
    justifyContent: 'center',
    alignItems: 'center',
  }

});

const options = {
  disableDefaultUI: true,
  zoomControl: true,
};

const Map = (props) => {
  const classes = useStyles();
  const [publicTransportPlaces, setPublicTransportPlaces] = useState(false);
  const [educationalPlaces, setEducationalPlaces] = useState(false);
  const [hospitalPlaces, setHospitalPlaces] = useState(false);
  const [shopPlaces, setShopPlaces] = useState(false);
  const dispatch = useDispatch();

  const mapRef = React.useRef();
  const onMapLoad = React.useCallback((map) => {
    mapRef.current = map;
  }, []);

  useEffect(() => {
    disableAllPlaces();
  }, [props.center]);

  const disableAllPlaces = () => {
    let map = mapRef.current;
    dispatch(markersAction.removeMarkers());
    setPublicTransportPlaces(false);
    setEducationalPlaces(false);
    setHospitalPlaces(false);
    setShopPlaces(false);
    dispatch(
      markersAction.addMarker(
        new google.maps.Marker({
          position: props.center,
          map,
        })
      )
    );
  };

  const getNearbyPlaces = (type) => {
    mapRef.current.panTo(props.center);
    let map = mapRef.current;

    let request = {
      location: props.center,
      radius: "2000",
      type: [type],
    };

    service = new google.maps.places.PlacesService(mapRef.current);
    service.nearbySearch(request, callback);
    function callback(results, status) {
      if (status === google.maps.places.PlacesServiceStatus.OK) {
        for (let i = 0; i < results.length; i++) {
          let place = results[i];

          const newMarker = new google.maps.Marker({
            position: place.geometry.location,
            map,
            animation: google.maps.Animation.DROP,
          });

          dispatch(markersAction.addMarker(newMarker));
        }
      }
    }
  };

  return (
    <div className={classes.nestedMapContainer}>
      <GoogleMap
        id="map"
        mapContainerStyle={mapContainerStyle}
        zoom={15}
        center={props.center}
        options={options}
        onLoad={onMapLoad}
      />
      <div className={classes.place_icon_container}>
        <div
          className={classes.map_icon}
          onClick={() => {
            if (publicTransportPlaces) {
              dispatch(markersAction.removeMarkers());

              setPublicTransportPlaces(false);
              setEducationalPlaces(false);
              setHospitalPlaces(false);
              setShopPlaces(false);
            } else {
              getNearbyPlaces("transit_station");
              setPublicTransportPlaces(!publicTransportPlaces);
            }
          }}
        >
          <i
            className={clsx({
              ["material-icons"] : true,
              ["active"] : publicTransportPlaces,
            })}
          >
            commute
          </i>
        </div>
        <div
          className={classes.map_icon}
          onClick={() => {
            if (educationalPlaces) {
              dispatch(markersAction.removeMarkers());
              setPublicTransportPlaces(false);
              setEducationalPlaces(false);
              setHospitalPlaces(false);
              setShopPlaces(false);
            } else {
              getNearbyPlaces("school");
              setEducationalPlaces(!educationalPlaces);
            }
          }}
        >
          <i
            className={clsx({
              ["material-icons"] : true,
              ["active"] : educationalPlaces,
            })}
          >
            school
          </i>
        </div>
        <div
          className={classes.map_icon}
          onClick={() => {
            if (hospitalPlaces) {
              dispatch(markersAction.removeMarkers());
              setPublicTransportPlaces(false);
              setEducationalPlaces(false);
              setHospitalPlaces(false);
              setShopPlaces(false);
            } else {
              getNearbyPlaces("hospital");
              setHospitalPlaces(!hospitalPlaces);
            }
          }}
        >
          <i
            className={clsx({
              ["material-icons"] : true,
              ["active"] : hospitalPlaces,
            })}
          >
            local_hospital
          </i>
        </div>
        <div
          className={classes.map_icon}
          onClick={() => {
            if (shopPlaces) {
              dispatch(markersAction.removeMarkers());
              setPublicTransportPlaces(false);
              setEducationalPlaces(false);
              setHospitalPlaces(false);
              setShopPlaces(false);
            } else {
              getNearbyPlaces("supermarket");

              setShopPlaces(!shopPlaces);
            }
          }}
        >
          <i
            className={clsx({
              ["material-icons"] : true,
              ["active"] : shopPlaces,
            })}
          >
            storefront
          </i>
        </div>
      </div>
    </div>
  );
};

export default Map;
