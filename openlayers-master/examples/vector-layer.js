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
//Define Projection
//======================================================================================
proj4.defs("EPSG:5179","+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs");
register(proj4);


//======================================================================================
// Define Global Variable
//======================================================================================
let showHLayer = false;
let showBLayer = true;
let showPrecisionLayer = false;
const wgs84 = new proj4.Proj('EPSG:4326');
const epsg5179 = new proj4.Proj('EPSG:5179');
const epsg3857 = new proj4.Proj('EPSG:3857');


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
    }),
    overflow: false,
  })
});

const ROAD_STYLE_1 = 'rgba(0, 0, 255, 0.5)';
const ROAD_STYLE_2 = 'rgba(0, 255, 0, 0.5)';
const ROAD_STYLE_3 = 'rgba(100, 114, 101, 1)';
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
	    width: 2
	  })
	}),
	new Style({
		stroke: new Stroke({
	    color: 'rgba(255, 255, 255, 0)',
	    width: 3
	  }),
	  text: new Text({
	    font: '12px Calibri,sans-serif',
	    fill: new Fill({
	      color: '#000'
	    }),
	    overflow: false,
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
    }),
    overflow: false,
    maxangle: 30,
    placement : 'line',
    align: 'center',
    textBaseline: 'bottom'
  })
});

const buildingStyle = new Style({
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
    }),
    overflow: true,
  })
});

//======================================================================================
// Factory Function
//======================================================================================
const createRoadLayer = function(geoJsonName, color, isSimple) {
	var layer = new VectorLayer({
		source: new VectorSource({
			url: 'data/geojson/' + geoJsonName,
			format: new GeoJSON()
		}),
		style: function(feature) {
			if (isSimple) {
				roadStyle[1].getText().setText('');
			} else {
				roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
			}
			roadStyle[0].getStroke().setColor(color);
			return roadStyle;
		},
		declutter: true
	});
	if (!isSimple) {
		layer.setVisible(false);
		layer.on('change', function(e) {
			if (isInitLayer(map.getLayers().item(6).getLayers())) {
				loadBuildingLayer();
			}
		});
	}
	return layer;
}

const createSDLayer = function(geoJsonName) {
	const layer = new VectorLayer({
		source: new VectorSource({
			url: 'data/geojson/' + geoJsonName,
			format: new GeoJSON()
		}),
		style: function(feature) {
			style.getText().setText(feature.get('area1'));
			style.getFill().setColor('rgba(255, 255, 255, 1)');
			return style;
		},
		declutter: true
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
			style.getFill().setColor('rgba(255, 255, 255, 1)');
			return style;
		},
		declutter: true
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
			style.getText().setText(feature.get('adm_nm'));
			style.getFill().setColor('rgba(248, 255, 155, 1)');
			return style;
		},
		declutter: true
	});
	layer.setVisible(false);
	return layer;
}

const createBEMDLayer = function(geoJsonName) {
	const layer = new VectorLayer({
		source: new VectorSource({
			url: 'data/geojson/' + geoJsonName,
			format: new GeoJSON()
		}),
		style: function(feature) {
			style.getText().setText(feature.get('EMD_KOR_NM'));
			style.getFill().setColor('rgba(248, 255, 155, 1)');
			return style;
		},
		declutter: true
	});
	layer.setVisible(false);
	return layer;
}

const createBuildingLayer = function(geoJsonName) {
	const layer = new VectorLayer({
		source: new VectorSource({
			url: 'data/geojson/' + geoJsonName,
			format: new GeoJSON()
		}),
		style: function(feature) {
			buildingStyle.getText().setText(feature.get('BULD_NM'));
			buildingStyle.getFill().setColor('rgba(255, 255, 255, 1)');
			return buildingStyle;
		},
		declutter: true
	});
	layer.setVisible(false);
	layer.on('change', function(e) {
		if (isInitLayer(map.getLayers().item(7).getLayers())) {
			$('#spinner').css('display', 'none');
		}
	});
	return layer;
}

