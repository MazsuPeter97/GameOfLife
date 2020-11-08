import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { uploadFile } from "./actions/projectActions";
import Board from "./game/board";



class Game extends Component {
  constructor() {
    super(); 
    this.onFileChangeHandler = this.onFileChangeHandler.bind(this);
  }

  //life cycle hooks
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  componentDidMount() {
    this.props.uploadFile();
  }
  
  onFileChangeHandler(e) {
    e.preventDefault();

    const formData = new FormData();
    formData.append('file', e.target.files[0]);

  this.props.uploadFile(formData, this.props.history);
   
};


render() {
    return (
      <div>
        <h1> The Game of Life</h1>
      
        <h2> upload file</h2>
        <form>
          <input type="file" className="form-control" name="file" onChange={this.onFileChangeHandler}/>
        </form>
             
        <Board />
        
      </div>
    );
  }
}

Game.propTypes = {
  coordinates: PropTypes.object.isRequired,
  uploadFile: PropTypes.func.isRequired,
  getCoordinatesWebVO: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  coordinates: state.coordinates,
  errors: state.errors
});

export default connect(
  mapStateToProps,
  { uploadFile }
)(Game);