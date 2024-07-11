window.onload = function () {
  mapSetting();
}

var map = null;
var markerTypes = {
  '한식': 'blue_marker.png',
  '일식': 'red_marker.png',
  '분식': 'green_marker.png',
  '경양식': 'purple_marker.png'
}
var markList = [];

/**
 * getMyLocation에서 본인위치 먼저 받아오고 나서 실행되는 메서드
 */
async function mapSetting() {
  try {
    const location = await getMyLocation();
    myLat = location.myLat;
    myLon = location.myLon;
  } catch (error) {
    // reject에서 반환한 값을 처리
    myLat = "37.6043803"
    myLon = "127.0366509"
  } finally {
    var mapOptions = {
      center: new naver.maps.LatLng(myLat, myLon),
      zoomControl: true,
      zoomControlOptions: {
        style: naver.maps.ZoomControlStyle.SMALL,
        position: naver.maps.Position.TOP_RIGHT
      },
      zoom: 17
    };
    map = new naver.maps.Map('map', mapOptions);

    var mapType = ["NORMAL", "TERRAIN", "SATELLITE", "HYBRID"] //일반, 지형도, 위성도, 혼합

    map.setMapTypeId(naver.maps.MapTypeId[mapType[0]]); // 지도 유형 변경하기


    // naver.maps.Event.addListener(map, 'click', function(e) {
    //   myMark.setPosition(e.coord); <- 이 코드를 통해서 위치가 이동되는 듯 합니다. 아마 좌표값을 인자로 넣으면 변할듯
    // });

    setCustomLocationControl(map);
    markMyLocation(map);   //내 위치
    markSeSACLocation(map);//학원 위치
    markDefaultStore(map);  //가게 위치(default)
  }
}

function selectMapType(type){
  var mapType = ["NORMAL", "TERRAIN", "SATELLITE", "HYBRID"]

  map.setMapTypeId(naver.maps.MapTypeId[mapType[type]]);
}

function setCustomLocationControl(map){
  var locationBtnHtml = '<button class="btn_mylct"><span class="spr_trff spr_ico_mylct">새싹으로 돌아가기</span></a>';
  naver.maps.Event.once(map, 'init', function(){
    var customControl = new naver.maps.CustomControl(locationBtnHtml, {
      position: naver.maps.Position.TOP_LEFT
    });
    customControl.setMap(map);
    naver.maps.Event.addDOMListener(customControl.getElement(), 'click', function() {
      map.setCenter(new naver.maps.LatLng(37.6043803, 127.0366509));
  });
  });
}

/**
 * 본인의 현재 위치 반환
 * @returns 위치값 lat, lon으로 반환
 */
function getMyLocation() {
  var myLat, myLon;
  return new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        console.log('testing');
        // GeolocationPosition 객체에서 coords 속성을 통해 latitude와 longitude 값을 추출합니다.
        myLat = position.coords.latitude;
        myLon = position.coords.longitude;
        resolve({ myLat, myLon });
      },
      (error) => {
        switch (error.code) {
          case error.PERMISSION_DENIED:
            console.error("User denied the request for Geolocation.");
            break;
          case error.POSITION_UNAVAILABLE:
            console.error("Location information is unavailable.");
            break;
          case error.TIMEOUT:
            console.error("The request to get user location timed out.");
            break;
          case error.UNKNOWN_ERROR:
            console.error("An unknown error occurred.");
            break;
        }
        // myLat = "37.6043803"
        // myLon = "127.0366509"
        reject(error);
      },
      {
        // 옵션 (선택사항): 위치 정보의 정확도, 타임아웃 시간 등을 설정할 수 있습니다.
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
      }//설정값
    );
  });
}

/**
 * 현재 본인의 위치 표시
 * @param {*} map 
 */
function markMyLocation(map) {
  if(myLat != null){
    var myMark = new naver.maps.Marker({
      position: new naver.maps.LatLng(myLat, myLon),
      icon: {
        content: [
          `<div style="display: flex; flex-direction: column; align-items: center; width: 20px; height: 20px;">`,
          ` <div style="display: flex; justify-content: center; align-items: center; width: 20px; height: 20px;">`,
          ` <img src="../img/circle.svg" style="width: 20px; background-color:#0099ff; height: 20px; border-radius: 50%;"/>`, //이미지 위치 확인
          ` </div>`,
          `</div>`
        ].join(''),
        size: new naver.maps.Size(25, 25),
        scaledSize: new naver.maps.Size(25, 25),
        origin: new naver.maps.Point(0, 0)
      },
      map: map,
      zIndex:100
    });
  } 
}

