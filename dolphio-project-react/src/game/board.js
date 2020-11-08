import React, { Component } from 'react'
import $ from 'jquery';
import { getCoordinatesWebVO, getLastStepData } from "../actions/projectActions";
import PropTypes from "prop-types";
import { connect } from "react-redux";

class Board extends Component {
   
    constructor() {
        super(); 
        this.state = {
            step: 0,
            errors: {}
        }

        this.startGame = this.startGame.bind(this);
        this.nextStep = this.nextStep.bind(this);
        this.automaticGame = this.automaticGame.bind(this);
        this.lastStep = this.lastStep.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.errors) {
          this.setState({ errors: nextProps.errors });
        }
    }

    startGame(e){
        e.preventDefault();
        this.paintStructure(this.getElementFromCell());
        this.setState({step : this.props.coordinates.coordinates[0].step});
        this.props.getCoordinatesWebVO(this.props.coordinates);       
    }

    nextStep(e){
        e.preventDefault();
        this.setState({step : this.props.coordinates.coordinates[0].step});
        this.props.getCoordinatesWebVO(this.props.coordinates);
        this.clearMap();
        this.paintStructure(this.getElementFromCell());  
    }

    lastStep(e) {
        e.preventDefault();
        this.props.getLastStepData(this.state.step);
        this.setState({step : this.props.coordinates.coordinates[0].step});
    }

    automaticGame(e){
        e.preventDefault();
        setTimeout(() => {this.nextStep(e)},250);
    }

    additionalClasses = (topEdge, leftEdge) => {
		var classes = '';
		if (0 === topEdge) {
			classes += ' top-cell';
		}
		if (0 === leftEdge) {
			classes += ' left-cell';
		}
		return classes;
	}

    drawMap = () => {
        var mainContainer = $('div#main');
        for (let i = 0; i < 100; i++) {
			for (let j = 0; j < 100; j++) {
				mainContainer.append('<div class="cell' + this.additionalClasses(i, j) + '" x=' + j + ' y=' + i + ' />');
			}
			mainContainer.append('<div class="clear" />');
		}  
    }
    
    componentDidMount() {
        this.drawMap();
    }
    
    getElementFromCell = () => {
		return this.props.coordinates;
    }
   
    clearMap =()=> {
        $('.cell.black').removeClass('black');
    }
      
    repaintStructure=(coordinatesWebVO)=> {
        this.clearMap();
        this.paintStructure(coordinatesWebVO);
    }
      
    paintStructure=(coordinatesWebVO)=> {
        $.each(coordinatesWebVO.coordinates, function(index, element) {
            $('.cell[x=' + element.x + '][y=' + element.y + ']').addClass('black');
        });
    }

    render() {
        return (
            <div>
                <div id="main" />
                <div>
                    <form>
                        <button onClick={this.startGame}>Indítás</button>
                        <button onClick={this.automaticGame}>Automatikus</button>
                        <button onClick={this.nextStep}>Következő</button>
                        <button onClick={this.lastStep}>Előző</button>
                        <div>{this.state.step}</div>
                    </form>
                </div>
            </div>
        )
    }
}

Board.propTypes = {
    coordinates: PropTypes.object.isRequired,
    getCoordinatesWebVO: PropTypes.func.isRequired,
    getLastStepData: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
};
  
const mapStateToProps = state => ({
    coordinates: state.coordinates,
    errors: state.errors
});

export default connect(mapStateToProps,{ getCoordinatesWebVO, getLastStepData })(Board);