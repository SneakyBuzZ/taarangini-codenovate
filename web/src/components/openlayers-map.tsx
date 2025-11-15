'use client';

import { useEffect, useRef } from 'react';
import { GeocodingResult } from '@/lib/geocoding';

// OpenLayers imports
import Map from 'ol/Map';
import View from 'ol/View';
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import Layer from 'ol/layer/Layer';
import VectorSource from 'ol/source/Vector';
import OSM from 'ol/source/OSM';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import Circle from 'ol/geom/Circle';
import { Style, Fill, Stroke, Circle as CircleStyle, Text } from 'ol/style';
import { fromLonLat } from 'ol/proj';
import 'ol/ol.css';

interface Tourist {
  id: string;
  name: string;
  lat: number;
  lng: number;
  status: 'safe' | 'alert' | 'danger';
}

interface OpenLayersMapProps {
  showTourists: boolean;
  showRiskZones: boolean;
  showCCTV: boolean;
  searchQuery: string;
  mapCenter?: [number, number] | null;
  searchResult?: GeocodingResult | null;
}

const tourists: Tourist[] = [
  { id: 'T-001', name: 'John Smith', lat: 28.6139, lng: 77.2090, status: 'safe' },
  { id: 'T-002', name: 'Maria Johnson', lat: 28.6129, lng: 77.2295, status: 'alert' },
  { id: 'T-003', name: 'David Lee', lat: 28.6169, lng: 77.2265, status: 'safe' },
  { id: 'T-004', name: 'Sarah Wilson', lat: 28.6089, lng: 77.2194, status: 'danger' },
  { id: 'T-005', name: 'Emily Chen', lat: 28.6159, lng: 77.2120, status: 'safe' },
  { id: 'T-006', name: 'Michael Brown', lat: 28.6099, lng: 77.2150, status: 'alert' },
];

// Sample CCTV camera locations
const cctvCameras = [
  { id: 'CAM-001', lat: 28.6120, lng: 77.2100 },
  { id: 'CAM-002', lat: 28.6150, lng: 77.2200 },
  { id: 'CAM-003', lat: 28.6180, lng: 77.2080 },
  { id: 'CAM-004', lat: 28.6090, lng: 77.2180 },
];

