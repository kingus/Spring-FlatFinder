import {
  ADD_APARTMENT_TO_FAV,
  REMOVE_APARTMENT_FROM_FAV,
  GET_APARTMENTS,
  GET_FAV_APARTMENTS,
  UPDATE_NOTE,
} from "../actions/apartments";

const initialState = {
  apartments: [],
  fav_apartments: [],
};
export default (state = initialState, action) => {
  switch (action.type) {
    case GET_APARTMENTS:
      return {
        ...state,
        apartments: action.apartments,
      };
    case GET_FAV_APARTMENTS:
      return {
        ...state,
        fav_apartments: action.apartments,
      };
    case ADD_APARTMENT_TO_FAV:
      return {
        ...state,
        fav_apartments: state.fav_apartments.concat(action.offer),
      };
    case REMOVE_APARTMENT_FROM_FAV:
      return {
        ...state,
        fav_apartments: state.fav_apartments.filter(
          (apartment) => apartment.id !== action.offer_id
        ),
      };
    case UPDATE_NOTE:
      let newFavApartments = state.fav_apartments;
      newFavApartments.forEach((apartment) => {
        if (apartment.id === action.id) {
          apartment.note = action.note;
        }
      });
      return {
        ...state,
        fav_apartments: newFavApartments,
      };
    default:
      return state;
  }
};
