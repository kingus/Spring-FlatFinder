import React, { useState, useEffect, useCallback, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";

import "./Apartaments.css";
import flat from "../../images/flat.jpg";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faHeart as faRegularHeart } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import GoogleMapReact from "google-map-react";
import LocationPin from "./LocationPin";
import { GOOGLE_MAP_API_KEY } from "../../constants/constants";
import * as placeActions from "../../store/actions/places";

const Apartament = (props) => {
  const [isFavourite, setIsFavourite] = useState(props.is_favourite);
  const [isMailSended, setIsMailSended] = useState(false);
  const [heart, setHeart] = useState(isFavourite ? "pink" : "#b6b6b6");
  library.add(faHeart, faRegularHeart);
  const dispatch = useDispatch();

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
          // onClick={() => setIsShowingDetails(!isShowingDetails)}
        >
          <div className="apartament_info">
            <div className="header">
              <a href={props.offer_url}>
                <h2>{props.description} </h2>
              </a>
            </div>
            <h3>Dzielnica: {props.place}</h3>
            <h3>Cena: {props.price} zł </h3>
            <h3>Cena za metr: {(props.price / props.area).toFixed(0)} zł</h3>
            <h3>
              Powierzchnia: {props.area} m<sup>2</sup>
            </h3>
            <h3>Portal: {props.source}</h3>
            <h3>Pokoje: {props.rooms}</h3>
          </div>
          <div className="icon_container">
            <div className="icon">
              <FontAwesomeIcon
                icon={["far", "heart"]}
                color={heart}
                size="2x"
                onClick={heartHandler}
              />
            </div>
            <div
              className="mail_icon"
              onClick={() => {
                if (!isMailSended) {
                  //TODO: sending mial action here id -> prop.id
                  console.log(
                    "Wysyłam maila z oferta o id: " +
                      props.id +
                      " i source id " +
                      props.source_id
                  );
                  setIsMailSended(true);
                }
              }}
            >
              {isMailSended ? (
                <i className="material-icons md-24">mark_email_read</i>
              ) : (
                <i className="material-icons  md-24">forward_to_inbox</i>
              )}
            </div>
          </div>
        </div>
      </div>
      {/* {isShowingDetails && (
        <div className="apartment_details">
          <div className="place_icon_container">
            <div className="icon">
              <i className="material-icons  orange600">commute</i>
            </div>
            <div className="icon">
              <i className="material-icons md-48">school</i>
            </div>
            <div className="icon">
              <i className="material-icons md-48">local_hospital</i>
            </div>
            <div className="icon">
              <i className="material-icons md-48">storefront</i>
            </div>
          </div>
        </div>
      )} */}
    </div>
  );
};

export default Apartament;
