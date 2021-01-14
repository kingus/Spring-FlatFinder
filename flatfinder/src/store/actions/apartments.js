import axios from "axios";
export const GET_APARTMENTS = "GET_APARTMENTS";
export const GET_FAV_APARTMENTS = "GET_FAV_APARTMENTS";
export const ADD_APARTMENT_TO_FAV = "ADD_APARTMENT_TO_FAV";
export const REMOVE_APARTMENT_FROM_FAV = "REMOVE_APARTMENT_FROM_FAV";
export const UPDATE_NOTE = "UPDATE_NOTE";

export const getApartments = () => {
  return async (dispatch, getState) => {
    let apartments = [];
    const config = {
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    };

    await fetch("http://localhost:8080/api/offers", config)
      .then((response) => response.json())
      .then((json) => {
        apartments = json;
      })
      .catch((error) => {});
    dispatch({
      type: GET_APARTMENTS,
      apartments: apartments,
    });
  };
};

export const getFavApartments = () => {
  return async (dispatch, getState) => {
    let apartments = [];
    const config = {
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    };

    await fetch("http://localhost:8080/api/user-offers", config)
      .then((response) => response.json())
      .then((json) => {
        apartments = json;
      })
      .catch((error) => {});
    dispatch({
      type: GET_FAV_APARTMENTS,
      apartments: apartments,
    });
  };
};

export const sendEmail = (offer_id) => {
  return async (dispatch, getState) => {
    const config = {
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    };

    axios
      .get("http://localhost:8080/api/send-email/" + offer_id, config)
      .then(function (response) {})
      .catch(function (error) {})
      .then(function () {});
  };
};

export const addApartmentToFav = (offer) => {
  return async (dispatch, getState) => {
    var myHeaders = new Headers();
    myHeaders.append("Authorization", localStorage.getItem("token"));
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({ offer_id: offer.id, note: "" });

    var requestOptions = {
      method: "POST",
      headers: myHeaders,
      body: raw,
      redirect: "follow",
    };

    fetch("http://localhost:8080/api/user-offers", requestOptions)
      .then((response) => {
        dispatch({
          type: ADD_APARTMENT_TO_FAV,
          offer: offer,
        });
      })
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));
  };
};

export const removeApartmentFromFav = (offer_id) => {
  return async (dispatch, getState) => {
    var myHeaders = new Headers();
    myHeaders.append("Authorization", localStorage.getItem("token"));

    var requestOptions = {
      method: "DELETE",
      headers: myHeaders,
      redirect: "follow",
    };
    console.log("http://localhost:8080/api/user-offers/" + offer_id);
    fetch("http://localhost:8080/api/user-offers/" + offer_id, requestOptions)
      .then((response) => {
        dispatch({
          type: REMOVE_APARTMENT_FROM_FAV,
          offer_id: offer_id,
        });
      })
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));
  };
};

export const updateNote = (id, note) => {
  return async (dispatch, getState) => {
    var myHeaders = new Headers();
    myHeaders.append("Authorization", localStorage.getItem("token"));
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({ note: note });
    var requestOptions = {
      method: "PATCH",
      headers: myHeaders,
      body: raw,
      redirect: "follow",
    };
    console.log("http://localhost:8080/api/user-offers/" + id);
    fetch("http://localhost:8080/api/user-offers/" + id, requestOptions)
      .then((response) => {
        dispatch({
          type: UPDATE_NOTE,
          id: id,
          note: note,
        });
      })
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));
  };
};
