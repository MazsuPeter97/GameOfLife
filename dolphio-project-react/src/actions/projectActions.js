import axios from "axios";
import { START_GAME, UPLOAD_FILE, LAST_STEP} from "./type";

export const getCoordinatesWebVO = (data) => async dispatch => {
  var headers = {'Content-Type': 'application/json; charset=utf-8'};
  
  const res = await axios.post("/api/project/game", data, {headers : headers});
  
  dispatch({
    type: START_GAME,
    payload: res.data
  });
}

export const uploadFile = (file, history) => async dispatch => {

    var headers = {'Content-Type': 'multipart/form-data', 
                  'Access-Control-Allow-Origin': '*'}; 

    const res = await axios.post("/api/project/upload",file, {headers : headers});
   
    dispatch({
      type: UPLOAD_FILE,
      payload: res.data
    });
}

export const getLastStepData = (step) => async dispatch => {
  var headers = {'Content-Type': 'application/json; charset=utf-8'};
  const res = await axios.post("/api/project/step",step, {headers : headers});

  dispatch({
    type: LAST_STEP,
    payload: res.data
  });

}

