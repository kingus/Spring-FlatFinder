import React, { useState } from "react";
import { useDispatch } from "react-redux";

import "./Apartaments.css";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faHeart as faRegularHeart } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as apartmentActions from "../../store/actions/apartments";

const Apartament = (props) => {
  const [isFavourite, setIsFavourite] = useState(props.is_favourite);
  const [isMailSended, setIsMailSended] = useState(false);
  library.add(faHeart, faRegularHeart);
  const dispatch = useDispatch();

  const heartHandler = () => {
    dispatch(apartmentActions.removeApartmentFromFav(props.id));
    setIsFavourite(!isFavourite);
  };

  return (
    <div>
      <div className="apartament">
        <a href={props.offer_url}>
          <img src={props.img_url} alt="Logo" className="apartament_photo" />
        </a>
        <div className="contain">
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
                  color="#e72f2f"
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
                  dispatch(apartmentActions.sendEmail(props.id));
                  setIsMailSended(true);
                }
              }}
            >
              {isMailSended ? (
                <i className="material-icons md-36 active">mark_email_read</i>
              ) : (
                <i className="material-icons  md-36 grey">forward_to_inbox</i>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Apartament;
