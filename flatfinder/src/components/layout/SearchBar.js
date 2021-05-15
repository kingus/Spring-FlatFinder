import React, { useState } from "react";
import { library } from "@fortawesome/fontawesome-svg-core";
import {
  faSearch,
  faPlus,
  faMinus,
  faTimes,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./SearchBar.css";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles({
  main_container: {
    /* padding-top: 15px; */
    width: '100%',
    height: '15vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  searchBar: {
    width: '78%',
    height: '80px',
    backgroundColor: 'white',
    borderRadius: '50px',
    borderColor: '#dddddd',
    borderWidth: '0.2px',
    borderStyle: 'solid',
    display: 'flex',
    padding: '1px',
    alignItems: 'center',
    paddingLeft: '20px',
    paddingRight: '20px',
    boxShadow: '3px 0px 5px 0px #b6b6b6',
  },
  search_description: {
    borderRadius: '10px 0px 0px 10px',
    width: 'auto',
    height: '60px',
    // borderColor: 'red',
    // borderWidth: '0px 1px 0px 0px',
    // borderStyle: 'solid',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'left',
    justifyContent: 'left',

  },
  row: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'left',
    alignItems: 'center',
    padding: '0px',
    marginLeft: '8px',
    marginRight: '8px',
    // fontWeight: 'bolder',

    '&:h5': {
      marginLeft: '8px',
      marginRight: '8px',
      // fontWeight: 'bolder',

    }
  },
  description_input: {
    width: '140px',
    borderColor: '#dddddd',
    borderWidth: '0.2px',
    borderBottom: '1px solid #bbbbbb',
    margin: '4px',
  },
  close_icon: {
    cursor: 'pointer',
    marginLeft: '15px',
    marginRight: '2px',
  },
  input_container_area: {
    position: 'relative',
    display: 'flex',
    width: '80px',
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
  input_container_price: {
    position: 'relative',
    display: 'flex',
    width: '140px',
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
  price_input: {
    width: '40px',
    borderColor: '#dddddd',
    borderWidth: '0.2px',
  },
  search_button: {
    backgroundImage: 'linear-gradient(#eb0303, #ec0101)',
    borderColor: '#dd0303',
    borderWidth: '0.2px',
    borderStyle: 'solid',
    borderRadius: '100px',
    width: '50px',
    height: '50px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    marginLeft: '15px',
    boxShadow: '3px 1px 5px 0px #b6b6b6',
  },
  area_input: {
    width: '40px',
    borderWidth: '0.2px',
  }

});


const SearchBar = (props) => {
  const classes = useStyles();
  const [districtsList, setDistrictsList] = useState([
    { value: "bemowo", label: "Bemowo" },
    { value: "bialoleka", label: "Białołeka" },
    { value: "bielany", label: "Bielany" },
    { value: "ochota", label: "Ochota" },
    { value: "praga-poludnie", label: "Praga-południe" },
    { value: "praga-polnoc", label: "Praga-północ" },
    { value: "rembertow", label: "Rembertow" },
    { value: "targowek", label: "Targowek" },
    { value: "ursus", label: "Ursus" },
    { value: "ursynow", label: "Ursynow" },
    { value: "wawer", label: "Wawer" },
    { value: "wesola", label: "Wesola" },
    { value: "wilanow", label: "Wilanow" },
    { value: "wola", label: "Wola" },
    { value: "wlochy", label: "Włochy" },
    { value: "srodmiescie", label: "Środmieście" },
    { value: "zoliborz", label: "Żoliborz" },
  ]);

  const [rooms, setRooms] = useState(0);
  const [area, setArea] = useState({
    min: "",
    max: "",
  });
  const [price, setPrice] = useState({
    min: "",
    max: "",
  });
  const [pricePerM, setPricePerM] = useState({
    min: "",
    max: "",
  });
  const [description, setDescription] = useState("");
  const [district, setDistrict] = useState("");

  library.add(faSearch, faPlus, faMinus, faTimes);

  const handleChangeDescription = (enteredDescription) => {
    setDescription(enteredDescription);
  };
  const handleChangeDistrict = (enteredDescription) => {
    setDistrict(enteredDescription);
  };

  return (
    <div className={classes.main_container}>
      <div className={classes.searchBar}>
        <div className={classes.search_description}>
          <h5>Title</h5>
          <div className={classes.row}>
            <input
              className={classes.description_input}
              value={description}
              onChange={(e) => {
                handleChangeDescription(e.target.value);
              }}
            />
            <div
              className={classes.close_icon}
              onClick={() => {
                setDescription("");
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>

        <div className={classes.search_description}>
          <h5>Area</h5>

          <div className={classes.row}>
            <div className={classes.input_container_area}>
              <input
                value={area.min}
                className={classes.area_input}
                onChange={(e) => {
                  setArea({ ...area, min: e.target.value });
                }}
              />
              <h5>m²</h5>
            </div>

            <h5>-</h5>
            <div className={classes.input_container_area}>
              <input
                value={area.max}
                className={classes.area_input}
                onChange={(e) => setArea({ ...area, max: e.target.value })}
              />
              <h5>m²</h5>
            </div>
            <div
                className={classes.close_icon}
              onClick={() => {
                setArea({ min: "", max: "" });
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>

        <div className={classes.search_description}>
          <h5>Price</h5>
          <div className={classes.row}>
            <div className={classes.input_container_price}>
              <input
                value={price.min}
                className={classes.price_input}
                onChange={(e) => setPrice({ ...price, min: e.target.value })}
              />
              <h5>PLN</h5>
            </div>

            <h5> - </h5>
            <div className={classes.input_container_price}>
              <input
                className={classes.price_input}
                value={price.max}
                onChange={(e) => setPrice({ ...price, max: e.target.value })}
              />
              <h5>PLN</h5>
            </div>
            <div
              className={classes.close_icon}
              onClick={() => {
                setPrice({ min: "", max: "" });
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>

        <div className={classes.search_description}>
          <h5>Price per m</h5>
          <div className={classes.row}>
            <div className={classes.input_container_price}>
              <input
                value={pricePerM.min}
                className={classes.price_input}
                onChange={(e) =>
                  setPricePerM({ ...pricePerM, min: e.target.value })
                }
              />
              <h5>PLN</h5>
            </div>
            <h5> - </h5>
            <div className={classes.input_container_price}>
              <input
                className={classes.price_input}
                value={pricePerM.max}
                onChange={(e) =>
                  setPricePerM({ ...pricePerM, max: e.target.value })
                }
              />
              <h5>PLN</h5>
            </div>
            <div
              className={classes.close_icon}
              onClick={() => {
                setPricePerM({ min: "", max: "" });
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>

        <div className={classes.search_description}>
          <h5>District</h5>
          <div className={classes.row}>
            <input
              className={classes.description_input}
              value={district}
              onChange={(e) => {
                handleChangeDistrict(e.target.value);
              }}
            />
            <div
              className={classes.close_icon}
              onClick={() => {
                setDistrict("");
              }}
            >
              <FontAwesomeIcon
                icon={["fa", "times"]}
                size="xs"
                color="#dddddd"
              />
            </div>
          </div>
        </div>

      </div>
      <div
        className={classes.search_button}
        onClick={() => {
          props.handleClickSearch(
            description,
            area,
            price,
            district,
            pricePerM,
            rooms
          );
        }}
      >
        <FontAwesomeIcon icon={["fa", "search"]} size="xs" color="white" />
      </div>
    </div>
  );
};
export default SearchBar;
