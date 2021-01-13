import React, { useState, useEffect, useCallback, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";

import "./Apartaments.css";
import flat from "../../images/flat.jpg";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faHeart as faRegularHeart } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as apartmentActions from "../../store/actions/apartments";
import GoogleMapReact from "google-map-react";
import LocationPin from "./LocationPin";
import { GOOGLE_MAP_API_KEY } from "../../constants/constants";

const Apartament = (props) => {
  const [isFavourite, setIsFavourite] = useState(props.is_favourite);
  const [isMailSended, setIsMailSended] = useState(false);
  const [heart, setHeart] = useState(props.is_favourite ? "pink" : "#b6b6b6");
  library.add(faHeart, faRegularHeart);
  const dispatch = useDispatch();

  const location = {
    address: "1600 Amphitheatre Parkway, Mountain View, california.",
    lat: props.lat,
    lng: props.lng,
  };

  const heartHandler = () => {
    console.log("Bede usuwał: " + props.id);
    dispatch(apartmentActions.removeApartmentFromFav(props.id));
    setIsFavourite(!isFavourite);

    // setIsFavourite(!isFavourite);
    // if (isFavourite) {
    //   setHeart("pink");

    //   dispatch(
    //     apartmentActions.addApartmentToFav({
    //       district: props.district,
    //       area: props.area,
    //       img_url: props.img_url,
    //       latitude: props.lat,
    //       longitude: props.lng,
    //       offer_url: props.offer_url,
    //       price: props.price,
    //       rooms: props.rooms,
    //       source: props.source,
    //       source_id: props.source_id,
    //       title: props.title,
    //       note: "",
    //       id: props.id,
    //     })
    //   );
    // } else {
    //   setHeart("#b6b6b6");
    //   dispatch(apartmentActions.removeApartmentFromFav(props.id));
    // }
    // props.notify(isFavourite);
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
            {/* <h3>ID: {props.id}</h3> */}
            <h4>{props.title}</h4>
            <h3>Dzielnica: {props.district}</h3>
            <h3>Cena: {props.price} zł </h3>
            <h3>Cena za metr: {(props.price / props.area).toFixed(0)} zł</h3>
            <h3>
              Powierzchnia: {props.area} m<sup>2</sup>
            </h3>
            <h3>Portal: {props.source}</h3>
            <h3>Pokoje: {props.rooms}</h3>
          </div>
          <div className="icon_container">
            {isFavourite ? (
              <div className="mail_icon">
                <FontAwesomeIcon
                  icon={["far", "heart"]}
                  color="pink"
                  size="2x"
                  onClick={heartHandler}
                />
              </div>
            ) : (
              <div className="mail_icon">
                <FontAwesomeIcon
                  icon={["far", "heart"]}
                  color="#b6b6b6"
                  size="2x"
                  onClick={() => {
                    dispatch(
                      apartmentActions.addApartmentToFav({
                        district: props.district,
                        area: props.area,
                        img_url: props.img_url,
                        latitude: props.lat,
                        longitude: props.lng,
                        offer_url: props.offer_url,
                        price: props.price,
                        rooms: props.rooms,
                        source: props.source,
                        source_id: props.source_id,
                        title: props.title,
                        note: "",
                        id: props.id,
                      })
                    );
                    setIsFavourite(!isFavourite);
                  }}
                />
              </div>
            )}
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
                  dispatch(apartmentActions.sendEmail(props.id));
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
