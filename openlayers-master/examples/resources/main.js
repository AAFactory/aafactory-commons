let labelMap = {
  sgg: 'SIG_KOR_NM',
  hEmd: 'adm_nm',
  bEmd: 'EMD_KOR_NM',
  highway: 'ROAD_NAME',
	nationRoad: 'ROAD_NAME'
}

function toggleOptionsDiv() {
  $('#selectOpttions').toggle();
}

function fitboundsLayer(layerGroupIndex, index) {
	map.getView().fit(map.getLayers().item(layerGroupIndex).getLayers().item(index).getSource().getExtent(), map.getSize());
  //console.log(map.getLayers().item(element.value).get('name'));
  
  var targetLayer = map.getLayers().item(layerGroupIndex).getLayers().item(index).getSource().getFeatures()[0];
  if (getHighlight()) featureOverlay.getSource().removeFeature(getHighlight());
  featureOverlay.getSource().addFeature(targetLayer);
  setHighlight(targetLayer);
}

function toggleRoadLayer(index) {
	var map = window.map;
	map.getLayers().item(index).getLayers().forEach(function(layer, j) {
		layer.setVisible(!layer.getVisible());
  });
}

function toggleEMDLayer() {
	var label = document.getElementById('switchEMD').children[0].innerText;
	if (label == '행정동') {
		document.getElementById('switchEMD').children[0].innerText = '법정동';
	} else if (label == '법정동') {
		document.getElementById('switchEMD').children[0].innerText = '행정동';
	}
	switchEMDLayer();
}

function fitBounds(extent) {
  map.getView().fit(extent, map.getSize());
  setTimeout(function(){ determineAreaName(0 )}, 100);
}

const highwayArr = [];
function initHighway() {
	if (highwayArr.length == 0) {
		map.getLayers().item(4).getLayers().forEach(function(layer) {
      layer.getSource().getFeatures().forEach(function(feature) {
      	highwayArr.push(feature);
      });
    });
      
    $.each(highwayArr, function(idx, feature) {
    	$('#highwaySelect').append($('<option>', {
        value: idx,
        text: feature.get(labelMap.highway)
      }));
    }); 
    
    $('#highwaySelect').on('change', function() {
    	fitboundsLayer(4, $(this).val());
    });
  }
}

const nationRoadArr = [];
function initnationRoad() {
	if (nationRoadArr.length == 0) {
		map.getLayers().item(5).getLayers().forEach(function(layer) {
      layer.getSource().getFeatures().forEach(function(feature) {
      	nationRoadArr.push(feature);
      });
    });
      
    $.each(nationRoadArr, function(idx, feature) {
    	$('#nationRoadSelect').append($('<option>', {
        value: idx,
        text: feature.get(labelMap.nationRoad)
      }));
    }); 
    
    $('#nationRoadSelect').on('change', function() {
    	fitboundsLayer(5, $(this).val());
    });
  }
}

const sidoArr = [];
function initSido() {
	if (sidoArr.length == 0) {
    map.getLayers().item(0).getLayers().forEach(function(layer) {
    	//console.log(layer.getSource().getFeatures()[0]);
    	sidoArr.push(layer);
    });
    
    $.each(sidoArr, function(idx, layer) {
    	$('#sidoSelect').append($('<option>', {
        value: idx,
        text: layer.getSource().getFeatures()[0].get('area1')
      }));
    });
    
    $('#sidoSelect').on('change', function() {
    	fitBounds(sidoArr[$(this).val()].getSource().getExtent());
    	updateSgg();
    });
	}
}