/**
 * getStoreLocationList()에서 리스트를 가져온 후에 실행됩니다
 * @param {*} map 
 */
async function markDefaultStore(map) {
  var locList = await getStoreLocationList()
  
  removeMarkList(markList);
  
  for (item of locList) {
    var marker = new naver.maps.Marker({
      icon:{
        content: [
          `<div style="display: flex; flex-direction: column; align-items: center; width: 30px; height: 30px;">`,
          ` <div style="display: flex; justify-content: center; align-items: center; width: 30px; height: 30px;">`,
          ` <img src="/img/map_marker/${markerTypes[item.style]}" style="width: 30px; height: 30px;"/>`, //이미지 위치 확인
          ` </div>`,
          `</div>`
        ].join(''), //border-radius: 50%;"
        size: new naver.maps.Size(30, 30),
        scaledSize: new naver.maps.Size(30, 30),
        origin: new naver.maps.Point(0, 0)
      },
      position: new naver.maps.LatLng(item.lat, item.lon),
      map: map,
    });

    naver.maps.Event.addListener(marker, 'click', function(){
      location.href=`http://localhost:12571/store/info?id=${item.id}`;
    })

    markList.push(marker);
  }

  console.log(`markList: ${markList}`);
}

/**
 * 제거하는 방법이 이게 유일하다고 합니다 ㅠㅠ
 * @param {*} markList 
 */
function removeMarkList(markList) {
  if (markList.length > 0) {
    for (mark of markList) {
      mark.setMap(null);
    }
  }
}

/**
 * 청년취업사관학교(학원)의 위치를 마킹해줍니다
 * @param {*} map 
 */
function markSeSACLocation(map) {
  var sesacLocation = ["37.6043803", "127.0366509"]
  var SeSACMark = new naver.maps.Marker({
    position: new naver.maps.LatLng(sesacLocation[0], sesacLocation[1]),
    icon: {
      content: [
        `<div style="display: flex; flex-direction: column; align-items: center; width: 40px; height: 40px;">`,
        ` <div style="display: flex; justify-content: center; align-items: center; width: 40px; height: 40px;">`,
        ` <img src="/img/SeSAC.png" style="width: 40px; height: 40px; border-radius: 20%; background-color:#5D5D5D"/>`, //이미지 위치 확인
        ` </div>`,
        `</div>`
      ].join(''),
      size: new naver.maps.Size(30, 30),
      scaledSize: new naver.maps.Size(30, 30),
      origin: new naver.maps.Point(0, 0)
    },
    map: map,
    zIndex:1
  });
}

/**
 * 서버에서 Store 테이블에서 리스트를 가져옵니다
 * @returns Store 테이블 정보
 */
function getStoreLocationList() {
  return axios.get(`/store/api/list`)
    .then(response => {
      return response.data; // axios.get()에서 바로 response.data를 반환
    })
    .catch(error => {
      console.error('Error fetching store data:', error);
      throw error; // 에러 처리를 위해 throw error 추가
    });
}

function getStoreLocationListByStyle(style) {
  console.log(`/store/api/list?style=${style}`);
  return axios.get(`/store/api/list?style=${style}`)
    .then(response => {
      console.log(response.data);
      markStoreByStyle(response.data);
      //return response.data; // axios.get()에서 바로 response.data를 반환
    })
    .catch(error => {
      console.error('Error fetching store data:', error);
      throw error; // 에러 처리를 위해 throw error 추가
    });
}

async function markStoreByStyle(storeList) {
  removeMarkList(markList);
  for (item of storeList) {
    var marker = new naver.maps.Marker({
      icon:{
        content: [
          `<div style="display: flex; flex-direction: column; align-items: center; width: 30px; height: 30px;">`,
          ` <div style="display: flex; justify-content: center; align-items: center; width: 30px; height: 30px;">`,
          ` <img src="/img/map_marker/${markerTypes[item.style]}" style="width: 30px; height: 30px;"/>`, //이미지 위치 확인
          ` </div>`,
          `</div>`
        ].join(''), //border-radius: 50%;"
        size: new naver.maps.Size(30, 30),
        scaledSize: new naver.maps.Size(30, 30),
        origin: new naver.maps.Point(0, 0)
      },
      position: new naver.maps.LatLng(item.lat, item.lon),
      map: map
    });
    naver.maps.Event.addListener(marker, 'click', function(){
      location.href=`http://localhost:12571/store/info?id=${item.id}`;
    })
    markList.push(marker);
    console.log(item);
  }
}