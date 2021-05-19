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
import { useTheme } from '@material-ui/core/styles';
import Input from '@material-ui/core/Input';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import ListItemText from '@material-ui/core/ListItemText';
import Select from '@material-ui/core/Select';
import Checkbox from '@material-ui/core/Checkbox';
import {TextField} from "@material-ui/core";

const useStyles = makeStyles((theme)=> ({
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
    height: '65px',
    backgroundColor: 'white',
    borderRadius: '50px',
    borderColor: '#dddddd',
    borderWidth: '0.2px',
    borderStyle: 'solid',
    display: 'flex',
    padding: '1px',
    justifyContent: 'center',
    alignItems: 'center',
    paddingLeft: '20px',
    paddingRight: '20px',
    boxShadow: '3px 0px 5px 0px #b6b6b6',
    fontWeight: 'normal',

  },
  search_description: {
    borderRadius: '10px 0px 0px 10px',
    width: 'auto',
    height: '60px',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'left',
    justifyContent: 'left',
    fontWeight: 'normal',
    '&:h5': {
      fontWeight: 'lighter',
    }
  },
  row: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'left',
    alignItems: 'center',
    padding: '0px',
    marginLeft: '8px',
    marginRight: '8px',
    fontWeight: 'lighter',

    '&:h5': {
      marginLeft: '8px',
      marginRight: '8px',

    }
  },
  search_bar_text: {
    display: 'flex',
    fontWeight: 'normal',
    alignSelf: 'center',
    paddingTop: '5%'
    // border: '1px solid red'
  },
  description_input: {
    width: '140px',
    borderColor: '#dddddd',
    borderWidth: '0.2px',
    borderBottom: '1px solid #bbbbbb',
    margin: '4px',
  },
  close_icon: {
    display: 'flex',
    fontWeight: 'normal',
    alignSelf: 'center',
    paddingTop: '5%',
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
    width: '130px',
    borderColor: '#dddddd',
    borderWidth: '0.2px',
    '& > *': {
      margin: theme.spacing(1),
      // width: '20ch',
    },
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
    width: '100px',
    borderWidth: '0.2px',
    '& > *': {
      margin: theme.spacing(1),
      // width: '20ch',
    },
  },
  formControl: {
    margin: theme.spacing(1),
    width: 180,
  },
  chips: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  chip: {
    margin: 2,
  },
  noLabel: {
    marginTop: theme.spacing(3),
  },
  chip_district_select: {
    display: "flex",
    height: '60px',
    border: "1px solid red",
    overflow: 'scroll',
    overflowX: 'hidden'
  },
  root: {
    '& > *': {
      margin: theme.spacing(1),
      width: '120px',
    },
  },
  district_list_item: {
    textTransform: "capitalize",
  }
}));

const ITEM_HEIGHT = 36;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

const districtsList = [
  "bemowo",
  "bialoleka",
  "bielany",
  "ochota",
  "praga-poludnie",
  "praga-polnoc",
  "rembertow",
  "targowek",
  "ursus",
  "ursynow",
  "wawer",
  "wesola",
  "wilanow",
  "wola",
  "wlochy",
  "srodmiescie",
  "zoliborz",
];

