(window["webpackJsonp"] = window["webpackJsonp"] || []).push([[29],{

/***/ 264:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _src_ol_Map_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(5);
/* harmony import */ var _src_ol_View_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(4);
/* harmony import */ var _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(38);
/* harmony import */ var _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(33);
/* harmony import */ var _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(22);
/* harmony import */ var _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(2);
/* harmony import */ var _src_ol_extent_js__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(3);
/* harmony import */ var _src_ol_coordinate__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(32);
/* harmony import */ var _src_ol_layer_js__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(9);
/* harmony import */ var _src_ol_interaction_Select_js__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(139);











//======================================================================================
// Define Layer Style
//======================================================================================
const style = new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
  fill: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Fill */ "c"]({
    color: 'rgba(255, 255, 255, 0.8)'
  }),
  stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
    color: '#000000',
    width: 1
  }),
  text: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Text */ "h"]({
    font: '12px Calibri,sans-serif',
    fill: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Fill */ "c"]({
      color: '#000'
    }),
    stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
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
	new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
	  stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
	  	color: ROAD_STYLE_1,
	    width: 5
	  })
	}),
	new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
		stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
	    color: '#FFFFFF',
	    width: 3
	  }),
	  text: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Text */ "h"]({
	    font: '12px Calibri,sans-serif',
	    fill: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Fill */ "c"]({
	      color: '#000'
	    }),
	    maxangle: 30,
	    placement : 'line',
	    align: 'center',
	    textBaseline: 'bottom',
	    stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
	      color: '#fff',
	      width: 3
	    })
	  })
  })
];

const highlightStyle = new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
  stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
    color: '#f00',
    width: 1
  }),
//  fill: new Fill({
//    color: 'rgba(255,0,0,0.1)'
//  }),
  text: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Text */ "h"]({
    font: '12px Calibri,sans-serif',
    fill: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Fill */ "c"]({
      color: '#000'
    }),
    stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
      color: '#f00',
      width: 1
    })
  })
});


//======================================================================================
//Factory Function
//======================================================================================
const createRoadLayer = function(geoJsonName, color) {
	return new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
		source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
			url: 'data/geojson/' + geoJsonName,
			format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
		}),
		style: function(feature) {
			roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
			roadStyle[0].getStroke().setColor(color);
			return roadStyle;
		},
	});
}

const createSDLayer = function(geoJsonName) {
	const layer = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
		source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
			url: 'data/geojson/' + geoJsonName,
			format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
		}),
		style: function(feature) {
			style.getText().setText(feature.get('area1'));
			return style;
		}
	});
	return layer;
}

const createSGGLayer = function(geoJsonName) {
	const layer = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
		source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
			url: 'data/geojson/' + geoJsonName,
			format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
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
	const layer = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
		source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
			url: 'data/geojson/' + geoJsonName,
			format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
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

const map = new _src_ol_Map_js__WEBPACK_IMPORTED_MODULE_0__[/* default */ "a"]({
  layers: [
  	new _src_ol_layer_js__WEBPACK_IMPORTED_MODULE_8__[/* Group */ "a"]({
  		layers: [
  			sidoLayer1, sidoLayer2, sidoLayer3, sidoLayer4,
  		  sidoLayer5, sidoLayer6, sidoLayer7, sidoLayer8,
  		  sidoLayer9, sidoLayer10, sidoLayer11, sidoLayer12,
  		  sidoLayer13, sidoLayer14, sidoLayer15, sidoLayer16,
  		  sidoLayer17, sidoLayer18, sidoLayer19
  		]
  	}),
  	new _src_ol_layer_js__WEBPACK_IMPORTED_MODULE_8__[/* Group */ "a"]({
  		layers: [
  			sgg11xxx, sgg41xxx, sgg41131xxx, sgg41133xxx, sgg41135xxx
  			]
  	}),
  	new _src_ol_layer_js__WEBPACK_IMPORTED_MODULE_8__[/* Group */ "a"]({
  		layers: [
  			highwayLayer1, highwayLayer2, highwayLayer3, highwayLayer4,
  		  highwayLayer5, highwayLayer6, highwayLayer7, highwayLayer8,
  		  highwayLayer9, highwayLayer10, highwayLayer11, highwayLayer12,
  		  highwayLayer13, highwayLayer14, highwayLayer15, highwayLayer16,
  		  highwayLayer17, highwayLayer18, highwayLayer19, highwayLayer20
  		]
  	}),
  	new _src_ol_layer_js__WEBPACK_IMPORTED_MODULE_8__[/* Group */ "a"]({
  		layers: [
  			nRoadLayer1, nRoadLayer2, nRoadLayer3
  		]
  	})
  ],
  target: 'map',
  view: new _src_ol_View_js__WEBPACK_IMPORTED_MODULE_1__[/* default */ "a"]({
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

const featureOverlay = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"](),
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
	} else {
		sgg41131xxx.setVisible(false);
		sgg41133xxx.setVisible(false);
		sgg41135xxx.setVisible(false);
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
	var extent = Object(_src_ol_extent_js__WEBPACK_IMPORTED_MODULE_6__[/* createEmpty */ "j"])();
	map.getLayers().forEach(function(layer) {
	  Object(_src_ol_extent_js__WEBPACK_IMPORTED_MODULE_6__[/* extend */ "q"])(extent, layer.getSource().getExtent());
	  console.log(layer.getSource().getExtent())
	});
	map.getView().fit(extent, map.getSize());	
}






//======================================================================================
// Export Global Variable
//======================================================================================

// map.getView().fit(map.getLayers().item(1).getSource().getFeatures()[0].getGeometry().getExtent(), map.getSize());
window.init = init;
window.map = map;
window.featureOverlay= featureOverlay;


/***/ })

},[[264,0]]]);
//# sourceMappingURL=vector-layer.js.map