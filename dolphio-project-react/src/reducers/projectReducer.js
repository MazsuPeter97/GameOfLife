import { START_GAME, UPLOAD_FILE, LAST_STEP } from "../actions/type";

const initialState = {};

export default function(state = initialState,action){
    switch(action.type){
        case UPLOAD_FILE :
            return action.payload;
        case START_GAME :
            return action.payload;
        case LAST_STEP:
            return action.payload;       
        default:
            return state;

    }


}