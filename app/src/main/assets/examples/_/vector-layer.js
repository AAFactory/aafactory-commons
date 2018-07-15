import Map from '../src/ol/Map.js';
import View from '../src/ol/View.js';
import GeoJSON from '../src/ol/format/GeoJSON.js';
import VectorLayer from '../src/ol/layer/Vector.js';
import VectorSource from '../src/ol/source/Vector.js';
import {Fill, Stroke, Style, Text} from '../src/ol/style.js';
import {boundingExtent, createEmpty, extend} from '../src/ol/extent.js';
import {toStringXY} from '../src/ol/coordinate';
import {Group as LayerGroup, Tile as TileLayer} from '../src/ol/layer.js';
import Select from '../src/ol/interaction/Select.js';
import {register} from '../src/ol/proj/proj4.js';

import proj4 from 'proj4';


//======================================================================================
// Define Projection
//======================================================================================
proj4.defs("EPSG:5179","+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
register(proj4);

//======================================================================================
// Define Layer Style
//======================================================================================
const style = new Style({
  fill: new Fill({
    color: 'rgba(255, 255, 255, 0.8)'
  }),
  stroke: new Stroke({
    color: '#000000',
    width: 1
  }),
  text: new Text({
    font: '12px Calibri,sans-serif',
    fill: new Fill({
      color: '#000'
    }),
    stroke: new Stroke({
      color: '#fff',
      width: 3
    })
  })
});

const ROAD_STYLE_1 = '#00FF00';
const ROAD_STYLE_2 = '#ffbc66';
const roadStyle = [
	/* We are using two different styles for the polygons:
	 *  - The first style is for the polygons themselves.
	 *  - The second style is to draw the vertices of the polygons.
	 *    In a custom `geometry` function the vertices of a polygon are
	 *    returned as `MultiPoint` geometry, which will be used to render
	 *    the style.
	 */
	new Style({
	  stroke: new Stroke({
	  	color: ROAD_STYLE_1,
	    width: 5
	  })
	}),
	new Style({
		stroke: new Stroke({
	    color: '#FFFFFF',
	    width: 3
	  }),
	  text: new Text({
	    font: '12px Calibri,sans-serif',
	    fill: new Fill({
	      color: '#000'
	    }),
	    maxangle: 30,
	    placement : 'line',
	    align: 'center',
	    textBaseline: 'bottom',
	    stroke: new Stroke({
	      color: '#fff',
	      width: 3
	    })
	  })
  })
];

const highlightStyle = new Style({
  stroke: new Stroke({
    color: '#f00',
    width: 1
  }),
//  fill: new Fill({
//    color: 'rgba(255,0,0,0.1)'
//  }),
  text: new Text({
    font: '12px Calibri,sans-serif',
    fill: new Fill({
      color: '#000'
    }),
    stroke: new Stroke({
      color: '#f00',
      width: 1
    })
  })
});


//======================================================================================
//Factory Function
//======================================================================================
const createRoadLayer = function(geoJsonName, color) {
	return new VectorLayer({
		source: new VectorSource({
			url: 'data/geojson/' + geoJsonName,
			format: new GeoJSON()
		}),
		style: function(feature) {
			//roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
			roadStyle[0].getStroke().setColor(color);
			return roadStyle;
		},
	});
}

const createSDLayer = function(geoJsonName) {
	const layer = new VectorLayer({
		source: new VectorSource({
			url: 'data/geojson/' + geoJsonName,
			format: new GeoJSON()
		}),
		style: function(feature) {
			style.getText().setText(feature.get('area1'));
			return style;
		}
	});
	return layer;
}

const createSGGLayer = function(geoJsonName) {
	const layer = new VectorLayer({
		source: new VectorSource({
			url: 'data/geojson/' + geoJsonName,
			format: new GeoJSON()
		}),
		style: function(feature) {
			style.getText().setText(feature.get('SIG_KOR_NM'));
			return style;
		}
	});
	layer.setVisible(false);
	return layer;
}

const createEMDLayer = function(geoJsonName) {
	const layer = new VectorLayer({
		source: new VectorSource({
			url: 'data/geojson/' + geoJsonName,
			format: new GeoJSON()
		}),
		style: function(feature) {
			style.getText().setText(feature.get('EMD_KOR_NM'));
			return style;
		}
	});
	layer.setVisible(false);
	return layer;
}


//======================================================================================
// Define Layer 
//======================================================================================
const sidoLayer1 = createSDLayer('경기도.geojson');
const sidoLayer2 = createSDLayer('강원도.geojson');
const sidoLayer3 = createSDLayer('경상북도.geojson');
const sidoLayer4 = createSDLayer('대전광역시.geojson');
const sidoLayer5 = createSDLayer('전라남도.geojson');
const sidoLayer6 = createSDLayer('전라북도.geojson');
const sidoLayer7 = createSDLayer('충청남도.geojson');
const sidoLayer8 = createSDLayer('제주특별자치도.geojson');
const sidoLayer9 = createSDLayer('울산광역시.geojson');
const sidoLayer10 = createSDLayer('부산광역시.geojson');
const sidoLayer11 = createSDLayer('경상남도.geojson');
const sidoLayer12 = createSDLayer('광주광역시.geojson');
const sidoLayer13 = createSDLayer('대구광역시.geojson');
const sidoLayer14 = createSDLayer('전라남도.geojson');
const sidoLayer15 = createSDLayer('서울특별시.geojson');
const sidoLayer16 = createSDLayer('충청북도.geojson');
const sidoLayer17 = createSDLayer('인천광역시.geojson');
const sidoLayer18 = createSDLayer('대전광역시.geojson');
const sidoLayer19 = createSDLayer('세종특별자치시.geojson');

const sgg11xxx = createSGGLayer('11xxx.geojson');
const sgg41xxx = createSGGLayer('41xxx.geojson');
const sgg41131xxx = createEMDLayer('41131xxx.geojson');
const sgg41133xxx = createEMDLayer('41133xxx.geojson');
const sgg41135xxx = createEMDLayer('41135xxx.geojson');
const sgg41610xxx = createEMDLayer('41610xxx.geojson');
const sgg41461xxx = createEMDLayer('41461xxx.geojson');
const sgg41463xxx = createEMDLayer('41463xxx.geojson');
const sgg41465xxx = createEMDLayer('41465xxx.geojson');
const sgg41117xxx = createEMDLayer('41117xxx.geojson');

const highwayLayer1 = createRoadLayer('경부고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer2 = createRoadLayer('호남고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer3 = createRoadLayer('중부고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer4 = createRoadLayer('통영대전고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer5 = createRoadLayer('중부내륙고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer6 = createRoadLayer('제2중부고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer7 = createRoadLayer('중부내륙고속도로지선_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer8 = createRoadLayer('서울양양고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer9 = createRoadLayer('영동고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer10 = createRoadLayer('경인고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer11 = createRoadLayer('광주원주고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer12 = createRoadLayer('세종포천고속도로(구리-포천)_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer13 = createRoadLayer('동해고속도로(부산-울산)_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer14 = createRoadLayer('동해고속도로(삼척-속초)_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer15 = createRoadLayer('동해고속도로(울산-포항)_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer16 = createRoadLayer('중앙고속도로(춘천-금호)_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer17 = createRoadLayer('서해안고속도로_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer18 = createRoadLayer('제2경인고속도로(안양-성남)_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer19 = createRoadLayer('제2경인고속도로(인천대교)_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer20 = createRoadLayer('중앙고속도로(부산-대구)_EPSG4326.geojson', ROAD_STYLE_1);
const highwayLayer21 = createRoadLayer('중앙고속도로(춘천-금호)_EPSG4326.geojson', ROAD_STYLE_1);

const nRoadLayer1 = createRoadLayer('내부순환로_EPSG4326.geojson', ROAD_STYLE_2);
const nRoadLayer2 = createRoadLayer('분당수서간도시고속화도로_EPSG4326.geojson', ROAD_STYLE_2);
const nRoadLayer3 = createRoadLayer('서울외곽순환고속도로_EPSG4326.geojson', ROAD_STYLE_2);
const nRoadLayer4 = createRoadLayer('국도1호선.geojson', ROAD_STYLE_2);
const nRoadLayer5 = createRoadLayer('국도2호선.geojson', ROAD_STYLE_2);
const nRoadLayer6 = createRoadLayer('국도3호선.geojson', ROAD_STYLE_2);
const nRoadLayer7 = createRoadLayer('분당내곡간도시고속화도로.geojson', ROAD_STYLE_2);
const nRoadLayer8 = createRoadLayer('국도4호선.geojson', ROAD_STYLE_2);
const nRoadLayer9 = createRoadLayer('국도6호선.geojson', ROAD_STYLE_2);
const nRoadLayer10 = createRoadLayer('국도7호선.geojson', ROAD_STYLE_2);
const nRoadLayer11 = createRoadLayer('동부간선도로.geojson', ROAD_STYLE_2);
const nRoadLayer12 = createRoadLayer('올림픽대로.geojson', ROAD_STYLE_2);

const map = new Map({
  layers: [
  	new LayerGroup({
  		layers: [
  			sidoLayer1, sidoLayer2, sidoLayer3, sidoLayer4,
  		  sidoLayer5, sidoLayer6, sidoLayer7, sidoLayer8,
  		  sidoLayer9, sidoLayer10, sidoLayer11, sidoLayer12,
  		  sidoLayer13, sidoLayer14, sidoLayer15, sidoLayer16,
  		  sidoLayer17, sidoLayer18, sidoLayer19
  		]
  	}),
  	new LayerGroup({
  		layers: [
  			sgg11xxx, sgg41xxx, sgg41131xxx, sgg41133xxx,
  			sgg41135xxx, sgg41610xxx, sgg41461xxx, sgg41463xxx,
  			sgg41465xxx, sgg41117xxx
  		]
  	}),
  	new LayerGroup({
  		layers: [
  			highwayLayer1, highwayLayer2, highwayLayer3, highwayLayer4,
  		  highwayLayer5, highwayLayer6, highwayLayer7, highwayLayer8,
  		  highwayLayer9, highwayLayer10, highwayLayer11, highwayLayer12,
  		  highwayLayer13, highwayLayer14, highwayLayer15, highwayLayer16,
  		  highwayLayer17, highwayLayer18, highwayLayer19, highwayLayer20
  		]
  	}),
  	new LayerGroup({
  		layers: [
  			nRoadLayer1, nRoadLayer2, nRoadLayer3, nRoadLayer4,
  			nRoadLayer5, nRoadLayer6, nRoadLayer7, nRoadLayer8,
  			nRoadLayer9, nRoadLayer10, nRoadLayer11, nRoadLayer12
  		]
  	})
  ],
  target: 'map',
  view: new View({
    projection: 'EPSG:3857',
    center: [14218435, 4385412],
    zoom: 7
  }),
  controls: []
});



//======================================================================================
// Event Controls
//======================================================================================

//var selectSingleClick = new Select();
//map.addInteraction(selectSingleClick);
//selectSingleClick.on('select', function(e) {
//  console.log(e.target.getFeatures().item(0).get('SIG_CD'));
//});

const featureOverlay = new VectorLayer({
  source: new VectorSource(),
  map: map,
  style: function(feature) {
    highlightStyle.getText().setText(feature.get('ROAD_NAME'));
    return highlightStyle;
  }
});

let highlight;
const displayFeatureInfo = function(pixel) {

  const feature = map.forEachFeatureAtPixel(pixel, function(feature) {
    return feature;
  });
  
  console.log(feature.values_);
//  if (feature.get('name') == '41') {
//  	toggleLayer(false);
//  	sgg41xxx.setVisible(true);
//  	sgg41xxx.getSource().on('change', function(e) {
//  		map.getView().fit(sgg41xxx.getSource().getExtent(), map.getSize());
//  	});
//  	map.getView().fit(sgg41xxx.getSource().getExtent(), map.getSize());
//  }
  if (feature !== highlight) {
    if (highlight) {
      featureOverlay.getSource().removeFeature(highlight);
    }
    if (feature) {
      featureOverlay.getSource().addFeature(feature);
    }
    highlight = feature;
  }
  
  
  map.getView().fit(feature.getGeometry().getExtent(), map.getSize());
};

//map.on('pointermove', function(evt) {
//  if (evt.dragging) {
//    return;
//  }
//  const pixel = map.getEventPixel(evt.originalEvent);
//  displayFeatureInfo(pixel);
//});

map.on('click', function(evt) {
  displayFeatureInfo(evt.pixel);
});

map.updateSize();

map.getView().on('propertychange', function(e) { 
	if (e.target.getZoom() >= 12) {
		sgg41131xxx.setVisible(true);
		sgg41133xxx.setVisible(true);
		sgg41135xxx.setVisible(true);
		sgg41610xxx.setVisible(true);
		sgg41461xxx.setVisible(true);
		sgg41463xxx.setVisible(true);
		sgg41465xxx.setVisible(true);
		sgg41117xxx.setVisible(true);
	} else {
		sgg41131xxx.setVisible(false);
		sgg41133xxx.setVisible(false);
		sgg41135xxx.setVisible(false);
		sgg41610xxx.setVisible(false);
		sgg41461xxx.setVisible(false);
		sgg41463xxx.setVisible(false);
		sgg41465xxx.setVisible(false);
		sgg41117xxx.setVisible(false);
	}
	
	if (e.target.getZoom() >= 9) {
		sgg11xxx.setVisible(true);
		sgg41xxx.setVisible(true);
	} else {
		sgg11xxx.setVisible(false);
		sgg41xxx.setVisible(false);
	}
	
//	if (e.target.getZoom() < 8) {
//		sgg41xxx.setVisible(false);
//		toggleLayer(true);
//		map.getView().setCenter([14218435, 4385412]);
//	} 
	
	
});

const toggleLayer = function(isShow) {
//	map.getLayers().forEach(function(layer, i) { 
//	  if (layer instanceof LayerGroup) {
//      layer.getLayers().forEach(function(sublayer, j) {
//        sublayer.setVisible(isShow);
//      });
//    }
//	});
	map.getLayers().item(0).getLayers().forEach(function(sublayer, j) {
		sublayer.setVisible(isShow);
	});
}

const init = function() {
	var extent = createEmpty();
	map.getLayers().forEach(function(layer) {
	  extend(extent, layer.getSource().getExtent());
	  console.log(layer.getSource().getExtent())
	});
	map.getView().fit(extent, map.getSize());	
}

let isLabelOn = false;
const toggleRoadLabel = function() {
	isLabelOn = !isLabelOn;
	map.getLayers().item(3).getLayers().forEach(function(layer) {
		layer.setStyle(function(feature) {
			if (isLabelOn) {
				roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
			} else {
				roadStyle[1].getText().setText(feature.get(''));
			}
			roadStyle[0].getStroke().setColor(ROAD_STYLE_2);
			return roadStyle;
		});
	});
	
	map.getLayers().item(2).getLayers().forEach(function(layer) {
		layer.setStyle(function(feature) {
			if (isLabelOn) {
				roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
			} else {
				roadStyle[1].getText().setText(feature.get(''));
			}
			roadStyle[0].getStroke().setColor(ROAD_STYLE_1);
			return roadStyle;
		});
	});
}


//======================================================================================
// Export Global Variable
//======================================================================================

// map.getView().fit(map.getLayers().item(1).getSource().getFeatures()[0].getGeometry().getExtent(), map.getSize());
window.init = init;
window.map = map;
window.featureOverlay= featureOverlay;
window.toggleRoadLabel = toggleRoadLabel;