//======================================================================================
// Define Layer 
//======================================================================================
const sidoLayer1 = createSDLayer('sido/11.geojson');  // 서울
const sidoLayer2 = createSDLayer('sido/26.geojson');  // 부산광역시
const sidoLayer3 = createSDLayer('sido/27.geojson');  // 대구광역시
const sidoLayer4 = createSDLayer('sido/28.geojson');  // 인천광역시
const sidoLayer5 = createSDLayer('sido/29.geojson');  // 광주광역시
const sidoLayer6 = createSDLayer('sido/30.geojson');  // 대전광역시
const sidoLayer7 = createSDLayer('sido/31.geojson');  // 울산광역시
const sidoLayer8 = createSDLayer('sido/36.geojson');  // 세종특별자치시
const sidoLayer9 = createSDLayer('sido/41.geojson');  // 경기도       
const sidoLayer10 = createSDLayer('sido/42.geojson'); // 강원도
const sidoLayer11 = createSDLayer('sido/43.geojson'); // 충청북도 
const sidoLayer12 = createSDLayer('sido/44.geojson'); // 충청남도 
const sidoLayer13 = createSDLayer('sido/45.geojson'); // 전라북도  
const sidoLayer14 = createSDLayer('sido/46.geojson'); // 전라남도 
const sidoLayer15 = createSDLayer('sido/47.geojson'); // 경상북도
const sidoLayer16 = createSDLayer('sido/48.geojson'); // 경상남도
const sidoLayer17 = createSDLayer('sido/50.geojson'); // 제주특별자치도


const sgg11xxx = createSGGLayer('sgg/11xxx.geojson');
const sgg26xxx = createSGGLayer('sgg/26xxx.geojson');
const sgg27xxx = createSGGLayer('sgg/27xxx.geojson');
const sgg28xxx = createSGGLayer('sgg/28xxx.geojson');
const sgg29xxx = createSGGLayer('sgg/29xxx.geojson');
const sgg30xxx = createSGGLayer('sgg/30xxx.geojson');
const sgg31xxx = createSGGLayer('sgg/31xxx.geojson');
const sgg36xxx = createSGGLayer('sgg/36xxx.geojson');
const sgg41xxx = createSGGLayer('sgg/41xxx.geojson');
const sgg42xxx = createSGGLayer('sgg/42xxx.geojson');
const sgg43xxx = createSGGLayer('sgg/43xxx.geojson');
const sgg44xxx = createSGGLayer('sgg/44xxx.geojson');
const sgg45xxx = createSGGLayer('sgg/45xxx.geojson');
const sgg46xxx = createSGGLayer('sgg/46xxx.geojson');
const sgg47xxx = createSGGLayer('sgg/47xxx.geojson');
const sgg48xxx = createSGGLayer('sgg/48xxx.geojson');
const sgg50xxx = createSGGLayer('sgg/50xxx.geojson');

const hemdLayer = createEMDLayer('emd/hemd.geojson');
const bemdLayer = createBEMDLayer('emd/bemd.geojson');
const bemd11xxxxxx = createBEMDLayer('emd/11xxxxxx.geojson');

