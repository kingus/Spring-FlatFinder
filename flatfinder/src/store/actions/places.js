import { GOOGLE_MAP_API_KEY } from "../../constants/constants";
import axios from "axios";

export const GET_NEARBY_PLACES = "GET_APARTMENTS";

export const getNearbyPlacesForOffer = (offer_id, type, radius, lat, lng) => {
  return async (dispatch, getState) => {
    let nearbyPlaces = {};
    nearbyPlaces[offer_id] = [];
    console.log(
      `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${lat},${lng}&radius=${radius}&type=${type}&key=${GOOGLE_MAP_API_KEY}`
    );
    // await fetch(
    //   `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${lat},${lng}&radius=${radius}&type=${type}&key=${GOOGLE_MAP_API_KEY}`
    // )
    //   .then((response) => response.json())
    //   .then((json) => {
    //     json.results.forEach((place) => {
    //       nearbyPlaces[offer_id].push({
    //         place_id: place.place_id,
    //         name: place.name,
    //         lat: place.geometry.location.lat,
    //         lng: place.geometry.location.lng,
    //         open_now:
    //           "opening_hours" in place
    //             ? place.opening_hours.open_now
    //             : "No info",
    //         address: place.vicinity,
    //         rating: place.rating,
    //         reviews: place.user_ratings_total,
    //         ifImage: place.hasOwnProperty("photos") ? true : false,
    //         // image: place.hasOwnProperty("photos")
    //         //   ? `https://maps.googleapis.com/maps/api/place/photo?maxwidth=300&photoreference=${place.photos[0].photo_reference}&key=${GOOGLE_MAP_API_KEY}`
    //         //   : "",
    //       });
    //     });
    //   })
    //   .catch((error) => {
    //     console.error(error);
    //     // err.text().then((errorMessage) => {
    //     //   console.log(errorMessage);
    //     // });
    //   });

    axios
      .get(
        `https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${lat},${lng}&radius=${radius}&type=${type}&key=${GOOGLE_MAP_API_KEY}`,
        {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
          },
        }
      )
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
        if (error.response) {
          console.log(error.response.data);
          console.log(error.response.status);
          console.log(error.response.headers);
        }
      });

    dispatch({
      type: GET_NEARBY_PLACES,
      nearbyPlaces: nearbyPlaces,
    });
  };
};
