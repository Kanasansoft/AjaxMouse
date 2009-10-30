var x=0;
var y=0;
var wheel=0;

function submit(params){
	var xhr=new XMLHttpRequest();
	ary=[];
	for(var key in params){
		ary.push(encodeURIComponent(key)+"="+encodeURIComponent(params[key]));
	}
	xhr.open("GET", "/service?"+ary.join("&"));
/*
	xhr.onreadystatechange=function(){
		if(xhr.readyState==4&&xhr.status==200) {
			document.getElementById("status").textContent=xhr.responseText;
		}
	}
*/
	xhr.send(null);
}

function handler(e){
	e.preventDefault();
}

function padHandler(e){
	switch(e.type){
	case "touchstart":
		if(e.touches.length!=1){return;}
		x=event.touches[0].pageX;
		y=event.touches[0].pageY;
		break;
	case "touchmove":
		if(e.touches.length!=1){return;}
		submit({
			"eventtype":"mousemove",
			"x":(event.touches[0].pageX-x).toString(10),
			"y":(event.touches[0].pageY-y).toString(10)
		});
		x=event.touches[0].pageX;
		y=event.touches[0].pageY;
		break;
	case "touchend":
		break;
	}
}

function leftHandler(e){
	mouseButtonHandler(e,"left");
}

function rightHandler(e){
	mouseButtonHandler(e,"right");
}

function mouseButtonHandler(e,buttonType){
	switch(e.type){
	case "touchstart":
		if(e.touches.length!=1){return;}
		submit({"eventtype":"mousedown","buttontype":buttonType});
		break;
	case "touchmove":
		break;
	case "touchend":
		if(e.touches.length!=0){return;}
		submit({"eventtype":"mouseup","buttontype":buttonType});
		break;
	}
}

function wheelHandler(e){
	switch(e.type){
	case "touchstart":
		if(e.touches.length!=1){return;}
		wheel=event.touches[0].pageY;
		break;
	case "touchmove":
		if(e.touches.length!=1){return;}
		submit({
			"eventtype":"mousewheel",
			"wheel":(event.touches[0].pageY-wheel).toString(10)
		});
		wheel=event.touches[0].pageY;
		break;
	case "touchend":
		break;
	}
}

function initialize(){
	submit({"eventtype":"start"});
	["touchstart","touchmove","touchend","gesturestart","gesturechange","gestureend"].forEach(
		function(eventName){
			document.addEventListener(eventName,handler,false);
			document.getElementById("pad").addEventListener(eventName,padHandler,false);
			document.getElementById("left").addEventListener(eventName,leftHandler,false);
			document.getElementById("right").addEventListener(eventName,rightHandler,false);
			document.getElementById("wheel").addEventListener(eventName,wheelHandler,false);
		}
	);
}

window.addEventListener("load",initialize,true);
