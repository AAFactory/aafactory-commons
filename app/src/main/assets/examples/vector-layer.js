(window["webpackJsonp"] = window["webpackJsonp"] || []).push([[29],{

/***/ 264:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _src_ol_Map_js__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(5);
/* harmony import */ var _src_ol_View_js__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(4);
/* harmony import */ var _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(44);
/* harmony import */ var _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(36);
/* harmony import */ var _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(22);
/* harmony import */ var _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(2);








const style = new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Style */ "g"]({
  fill: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Fill */ "c"]({
    color: 'rgba(255, 255, 255, 0.6)'
  }),
  stroke: new _src_ol_style_js__WEBPACK_IMPORTED_MODULE_5__[/* Stroke */ "f"]({
    color: '#319FD3',
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

const vectorLayer = new _src_ol_layer_Vector_js__WEBPACK_IMPORTED_MODULE_3__[/* default */ "a"]({
  source: new _src_ol_source_Vector_js__WEBPACK_IMPORTED_MODULE_4__[/* default */ "b"]({
    url: 'data/geojson/countries.geojson',
    format: new _src_ol_format_GeoJSON_js__WEBPACK_IMPORTED_MODULE_2__[/* default */ "a"]()
  }),
  style: function(feature) {
    style.getText().setText(feature.get('name'));
    return style;
  }
});

const map = new _src_ol_Map_js__WEBPACK_IMPORTED_MODULE_0__[/* default */ "a"]({
  layers: [vectorLayer],
  target: 'map',
  view: new _src_ol_View_js__WEBPACK_IMPORTED_MODULE_1__[/* default */ "a"]({
    center: [0, 0],
    zoom: 1
  })
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

map.on('pointermove', function(evt) {
  if (evt.dragging) {
    return;
  }
  const pixel = map.getEventPixel(evt.originalEvent);
  displayFeatureInfo(pixel);
});

map.on('click', function(evt) {
  displayFeatureInfo(evt.pixel);
});

map.updateSize();


/***/ })

},[[264,0]]]);
//# sourceMappingURL=vector-layer.js.map