const SearchBar = (props) => {
  const classes = useStyles();
  const theme = useTheme();
  const [personName, setPersonName] = React.useState([]);

  const handleChange = (event) => {
    setPersonName(event.target.value);
  };

  const handleChangeMultiple = (event) => {
    const { options } = event.target;
    const value = [];
    for (let i = 0, l = options.length; i < l; i += 1) {
      if (options[i].selected) {
        value.push(options[i].value);
      }
    }
    setPersonName(value);
  };


  // const [districtsList, setDistrictsList] = useState([
  //   { value: "bemowo", label: "Bemowo" },
  //   { value: "bialoleka", label: "Białołeka" },
  //   { value: "bielany", label: "Bielany" },
  //   { value: "ochota", label: "Ochota" },
  //   { value: "praga-poludnie", label: "Praga-południe" },
  //   { value: "praga-polnoc", label: "Praga-północ" },
  //   { value: "rembertow", label: "Rembertow" },
  //   { value: "targowek", label: "Targowek" },
  //   { value: "ursus", label: "Ursus" },
  //   { value: "ursynow", label: "Ursynow" },
  //   { value: "wawer", label: "Wawer" },
  //   { value: "wesola", label: "Wesola" },
  //   { value: "wilanow", label: "Wilanow" },
  //   { value: "wola", label: "Wola" },
  //   { value: "wlochy", label: "Włochy" },
  //   { value: "srodmiescie", label: "Środmieście" },
  //   { value: "zoliborz", label: "Żoliborz" },
  // ]);

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
  const [district, setDistrict] = useState([]);

  library.add(faSearch, faPlus, faMinus, faTimes);

  const handleChangeDescription = (enteredDescription) => {
    setDescription(enteredDescription);
  };
  const handleChangeDistrict = (enteredDistrict) => {
    console.log(enteredDistrict.toString());
    setDistrict(enteredDistrict);
  };

  return (
    <div className={classes.main_container}>
      <div className={classes.searchBar}>
        <div className={classes.search_description}>
          <div className={classes.row}>
            <form className={classes.root} noValidate autoComplete="off">
              <TextField
                  label="Title"
                  value={description}
                  onChange={(e) => {
                           handleChangeDescription(e.target.value);
                   }}/>
            </form>
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
          <div className={classes.row}>
            <form className={classes.area_input} noValidate autoComplete="off">
              <TextField
                  label="Area min"
                  type="number"
                  value={area.min}
                  onChange={(e) => {
                    setArea({ ...area, min: e.target.value });
                  }}
              />
            </form>
            <h5 className={classes.search_bar_text}>m²</h5>
            <h5 className={classes.search_bar_text}>-</h5>
            <form className={classes.area_input} noValidate autoComplete="off">

              <TextField
                  id="standard-number"
                  label="Area max"
                  type="number"
                  value={area.max}
                  onChange={(e) => setArea({ ...area, max: e.target.value })}
              />
            </form>
            <h5 className={classes.search_bar_text}>m²</h5>
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
          {/*<h5>Price</h5>*/}
          <div className={classes.row}>
            <form className={classes.area_input} noValidate autoComplete="off">
              <TextField
                  id="standard-number"
                  label="Price min"
                  type="number"
                  value={price.min}
                  // className={classes.price_input}
                  onChange={(e) => setPrice({ ...price, min: e.target.value })}
              />
            </form>

            <h5 className={classes.search_bar_text}>PLN</h5>
            <h5 className={classes.search_bar_text}> - </h5>
            <form className={classes.area_input} noValidate autoComplete="off">
              <TextField
                  id="standard-number"
                  label="Price max"
                  type="number"
                  value={price.max}
                  onChange={(e) => setPrice({ ...price, max: e.target.value })}
              />
            </form>
            <h5 className={classes.search_bar_text}>PLN</h5>
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
          <div className={classes.row}>
            <form className={classes.price_input} noValidate autoComplete="off">
              <TextField
                  id="standard-number"
                  label="Price/m² min"
                  type="number"
                  value={pricePerM.min}
                  // className={classes.price_input}
                  onChange={(e) =>
                      setPricePerM({ ...pricePerM, min: e.target.value })
                  }
              />
            </form>
            <h5 className={classes.search_bar_text}>PLN</h5>
            <h5 className={classes.search_bar_text}> - </h5>
            <form className={classes.price_input} noValidate autoComplete="off">
              <TextField
                  id="standard-number"
                  label="Price/m² max"
                  type="number"
                  value={pricePerM.max}
                  onChange={(e) =>
                      setPricePerM({ ...pricePerM, max: e.target.value })
                  }
              />
            </form>
            <h5 className={classes.search_bar_text}>PLN</h5>
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
          {/*<div className={classes.chip_district_select}>*/}
          <FormControl className={classes.formControl}>
            <InputLabel>District</InputLabel>
            <Select
                multiple
                value={district}
                onChange={(e) => {
                  handleChangeDistrict(e.target.value);
                }}
                input={<Input />}
                renderValue={(selected) => selected.join(', ')}
                MenuProps={MenuProps}
            >
              {districtsList.map((name, index) => (
                  <MenuItem key={index} value={name}>
                    <Checkbox checked={district.indexOf(name) > -1} />
                    <ListItemText className={classes.district_list_item} primary={name} />
                  </MenuItem>
              ))}
            </Select>
          </FormControl>
          {/*<div*/}
          {/*  className={classes.close_icon}*/}
          {/*  onClick={() => {*/}
          {/*    setDistrict([]);*/}
          {/*  }}*/}
          {/*>*/}
          {/*  <FontAwesomeIcon*/}
          {/*    icon={["fa", "times"]}*/}
          {/*    size="xs"*/}
          {/*    color="#dddddd"*/}
          {/*  />*/}
          {/*</div>*/}

        </div>

      </div>
      <div
        className={classes.search_button}
        onClick={() => {
          props.handleClickSearch(
            description,
            area,
            price,
            district.toString(),
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
