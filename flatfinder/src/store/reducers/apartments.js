import {
  ADD_APARTMENT_TO_FAV,
  REMOVE_APARTMENT_FROM_FAV,
  GET_APARTMENTS,
  GET_FAV_APARTMENTS,
} from "../actions/apartments";

const initialState = {
  apartments: [],
  fav_apartments: [],
};
export default (state = initialState, action) => {
  switch (action.type) {
    case GET_APARTMENTS:
      console.log("Reducer here!");
      return {
        ...state,
        apartments: action.apartments,
      };
    case GET_FAV_APARTMENTS:
      console.log("Reducer here!");
      return {
        ...state,
        fav_apartments: action.apartments,
      };
    case ADD_APARTMENT_TO_FAV:
      console.log("Reducer here!");
      return {
        ...state,
        fav_apartments: state.fav_apartments.concat(action.offer),
      };
    case REMOVE_APARTMENT_FROM_FAV:
      console.log("Usuwam z ulub");
      return {
        ...state,
        fav_apartments: state.fav_apartments.concat(action.offer),
        fav_apartments: state.fav_apartments.filter(
          (apartment) => apartment.id !== apartment.id
        ),
      };
    // case ADD_EVENT:
    //   const newEvent = new Event(
    //     action.eventData.name,
    //     action.eventData.desc,
    //     action.eventData.date,
    //     action.eventData.time,
    //     action.eventData.tripParent
    //   );
    //   return {
    //     tripEvents: state.tripEvents.concat(newEvent),
    //   };
    // case EDIT_EVENT:
    //   let tmpTripEvents = state.tripEvents;
    //   tmpTripEvents.forEach(function (event, index, array) {
    //     if (event.id == action.eventData.id) {
    //       array[index] = {
    //         ...event,
    //         title: action.eventData.eventName,
    //         description: action.eventData.eventDesc,
    //         date: action.eventData.eventDate,
    //         time: action.eventData.time,
    //       };
    //     }
    //   });
    //   return {
    //     ...state,
    //     tripEvents: tmpTripEvents,
    //   };

    // case DELETE_EVENT:
    //   return {
    //     //...state,
    //     tripEvents: state.tripEvents.filter(
    //       (event) => event.id !== action.eventId
    //     ),
    //   };
    default:
      return state;
  }
};
