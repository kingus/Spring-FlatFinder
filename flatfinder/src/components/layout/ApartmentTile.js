import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { makeStyles } from '@material-ui/core/styles';

import "./Apartaments.css";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faHeart as faRegularHeart } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import * as apartmentActions from "../../store/actions/apartments";
import {Tooltip} from "@material-ui/core";

const useStyles = makeStyles({
  apartment_tile: {
    display: 'flex',
    flexDirection: 'row',
    justifySelf: 'flex-start',
    backgroundColor: 'white',
    width: '50vw',
    height: '250px',
    borderRadius: '10px',
    boxShadow: '3px 3px 10px 1px #b6b6b6',
  },
  apartment_tile_right_part: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-start',
    backgroundColor: 'white',
    width: '100%',
    alignItems: 'flex-end',
    borderRadius: '0px 10px 10px 0px',
    '&:hover': {
      backgroundColor: '#d5c8c8',
    }
  },
  apartment_info: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'flex-start',
    width: '90%',
    height: '100%',
  },
  apartment_photo: {
    width: '300px',
    height: '250px',
    borderRadius: '10px 0px 0px 10px',
  },
  apartment_details_header: {
    padding: '4px',
    fontWeight: '700',
    paddingLeft: '35px',
    height: '15%',
  },
  apartment_details_header_text: {
    color: 'black',
    fontSize: '17px',
    fontFamily: "Noto Sans",
    '&:hover' : {
      color: 'grey',
    }
  },
  property_row: {
    display: 'flex',
    flexDirection: 'row',
    height: '30px',
    marginLeft: '35px',
  },
  property_row_key: {
    fontWeight: 'bolder',
  },
  property_row_value: {
    marginLeft: '5px'
  },
  icon_container: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'flex-end',
    flexDirection: 'column',
    height: '90%',
    paddingRight: '15px',
    paddingBottom: '15px',
    // paddingTop: '15px',
    borderRadius: '10px',
    '&:hover': {
      backgroundColor: '#d5c8c8',
    },
    // border: "1px solid red"
  },
  icon: {
    color: '#b6b6b6',
    display: 'flex',
    alignItems: 'flex-start',
    alignSelf: 'left',
    cursor: 'pointer',
  },
  icon_mail_sent: {
    color: 'red',
    display: 'flex',
    alignItems: 'flex-start',
    alignSelf: 'left',
    cursor: 'pointer',
    fontSize: '30px'
  }
});

const PropertyRow = (props) => {
  const classes = useStyles();

  return (
      <div className={classes.property_row}>
        <p className={classes.property_row_key}>{props.keyName}</p>
        <p>{":"}</p>
        <p className={classes.property_row_value}>{props.value}</p>
      </div>
  );
}
const ApartmentTile = (props) => {
  const [isFavourite, setIsFavourite] = useState(props.is_favourite);
  const [isMailSent, setIsMailSent] = useState(false);
  library.add(faHeart, faRegularHeart);
  const dispatch = useDispatch();
  const classes = useStyles();


  const heartHandler = () => {
    dispatch(apartmentActions.removeApartmentFromFav(props.id));
    setIsFavourite(!isFavourite);
  };

  return (
    <div>
      <div className={classes.apartment_tile}>
        <Tooltip title="Go to offer page" placement={"top-end"}>
          <a href={props.offer_url}>
            <img src={props.img_url} alt="Logo" className={classes.apartment_photo}/>
          </a>
        </Tooltip>
        <div className={classes.apartment_tile_right_part}>
          <div className={classes.apartment_info}>
            <div className={classes.apartment_details_header}>
              <a href={props.offer_url} style={{textDecoration: "none"}}>
                {/*<h2>{props.description} </h2>*/}
                <p className={classes.apartment_details_header_text}>{props.title}</p>
              </a>
            </div>
            <PropertyRow keyName={"Dzielnica"} value={props.district}/>
            <PropertyRow keyName={"Cena"} value={(props.price) + 'zł'}/>
            <PropertyRow keyName={"Cena za metr"} value={(props.price / props.area).toFixed(0)}/>
            <PropertyRow keyName={"Powierzchnia"} value={(props.area) + 'm2'}/>
            {/*Powierzchnia: {props.area} m<sup>2</sup>*/}
            <PropertyRow keyName={"Portal"} value={props.source}/>
            <PropertyRow keyName={"Pokoje"} value={props.rooms}/>

          </div>
          <div className={classes.icon_container}>
            {isFavourite ? (
              <div className={classes.icon}>
                <FontAwesomeIcon
                  icon={["far", "heart"]}
                  color="#e72f2f"
                  size="2x"
                  onClick={heartHandler}
                />
              </div>
            ) : (
              <div className={classes.icon}>
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
              onClick={() => {
                if (!isMailSent) {
                  dispatch(apartmentActions.sendEmail(props.id));
                  setIsMailSent(true);
                }
              }}
            >
                {isMailSent ? (
                    <div className={classes.icon_mail_sent}>
                      <i className="material-icons md-30">mark_email_read</i>
                    </div>
                ) : (
                    <div className={classes.icon}>
                      <i className="material-icons md-30">forward_to_inbox</i>
                    </div>
                )}

            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ApartmentTile;
