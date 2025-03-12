# Telangana Travel Guide App

## Overview

Telangana Travel Guide is an Android application that helps users explore the best tourist attractions, hotels, restaurants, and travel facilities across all 34 districts of Telangana. The app provides detailed information, including images, ratings, descriptions, and transport options for each location.

## Features

- **Interactive Telangana Map**: Select districts using an interactive map with ImageButtons.
- **Tourist Attractions**: View detailed information about top tourist places in each district.
- **Hotels & Restaurants**: Explore the best accommodations and dining options nearby.
- **Transport Options**: Get details on nearby bus and train stations, fuel stations, and ATMs.
- **ViewPager2 Slideshow**: Automatically cycles through images of places with descriptions.
- **Search & Filters**: Search across attractions, hotels, and restaurants with tag-based filters.
- **RecyclerViews with Glide**: Fetches and caches images using Glide for efficient display.
- **Offline Access**: Uses cached data for previously visited places.
- **JSON Data Handling**: Loads and unifies data from Google Maps and TripAdvisor sources.


## Tech Stack

- **Language**: Java (Main),XML(for App layouts)
- **UI Components**: ViewPager2, RecyclerView, CardView
- **Data Handling**: JSON, Glide (Image Loading & Caching)
- **Architecture**: MVVM (ViewModel, LiveData, Repository)
- **In-App Storage**: JSON Files (For images)
- **Location Services**: Google Maps API (For distance calculation)

## How It Works

1. The app loads district-based tourist data from a unified JSON file.
2. Users select a district from the interactive map to view attractions.
3. A ViewPager2 slideshow showcases tourist spots.
4. The app provides transport and nearby facility details.
5. Users can explore hotels and restaurants through categorized RecyclerViews.
6. A search feature with filter chips allows easy discovery of locations.
7. Images are fetched from Firebase Storage and cached using Glide.
8. A background service updates images if the internet is available.

## Future Enhancements

- **User Reviews & Ratings**:  users can  ratings and reviews for places.
- **Itinerary Planner**: Suggest best travel routes and plans.
- **Dark Mode**: Support for light and dark themes.
- **Offline Details**: Can Browse Through app without internet.
- **Real-Time Weather Updates**: Show current weather for selected places.
**Note**:First Installaion requires Internet and Browsing fisrt
## Installation

1. Clone this repository:
   ```bash
   https://github.com/Andrew-003/Travel-Guide-For-Telangana.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle and install dependencies.
4. Run the app on an emulator or physical device.

## License

This project is open-source and available under the [MIT License](LICENSE).

## Contact

For any queries, reach out via email at `mosesandrew003@gaml.com` or open an issue in the repository.



