var urls="http://192.168.1.156:18005/images/image/patient/";     //156
var greyBaseMapAllChromosome = "http://192.168.1.156:8080/images/image/patient/";   //156
var nodeUrl = "http://192.168.1.156:18015";     //156

//var urls="http://192.168.80.64:18005/images/image/patient/";     //64
//var greyBaseMapAllChromosome = "http://192.168.80.64:8080/images/image/patient/"; 
//var nodeUrl = "http://192.168.80.64:18015";            //64




var dbName = 'wasm';
var dbVersion = 1;
var storeName = 'cut-wasm-cache';
function openDatabase() {
    return new Promise((resolve, reject) => {
      var request = indexedDB.open(dbName, dbVersion);
      request.onerror = reject.bind(null, 'Error opening wasm cache database');
      request.onsuccess = () => { resolve(request.result) };
      request.onupgradeneeded = event => {
        var db = request.result;
        if (db.objectStoreNames.contains(storeName)) {
            console.log(`Clearing out version ${event.oldVersion} wasm cache`);
            db.deleteObjectStore(storeName);
        }
        console.log(`Creating version ${event.newVersion} wasm cache`);
        db.createObjectStore(storeName)
      };
    });
  }