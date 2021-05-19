import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import * as apartmentsActions from "../../store/actions/apartments";
import { Puff } from "@agney/react-loading";
import "../layout/Apartaments.css";
import ApartmentTile from "../layout/ApartmentTile";
import { Form } from "semantic-ui-react";

const UserOffers = () => {
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
    <div className="container">
      <div id="map"></div>
      {isLoading ? (
        <div className="puff">
          <Puff width="100" />
        </div>
      ) : (
        <div className="mainContainer">
          <div className="fav_apartaments_list">
            {apartments.map((apartment) => {
              return (
                <div
                  className={
                    currentOffer.id === apartment.id
                      ? "apartments_container current_offer"
                      : "apartments_container"
                  }
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
          <div className="note_container">
            <div className="note_form">
              <div className="note_title">Note</div>

              <Form>
                <textarea
                  rows="35"
                  className="note_text_area"
                  placeholder="Note something..."
                  value={currentOffer.note}
                  onChange={(event) => {
                    setCurrentOffer({
                      ...currentOffer,
                      note: event.target.value,
                    });
                  }}
                ></textarea>
                <button
                  className="btn-update"
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
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default UserOffers;
