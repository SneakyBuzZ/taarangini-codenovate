export interface GeocodingResult {
  lat: number;
  lon: number;
  display_name: string;
  place_id: string;
  importance: number;
}

export interface SearchPlaceResult {
  success: boolean;
  results: GeocodingResult[];
  error?: string;
}

export async function searchPlace(query: string): Promise<SearchPlaceResult> {
  if (!query.trim()) {
    return {
      success: false,
      results: [],
      error: 'Search query is empty'
    };
  }

  try {
    const response = await fetch(
      `https://nominatim.openstreetmap.org/search?` +
      new URLSearchParams({
        q: query,
        format: 'json',
        limit: '5',
        addressdetails: '1',
        countrycodes: 'in' // Limit to India for tourist safety app
      }),
      {
        headers: {
          'User-Agent': 'TrianaDash/1.0 (Tourist Safety App)'
        }
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }

    const data = await response.json();
    
    const results: GeocodingResult[] = data.map((item: any) => ({
      lat: parseFloat(item.lat),
      lon: parseFloat(item.lon),
      display_name: item.display_name,
      place_id: item.place_id,
      importance: item.importance || 0
    }));

    return {
      success: true,
      results
    };
  } catch (error) {
    console.error('Geocoding error:', error);
    return {
      success: false,
      results: [],
      error: error instanceof Error ? error.message : 'Unknown error occurred'
    };
  }
}