import { GET_APARTMENTS } from "../actions/apartments";

const initialState = {
  apartments: [],
};
export default (state = initialState, action) => {
  switch (action.type) {
    case GET_APARTMENTS:
      console.log("Reducer here!");
      return {
        apartments: action.apartments,
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
