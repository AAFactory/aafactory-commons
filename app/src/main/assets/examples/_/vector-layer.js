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

const sidoLayer1 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/경기도.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    feature.set('name', '41')
    return style;
  }
});

const sidoLayer2 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/강원도.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    return style;
  }
});

const sidoLayer3 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/경상북도.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    return style;
  }
});

const sidoLayer4 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/대전광역시.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    return style;
  }
});

const sidoLayer5 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/전라남도.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    return style;
  }
});

const sidoLayer6 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/전라북도.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    return style;
  }
});

const sidoLayer7 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/충청남도.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    return style;
  }
});

const sidoLayer8 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/제주특별자치도.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    return style;
  }
});

const sidoLayer9 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/울산광역시.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    return style;
  }
});

const sidoLayer10 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/부산광역시.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer11 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/경상남도.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer12 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/광주광역시.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer13 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/대구광역시.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer14 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/전라남도.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer15 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/서울특별시.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer16 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/충청북도.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer17 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/인천광역시.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer18 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/대전광역시.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sidoLayer19 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/세종특별자치시.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		return style;
	}
});

const sgg41xxx = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/41xxx.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('SIG_KOR_NM'));
		feature.set('name', 'sgg41xxx');
		return style;
	}
});
sgg41xxx.setVisible(false);

const sgg41131xxx = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/41131xxx.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('EMD_KOR_NM'));
		return style;
	}
});
sgg41131xxx.setVisible(false);

const sgg41133xxx = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/41133xxx.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('EMD_KOR_NM'));
		return style;
	}
});
sgg41133xxx.setVisible(false);

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
	  	color: '#FFFFFF',
	    width: 3
	  })
	}),
	new Style({
		stroke: new Stroke({
	    color: ROAD_STYLE_1,
	    width: 1
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

const roadLayer1 = new VectorLayer({
	source: new VectorSource({
    url: 'data/geojson/경부고속도로_EPSG4326.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
    roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
    roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
    feature.set('name', '경부고속도로');
    return roadStyle;
  }
});

const roadLayer2 = new VectorLayer({
  source: new VectorSource({
    url: 'data/geojson/호남고속도로_EPSG4326.geojson',
    format: new GeoJSON()
  }),
  style: function(feature) {
  	roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
  	roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
    return roadStyle;
  }
});

const roadLayer3 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/중부고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer4 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/통영대전고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer5 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/중부내륙고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer6 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/제2중부고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer7 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/중부내륙고속도로지선_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer8 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/서울양양고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer9 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/영동고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer10 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/경인고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer11 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/광주원주고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer12 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/내부순환로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_2);
		return roadStyle;
	},
});

const roadLayer13 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/동해고속도로(부산-울산)_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer14 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/동해고속도로(삼척-속초)_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer15 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/동해고속도로(울산-포항)_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer16 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/중앙고속도로(춘천-금호)_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer17 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/서해안고속도로_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer18 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/제2경인고속도로(안양-성남)_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer19 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/제2경인고속도로(인천대교)_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer20 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/중앙고속도로(부산-대구)_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer21 = new VectorLayer({
	source: new VectorSource({
		url: 'data/geojson/중앙고속도로(춘천-금호)_EPSG4326.geojson',
		format: new GeoJSON()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

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
  			sgg41xxx, sgg41131xxx, sgg41133xxx
  			]
  	}),
  	new LayerGroup({
  		layers: [
  			roadLayer1, roadLayer2, roadLayer3, roadLayer4,
  		  roadLayer5, roadLayer6, roadLayer7, roadLayer8,
  		  roadLayer9, roadLayer10, roadLayer11, roadLayer12,
  		  roadLayer13, roadLayer14, roadLayer15, roadLayer16,
  		  roadLayer17, roadLayer18, roadLayer19, roadLayer20
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

//var selectSingleClick = new Select();
//map.addInteraction(selectSingleClick);
//selectSingleClick.on('select', function(e) {
//  console.log(e.target.getFeatures().item(0).get('SIG_CD'));
//});

const highlightStyle = new Style({
  stroke: new Stroke({
    color: '#f00',
    width: 1
  }),
  fill: new Fill({
    color: 'rgba(255,0,0,0.1)'
  }),
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
	} else {
		sgg41131xxx.setVisible(false);
		sgg41133xxx.setVisible(false);
	}
	
	if (e.target.getZoom() >= 8) {
		sgg41xxx.setVisible(true);
	} else {
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

// map.getView().fit(map.getLayers().item(1).getSource().getFeatures()[0].getGeometry().getExtent(), map.getSize());
window.init = init;
window.map = map;
window.featureOverlay= featureOverlay;
