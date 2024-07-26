import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import Map from './Map';
import './VehicleTracker.css'; 

const VehicleTracker = () => {
  const [position, setPosition] = useState([25.5941, 85.1376]); 
  const [route, setRoute] = useState([]);
  const [isPlaying, setIsPlaying] = useState(false);
  const [currentStep, setCurrentStep] = useState(0);
  const intervalRef = useRef(null);

  useEffect(() => {
    const fetchVehicleData = async () => {
      try {
        // Fetch current position
        const positionResponse = await axios.get('http://localhost:8080/api/vehicle/current');
        const currentPosition = positionResponse.data;
        console.log(currentPosition)
        setPosition([currentPosition.latitude, currentPosition.longitude]);

        // Fetch route
        const routeResponse = await axios.get('http://localhost:8080/api/vehicle/route');
        setRoute(routeResponse.data);
        console.log(routeResponse.data)
      } catch (error) {
        console.error("There was an error fetching the vehicle data!", error);
      }
    };

    fetchVehicleData();
    const interval = setInterval(fetchVehicleData, 5000); 

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    if (isPlaying) {
      intervalRef.current = setInterval(() => {
        if (currentStep < route.length - 1) {
          setCurrentStep(prevStep => prevStep + 1);
        } else {
          setIsPlaying(false); 
        }
      }, 1000); 
    } else {
      clearInterval(intervalRef.current);
    }

    return () => clearInterval(intervalRef.current);
  }, [isPlaying, currentStep, route]);

  const handlePlayPause = () => {
    setIsPlaying(prev => !prev);
  };

  return (
    <div className="VehicleTracker">
      <div className="map-container">
        <Map position={route[currentStep] || position} route={route.slice(0, currentStep + 1)} />
        <button className="playPauseButton" onClick={handlePlayPause}>
          {isPlaying ? 'Pause' : 'Play'}
        </button>
      </div>
    </div>
  );
};

export default VehicleTracker;
