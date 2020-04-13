import React from 'react';
import './App.css';
import Background from './assets/wallpaper.jpg';
import axios from 'axios'

var backgroundStyle = {
  backgroundImage: `url(${Background})`
}


export class App extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      outputResults: "",
      searchInput: ""
    }
    this.handleSubmit = this.handleSubmit.bind(this);
    this.updateInput = this.updateInput.bind(this);

    this.GET_URL = "http://localhost:8080/search?query=";
  }

  handleSubmit = () => {
    if (this.state.searchInput.length > 0) {
      var URL = this.GET_URL + encodeURIComponent(this.state.searchInput);
      axios.get(URL, {
        headers: {
          'Access-Control-Allow-Origin': '*'
        }
      }
      ).then(response => this.showOutput(response.data));
    }
  }


  showOutput = (data) => {
    var output = "";
    data.forEach(element => {
      output += element + "\n\n";
    });
    this.setState({
      ...this.state,
      outputResults: output
    });
  }

  updateInput(event) {
    this.setState({
      ...this.state,
      [event.target.id]: event.target.value
    });
  }


  render() {
    return (
      <div style={backgroundStyle} id="content">
        <div id="title">
          Engine Solution
        </div>
        <div id="flex-container">
          <div id="input-container">
            <input type="text" id="searchInput" onChange={this.updateInput}></input>
            <button onClick={this.handleSubmit} >Search</button>
            <div id="legend">| - OR, & - AND, ! - NOT</div>
          </div>
          <div id="output-container">
            <label>Results</label>
            <textarea id="search-output" readOnly value={this.state.outputResults}>

            </textarea>
          </div>
        </div>

      </div>
    );
  }
}

export default App;
