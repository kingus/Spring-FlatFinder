import { GET_NEARBY_PLACES } from "../actions/places";

const initialState = {
  nearbyPlaces: {},
};
export default (state = initialState, action) => {
  switch (action.type) {
    case GET_NEARBY_PLACES:
      console.log("Reducer GET_NEARBY_PLACES here!");
      return {
        nearbyPlaces: { ...nearbyPlaces, ...action.nearbyPlaces },
      };

    default:
      return state;
  }
};
