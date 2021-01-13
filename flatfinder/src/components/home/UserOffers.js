import React, { useState, useEffect, useCallback, useRef } from "react";
// import Form from "./Form";
import { useDispatch, useSelector } from "react-redux";
import * as apartmentsActions from "../../store/actions/apartments";
import SearchBar from "../layout/SearchBar";
import { Circles, Puff } from "@agney/react-loading";
import "../layout/Apartaments.css";
import Apartament from "../layout/Apartament";
import { Form, TextArea } from "semantic-ui-react";

const UserOffers = () => {
  const [isLoading, setIsLoading] = useState(false);
  const apartments = useSelector((state) => state.apartments.fav_apartments);
  const fav_apartments = useSelector(
    (state) => state.apartments.fav_apartments
  );
  const [currentOffer, setCurrentOffer] = useState({});
  const [filtered_apartaments, setFilteredApartaments] = useState([]);

  const [error, setError] = useState();
  const dispatch = useDispatch();

  const loadApartments = useCallback(async () => {
    setError(null);
    try {
      await dispatch(apartmentsActions.getFavApartments());
    } catch (err) {
      setError(err.message);
    }
  }, [dispatch, setIsLoading, setError]);

  useEffect(() => {
    setIsLoading(true);
    loadApartments().then(() => {
      setIsLoading(false);
    });
  }, [dispatch, loadApartments]);

  const handleClickSearch = (
    searchDescription,
    area,
    price,
    district,
    pricePerM
  ) => {
    var area_min = area.min;
    var area_max = area.max;
    var price_min = price.min;
    var price_max = price.max;
    var price_per_m_min = pricePerM.min;
    var price_per_m_max = pricePerM.max;
    console.log(price_per_m_min);
    console.log(price_per_m_max);

    if (!(area_min || area_max)) {
      area_min = 0;
      area_max = 99999999;
    }
    if (!(price_min || price_max)) {
      price_min = 0;
      price_max = 99999999;
    }
    if (!(price_per_m_min || price_per_m_max)) {
      price_per_m_min = 0;
      price_per_m_max = 99999999;
    }

    setFilteredApartaments(
      apartments.filter((apartment) => {
        console.log("TYYYY", apartment.price_per_m);
        if (
          apartment.description
            .toLowerCase()
            .includes(searchDescription.toLowerCase()) &&
          area_min <= apartment.area &&
          apartment.area <= area_max &&
          price_min <= apartment.price &&
          apartment.price <= price_max &&
          price_per_m_min <= apartment.price_per_m &&
          apartment.price_per_m <= price_per_m_max &&
          apartment.place.toLowerCase().includes(district.toLowerCase())
        ) {
          return apartment;
        }
      })
    );
  };
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
      {/* <SearchBar
        handleClickSearch={() => {
          handleClickSearch(handleClickSearch);
        }}
      ></SearchBar> */}
      {isLoading ? (
        <div className="puff">
          <Puff width="100" />
        </div>
      ) : (
        <div className="mainContainer">
          <div className="fav_apartaments_list">
            {apartments.map((apartment) => {
              // console.log(apartment.is_favourite);
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
                  <Apartament
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
              <div className="note_title">Your note about offer</div>

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
                    console.log(currentOffer.note);
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
                {/* <TextArea rows={5} placeholder="Tell us more" /> */}
              </Form>
            </div>
          </div>
        </div>
      )}
    </div>

    //

    // <div className="container">
    //   <Puff width="100" />
    //   <div className="container">
    //     <div className="row center-align">
    //       <div className="col s7">
    //         <Form />
    //       </div>
    //       <div className="col s5">notelist</div>
    //     </div>
    //   </div>
    // </div>
  );
};

export default UserOffers;