const highwayLayer1 = createRoadLayer('highway/경부고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer2 = createRoadLayer('highway/경인고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer3 = createRoadLayer('highway/광주원주고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer4 = createRoadLayer('highway/동해고속도로(부산-울산)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer5 = createRoadLayer('highway/동해고속도로(삼척-속초)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer6 = createRoadLayer('highway/동해고속도로(울산-포항)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer7 = createRoadLayer('highway/서울양양고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer8 = createRoadLayer('highway/서해안고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer9 = createRoadLayer('highway/세종포천고속도로(구리-포천)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer11 = createRoadLayer('highway/제2경인고속도로(안양-성남)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer10 = createRoadLayer('highway/영동고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer12 = createRoadLayer('highway/제2경인고속도로(인천대교)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer13 = createRoadLayer('highway/제2중부고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer14 = createRoadLayer('highway/중부고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer15 = createRoadLayer('highway/중부내륙고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer16 = createRoadLayer('highway/중부내륙고속도로지선_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer17 = createRoadLayer('highway/중앙고속도로(부산-대구)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer18 = createRoadLayer('highway/중앙고속도로(삼락-대동)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer19 = createRoadLayer('highway/중앙고속도로(춘천-금호)_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer20 = createRoadLayer('highway/통영대전고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer21 = createRoadLayer('highway/호남고속도로_EPSG4326.geojson', ROAD_STYLE_1, true);
const highwayLayer22 = createRoadLayer('highway/광주대구고속도로.geojson', ROAD_STYLE_1, true);
const highwayLayer23 = createRoadLayer('highway/평택제천고속도.geojson', ROAD_STYLE_1, true);

const nRoadLayer1 = createRoadLayer('nationalroad/내부순환로_EPSG4326.geojson', ROAD_STYLE_2, true);
const nRoadLayer2 = createRoadLayer('nationalroad/분당수서간도시고속화도로_EPSG4326.geojson', ROAD_STYLE_2, true);
const nRoadLayer3 = createRoadLayer('nationalroad/서울외곽순환고속도로_EPSG4326.geojson', ROAD_STYLE_2, true);
const nRoadLayer4 = createRoadLayer('nationalroad/국도1호선.geojson', ROAD_STYLE_2, true);
const nRoadLayer5 = createRoadLayer('nationalroad/국도2호선.geojson', ROAD_STYLE_2, true);
const nRoadLayer6 = createRoadLayer('nationalroad/국도3호선.geojson', ROAD_STYLE_2, true);
const nRoadLayer7 = createRoadLayer('nationalroad/분당내곡간도시고속화도로.geojson', ROAD_STYLE_2, true);
const nRoadLayer8 = createRoadLayer('nationalroad/국도4호선.geojson', ROAD_STYLE_2, true);
const nRoadLayer9 = createRoadLayer('nationalroad/국도6호선.geojson', ROAD_STYLE_2, true);
const nRoadLayer10 = createRoadLayer('nationalroad/국도7호선.geojson', ROAD_STYLE_2, true);
const nRoadLayer11 = createRoadLayer('nationalroad/동부간선도로.geojson', ROAD_STYLE_2, true);
const nRoadLayer12 = createRoadLayer('nationalroad/올림픽대로.geojson', ROAD_STYLE_2, true);

const preciseRoadLayer01 = createRoadLayer('precision/평택제천고속도.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer02 = createRoadLayer('precision/서울외곽순환고속도로.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer03 = createRoadLayer('precision/서울외곽순환고속도로.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer04 = createRoadLayer('precision/성남대로.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer05 = createRoadLayer('precision/헌릉로.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer06 = createRoadLayer('precision/일반국도3호선.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer07 = createRoadLayer('precision/동부간선도로.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer08 = createRoadLayer('precision/20400xxxxx.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer09 = createRoadLayer('precision/20401xxxxx.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer10 = createRoadLayer('precision/20500xxxxx.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer11 = createRoadLayer('precision/20601xxxxx.geojson', ROAD_STYLE_3, false);
const preciseRoadLayer12 = createRoadLayer('precision/20602xxxxx.geojson', ROAD_STYLE_3, false);

const buildingLayer1 = createBuildingLayer('buildings/elementary_school.geojson');
const buildingLayer2 = createBuildingLayer('buildings/parking_lot.geojson');
const buildingLayer3 = createBuildingLayer('buildings/apartment_4113x.geojson');
const buildingLayer4 = createBuildingLayer('buildings/railway_station.geojson');

const map = new Map({
  layers: [
  	new LayerGroup({ // index 0
  		layers: [
  			sidoLayer1, sidoLayer2, sidoLayer3, sidoLayer4, sidoLayer5,
  			sidoLayer6, sidoLayer7, sidoLayer8, sidoLayer9, sidoLayer10,
  			sidoLayer11, sidoLayer12, sidoLayer13, sidoLayer14, sidoLayer15,
  			sidoLayer16, sidoLayer17
  		]
  	}),
  	new LayerGroup({ // index 1  
  		layers: [
  			sgg11xxx, sgg26xxx, sgg27xxx, sgg28xxx, sgg29xxx,
  			sgg30xxx, sgg31xxx, sgg36xxx, sgg41xxx, sgg42xxx,
  			sgg43xxx, sgg44xxx, sgg45xxx, sgg46xxx, sgg47xxx,
  			sgg48xxx, sgg50xxx
  		]
  	}),
  	hemdLayer,       // index 2
  	new LayerGroup({ // index 3
  		layers: [
  			bemdLayer, bemd11xxxxxx
  		]
  	}),
  	new LayerGroup({ // index 4
  		layers: [
  			highwayLayer1, highwayLayer2, highwayLayer3, highwayLayer4, highwayLayer5,
  			highwayLayer6, highwayLayer7, highwayLayer8, highwayLayer9, highwayLayer10,
  			highwayLayer11, highwayLayer12, highwayLayer13, highwayLayer14, highwayLayer15,
  			highwayLayer16, highwayLayer17, highwayLayer18, highwayLayer19, highwayLayer20,
  			highwayLayer21/*, highwayLayer22, highwayLayer23*/
  		]
  	}),
  	new LayerGroup({ // index 5
  		layers: [
  			nRoadLayer1, nRoadLayer2, nRoadLayer3, nRoadLayer4, nRoadLayer5,
  			nRoadLayer6, nRoadLayer7, nRoadLayer8, nRoadLayer9, nRoadLayer10,
  			nRoadLayer11, nRoadLayer12
  		]
  	}),
  	new LayerGroup({ // index 6
  		layers: [
  			preciseRoadLayer01, preciseRoadLayer02, preciseRoadLayer03, preciseRoadLayer04, preciseRoadLayer05,
  			preciseRoadLayer06, preciseRoadLayer07, preciseRoadLayer08, preciseRoadLayer09, preciseRoadLayer10,
  			preciseRoadLayer11, preciseRoadLayer12
  		]
  	}),
  	new LayerGroup({ // index 7
  		layers: [
  			buildingLayer1, buildingLayer2, buildingLayer3, buildingLayer4
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
  },
  declutter: true
});

let highlight;
const getHighlight = function() { return highlight; }
const setHighlight = function(feature) { highlight = feature; }
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
  
  
//  map.getView().fit(feature.getGeometry().getExtent(), map.getSize());
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
	var degree = (map.getView().getRotation() * 57.2) + 180;
//	console.log(degree);
	 $('#compass').css({'transform' : 'rotate('+ degree +'deg)'});
//	const point = proj4.Point(e.target.getCenter()[0], e.target.getCenter()[1]);
	const point = {x: e.target.getCenter()[0], y: e.target.getCenter()[1]};
	const wgs84LatLng = proj4.transform(epsg3857, wgs84, point);
	updateMapStatus(wgs84LatLng.x, wgs84LatLng.y, e.target.getZoom());
	updateLayer(e.target.getZoom());
	determineAreaName();
});

const updateLayer = function(zoomLevel) {
	if (zoomLevel < 9) {
		toggleLayers(true, 0);
		toggleLayers(false, 1);
		toggleLayers(false, 2);
		toggleLayers(false, 3);
	} else if (zoomLevel >= 9 && zoomLevel < 11) {
		toggleLayers(true, 0);
		toggleLayers(true, 1);
		toggleLayers(false, 2);
		toggleLayers(false, 3);
	} else if (zoomLevel >= 11 && zoomLevel < 14) {
		toggleLayers(true, 0);
		toggleLayers(true, 1);
		toggleLayers(showHLayer, 2);
		toggleLayers(showBLayer, 3);
	} 
	
	if (zoomLevel >= 15) {
		showPrecisionLayer = true; 
		toggleLayers(showPrecisionLayer, 6);
		toggleLayers(true, 7);
	} else {
		showPrecisionLayer = false;
		toggleLayers(showPrecisionLayer, 6);
		toggleLayers(false, 7);
	}
	
//	if (e.target.getZoom() < 8) {
//		sgg41xxx.setVisible(false);
//		toggleLayer(true);
//		map.getView().setCenter([14218435, 4385412]);
//	} 
}

bemdLayer.on('change', function(e) {
	if (isInitLayer(map.getLayers().item(3).getLayers())) {
		$('#spinner').css('display', 'none');
	}
});

bemd11xxxxxx.on('change', function(e) {
	if (isInitLayer(map.getLayers().item(3).getLayers())) {
		$('#spinner').css('display', 'none');
	}
});

const switchEMDLayer = function() {
	showHLayer = !showHLayer;
	showBLayer = !showBLayer;
	updateLayer(map.getView().getZoom());
}

const toggleLayers = function(isVisible, index) {
	if (index == 2) {
		map.getLayers().item(index).setVisible(isVisible);
	} else if (index == 3 || index == 6) {
		if (isVisible && !isInitLayer(map.getLayers().item(index).getLayers())) {
			if (index == 3) {
				$('#spinner').html("Loading...");
			} else if (index == 6) {
				$('#spinner').html("Update Road Layer...");
			}
			$('#spinner').css('display', 'block');
		}
		map.getLayers().item(index).getLayers().forEach(function(layer) {
			layer.setVisible(isVisible);
		});
	} else {
		map.getLayers().item(index).getLayers().forEach(function(sggLayer) {
			sggLayer.setVisible(isVisible);
		});
	}
}

const loadBuildingLayer = function() {
	$('#spinner').html("Update Building Layer...");
	map.getLayers().item(7).getLayers().forEach(function(layer) {
		layer.setVisible(true);
	});
}

const isInitLayer = function(layers) {
	var count = 0;
	layers.forEach(function(subLayer) {
		if (subLayer.getSource().getFeatures().length > 0) count++;
	});
	console.log(count, layers.getArray().length, count == layers.getArray().length);
	return count == layers.getArray().length
}

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
	map.getLayers().item(4).getLayers().forEach(function(layer) {
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
	
	map.getLayers().item(5).getLayers().forEach(function(layer) {
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
}

const determineAreaName = function() {
	const nameMap = {sido:'', sgg:'', hemd:'', bemd:'', roadName:''};
	map.forEachFeatureAtPixel([window.innerWidth / 2, window.innerHeight / 2], function(feature) {
		if (feature.get('area1')) nameMap.sido = feature.get('area1');
		if (feature.get('SIG_KOR_NM')) nameMap.sgg = feature.get('SIG_KOR_NM');
		if (feature.get('adm_nm')) nameMap.hemd = feature.get('adm_nm');
		if (feature.get('EMD_KOR_NM')) nameMap.bemd = feature.get('EMD_KOR_NM');
		if (feature.get('ROAD_NAME')) nameMap.roadName = feature.get('ROAD_NAME');
	});
	updateAreaName(nameMap);
}

const setPrecisionLayer = function(flag) {
	showPrecisionLayer = flag;
}

const getPrecisionLayer = function() {
	return showPrecisionLayer;
}

//======================================================================================
// Export Global Variable
//======================================================================================

// map.getView().fit(map.getLayers().item(1).getSource().getFeatures()[0].getGeometry().getExtent(), map.getSize());
window.init = init;
window.map = map;
window.featureOverlay= featureOverlay;
window.toggleRoadLabel = toggleRoadLabel;
window.toggleLayers = toggleLayers;
window.switchEMDLayer = switchEMDLayer;
window.proj4 = proj4;
window.determineAreaName = determineAreaName;
window.setPrecisionLayer = setPrecisionLayer;
window.getPrecisionLayer = getPrecisionLayer;
window.getHighlight = getHighlight;
window.setHighlight = setHighlight;