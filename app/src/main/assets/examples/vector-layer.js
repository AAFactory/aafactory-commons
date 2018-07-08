let map;

(window["webpackJsonp"] = window["webpackJsonp"] || []).push([[29],{

/***/ 264:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _src_ol_Map_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(5);
/* harmony import */ var _src_ol_View_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(4);
/* harmony import */ var _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(30);
/* harmony import */ var _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(28);
/* harmony import */ var _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(16);
/* harmony import */ var _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(2);







const style = new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
  fill: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Fill */ "c"]({
    color: 'rgba(255, 255, 255, 0.1)'
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

const sidoLayer1 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/경기도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(247, 218, 103, 0.1)');
    return style;
  }
});
sidoLayer1.set('name', 'sidoLayer1');

const sidoLayer2 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/강원도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(188, 244, 83, 0.1)');
    return style;
  }
});

const sidoLayer3 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/경상북도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(224, 168, 96, 0.1)');
    return style;
  }
});

const sidoLayer4 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/대전광역시.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(18, 144, 83, 0.1)');
    return style;
  }
});

const sidoLayer5 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/전라남도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(100, 0, 83, 0.1)');
    return style;
  }
});

const sidoLayer6 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/전라북도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(100, 0, 100, 0.1)');
    return style;
  }
});

const sidoLayer7 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/충청남도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(100, 0, 255, 0.1)');
    return style;
  }
});

const sidoLayer8 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/제주특별자치도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(0, 0, 255, 0.1)');
    return style;
  }
});

const sidoLayer9 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/울산광역시.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    style.getFill().setColor('rgba(100, 51, 206, 0.1)');
    return style;
  }
});

const sidoLayer10 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/부산광역시.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(60, 151, 206, 0.1)');
		return style;
	}
});

const sidoLayer11 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/경상남도.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(160, 51, 206, 0.1)');
		return style;
	}
});

const sidoLayer12 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/광주광역시.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(60, 51, 0, 0.1)');
		return style;
	}
});

const sidoLayer13 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/대구광역시.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(60, 0, 206, 0.1)');
		return style;
	}
});

const sidoLayer14 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/전라남도.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(60, 111, 111, 0.1)');
		return style;
	}
});

const sidoLayer15 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/서울특별시.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(55, 55, 206, 0.6)');
		return style;
	}
});

const sidoLayer16 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/충청북도.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(155, 155, 206, 0.1)');
		return style;
	}
});

const sidoLayer17 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/인천광역시.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(60, 235, 206, 0.1)');
		return style;
	}
});

const sidoLayer18 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/대전광역시.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(60, 51, 161, 0.1)');
		return style;
	}
});

const sidoLayer19 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/세종특별자치시.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('area1'));
		style.getFill().setColor('rgba(11, 51, 22, 0.1)');
		return style;
	}
});
sidoLayer19.set('name', 'sidoLayer19');

const loadStyle1 = new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
  stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
	    color: '#000000',
	    width: 3
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

const loadStyle = [
	/* We are using two different styles for the polygons:
	 *  - The first style is for the polygons themselves.
	 *  - The second style is to draw the vertices of the polygons.
	 *    In a custom `geometry` function the vertices of a polygon are
	 *    returned as `MultiPoint` geometry, which will be used to render
	 *    the style.
	 */
	new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
	  stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
	  	color: '#FFFFFF',
	    width: 6
	  })
	}),
	new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
		stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
	    color: '#00FF00',
	    width: 2
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
  })
];

const loadLayer1 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/경부고속도로.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    loadStyle[1].getText().setText(feature.get('ROAD_NAME'));
    loadStyle[1].getStroke().setColor('rgba(21, 255, 32, 1)');
    return loadStyle;
  }
});
loadLayer1.set('name', 'loadLayer1');

const loadLayer2 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/호남고속도로.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
  	loadStyle[1].getText().setText(feature.get('ROAD_NAME'));
  	loadStyle[1].getStroke().setColor('rgba(154, 47, 200, 1)');
    return loadStyle;
  }
});

const loadLayer3 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중부고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		loadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		loadStyle[1].getStroke().setColor('rgba(98, 25, 255, 1)');
		return loadStyle;
	},
});

const loadLayer4 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/통영대전고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		loadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		loadStyle[1].getStroke().setColor('rgba(255, 23, 73, 1)');
		return loadStyle;
	},
});

const loadLayer5 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중부내륙고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		loadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		loadStyle[1].getStroke().setColor('rgba(229, 255, 29, 1)');
		return loadStyle;
	},
});

const loadLayer6 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/제2중부고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		loadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		loadStyle[1].getStroke().setColor('rgba(255, 53, 195, 1)');
		return loadStyle;
	},
});

const loadLayer7 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중부내륙고속도로지선_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		loadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		loadStyle[1].getStroke().setColor('rgba(163, 41, 16, 1)');
		return loadStyle;
	},
});

map = new _src_ol_Map_js__WEBPACK_IMPORTED_MODULE_0__[/* default */ "a"]({
  layers: [
	  sidoLayer1, sidoLayer2, sidoLayer3, sidoLayer4,
	  sidoLayer5, sidoLayer6, sidoLayer7, sidoLayer8,
	  sidoLayer9, sidoLayer10, sidoLayer11, sidoLayer12,
	  sidoLayer13, sidoLayer14, sidoLayer15, sidoLayer16,
	  sidoLayer17, sidoLayer18, sidoLayer19,
	  loadLayer1, loadLayer2, loadLayer3, loadLayer4,
	  loadLayer5, loadLayer6, loadLayer7],
  target: 'map',
  view: new _src_ol_View_js__WEBPACK_IMPORTED_MODULE_1__[/* default */ "a"]({
    projection: 'EPSG:3857',
    center: [14218435, 4385412],
    zoom: 7
  }),
  controls: []
});

//loadLayer1.setMap(map);
//loadLayer2.setMap(map);
//loadLayer3.setMap(map);
//loadLayer4.setMap(map);
//loadLayer5.setMap(map);
//loadLayer6.setMap(map);
//loadLayer7.setMap(map);

const highlightStyle = new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
  stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
    color: '#f00',
    width: 1
  }),
  fill: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Fill */ "c"]({
    color: 'rgba(255,0,0,0.1)'
  }),
  text: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Text */ "h"]({
    font: '12px Calibri,sans-serif',
    fill: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Fill */ "c"]({
      color: '#000'
    }),
    stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
      color: '#f00',
      width: 3
    })
  })
});

const featureOverlay = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"](),
  map: map,
  style: function(feature) {
    highlightStyle.getText().setText(feature.get('name'));
    return highlightStyle;
  }
});

let highlight;
const displayFeatureInfo = function(pixel) {

  const feature = map.forEachFeatureAtPixel(pixel, function(feature) {
    return feature;
  });
  
  

  if (feature !== highlight) {
    if (highlight) {
      featureOverlay.getSource().removeFeature(highlight);
    }
    if (feature) {
      featureOverlay.getSource().addFeature(feature);
    }
    highlight = feature;
  }

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


/***/ })

},[[264,0]]]);
//# sourceMappingURL=vector-layer.js.map