// src/components/Map.js
import React from 'react';
import { MapContainer, TileLayer, Marker, Polyline } from 'react-leaflet';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

const vehicleIcon = new L.Icon({
  iconUrl: 'https://png.pngtree.com/element_our/20200703/ourmid/pngtree-three-dimensional-cool-sports-car-png-image_2300881.jpg', 
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -32],
});

const Map = ({ position, route }) => {
  return (
    <MapContainer center={position} zoom={13} style={{ height: '100vh', width: '100%' }}>
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />
      <Marker position={position} icon={vehicleIcon}>
        <div>Current Position</div>
      </Marker>
      <Polyline positions={route} color="blue" />
    </MapContainer>
  );
};

export default Map;
