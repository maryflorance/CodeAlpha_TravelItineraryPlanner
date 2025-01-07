import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TravelItineraryPlannerWithMap {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Take user input for destination and travel dates
        System.out.print("Enter destination (e.g., New York, Paris): ");
        String destination = scanner.nextLine();

        System.out.print("Enter travel dates (e.g., 2025-06-15): ");
        String travelDate = scanner.nextLine();

        // Step 2: Generate map URL for the destination using MapTiler
        String mapUrl = generateMapUrl(destination);

        System.out.println("\nTravel Itinerary:");
        System.out.println("Destination: " + destination);
        System.out.println("Travel Date: " + travelDate);
        System.out.println("Map URL: " + mapUrl);

        // Step 3: Download and save the map image (optional)
        String outputFilePath = destination.replace(" ", "_") + "_map.png";
        try {
            downloadMapImage(mapUrl, outputFilePath);
            System.out.println("Map image saved as " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error downloading the map image: " + e.getMessage());
        }
    }

    // Method to generate the MapTiler Static Map URL
    public static String generateMapUrl(String location) {
        // This is the base URL for MapTiler's Basic Map API
        String baseUrl = "https://api.maptiler.com/maps/basic-v2/?key=TrML9YeaXlAI4H4uvWjB";
        
        // Encode the location into the URL and append it to the base URL
        // Replace with proper coordinates (currently placeholders for New York)
        String coordinates = getCoordinatesForLocation(location);
        
        // Generate the final map URL
        return baseUrl + coordinates;
    }

    // Helper method to get coordinates for a location (using a placeholder for now)
    public static String getCoordinatesForLocation(String location) {
        // Here you would use a geocoding API like OpenCage or MapTiler Geocoding API
        // In this case, let's use a static placeholder for New York coordinates
        if (location.equalsIgnoreCase("New York")) {
            return "#-0.2/0.00000/53.69867";  // Coordinates for New York (you can replace this with dynamic coordinates)
        }
        // Default placeholder for other locations (change as per requirement)
        return "#0.0/0.0/0.0";
    }

    // Method to download the map image from the URL
    public static void downloadMapImage(String urlString, String outputFilePath) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            Files.copy(connection.getInputStream(), Paths.get(outputFilePath));
        } else {
            throw new IOException("Failed to download the map: HTTP " + responseCode + " - " + connection.getResponseMessage());
        }

        connection.disconnect();
    }
}
