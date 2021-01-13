/*global google*/
import React, { useState, useEffect } from "react";
import { useDispatch } from "react-redux";

import { GoogleMap } from "@react-google-maps/api";
import "../layout/Apartaments.css";
import * as markersAction from "../../store/actions/markers";

let service;

const mapContainerStyle = {
  height: "100%",

  width: "100%",
};
const options = {
  disableDefaultUI: true,
  zoomControl: true,
};

const Map = (props) => {
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
    <div className="nestedMapContainer">
      <GoogleMap
        id="map"
        mapContainerStyle={mapContainerStyle}
        zoom={15}
        center={props.center}
        options={options}
        onLoad={onMapLoad}
      />
      <div className="place_icon_container">
        <div
          className="map_icon"
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
            className={
              publicTransportPlaces ? "material-icons active" : "material-icons"
            }
          >
            commute
          </i>
        </div>
        <div
          className="map_icon"
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
            className={
              educationalPlaces ? "material-icons active" : "material-icons"
            }
          >
            school
          </i>
        </div>
        <div
          className="map_icon"
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
            className={
              hospitalPlaces ? "material-icons active" : "material-icons"
            }
          >
            local_hospital
          </i>
        </div>
        <div
          className="map_icon"
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
            className={shopPlaces ? "material-icons active" : "material-icons"}
          >
            storefront
          </i>
        </div>
      </div>
    </div>
  );
};

export default Map;