const sggArr = [];
function initSgg() {
  if (sggArr.length == 0 && $('#sidoSelect').val()) {
  	updateEmd();
  	
  	var sidoCode = sidoArr[$('#sidoSelect').val()].getSource().getFeatures()[0].get('admcode').substring(0, 2);
    map.getLayers().item(1).getLayers().forEach(function(layer) {
      layer.getSource().getFeatures().forEach(function(feature) {
      	console.log(feature);
        //hEmdArr.push(feature.get('adm_nm'));
        if (feature.get('SIG_CD').indexOf(sidoCode) == 0) sggArr.push(feature);
      });
    });
      
    $.each(sggArr, function(idx, feature) {
    	$('#sggSelect').append($('<option>', {
        value: idx,
        text: feature.get(labelMap.sgg)
      }));
    }); 
    
    $('#sggSelect').on('change', function() {
      fitBounds(sggArr[$(this).val()].getGeometry().getExtent());
      updateEmd();
    });
  }
}

function updateSgg() {
  sggArr.length = 0;
  $('#sggSelect')[0].options.length = 1;
  initSgg();
}

function updateEmd() {
  emdArr.length = 0;
	$('#emdSelect')[0].options.length = 1;
	initEmd();
}

const emdArr = [];
function initEmd() {
	var itemIndex = $('#switchEMD span').html() == '행정동' ? 2 : 3;
	var labelName = $('#switchEMD span').html() == '행정동' ? labelMap.hEmd : labelMap.bEmd;
  if (sggArr.length > 0 && emdArr.length == 0) {
  	var sggCode = sggArr[$('#sggSelect').val()].get('SIG_CD');
  	console.log(sggCode);
    
    if (itemIndex == 2) {
    	map.getLayers().item(2).getSource().getFeatures().forEach(function(feature) {
    		emdArr.push(feature);
    	});
    } else if (itemIndex == 3) {
    	map.getLayers().item(3).getLayers().forEach(function(layer) {
    		layer.getSource().getFeatures().forEach(function(feature) {
      		if (feature.get("EMD_CD").indexOf(sggCode) == 0) emdArr.push(feature);
        });
    	});
    }
    
    $.each(emdArr, function(idx, feature) {
      $('#emdSelect').append($('<option>', {
        value: idx,
        text: feature.get(labelName)
      }));
    }); 
    
    $('#emdSelect').on('change', function() {
      map.getView().fit(emdArr[$(this).val()].getGeometry().getExtent(), map.getSize());
      fitBounds(emdArr[$(this).val()].getGeometry().getExtent());
    });
  }
}

function printSggName() {
	map.getLayers().item(1).getLayers().forEach(function(layer) { 
		layer.getSource().getFeatures().forEach(function(feature) { 
			console.log(feature.get('SIG_KOR_NM'))
		}) 
	});
}

function printHEmdName() {
	map.getLayers().item(2).getLayers().forEach(function(layer) { 
    layer.getSource().getFeatures().forEach(function(feature) { 
      console.log(feature.get('adm_cd'), feature.get('adm_nm'))
    }) 
  });
}

function printBEmdName() {
	map.getLayers().item(3).getLayers().forEach(function(layer) { 
    layer.getSource().getFeatures().forEach(function(feature) { 
      console.log(feature.get('EMD_CD'), feature.get('EMD_KOR_NM'))
    }) 
  });
}

function updateMapStatus(x, y, z) {
	$('#mapStatus').html(x.toFixed(2) + ' ' + y.toFixed(2) + ' ' + z.toFixed(2) + ' '+ window.innerWidth + 'x' + window.innerHeight);
}

function updateAreaName(nameMap) {
  $('#areaName').html(nameMap.sido + ' ' + nameMap.sgg + ' ' + nameMap.hemd + ' ' + nameMap.bemd + '<br />' + nameMap.roadName);
}

$(function() {
	$('.button').on('click', function() {
		if ($(this).hasClass('a')) {
			toggleOptionsDiv();
			initSido();
//			updateSgg();
//      updateEmd();
		} else if ($(this).hasClass('b')) {
			toggleRoadLabel();
		} else if ($(this).hasClass('c')) {
			updateSgg();
			updateEmd();
			initHighway();
			initnationRoad();
		} else if ($(this).hasClass('d')) {
			setPrecisionLayer(!getPrecisionLayer());
			toggleLayers(getPrecisionLayer(), 6);
		}
	});
	
	$('#compass img').on('click', function() {
		map.getView().setRotation(0);
	});
});