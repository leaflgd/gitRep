/*$(function() {
	$("#return").click(function() {
		window.location.href = "/chromosome/user/logname";
	});

	var btn = document.getElementById('register_btn');
	btn.onmousedown = function() {
		btn.style.background = "linear-gradient(#62d7fb, #20b4ef,#00a9ea)"
	}
	btn.onmouseup = function() {
		btn.style.background = "linear-gradient(#2ec0fb, #0e9aee,#0091ea)"
	}
	var info = document.getElementById('info');
	var user = document.getElementById('username');
	var pas = document.getElementById('password');
	var pas2 = document.getElementById('password2');
	/* 判断用户名格式是否正确 */
/*user.onblur = function() {
 var username = document.getElementById('username').value;
 var rel = /^w{3,9}$/;
 var correct = rel.test(username);
 if (!correct) {
 info.style.display = 'block';
 info.innerHTML = '请输入数字、英文字符或下划线';
 } else {
 info.style.display = 'none';
 info.innerHTML = '';
 }
 }
 pas.onblur = function() {
 var password = document.getElementById('password').value;
 var rel = /^w{6,16}$/;
 var correct = rel.test(password);
 if (!correct) {
 info.style.display = 'block';
 info.innerHTML = '您输入的密码格式不正确';
 } else {
 info.style.display = 'none';
 info.innerHTML = '';
 }
 }
 pas2.oninput = function() {
 var password = pas.value;
 var password2 = pas2.value;
 var sub = document.getElementById('register_btn');
 if (password2 !== password) {
 info.style.display = 'block';
 info.innerHTML = '您两次输入的密码不同';
 sub.setAttribute('disabled','disabled');
 } else {
 sub.removeAttribute('disabled');
 info.style.display = 'none';
 info.innerHTML = '';
 }
 }
 });
 */
var info = document.getElementById('info');
function checkUser() {
	var username = document.getElementById("username").value;
	var trueName = document.getElementById("userName").value;
	var pas = document.getElementById("password").value;
	var pas2 = document.getElementById('password2').value;
	;
	var rel = /^[0-9a-zA-Z_]{3,9}$/;
	var correct = rel.test(username);
	var reg = /^[0-9a-zA-Z_]{6,16}$/;
	var corrOne = reg.test(pas);
	var corrTwo = reg.test(pas2);

	if (!username) {
		info.innerHTML = '用户名不能为空!'
		return false;
	} else if (!correct) {
		info.innerHTML = '用户名格式不正确!'
		return false;
	} else if (username.length < 3) {
		info.innerHTML = '用户名格式不正确!'
		return false;
	} else if (username.length > 16) {
		info.innerHTML = '用户名格式不正确!'
		return false;
	} else {
		info.innerHTML = ''
	}
	;
	if (!trueName) {
		info.innerHTML = '真实姓名不能为空!'
		return false;
	}

	if (!corrOne) {
		info.innerHTML = '密码不能为空!'
		return false;
	} else if (pas.length < 6) {
		info.innerHTML = '密码格式不正确!'
		return false;
	} else if (pas.length > 16) {
		info.innerHTML = '密码格式不正确!'
		return false;
	} else {
		info.innerHTML = ''
	}
	if (!corrTwo) {
		info.innerHTML = '第二次输入的密码不能为空!'
		return false;
	} else if (pas != pas2) {
		info.innerHTML = '两次输入的密码不正确!'
		return false;
	} else {
		info.innerHTML = ''
	}

	document.getElementById("formid").submit();
}

function checkName() {
	
	// ** JavaScript中值识别'' 而不能识别""
	var u1 = $('#username').val();
	$.ajax({
		type : 'post',
		data : {'username':u1},
		dataType : 'json',
		url : '/chromosome/user/check',
		
		success : function(data) {
			console.log(data);
			if(data == '3'){
				$("#unameSpan").text("用户名不能为空！");
				$("#unameSpan").css("color","red");
			}else if(data=='2'){
				$("#unameSpan").text("用户名已存在！");
				$("#unameSpan").css("color","red");
			}else if(data=='1'){
				$("#unameSpan").text("用户名可以使用！");
				$("#unameSpan").css("color","green");
			}
		
		},
		error:function(msg){
			alert(msg.status+"---"+msg.statusText);
		}

	});

}