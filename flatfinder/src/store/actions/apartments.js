import axios from "axios";
export const GET_APARTMENTS = "GET_APARTMENTS";
export const GET_FAV_APARTMENTS = "GET_FAV_APARTMENTS";
export const ADD_APARTMENT_TO_FAV = "ADD_APARTMENT_TO_FAV";
export const REMOVE_APARTMENT_FROM_FAV = "REMOVE_APARTMENT_FROM_FAV";

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
        console.log(json);
        apartments = json;
      })
      .catch((error) => {
        console.error(error);
      });
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
        console.log(json);
        apartments = json;
      })
      .catch((error) => {
        console.error(error);
      });
    dispatch({
      type: GET_FAV_APARTMENTS,
      apartments: apartments,
    });
  };
};

export const sendEmail = (offer_id) => {
  return async (dispatch, getState) => {
    console.log("Send email Action");
    const config = {
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    };

    axios
      .get("http://localhost:8080/api/send-email/" + offer_id, config)
      .then(function (response) {
        console.log(response.status);
      })
      .catch(function (error) {
        // handle error
        console.log("Sending email error");
        console.log(error);
      })
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
        console.log(response.status);
        dispatch({
          type: ADD_APARTMENT_TO_FAV,
          offer: offer,
        });
      })
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));

    //   console.log(offer);
    //   const body = JSON.stringify({ offer_id: offer.id, note: "" });
    //   const config = {
    //     headers: {
    //       "Content-Type": "application/json",
    //       Authorization: localStorage.getItem("token"),
    //     },
    //     data: body,
    //   };
    //   console.log("config");
    //   console.log(config);
    //   axios
    //     .post("http://localhost:8080/api/user-offers", config)
    //     .then(function (response) {
    //       console.log(response.status);
    //       dispatch({
    //         type: ADD_APARTMENT_TO_FAV,
    //         offer: offer,
    //       });
    //     })
    //     .catch(function (error) {
    //       console.log("Error adding to fav");
    //       console.log(error);
    //     })
    //     .then(function () {});
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

    fetch("http://localhost:8080/api/user-offers/" + offer_id, requestOptions)
      .then((response) => {
        console.log(response.status);
        dispatch({
          type: REMOVE_APARTMENT_FROM_FAV,
          offer_id: offer_id,
        });
      })
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));

    // const config = {
    //   headers: {
    //     Authorization: localStorage.getItem("token"),
    //   },
    // };
    // axios
    //   .delete("http://localhost:8080/api/user-offers/" + offer_id, config)
    //   .then(function (response) {
    //     console.log(response.status);
    //     dispatch({
    //       type: REMOVE_APARTMENT_FROM_FAV,
    //       offer_id: offer_id,
    //     });
    //   })
    //   .catch(function (error) {
    //     // handle error
    //   })
    //   .then(function () {});
  };
};
