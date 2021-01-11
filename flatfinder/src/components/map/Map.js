/*global google*/
import React, { useState, useEffect, useCallback, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";

import { GoogleMap, useLoadScript } from "@react-google-maps/api";
import Search from "./Search";
import "../layout/Apartaments.css";
import * as markersAction from "../../store/actions/markers";

const google = window.google;
let service;
const libraries = ["places"];

const mapContainerStyle = {
  height: "100%",

  width: "100%",
  //   border: "2px red solid",
};
const options = {
  disableDefaultUI: true,
  zoomControl: true,
};
const center = {
  lat: 52,
  lng: 21,
};

const Map = (props) => {
  const apartments = useSelector((state) => state.apartments.apartments);
  const markers = useSelector((state) => state.markers.markers);

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

  const panTo = React.useCallback(({ lat, lng }) => {
    mapRef.current.panTo({ lat, lng });
    mapRef.current.setZoom(12);
    let map = mapRef.current;

    let request = {
      location: { lat, lng },
      radius: "2000",
      type: ["transit_station"],
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
          });
          //   setMarkers(markers.concat(newMarker));
        }
      }
    }
  }, []);

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
          });
          dispatch(markersAction.addMarker(newMarker));
        }
      }
    }
  };

  return (
    <div className="nestedMapContainer">
      {/* <Search panTo={panTo} /> */}
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
