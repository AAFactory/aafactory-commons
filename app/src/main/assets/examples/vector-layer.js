(window["webpackJsonp"] = window["webpackJsonp"] || []).push([[29],{

/***/ 264:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _src_ol_Map_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(5);
/* harmony import */ var _src_ol_View_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(4);
/* harmony import */ var _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(28);
/* harmony import */ var _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(24);
/* harmony import */ var _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(13);
/* harmony import */ var _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(2);
/* harmony import */ var _src_ol_extent_js__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(3);
/* harmony import */ var _src_ol_coordinate__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(34);
/* harmony import */ var _src_ol_layer_js__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(10);










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

const sidoLayer1 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/경기도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
    feature.set('name', '41')
    return style;
  }
});

const sidoLayer2 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/강원도.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('area1'));
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
		return style;
	}
});

const sgg41xxx = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/41xxx.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		style.getText().setText(feature.get('SIG_KOR_NM'));
		feature.set('name', 'sgg41xxx');
		return style;
	}
});
sgg41xxx.setVisible(false);

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
	  	color: '#FFFFFF',
	    width: 3
	  })
	}),
	new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
		stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
	    color: ROAD_STYLE_1,
	    width: 1
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

const roadLayer1 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/경부고속도로_EPSG4326.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
    roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
    feature.set('name', '경부고속도로');
    return roadStyle;
  }
});

const roadLayer2 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/호남고속도로_EPSG4326.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
  	roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
  	roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
    return roadStyle;
  }
});

const roadLayer3 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중부고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer4 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/통영대전고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer5 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중부내륙고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer6 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/제2중부고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer7 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중부내륙고속도로지선_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer8 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/서울양양고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer9 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/영동고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer10 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/경인고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer11 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/광주원주고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer12 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/내부순환로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_2);
		return roadStyle;
	},
});

const roadLayer13 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/동해고속도로(부산-울산)_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer14 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/동해고속도로(삼척-속초)_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer15 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/동해고속도로(울산-포항)_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer16 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중앙고속도로(춘천-금호)_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer17 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/서해안고속도로_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer18 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/제2경인고속도로(안양-성남)_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer19 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/제2경인고속도로(인천대교)_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer20 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중앙고속도로(부산-대구)_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

const roadLayer21 = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
	source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
		url: 'data/geojson/중앙고속도로(춘천-금호)_EPSG4326.geojson',
		format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
	}),
	style: function(feature) {
		roadStyle[1].getText().setText(feature.get('ROAD_NAME'));
		roadStyle[1].getStroke().setColor(ROAD_STYLE_1);
		return roadStyle;
	},
});

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
  	sgg41xxx,
  	new _src_ol_layer_js__WEBPACK_IMPORTED_MODULE_8__[/* Group */ "a"]({
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
  view: new _src_ol_View_js__WEBPACK_IMPORTED_MODULE_1__[/* default */ "a"]({
    projection: 'EPSG:3857',
    center: [14218435, 4385412],
    zoom: 7
  }),
  controls: []
});

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
      width: 1
    })
  })
});

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
  
  console.log(feature.get('name'));
  if (feature.get('name') == '41') {
  	toggleLayer(false);
  	sgg41xxx.setVisible(true);
  	sgg41xxx.getSource().on('change', function(e) {
  		map.getView().fit(sgg41xxx.getSource().getExtent(), map.getSize());
  	});
  	map.getView().fit(sgg41xxx.getSource().getExtent(), map.getSize());
  }
//  if (feature !== highlight) {
//    if (highlight) {
//      featureOverlay.getSource().removeFeature(highlight);
//    }
//    if (feature) {
//      featureOverlay.getSource().addFeature(feature);
//    }
//    highlight = feature;
//  }

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
	if (e.target.getZoom() < 8 && (map.getView().getCenter()[0] != 14218435)) {
		sgg41xxx.setVisible(false);
		toggleLayer(true);
		map.getView().setCenter([14218435, 4385412]);
	}
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

window.init = init;
window.map = map;
window.featureOverlay= featureOverlay;


/***/ })

},[[264,0]]]);
//# sourceMappingURL=vector-layer.js.map