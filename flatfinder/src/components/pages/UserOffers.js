import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import * as apartmentsActions from "../../store/actions/apartments";
import { Puff } from "@agney/react-loading";
import "../layout/Apartaments.css";
import ApartmentTile from "../layout/ApartmentTile";
import { Form } from "semantic-ui-react";
import {makeStyles} from "@material-ui/core/styles";
import clsx from "clsx";

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
    height: '91.5vh',
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
  // note_container: {
  //   width: '30%',
  //   display: 'flex',
  //   flexDirection: 'column',
  //   justifyItems: 'center',
  //   alignItems: 'center',
  //   marginTop: '20vh',
  // },
  note_container: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100%',
    width: '40%',
    // paddingTop: '15px',
    borderRadius: '10px',
    // border: '3px solid green',

  },
  note_form: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-around',
    alignItems: 'center',
    width: "70%",
    height: '80%',
    padding: '20px',
    borderRadius: '10px',
    backgroundColor: 'white',
    boxShadow: '3px 3px 10px 1px #b6b6b6',
    // border: '1px solid blue',
  },
  note_title: {
    // border: '1px solid red',
    fontSize: '30px',
    // marginTop: '10px',
    // marginBottom: '10px',
    borderRadius: '10px',
  },
  note_text_area: {
    display: 'flex',
    alignSelf: 'center',
    padding: "10px",
    height: '60%',
    minWidth: '90%',
    maxWidth: '90%',
    resize: 'none',
    borderRadius: '10px',
    border: '2px solid #616161',
    '&:focus': {
      outline: "none",
      border: '2px solid #616161',
    },
  },
  btn_update: {
    background: "rgba(0, 0, 0, 0.3)",
    backgroundColor: "red",
    // backgroundColor: "#616161",
    fontSize: "18px",
    letterSpacing: "1px",
    color: "white",
    cursor: "pointer",
    fontFamily: "Noto Sans",
    textAlign: "center",
    flex: "1",
    border: 'none',
    // border: "1px solid #545454",
    borderRadius: "10px",
    maxHeight: "50px",
    width: '90%',

    alignItems: "center",
    display: "flex",
    justifyContent: "center",
    '&:focus': {
      outline: "none",
      borderRadius: "10px"
    },
  },
});

const UserOffers = () => {
  const classes = useStyles();
  const [isLoading, setIsLoading] = useState(false);
  const apartments = useSelector((state) => state.apartments.fav_apartments);
  const fav_apartments = useSelector(
    (state) => state.apartments.fav_apartments
  );
  const [currentOffer, setCurrentOffer] = useState({});
  const dispatch = useDispatch();

  const loadApartments = useCallback(async () => {
    try {
      await dispatch(apartmentsActions.getFavApartments());
    } catch (err) {}
  }, [dispatch, setIsLoading]);

  useEffect(() => {
    setIsLoading(true);
    loadApartments().then(() => {
      setIsLoading(false);
    });
  }, [dispatch, loadApartments]);

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
    <div className={classes.main_home_container}>
      <div id="map"></div>
      {isLoading ? (
        <div className={classes.puff}>
          <Puff width="100" />
        </div>
      ) : (
        <div className={classes.under_searchbar_container}>
          <div className={classes.apartments_list}>
            {apartments.map((apartment) => {
              return (
                <div
                    className={clsx({
                      [classes.apartment_offer] : true,
                      [classes.current_offer] : currentOffer.id === apartment.id,
                    })}
                    onClick={() => {
                      setCurrentOffer({
                        id: apartment.id,
                        note: apartment.note,
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
          <div className={classes.note_container}>
            {/*<div className={classes.note_form}>*/}


              <Form className={classes.note_form}>
                <div className={classes.note_title}>Note</div>
                <textarea
                  rows="35"
                  className={classes.note_text_area}
                  placeholder="Note something..."
                  value={currentOffer.note}
                  onChange={(event) => {
                    setCurrentOffer({
                      ...currentOffer,
                      note: event.target.value,
                    });
                  }}
                />
                <button
                  className={classes.btn_update}
                  onClick={() => {
                    dispatch(
                      apartmentsActions.updateNote(
                        currentOffer.id,
                        currentOffer.note
                      )
                    );
                  }}
                >
                  Update
                </button>
              </Form>
            {/*</div>*/}
          </div>
        </div>
      )}
    </div>
  );
};

export default UserOffers;