const OpenLayersMap: React.FC<OpenLayersMapProps> = ({ 
  showTourists, 
  showRiskZones, 
  showCCTV, 
  searchQuery,
  mapCenter,
  searchResult 
}) => {
  const mapRef = useRef<HTMLDivElement>(null);
  const mapInstanceRef = useRef<Map | null>(null);

  useEffect(() => {
    if (!mapRef.current || mapInstanceRef.current) return;

    // Create vector source for tourist markers
    const vectorSource = new VectorSource();

    // Add tourist markers with search filtering
    const filteredTourists = tourists.filter(tourist => {
      if (!searchQuery.trim()) return true; // Show all if no search query
      
      return tourist.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
             tourist.id.toLowerCase().includes(searchQuery.toLowerCase());
    });

    filteredTourists.forEach(tourist => {
      const feature = new Feature({
        geometry: new Point(fromLonLat([tourist.lng, tourist.lat])),
        name: tourist.name,
        id: tourist.id,
        status: tourist.status,
      });

      // Style based on status
      const color = tourist.status === 'safe' ? '#10b981' : 
                   tourist.status === 'alert' ? '#f59e0b' : '#ef4444';
      
      feature.setStyle(new Style({
        image: new CircleStyle({
          radius: 12,
          fill: new Fill({
            color: color,
          }),
          stroke: new Stroke({
            color: '#ffffff',
            width: 3,
          }),
        }),
        text: new Text({
          text: tourist.status === 'safe' ? '✓' : tourist.status === 'alert' ? '!' : '⚠',
          fill: new Fill({
            color: '#ffffff',
          }),
          font: 'bold 12px Arial',
        }),
      }));

      vectorSource.addFeature(feature);
    });

    // Create geo-fence zones
    const geoFenceSource = new VectorSource();

    // Safe zone
    const safeZone = new Feature({
      geometry: new Circle(fromLonLat([77.2090, 28.6139]), 1000),
      name: 'Safe Zone',
    });
    safeZone.setStyle(new Style({
      stroke: new Stroke({
        color: '#14b8a6',
        width: 2,
        lineDash: [5, 5],
      }),
      fill: new Fill({
        color: 'rgba(20, 184, 166, 0.1)',
      }),
    }));
    geoFenceSource.addFeature(safeZone);

    // Risk zone
    const riskZone = new Feature({
      geometry: new Circle(fromLonLat([77.2194, 28.6089]), 500),
      name: 'High-Risk Zone',
    });
    riskZone.setStyle(new Style({
      stroke: new Stroke({
        color: '#ef4444',
        width: 3,
        lineDash: [10, 5],
      }),
      fill: new Fill({
        color: 'rgba(239, 68, 68, 0.15)',
      }),
    }));
    geoFenceSource.addFeature(riskZone);

    // Monitoring zone
    const monitoringZone = new Feature({
      geometry: new Circle(fromLonLat([77.2180, 28.6149]), 750),
      name: 'Monitoring Zone',
    });
    monitoringZone.setStyle(new Style({
      stroke: new Stroke({
        color: '#f59e0b',
        width: 2,
        lineDash: [3, 7],
      }),
      fill: new Fill({
        color: 'rgba(245, 158, 11, 0.08)',
      }),
    }));
    geoFenceSource.addFeature(monitoringZone);

    // Create CCTV camera layer
    const cctvSource = new VectorSource();
    
    // Add CCTV camera features
    cctvCameras.forEach(camera => {
      const feature = new Feature({
        geometry: new Point(fromLonLat([camera.lng, camera.lat])),
        id: camera.id,
        type: 'cctv'
      });

      feature.setStyle(new Style({
        image: new CircleStyle({
          radius: 8,
          fill: new Fill({ color: '#3b82f6' }),
          stroke: new Stroke({ color: 'white', width: 2 }),
        }),
        text: new Text({
          text: '📹',
          font: '12px sans-serif',
          offsetY: -2,
        }),
      }));

      cctvSource.addFeature(feature);
    });

    const cctvLayer = new VectorLayer({
      source: cctvSource,
    });

    // Create vector layers
    const vectorLayer = new VectorLayer({
      source: vectorSource,
    });

    const geoFenceLayer = new VectorLayer({
      source: geoFenceSource,
    });

    // Build layers array conditionally based on props
    const mapLayers: Layer<any>[] = [
      new TileLayer({
        source: new OSM({
          url: 'https://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer/tile/{z}/{y}/{x}',
          attributions: '© Esri, DeLorme, NAVTEQ'
        })
      })
    ];

    // Conditionally add layers based on props
    if (showRiskZones) {
      mapLayers.push(geoFenceLayer);
    }
    if (showTourists) {
      mapLayers.push(vectorLayer);
    }
    if (showCCTV) {
      mapLayers.push(cctvLayer);
    }

    // Create the map
    const map = new Map({
      target: mapRef.current,
      layers: mapLayers,
      view: new View({
        center: fromLonLat([77.2090, 28.6139]), // New Delhi coordinates
        zoom: 13,
      }),
    });

    // Add click event for popups
    map.on('singleclick', (evt) => {
      const feature = map.forEachFeatureAtPixel(evt.pixel, (feature) => feature);
      
      if (feature) {
        const properties = feature.getProperties();
        if (properties.name && properties.id) {
          // Tourist marker clicked
          const popup = document.createElement('div');
          popup.className = 'ol-popup';
          popup.style.cssText = `
            position: absolute;
            background: rgba(0, 0, 0, 0.95);
            border: 1px solid #14b8a6;
            border-radius: 8px;
            padding: 12px;
            color: #f0fdfa;
            font-family: system-ui, sans-serif;
            min-width: 200px;
            box-shadow: 0 4px 20px rgba(20, 184, 166, 0.3);
            z-index: 1000;
          `;
          
          const statusColor = properties.status === 'safe' ? '#10b981' : 
                             properties.status === 'alert' ? '#f59e0b' : '#ef4444';
          
          popup.innerHTML = `
            <div style="display: flex; align-items: center; margin-bottom: 8px;">
              <div style="width: 12px; height: 12px; border-radius: 50%; background-color: ${statusColor}; margin-right: 8px;"></div>
              <h3 style="margin: 0; color: #14b8a6; font-weight: bold;">${properties.name}</h3>
            </div>
            <p style="margin: 4px 0; font-size: 14px;"><strong>Tourist ID:</strong> ${properties.id}</p>
            <p style="margin: 4px 0; font-size: 14px;"><strong>Status:</strong> 
              <span style="color: ${statusColor}; font-weight: bold; text-transform: capitalize;">${properties.status}</span>
            </p>
            <p style="margin: 4px 0; font-size: 12px; color: #5eead4;">📍 Last updated: ${new Date().toLocaleTimeString()}</p>
          `;
          
          // Position popup
          const coordinate = evt.coordinate;
          const pixel = map.getPixelFromCoordinate(coordinate);
          popup.style.left = (pixel[0] + 10) + 'px';
          popup.style.top = (pixel[1] - 10) + 'px';
          
          // Remove existing popups
          const existingPopups = mapRef.current?.querySelectorAll('.ol-popup');
          existingPopups?.forEach(p => p.remove());
          
          // Add popup to map
          mapRef.current?.appendChild(popup);
          
          // Auto-remove popup after 3 seconds
          setTimeout(() => {
            popup.remove();
          }, 3000);
        }
      }
    });

    mapInstanceRef.current = map;

    return () => {
      if (mapInstanceRef.current) {
        mapInstanceRef.current.setTarget(undefined);
        mapInstanceRef.current = null;
      }
    };
  }, [showTourists, showRiskZones, showCCTV, searchQuery]);

  // Handle map center changes (place search navigation)
  useEffect(() => {
    if (mapInstanceRef.current && mapCenter) {
      const view = mapInstanceRef.current.getView();
      
      // Animate to the new location
      view.animate({
        center: fromLonLat(mapCenter), // mapCenter is [lon, lat]
        zoom: 16, // Zoom in to show the location clearly
        duration: 1000 // 1 second animation
      });
      
      // Add a search result marker if we have search result data
      if (searchResult) {
        // Remove any existing search markers
        const existingSearchLayer = mapInstanceRef.current.getLayers().getArray()
          .find(layer => layer.get('id') === 'search-result-layer');
        if (existingSearchLayer) {
          mapInstanceRef.current.removeLayer(existingSearchLayer);
        }
        
        // Create a new marker for the search result
        const searchMarker = new Feature({
          geometry: new Point(fromLonLat([searchResult.lon, searchResult.lat])),
          name: searchResult.display_name,
          type: 'search-result'
        });
        
        searchMarker.setStyle(new Style({
          image: new CircleStyle({
            radius: 12,
            fill: new Fill({
              color: '#f59e0b' // Amber color for search results
            }),
            stroke: new Stroke({
              color: '#ffffff',
              width: 3
            })
          }),
          text: new Text({
            text: '📍',
            font: '16px Arial',
            offsetY: -2
          })
        }));
        
        const searchSource = new VectorSource({
          features: [searchMarker]
        });
        
        const searchLayer = new VectorLayer({
          source: searchSource
        });
        searchLayer.set('id', 'search-result-layer');
        
        mapInstanceRef.current.addLayer(searchLayer);
      }
    }
  }, [mapCenter, searchResult]);

  return (
    <div className="relative w-full h-full">
      <div 
        ref={mapRef} 
        className="w-full h-full rounded-lg border border-teal-600 shadow-2xl bg-white"
        style={{ 
          minHeight: '500px',
          filter: 'brightness(1.2) contrast(1.1)'
        }}
      />
      
      {/* Custom styles for OpenLayers */}
      <style jsx>{`
        :global(.ol-zoom) {
          background: rgba(0, 0, 0, 0.9) !important;
          border: 1px solid #14b8a6 !important;
          border-radius: 4px !important;
        }
        
        :global(.ol-zoom button) {
          background: rgba(0, 0, 0, 0.9) !important;
          color: #14b8a6 !important;
          border: none !important;
        }
        
        :global(.ol-zoom button:hover) {
          background: rgba(20, 184, 166, 0.2) !important;
          color: #0d9488 !important;
        }
        
        :global(.ol-attribution) {
          background: rgba(0, 0, 0, 0.8) !important;
          border: 1px solid #14b8a6 !important;
          border-radius: 4px !important;
        }
        
        :global(.ol-attribution a) {
          color: #14b8a6 !important;
        }
        
        :global(.ol-popup) {
          pointer-events: none;
        }
      `}</style>
    </div>
  );
};

export default OpenLayersMap;