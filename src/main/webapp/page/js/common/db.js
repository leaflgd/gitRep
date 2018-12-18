function opendb(databaseName, version,cb) {
	var indexedDB = window.indexedDB || window.webkitIndexedDB || window.mozIndexedDB || window.OIndexedDB || window.msIndexedDB,
    IDBTransaction = window.IDBTransaction || window.webkitIDBTransaction || window.OIDBTransaction || window.msIDBTransaction;
	var request = indexedDB.open(databaseName, version);
	var db;
	let cbCount = 0;
	request.onerror = function(event) {
		console.log('数据库打开报错');
		cb("数据库打开报错",null);
	};
	request.onsuccess = function(event) {
		db = request.result;
		console.log('数据库打开成功');
		if(cbCount > 0){
			return;
		}
		cb(null,db);
		cbCount++;
	};
	
	request.onupgradeneeded = function(event) {
		db = event.target.result;
		var objectStore;
		if (!db.objectStoreNames.contains('chromosome')) {
			objectStore = db.createObjectStore('chromosome', {
				keyPath : 'id'
			});
		    objectStore.createIndex('grayId', 'grayId', {unique: false});

		}
		getdb(function(err,db){
			cb(null,db);
		})
		
		cbCount++;
	}
}

function getImageFile(id,url,db) {
    // Create XHR
    var xhr = new XMLHttpRequest(),buffer;
    xhr.cid = id;
    xhr.curl = url;
    xhr.open("GET", url, true);
    // Set the responseType to blob
    xhr.responseType = "arraybuffer";

    xhr.addEventListener("load", function () {
        if (xhr.status === 200) {
            
            // Blob as response
            buffer = xhr.response;

            // Put the received blob into IndexedDB
            let imageInfo = {
            	id: this.cid,
            	url:this.curl,
            	blob: buffer
            }
            putChromosomeInDb(db,imageInfo);
        }
    }, false);
    // Send XHR
    xhr.send();
};

function getImageObj(id,grayId,url,db) {
	var canvas = document.getElementById('enhanceIn');
	var context = canvas.getContext('2d');
	let img = new Image();
	img.crossOrigin = '';
	img.src = url;
	img.id = id;
	img.onload = function(){
		context.drawImage(img, 0, 0, this.width, this.height);
		imageData = context.getImageData(0, 0, this.width, this.height).data;
		 let imageInfo = {
        	id: this.id,
        	grayId:grayId,
        	imageData: imageData,
        	width: this.width,
        	height: this.height
	     }
	     putChromosomeInDb(db,imageInfo);
	}
}

function putChromosomeInDb(db,imgInfo) {	
	var request = db.transaction([ 'chromosome' ], 'readwrite').objectStore('chromosome').put(imgInfo);

	request.onsuccess = function(event) {
		// console.log('数据写入成功');
	};

	request.onerror = function(event) {
		console.log('数据写入失败');
	}
}

function read(id,db,cb) {
	var transaction = db.transaction([ 'chromosome' ]);
	var objectStore = transaction.objectStore('chromosome');
	var request = objectStore.get(id +"");

	request.onerror = function(event) {
		console.log('事务失败');
		cb('事务失败',null);
	};

	request.onsuccess = function(event) {
		cb(null,request.result);
	};
}


function readAll(db) {
	var objectStore = db.transaction('chromosome').objectStore('chromosome');

	objectStore.openCursor().onsuccess = function(event) {
		var cursor = event.target.result;

		if (cursor) {
			console.log('Id: ' + cursor.key);
			console.log('Name: ' + cursor.value.name);
			console.log('Age: ' + cursor.value.age);
			console.log('Email: ' + cursor.value.email);
			cursor.continue();
		} else {
			console.log('没有更多数据了！');
		}
	};
}

function update() {
	var request = db.transaction([ 'chromosome' ], 'readwrite').objectStore('chromosome').put({
		id : 1,
		name : '李四',
		age : 35,
		email : 'lisi@example.com'
	});

	request.onsuccess = function(event) {
		console.log('数据更新成功');
	};

	request.onerror = function(event) {
		console.log('数据更新失败');
	}
}


function remove() {
	  var request = db.transaction(['chromosome'], 'readwrite')
	    .objectStore('chromosome')
	    .delete(1);

	  request.onsuccess = function (event) {
	    console.log('数据删除成功');
	  };
	}
function getdb(cb){
	return opendb("chromodb",1.0,cb);
}
