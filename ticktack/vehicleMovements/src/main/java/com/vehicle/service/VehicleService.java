package com.vehicle.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.vehicle.entity.VehiclePosition;

@Service
public class VehicleService {

    @Value("")//place your api 
    private String apiKey;

    public VehiclePosition getCurrentPosition() {
        return new VehiclePosition(25.5941, 85.1376); // Patna
    }

    public List<VehiclePosition> getRoute() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://maps.googleapis.com/maps/api/directions/json?origin=Patna&destination=Varanasi&key=%s", apiKey);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        // Parse the routes
        List<Map<String, Object>> routes = (List<Map<String, Object>>) response.get("routes");
        if (routes == null || routes.isEmpty()) {
            return new ArrayList<>();
        }

        // Extract the first route
        Map<String, Object> route = (Map<String, Object>) routes.get(0);
        List<Map<String, Object>> legs = (List<Map<String, Object>>) route.get("legs");
        if (legs == null || legs.isEmpty()) {
            return new ArrayList<>();
        }

        // Extract steps from the first leg
        Map<String, Object> leg = (Map<String, Object>) legs.get(0);
        List<Map<String, Object>> steps = (List<Map<String, Object>>) leg.get("steps");

        // Decode polyline points from each step
        List<VehiclePosition> positions = new ArrayList<>();
        for (Map<String, Object> step : steps) {
            Map<String, Object> polyline = (Map<String, Object>) step.get("polyline");
            String encodedPoints = (String) polyline.get("points");
            positions.addAll(decodePolyline(encodedPoints));
        }

        return positions;
    }

    private List<VehiclePosition> decodePolyline(String encoded) {
        List<VehiclePosition> decodedPath = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = (result & 1) != 0 ? ~(result >> 1) : (result >> 1);
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = (result & 1) != 0 ? ~(result >> 1) : (result >> 1);
            lng += dlng;

            decodedPath.add(new VehiclePosition(((lat / 1E5)), ((lng / 1E5))));
        }
        return decodedPath;
    }
}
