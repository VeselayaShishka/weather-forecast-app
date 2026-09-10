# Weather Forecast App

Console application that fetches tomorrow's weather forecast for
Chisinau, Madrid, Kyiv, and Amsterdam using the [WeatherAPI.com](https://www.weatherapi.com/).

#### By default, data for Chisinau is compromised with first 'i' changed to '1' to demonstrate error handling mechanism

- **Language:** Java 25
- **Build tool:** Gradle (Kotlin DSL)
- **HTTP client:** Retrofit 2
- **Utilities:** Lombok, Gson

## Data shown per city

- Minimum Temperature (°C)
- Maximum Temperature (°C)
- Humidity (%)
- Wind Speed (kph)
- Wind Direction

## Prerequisites

- JDK 25+
- A free WeatherAPI.com account and API key: https://www.weatherapi.com/signup.aspx

## Setup

1. Clone the repository.
2. Export your API key as an environment variable:
 
```powershell
  $env:WEATHER_API_KEY = "your_api_key_here"
```

## Build & Run

```
./gradlew run
```

### Windows (PowerShell)



Gradle will compile the project and run `Main`, which prints a table like:

```
City         |    Min t |    Max t |  Humidity % |  Wind km/h | Wind Direction
------------------------------------------------------------------------------
Chisinau     |   14,8 C |   26,9 C |          57 |       21,6 | NNE      
Madrid       |   17,1 C |   27,6 C |          16 |       11,5 | E        
Kyiv         |   12,5 C |   21,4 C |          51 |       16,9 | NNW      
Amsterdam    |   12,4 C |   21,1 C |          78 |       14,8 | SW  
```

If a city's data cannot be retrieved (network issue, bad API key, unknown
location, etc.), that row instead shows `ERROR - <HTTP code> <Weather API inner error code> <error message>` and the rest of
the cities are still processed.

## Project structure

```
src/main/java/com/example
                    └───weather
                        │   Main.java                                  # entry point
                        │   
                        ├───api                                         
                        │       RetrofitClient.java                    # Retrofit endpoint definition
                        │       WeatherApiService.java                 # Retrofit/OkHttp client setup
                        │       
                        ├───data
                        │   └───exception                              
                        │           CityForecastException.java         # Custom exceptions records used to represent errors
                        │           CityForecastExceptionDetails.java
                        │           ExceptionParser.java               # Parses raw errors into custom exception records 
                        │           
                        ├───dto                                        # DTO's used to represent the JSON response
                        │       CityForecastResult.java
                        │       Forecast.java
                        │       ForecastDay.java
                        │       ForecastResponse.java
                        │       Location.java
                        │       
                        ├───model                                      # Models used to parse JSON into POJOs
                        │       Day.java
                        │       Hour.java
                        │       
                        └───service                                    
                                PrinterService.java                    # Service that formats results as a STDOUT table
                                WeatherService.java                    # Service containing main business logic for 
                                                                       # fetching forecast    
```

## Notes on implementation choices

- WeatherAPI.com's daily summary (`day` object) does not include a single
  aggregated wind direction, so the midday (12:00) hourly reading is used
  as a representative value for the day.
- Per-city failures are caught individually so one failed city fetch 
  doesn't prevent the others from being displayed.
- The application uses a custom exception records hierarchy to represent
  errors.