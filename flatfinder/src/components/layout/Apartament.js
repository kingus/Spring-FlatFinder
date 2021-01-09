import React, { useState } from "react";
import "./Apartaments.css";
import flat from "../../images/flat.jpg";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faHeart as faRegularHeart } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import GoogleMapReact from "google-map-react";
import LocationPin from "./LocationPin";
const Apartament = (props) => {
  const [isFavourite, setIsFavourite] = useState(props.is_favourite);
  const [isShowingDetails, setIsShowingDetails] = useState(
    props.isShowingDetails
  );
  const [heart, setHeart] = useState(isFavourite ? "pink" : "#b6b6b6");
  library.add(faHeart, faRegularHeart);
  const location = {
    address: "1600 Amphitheatre Parkway, Mountain View, california.",
    lat: props.lat,
    lng: props.lng,
  };

  const heartHandler = () => {
    setIsFavourite(!isFavourite);
    if (isFavourite) {
      setHeart("pink");
    } else {
      setHeart("#b6b6b6");
    }
    props.notify(isFavourite);
  };

  return (
    <div>
      <div className="apartament">
        <a href={props.offer_url}>
          <img src={props.img_url} alt="Logo" className="apartament_photo" />
        </a>
        <div
          className="contain"
          onClick={() => setIsShowingDetails(!isShowingDetails)}
        >
          <div className="apartament_info">
            <div className="header">
              <a href={props.offer_url}>
                <h2>{props.description} </h2>
              </a>
            </div>
            <h3>Dzielnica: {props.place}</h3>
            <h3>Cena: {props.price} </h3>
            <h3>Cena za metr: {(props.price / props.area).toFixed(0)} zł</h3>
            <h3>Powierzchnia: {props.area}</h3>
            <h3>Portal: {props.source}</h3>
            <h3>Pokoje: {props.rooms}</h3>
          </div>
          <div className="icon">
            <FontAwesomeIcon
              icon={["far", "heart"]}
              color={heart}
              size="2x"
              onClick={heartHandler}
            />
          </div>
        </div>
      </div>
      {isShowingDetails && (
        <div className="apartment_details">
          <div className="google-map">
            <GoogleMapReact
              bootstrapURLKeys={{
                key: "AIzaSyAXszJlRJjCSrP2G3WawzciISDY6v4B9FU",
              }}
              defaultCenter={{
                address:
                  "1600 Amphitheatre Parkway, Mountain View, california.",
                lat: props.lat,
                lng: props.lng,
              }}
              defaultZoom={14}
            >
              <LocationPin
                lat={location.lat}
                lng={location.lng}
                text={location.address}
              />
            </GoogleMapReact>
          </div>
        </div>
      )}
    </div>
  );
};

export default Apartament;
